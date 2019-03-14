# Lambda Expressions

在 java 8 以上引入了 **函数式接口**（functional interface）的概念，指只有一个抽象方法的接口，如：

```java
interface CheckPerson {
    boolean test(Person p);
}
```

lambda 表达式可以简写函数式接口，CheckPerson 使用 lambda 表示如下：

```java
CheckPerson checkPerson = (Person p) -> p.getAge > 10;
```

因为 checkPerson 这个方法接受参数为 Person，所以可以基于类型推断，省略参数的类型：

```java
CheckPerson checkPerson = p -> p.getAge > 10;
```

像这种接口足够简单也很常用，JDK 已经帮我们定义了，类似的接口都在 `java.util.function` 中。

比如像上面的例子，包含 test 方法的接口，可以使用 `Predicate<T>` 代替。

```java
interface Predicate<T> {
    boolean test(T t);
}
```

## 方法引用 Method References

上面使用 lambda 表达式创建匿名函数，也可以使用 lambda 表达式调用一个具名函数。

假设有 Person 类如下：

```java
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public int getAge() {
        // ...
    }
    
    public Calendar getBirthday() {
        return birthday;
    }    

    public static int compareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }
}
```

现在有一个 Person 的数组需要按年龄进行排序：

```java
Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);

class PersonAgeComparator implements Comparator<Person> {
    public int compare(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}
        
Arrays.sort(rosterAsArray, new PersonAgeComparator());
```

sort 的签名如下：

```java
static <T> void sort(T[] a, Comparator<? super T> c)
```

注意 `Comparator` 是一个函数式接口，所以这里可以直接使用 lambda 表达式，而不用创建一个 Comparator 的实现类。

```java
Arrays.sort(rosterAsArray,
    (Person a, Person b) -> {
        return a.getBirthday().compareTo(b.getBirthday());
    }
);
```

不过，比较方法在 `Person.compareByAge` 上已经实现了，所以在 lambda 表达式内可以直接调用他：

```java
Arrays.sort(rosterAsArray,
    (a, b) -> Person.compareByAge(a, b)
);
```

因为这个 lambda 表达式调用的是一个具名函数，所以可以使用方法引用来简写：

```java
Arrays.sort(rosterAsArray, Person::compareByAge);
```

方法引用 `Person::compareByAge` 在语义上等同于 lambda 表达式 `(a, b) -> Person.compareByAge(a, b)`，他们有两个特征：

- 他们的形参都是 `Comparator<Person>.compare` 的复制，也就是 `(Person, Person)`
- body 里都调用了 `Person.compareByAge`

### 方法引用的类型

方法引用有四种类型：

| Kind | Example |
| --- | --- |
|Reference to a static method|	ContainingClass::staticMethodName|
|Reference to an instance method of a particular object |	containingObject::instanceMethodName|
|Reference to an instance method of an arbitrary object of a particular type	| ContainingType::methodName|
|Reference to a constructor |	ClassName::new|

#### 类::静态方法引用

`Person::compareByAge` 就是一个静态方法的引用。

#### 实例::实例方法引用 

下面是一个例子：

```java
class ComparisonProvider {
    public int compareByName(Person a, Person b) {
        return a.getName().compareTo(b.getName());
    }
        
    public int compareByAge(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}

ComparisonProvider myComparisonProvider = new ComparisonProvider();

Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
```

JRE 会推断出方法的参数类型，也就是 `(Person, Person)`。

#### 类::实例方法引用

即使用类来引用实例方法：`Class::instanceMethod`，对于这种情况，第一个参数会成为方法的目标，比如 `String::compareToIgnoreCase` 等同于 `(x, y) -> x.compareToIgnoreCase(y)`

下面是一个例子：

```java
String[] stringArray = { "Barbara", "James", "Mary", "John",
    "Patricia", "Robert", "Michael", "Linda" };
Arrays.sort(stringArray, String::compareToIgnoreCase);
```

#### 构造函数的引用

```java
public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST transferElements(
        SOURCE sourceCollection,
        Supplier<DEST> collectionFactory
) {
        
        DEST result = collectionFactory.get();
        for (T t : sourceCollection) {
            result.add(t);
        }
        return result;
}
```

Supplier 是一个函数式接口，它包含一个不接受参数返回一个对象的方法。所以可以这么调：

```java
Set<Person> rosterSetLambda =
    transferElements(roster, () -> { return new HashSet<>(); });
```

你可以使用构造函数的引用来替换这个地方：

```java
Set<Person> rosterSet = transferElements(roster, HashSet::new);
```

Java 编译器会推断你想创建一个 HashSet 的集合并且只包含 Person，所以你也可以这么调：

```java
Set<Person> rosterSet = transferElements(roster, HashSet<Person>::new);
```

### this 和 super

方法引用中还可以使用 this 和 super。如 `this::equals` 等同于 `x -> this.equals(x)`

## 闭包

### 变量作用域

通常可能希望在 lambda 表达式中访问外围方法或类中的变量。

```java
public static boolean checkPersonIfAgeBiggerThan(Person p, int age) {
    CheckPerson checkPerson = person -> {
        if (age < 0) {
            throw new Error();
        }

        return person.getAge() > age;
    };

    return checkPerson.test(p);
}
```

在上面的 lambda 表达式中，使用了 age 这个参数。age 称为 lambda 表达式的自由变量，指非参数而且不在代码中定义的变量，我们说这个变量被 lambda 表达式捕获（captured）了。

但也有限制，不能引用值会改变的变量，如：

```java
public static void countDown(int start, int delay) {
    ActionListener listener = event -> {
        start--; // Error: Can't mutate captured variable
        System.out.println(start);
    };
    new Timer(delay, listener).start();
}
```

限制的原因主要是考虑到并发的安全，另外引用外部改变的变量也是不合法的：

```java
public static void repeat(String text, int count) {
    for (int i = 1; i < count; i++) {
        ActionListener listener = event -> {
            System.out.println(i + ": " + text); // Error: Cannot refer to changing i
        };
        new Timer(delay, listener).start();
    }
}
```

实际上这里有一条规则：lambda 表达式中捕获的变量必须实际上是最终变量（effectively final），实际上的最终变量是初始化之后就不会再为它赋新值。

lambda 表达式的体与嵌套块有相同的作用域。这同样适用于命名冲突和屏蔽的有关规则。

### this

在 lambda 表达式中使用 this 关键字，指的是创建这个 lambda 表达式的方法的 this 参数：

```java
public class Application {
    public void init() {
        ActionListener listener = event -> {
            System.out.println(this.toString());
        };
    }
}
```

上面的 toString 会调用 Application 的 toString 方法，而不是 listener。
