# 集合 Collection

集合的核心接口是 Java 集合框架的基础。

![The core collection interfaces.](https://docs.oracle.com/javase/tutorial/figures/collections/colls-coreInterfaces.gif)

所有的集合接口都是泛型的，如 Collection 的定义：

```java
public interface Collection<E>...
```

当声明一个集合时，你应该指定集合内元素的类型，这样编译器就能在编译期检查添加到集合的元素的正确性，以减少在运行时的异常。

集合的核心接口概览如下：

- Collection - 集合层次结构的顶级类。一个集合代表一组对象的集合，它们称为集合的元素（element）。Collection 接口是所有集合实现的最小公分母，Java 没有提供 Collection 的直接实现，而是分为了 Set 和 List等。
- Set - 不包含重复元素的集合。
- List - 有序的集合（有时也成为序列）。List 可以包含重复元素。
- Queue - 一般用于处理的元素具有优先级。除了优先队列，一般队列都是按先进先出排列元素。
- Deque - 一般用于处理的元素具有优先级。Deque 可以在两端处理元素，也就是说可以先进先出，也可以后进先出。
- Map - 一种映射键值对的对象。一个 Map 不能有重复的 key，每个 key 最多映射到一个 value。

还有两个接口只是 Set 和 Map 的有序版本：

- SortedSet - 一个按升序排列的 Set。
- SortedMap - 一个维护按升序排列的键的 Map。

## Collection 接口

Collection 接口包含一些基本操作，比如 `int size()`、`boolean isEmpty()`、`boolean contains(Object element)`、`boolean add(E element)`、`boolean remove(Object element)` 和 `Iterator<E> iterator()`。

还有一些操作整个 Collection 的方法，比如 `boolean containsAll(Collection<?> c)`, `boolean addAll(Collection<? extends E> c)`, `boolean removeAll(Collection<?> c)`, `boolean retainAll(Collection<?> c)` 和 `void clear()`。

还有一些额外的数组操作方法，如 `Object[] toArray()` 和 `<T> T[] toArray(T[] a)`。

在 JDK 8 以上的版本，Collection 接口还暴露了两个方法 `Stream<E> stream()` 和 `Stream<E> parallelStream()`，可以用来获取顺序或并行的流处理。

### 操作集合

操作集合有三种方式：1）聚合方法；2）for-each；3）迭代器。

#### 聚合方法

在 JDK 8 以上版本，遍历集合的首选方法是获取流然后执行 **聚合方法**（Aggregate Operations ）。聚合操作往往与 lambda 表达式一起使用，使编程更富有表现力也用更少的代码。

如下面的代码按顺序打印红色的 Shape：

```java
myShapesCollection.stream()
.filter(e -> e.getColor() == Color.RED)
.forEach(e -> System.out.println(e.getName()));
```

另外当集合比较大而且电脑有够多的 CPU 的话，可以使用并行流：

```java
myShapesCollection.parallelStream()
.filter(e -> e.getColor() == Color.RED)
.forEach(e -> System.out.println(e.getName()));
```

要将一个集合的元素转换为字符串对象，然后用逗号分隔符拼接：

```java
String joined = elements.stream()
    .map(Object::toString)
    .collect(Collectors.joining(", "));
```

又如计算所有员工的工资：

```java
int total = employees.stream()
.collect(Collectors.summingInt(Employee::getSalary)));
```

JDK 8 新引入的聚合方法和 Collection 之前的方法相比最大的不同就是，Collection 的旧方法都是可变的（mutative），意味着会操作原始集合内的数据，但聚合方法不会。

#### For-each

```java
for (Object o : collection)
    System.out.println(o);
```

#### 迭代器

迭代器是一个对象，使得能够遍历一个集合，并在有需要的时候有选择地从集合中删除元素。可以通过调用集合的 `iterator` 方法获取集合的 `Iterator` 对象。

下面是 Iterator 接口：

```java
public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove(); //optional
}
```

当集合还有元素时，`hasNext` 方法返回 true，并可以通过 `next` 方法返回下一个元素。`remove` 方法返回 `next` 方法之前返回的最后一个元素。每调用 `next` 方法一次可能只会调用一次 `remove`，如果违反了这个规则会抛出一个异常。

注意 `remove` 方法是在集合迭代时操作集合的唯一安全的方法。

在以下情况下使用 Iterator 代替 for-each：

- 删除当前元素。for-each 隐藏了迭代器，因此不能调用 `remove`。因此，for-each 不适用于过滤。
- 并行遍历多个集合。

下面的方法展示了如何使用一个迭代器过滤一个任意的集合——即遍历集合移除特定元素：

```java
static void filter(Collection<?> c) {
    for (Iterator<?> it = c.iterator(); it.hasNext(); )
        if (!cond(it.next()))
            it.remove();
}
```

这个简单的代码是多态的，这意味着它适用于任何 Collection 而不管是什么实现的，这个例子演示了使用 Java 集合框架写一个多态算法是多么容易。

### 集合接口批量操作（Bulk Operations）

- `containsAll` - 当集合包含指定集合所有元素时返回 true。
- `addAll` - 将指定集合内所有元素添加到集合中。
- `removeAll` - 删除集合中所有指定集合也包含的元素。
- `retainAll` - 删除集合中所有指定集合不包含的元素。
- `clear` - 清空集合。

`addAll`, `removeAll` 和 `retainAll` 如果执行时修改了集合则返回 true。

下面例子展示了删除所有 e 的实例：

```java
c.removeAll(Collections.singleton(e));
```

如果要删除所有 null：

```java
c.removeAll(Collections.singleton(null));
```

上面示例用到了 `Collection.singleton`，这是一个静态工厂方法，返回一个不可变的 Set 并只包含指定的元素。

### 数组操作

`toArray` 方法提供了集合到只支持数组的老 API 的一座桥梁。数组操作方法允许将一个集合转为一个数组，最简单的不带参数的调用会创建一个包含 Object 的新数组。

例如，假设 c 是一个 Collection，下面代码将 c 的内容存到一个长度与 c 相同、内容都是 Object 的数组中：

```java
Object[] a = c.toArray();
```

如果 c 中只包含 String（比如 c 的类型是 `Collection<String>`），下面的代码将 c 的内容存到长度与 c 相同，内容都是 String 的数组中：

```java
String[] a = c.toArray(new String[0]);
```
