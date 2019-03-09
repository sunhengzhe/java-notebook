# 泛型 Generics

泛型可以提高代码的可读性和鲁棒性。泛型使得编译器可以对类型进行更全面的检查，只在编译期有效。

```java
// 不使用泛型的情况
List myIntList = new LinkedList(); // 1
myIntList.add(new Integer(0)); // 2
Integer x = (Integer) myIntList.iterator().next(); // 3


// 使用泛型的情况，第三步可以省略类型转化
List<Integer> myIntList = new LinkedList<Integer>(); // 1'
myIntList.add(new Integer(0)); // 2'
Integer x = myIntList.iterator().next(); // 3'
```
## 定义简单的泛型

`java.util` 的 `List` 和 `Iterator` 的简化版如下：

```java
public interface List <E> {
    void add(E x);
    Iterator<E> iterator();
}

public interface Iterator<E> {
    E next();
    boolean hasNext();
}
```

这些尖括号就是泛型的内容，它们也称为 `List` 和 `Iterator` 的形式类型参数（The formal type parameters）。
在上面的调用例子上可以看到，声明时使用具体的类型（称为参数化类型 parameterized type）来调用，所有的 `E` 都会被替换为实际的参数（这个例子中是 Integer）。

一般建议给形式类型参数使用简练的名称命令（一般使用单个大写字母，如 `E`）。

## 泛型和子类型

```java
List<String> ls = new ArrayList<String>(); // 1
List<Object> lo = ls; // 2 
```

虽然 String 是 Object 的子类，但是上面代码的第二行是不被允许的。

因为如果允许泛型的改变，那么可能会出现这种情况：

```java
lo.add(new Object()); // 3
String s = ls.get(0); // 4: Attempts to assign an Object to a String!
```

若 `lo` 添加了 Object 进去，因为 ls 和 lo 指向的是同一个内存地址，那么 ls 里包含的就不仅仅是 String 类型的了。

所以，一般地，如果 `Foo` 是 `Bar` 的子类型（子类或子接口），G 是一种类属性声明，那么这 **不** 意味着 G<Foo> 是 G<Bar> 的子类型。

## 通配符

可以使用 `?` 代表参数匹配所有类型，如：

```java
public void printCollection(Collection<?> c) {
    for (Object e : c) {
        System.out.println(e);
    }
}
```

这样可以传入包含不同类型的 Collection：

```java
ArrayList<String> strings = new ArrayList<>();
strings.add("aaa");
strings.add("bb");
strings.add("cc");
Demo.printCollection(strings);

ArrayList<Integer> integers = new ArrayList<>();
integers.add(1);
integers.add(2);
integers.add(3);
Demo.printCollection(integers);
```

## 带限制的通配符

可以限制通配符继承于某个类：

```java
public static void printPersons(Collection<? extends Person> c) {
    for (Person e : c) {
        e.say();
    }
}
```

## 泛型方法

假设现在需要提供一个方法，接受一个对象数组和一个集合，这个方法会将对象数组所有对象添加到集合中。

```java
static void fromArrayToCollection(Object[] a, Collection<?> c) {
    for (Object o : a) { 
        c.add(o); // compile-time error
    }
}
```

上面的方法是不对的，不能把一个 Object 添加到 ? 中。这时候需要使用泛型方法：

```java
static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
    for (T o : a) { 
        c.add(o);
    }
}
```
