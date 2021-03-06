# Optional

一位智者说过，没有处理过空指针异常就不算一个真正的 Java 程序员。这当然是开玩笑，但是空指针异常确实是很多程序出错的源头。
于是，在 Java 8 引入了 `java.util.Optional`，Optional 用来代表一种 **可能有可能没有** 的数据，可以用来缓解空指针异常的问题。

简单地说，Optional 用来避免这种代码：

```java
String version = "UNKNOWN";
if(computer != null){
  Soundcard soundcard = computer.getSoundcard();
  if(soundcard != null){
    USB usb = soundcard.getUSB();
    if(usb != null){
      version = usb.getVersion();
    }
  }
}
```

如果用 Optional 表示呢？大概是这样：

```java
String version = computer.flatMap(Computer::getSoundcard)
                         .flatMap(Soundcard::getUSB)
                         .map(USB::getVersion)
                         .orElse("UNKNOWN");
```

实际上，Optional 即函数式编程中的 Maybe。

以下示例在 [OptionalTest.java](../../../test/java/functional_programming/OptionalTest.java) 中。

## 创建

创建 Optional 有三种方式，分别是 empty、 of 和 ofNullable。

### empty

`empty` 用来创建一个空的 Optional

```java
@Test
public void create_optional_with_empty() {
    Optional<String> empty = Optional.empty();
    assertFalse(empty.isPresent());
}
```

### of

`of` 用来创建一个非空的 Optional：

```java
@Test
public void create_optional_with_of() {
    Optional<String> java = Optional.of("Java");
    assertTrue(java.isPresent());
}
```

但是参数不能为 null，否则会抛空指针异常：

```java
@Test(expected = NullPointerException.class)
public void create_optional_with_of_with_null() {
    Optional.of(null);
}
```

### ofNullable

`ofNullable` 用来创建一个可能为空的 Optional：

```java
@Test
public void create_optional_with_ofNullable() {
    Optional<String> java = Optional.ofNullable("Java");
    assertTrue(java.isPresent());

    Optional<Object> o = Optional.ofNullable(null);
    assertFalse(o.isPresent());
}
```

## 检测值是否存在

可以使用 `isPresent` 和 `isEmpty` 判断 Optional 的值是否为空。

### isPresent

如果 Optional 中值非 null，则返回 true，否则返回 false。

```java
@Test
public void check_optional_with_isPresent() {
    Optional<String> java = Optional.ofNullable("java");
    Optional<Object> aNull = Optional.ofNullable(null);

    assertTrue(java.isPresent());
    assertFalse(aNull.isPresent());
}
```

### isEmpty

Java 11 开始可以使用 `isEmpty`。

`isEmpty` 与 `isPresent` 相反，如果为 null 返回 true。

```java
@Test
public void check_optional_with_isEmpty() {
    Optional<String> java = Optional.ofNullable("java");
    Optional<Object> aNull = Optional.ofNullable(null);

    assertFalse(java.isEmpty());
    assertTrue(aNull.isEmpty());
}
```

## 条件动作

关于条件的动作有 `ifPresent`、`orElse`、`orElseGet`、`orElseThrow`、`or`、`ifPresentOrElse`，它们执行与否取决于 Optional 的值是否为 null。

为了避免空指针异常，我们会经常写下面的代码：

```java
if (name != null){
    System.out.println(name.length);
}
```

Optional 使用一种函数式的方式来替代上面的写法。

### ifPresent

ifPresent 接受一个 Consumer，在 Optional 值非 null 时调用，并接受 Optional 的值。

```java
@Test
public void condition_action_ifPresent() {
    Optional<String> java = Optional.ofNullable("java");
    java.ifPresent((value) -> System.out.println("ifPresent accept " + value));

    Optional<Object> aNull = Optional.ofNullable(null);
    aNull.ifPresent(value -> System.out.println("this will never execute"));
}
```

### orElse

orElse 在 Optional 值为 null 时触发，它接受一个参数，作为 Optional 的默认值。

```java
@Test
public void condition_action_orElse() {
    assertTrue(Optional.ofNullable("java").orElse("javascript").equals("java"));
    assertTrue(Optional.ofNullable(null).orElse("java").equals("java"));
}
```

### orElseGet

orElseGet 与 orElse 类似，但 orElseGet 接受的是一个 Supplier，Supplier 返回的值作为 Optional 的默认值。

```java
@Test
public void condition_action_orElseGet() {
    assertTrue(Optional.ofNullable("java").orElseGet(() -> "javascript").equals("java"));
    assertTrue(Optional.ofNullable(null).orElseGet(() -> "java").equals("java"));
}
```

### orElse 和 orElseGet 的区别

`orElse` 和 `orElseGet` 的函数签名是不一样的，但如果想使用同样的函数的返回值来作为 Optional 的默认值，我们很可能会这么干：

```java
public String getDefaultName() {
    System.out.println("You got a default name");
    return "default";
}
    
@Test
public void difference_between_orElse_and_orElseGet() {
    Optional<String> java = Optional.of("java");

    System.out.println("orElse:");
    assertEquals("java", java.orElse(getDefaultName()));
    System.out.println("orElseGet:");
    assertEquals("java", java.orElseGet(this::getDefaultName));
}
```

若 java 是 null，则 orElse 和 orElseGet 没有什么不同，getDefaultName 方法都会执行并将返回值作为 Optional 的默认值。

当在上面的例子中，java 非 null，这时 orElse 的 getDefaultName 还是会执行，但 orElseGet 不会。输出：

```
orElse:
You got a default name
orElseGet:
```

当 getDefaultName 中有副作用或耗时操作时需要注意。

### orElseThrow

orElseThrow 与 orElse 一样也在当 Optional 值为 null 时触发，但与之不同的是会抛出指定的异常：

