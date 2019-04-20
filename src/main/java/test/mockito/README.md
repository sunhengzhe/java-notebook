# Mockito

> Tasty mocking framework for unit tests in Java

Mockito 可以改变一个类或者对象的行为，能够让我们更加专注地去测试代码逻辑，省去了构造数据所花费的努力。


## mock 实例

```java
Person person = mock(Person.class);
```

### 判断函数被调用次数

```java
// 等同于 times 1
verify(person).say("boy");
verify(person, times(2)).say("girl");
```

### 修改函数行为

```java
when(person.getName()).thenReturn("mock!");
```

### 定义参数匹配规则

```java
// 任何布尔型都返回 false
when(person.echo(anyBoolean())).thenReturn(false);
// 任何整数都返回 99
when(person.echo(anyInt())).thenReturn(99);
```

自定义匹配

```java
// 参数为 x 开头的字符串
when(person.echo(argThat(new ArgumentMatcher<>() {
    @Override
    public boolean matches(Object o) {
        return o.toString().startsWith("x");
    }
}))).thenReturn("xxx");
```
