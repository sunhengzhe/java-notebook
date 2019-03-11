# 聚合运算 Aggregate Operations

使用 for-each 循环遍历打印集合所有元素为：

```java
for (Person p : roster) {
    System.out.println(p.getName());
}
```

使用聚合运算的 forEach 如下：

```java
roster
    .stream()
    .forEach(e -> System.out.println(e.getName());
```

## Pipeline 和 Stream

一系列聚合运算称为一条 Pipeline，如下面用 filter 和 forEach 组成的 pipeline：

```java
roster
    .stream()
    .filter(e -> e.getGender() == Person.Sex.MALE)
    .forEach(e -> System.out.println(e.getName()));
```

对比 for-each 的实现：

```java
for (Person p : roster) {
    if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
    }
}
```

一条 pipeline 包含以下内容：

- 一个源，比如集合、数组等等。
- 一个或多个中间操作（intermediate operations），中间操作比如 filter，产生新的 stream
- 一个终止操作（terminal operation），终止操作比如 forEach，生产非 Stream 的结果，比如基本数据类型、集合，或像 forEach 一样什么也不返回，

下面的例子计算平均值：

```java
double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```

`mapToInt` 操作返回一个 `IntStream` 类型的新 Stream（只包含 int 的 Stream），

`average` 操作计算 `IntStream` 包含元素的平均值，返回一个 `OptionalDouble` 类型的对象。如果流里面没有任何元素，则返回 OptionalDouble 的空实例，这时接着调用 `getAsDouble` 就会抛出 `NoSuchElementException` 异常。

JDK 里包含很多像 average 这样的方法，它们将 stream 里面的数据合并为一个值，这类方法称为 **Reduction operations**。

## 聚合运算和迭代器的区别

聚合运算比如 forEach，看起来很像迭代器，但是它们有着根本性的差异：

- 聚合运算使用内部迭代：聚合运算没有像 next 之类的方法来指示下一个迭代哪个元素，你的应用决定了迭代哪些元素而 JDK 来决定怎么迭代。使用迭代器，你可以决定迭代哪些和如何迭代，但只能按顺序迭代。内部迭代器就没有这个限制了，它可以支持并行处理。
- 聚合运算处理的是 Stream 里的元素：而不是直接处理的集合，因此也被称为流操作（Stream operations）。
- 聚合运算支持使用行为作为参数：大部分聚合运算里，你可以使用 lambda 表达式作为参数，这样能控制特定聚合运算的行为。

## Reduction

JDK 包含许多终止操作(如 `average`, `sum`, `min`, `max`, and `count`)，将流里面的所有内容合并为一个值。这些操作称为 Reduction Operations。
JDK 也包含一些返回集合而不是单个值的 Reduction 操作，

一般来说每个 Reduction 操作都是用来完成某一类特殊的任务的，比如计算平均值的 average。不过 JDK 还提供两个通用目的的方法：`reduce` 和 `collect`。

### Stream.reduce

和 javascript 的 reduce 比较像，求和的计算可以使用 sum：

```java
Integer totalAge = roster
    .stream()
    .mapToInt(Person::getAge)
    .sum();
``` 

但如果使用 `Stream.reduce` 如下：

```java
Integer totalAgeReduce = roster
   .stream()
   .map(Person::getAge)
   .reduce(
       0,
       (a, b) -> a + b);
```

reduce 方法接收两个参数：

- `identity`：既是初始值，也是当 stream 没有内容时的默认值
- `accumulator`：这个方法接收两个参数，一个是 reduction 过程中的累计值，一个是下一个元素的值。它返回的是新的累计值。

reduction 方法总是返回一个新的值。其实 accumulator 函数也是每次返回一个新值，这意味着如果你需要在 reduction 过程中添加新的元素，将在每次添加完后都创建一个新的集合，这是低效的。这时候应该使用 `collect` 方法。

### Stream.collect

与在处理过程中总是创建新值的 reduce 不同的是，collect 方法修改、改变已有的值。

Consider how to find the average of values in a stream. You require two pieces of data: the total number of values and the sum of those values. However, like the reduce method and all other reduction methods, the collect method returns only one value. You can create a new data type that contains member variables that keep track of the total number of values and the sum of those values, such as the following class, Averager:

考虑如何求流里面值的平均数。你需要两种数据：总数和个数。然而和 reduce 以及其他 reduction 方法一样，collect 方法只返回一个值，你可以创建一个新类型，包含你想追踪的数据比如总数和个数。如下面的 Averager：