```java
@Test(expected = IllegalArgumentException.class)
public void condition_action_orElseThrow() {
    Optional.ofNullable(null).orElseThrow(IllegalArgumentException::new);
}
```

### or

or 是 Java 9 中新增方法。与 orElseGet 很相似，or 也接受一个 Supplier，但 or 返回的是一个新的 Optional。

```java
@Test
public void condition_or_optional() {
    Optional<String> java = Optional.of("java")
                                    .or(() -> Optional.of("javascript"));
    Optional<Object> java1 = Optional.empty()
                                     .or(() -> Optional.of("java"));
    assertEquals("java", java.get());
    assertEquals("java", java1.get());
}
```

### ifPresentOrElse

ifPresentOrElse 是 Java 9 中新增的方法。ifPresent 就如同命令式编程中的 `if-else`，它接受两个参数，第一个为 Consumer，在 Optional 有值时调用，第二个为 Runnable，在无值时调用：

```java
@Test
public void condition_ifPresentOrElse() {
    // value is java
    Optional.of("java")
            .ifPresentOrElse(value -> System.out.println("value is " + value), () -> System.out.println("ooops"));

    // ooops
    Optional.empty()
            .ifPresentOrElse(value -> System.out.println("value is " + value), () -> System.out.println("ooops"));
}
```

## 获取值

Optional 提供了一个 `get` 方法获取值，但 get 方法只能在 Optional 有值时使用，否则会抛出 `NoSuchElementException` 异常：

```java
@Test
public void get_optional_with_of() {
    Optional<String> java = Optional.of("Java");
    assertEquals("java", java.get());
}

@Test(expected = NoSuchElementException.class)
public void get_optional_with_of_with_null() {
    Optional.empty().get();
}
```

## 验证值

`filter` 方法用来验证 Optional 的值是否符合条件，它接受一个 Predicate 作为参数。如果 Optional 的值为 null 或 Predicate 判断不通过，则返回 empty；否则返回该 Optional。

```java
@Test
public void test_optional_by_filter() {
    Integer nullYear = null;
    Optional<Integer> integer = Optional.ofNullable(nullYear)
                                        .filter(value -> value == 2018);
    assertEquals(Optional.empty(), integer);

    Integer year = 2019;
    Optional<Integer> integer1 = Optional.ofNullable(year)
                                         .filter(value -> value == 2018);
    assertEquals(Optional.empty(), integer1);

    Optional<Integer> integer2 = Optional.ofNullable(year)
                                         .filter(value -> value == 2019);
    assertEquals("Optional[2019]", integer2.toString());
}
```

filter 相对传统 if 而言省去了很多样板代码，如：

```java
public boolean priceIsInRange1(Modem modem) {
    boolean isInRange = false;
 
    if (modem != null && modem.getPrice() != null
      && (modem.getPrice() >= 10
        && modem.getPrice() <= 15)) {
 
        isInRange = true;
    }
    return isInRange;
}
```

使用 Optional 实现同样的方法：

```java
public boolean priceIsInRange2(Modem modem2) {
     return Optional.ofNullable(modem2)
       .map(Modem::getPrice)
       .filter(p -> p >= 10)
       .filter(p -> p <= 15)
       .isPresent();
}
```

## 处理值

处理值的方式有 map 和 flatMap。

### map

使用 map 可以对 Optional 中的值进行处理并返回。

```java
@Test
public void map_optional() {
    Optional<String> java = Optional.of("java");
    String result = java.map(String::toUpperCase).orElse("");
    assertEquals("JAVA", result);
}
```

### flatMap

flatMap 与 map 的区别在于 map 处理值后会包装返回值，而 flatMap 不包装。

```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}

@Test
public void flatMap_optional() {
    Person person = new Person("john");
    Optional<Person> personOptional = Optional.of(person);

    String byMap = personOptional.map(Person::getName)
                                 // 需要手动打开包装
                                 .orElse(Optional.empty())
                                 .orElse("");

    String byFlatMap = personOptional.flatMap(Person::getName)
                                     .orElse("");

    assertEquals("john", byMap);
    assertEquals("john", byFlatMap);
}
```

## 流操作

在 Java 9 中，新增了 stream 方法，可以对 Optional 创建 stream，然后可以使用 stream 上的所有方法。

如果 Optional 为 empty，则创建一个 empty 的 stream。

```java
@Test
public void treat_optional_as_stream() {
    List<String> collect = Optional.of("java")
                                   .stream()
                                   .map(value -> value.concat("script"))
                                   .collect(Collectors.toList());

    assertArrayEquals(new String[]{"javascript"}, collect.toArray());


    // empty optional
    Optional<String> value = Optional.empty();
    List<String> emptyStream = value.stream()
                                    .map(String::toUpperCase)
                                    .collect(Collectors.toList());

    assertEquals(0, emptyStream.size());
}
```

所以使用 stram 也可以筛出非 null 的 Optional 的值：

```java
@Test
public void filter_empty_by_stream() {
    List<Optional<String>> languages = List.of(Optional.of("java"), Optional.empty(), Optional.empty(), Optional.of("javascript"));
    List<String> collect = languages.stream()
                                    .flatMap(Optional::stream)
                                    .collect(Collectors.toList());

    assertArrayEquals(new String[]{"java", "javascript"}, collect.toArray());
}
```

## 参考

- [Tired of Null Pointer Exceptions? Consider Using Java SE 8's Optional!](https://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html)
- [Guide To Java 8 Optional](https://www.baeldung.com/java-optional)
- [Java 9 Optional API Additions](https://www.baeldung.com/java-9-optional)
- [Filtering a Stream of Optionals in Java](https://www.baeldung.com/java-filter-stream-of-optional)
