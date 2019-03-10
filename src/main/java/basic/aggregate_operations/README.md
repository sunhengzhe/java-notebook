# 聚合操作 Aggregate Operations

使用 for-each 循环遍历打印集合所有元素为：

```java
for (Person p : roster) {
    System.out.println(p.getName());
}
```

使用聚合操作的 forEach 如下：

```java
roster
    .stream()
    .forEach(e -> System.out.println(e.getName());
```

## Pipeline 和 Stream

一系列聚合操作称为一条 Pipeline，如下面用 filter 和 forEach 组成的 pipeline：

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

## 聚合操作和迭代器的区别

聚合操作比如 forEach，看起来很像迭代器，但是它们有着根本性的差异：

- 聚合操作使用内部迭代：聚合操作没有像 next 之类的方法来指示下一个迭代哪个元素，你的应用决定了迭代哪些元素而 JDK 来决定怎么迭代。使用迭代器，你可以决定迭代哪些和如何迭代，但只能按顺序迭代。内部迭代器就没有这个限制了，它可以支持并行处理。
- 聚合操作处理的是 Stream 里的元素：而不是直接处理的集合，因此也被称为流操作（Stream operations）。
- 聚合操作支持使用行为作为参数：大部分聚合操作里，你可以使用 lambda 表达式作为参数，这样能控制特定聚合操作的行为。

## Reduction

JDK 包含许多终止操作(如 `average`, `sum`, `min`, `max`, and `count`)，将流里面的所有内容合并为一个值。这些操作称为 Reduction Operations。
JDK 也包含一些返回集合而不是单个值的 Reduction 操作，

一般来说每个 Reduction 操作都是用来完成某一类特殊的任务的，比如计算平均值的 average。不过 JDK 还提供两个通用目的的方法：`reduce` 和 `collect`。

### reduce

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

### collect