```java
class Averager {
    private int total = 0;
    private int count = 0;
        
    public double average() {
        return count > 0 ? ((double) total)/count : 0;
    }
        
    public void accept(int i) { total += i; count++; }
    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}
```

下面的 pipeline 使用 Averager 类和集合方法计算所有男性成员的平均年龄：

```java
Averager averageCollect = roster.stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(Person::getAge)
    .collect(Averager::new, Averager::accept, Averager::combine);
                   
System.out.println("Average age of male members: " +
    averageCollect.average());
```

在这个例子中 collect 操作接受三个参数：

- 供应者 supplier: 这是一个工厂方法，构造一个新的实例。对于集合操作来说，它创建一个结果容器（result container）的实例。在这个例子里面，它创建了一个 Averager 类的新实例。
- 累加器 accumulator: 累加器函数将 stream 里的元素合并到结果容器中。在这个例子中，它通过给 count 变量加一和把 stream 中元素的值添加到成员变量 total 中来修改 Averager 结果容器。
- 组合器 combiner: 组合器函数接受两个结果容器并合并他们的内容。在这个例子中，就是 combine 方法里对 total 和 count 做的事情。

注意：

- 供应者是一个 lambda 表达式（或方法引用）而不是像 reduce 操作中的一个单位元素。
- 累加器和组合器不返回任何值。
- 你可以使用 collect 方法处理并行流。

虽然 JDK 给你提供了 average 运算来计算一个流里面元素的平均值，但你可以使用 collect 运算和一个自定义类来计算流里面元素的多个值。

collect 运算最适合集合了。下面的例子使用 collect 运算将男性成员的名字添加到一个集合中：

```java
List<String> namesOfMaleMembersCollect = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .map(p -> p.getName())
    .collect(Collectors.toList());
```

这个版本的 collect 运算接受一个 `Collector` 类型的参数，这个类封装了三个函数，也就是 collect 运算需要的三个参数（supplier, accumulator 和 combiner 函数）。

`Collectors` 类包含很多有用的 reduction 运算，比如累加元素到一个集合中和根据不同的要求汇总元素。这些 reduction 运算返回一个 `Collector` 类的实例，所以你可以作为 collect 运算的参数使用它们。

下面的例子使用 `Collectors.toList` 运算，它将流里面的元素累积到一个 List 的实例中。和 Collectors 类中的大多数运算一样，`toList` 运算返回一个 Collector 的实例，而不是 Collection。

下面的例子将 roster 集合的成员根据性别进行分组：

```java
Map<Person.Sex, List<Person>> byGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(Person::getGender));
```

`groupingBy` 运算返回一个 Map，key 是 lambda 表达式接受的参数返回的值（这个函数也成为分类函数 classification function）。
在这个例子中，返回的 Map 的 key 有两个值，`Person.Sex.MALE` 和 `Person.Sex.FEMALE`。它们对应的值就是流里面包含的对应性别的元素，它们会被分类函数处理，根据返回的值对应哪个 key 决定放在哪个 List 实例中。

下面这个例子检索集合中每个成员的 name 并根据 gender 对他们分类：

```java
Map<Person.Sex, List<String>> namesByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.mapping(
                    Person::getName,
                    Collectors.toList())));
```

这次的 `groupingBy` 运算接受两个参数，一个分类函数，一个 Collector 实例。这个 Collector 参数称为下游函数（downstream collector）。
Java 运行时会将其他 collector 的结果传递到这个 collector。
在这个例子里，groupingBy 运算返回的 List 再次被提交到 collect 方法，也就是 `mapping` 运算，mapping 运算接受 `Person::getName` 然后返回只包含 name 的流。

一个包含多个下游函数的 pipeline 称为 multilevel reduction。

下面例子检索每种性别成员的年龄总和：

```java
Map<Person.Sex, Integer> totalAgeByGender =
    roster
        .stream()
        .collect(
            Collectors.groupingBy(
                Person::getGender,                      
                Collectors.reducing(
                    0,
                    Person::getAge,
                    Integer::sum)));

```

`reducing` 运算接受三个参数：

- identity: 和 reduce 类似，是默认值也是初始值 
- mapper: reducing 运算会对所有元素执行此函数
- operation: 对所有 mapper 过元素进行 reduce
  
下面例子检索每种性别成员的平均年龄：

```java
Map<Person.Sex, Double> averageAgeByGender = roster
    .stream()
    .collect(
        Collectors.groupingBy(
            Person::getGender,                      
            Collectors.averagingInt(Person::getAge)));
```
