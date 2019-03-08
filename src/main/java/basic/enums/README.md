# 枚举 Enum Types

枚举用来定义一组常量。

语法：

```java
public enum Day {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
    THURSDAY, FRIDAY, SATURDAY 
}
```

枚举类里面也可以包含方法和属性，编译器还会自动给枚举类添加一些方法，比如静态方法 `values()` 会返回一个包含所有枚举值的数组，并按声明的先后顺序排序。

```java
for(Day day: Day.values()) {
    System.out.println("day " + day.toString());
}
```

> 注：枚举默认继承 `java.lang.Enum`，所以不可以继承其他类。

可以给枚举值添加变量，值会在常量创建的时候传递进构造函数。

> 注：构造函数只能声明为 package-private 或者 private，不可手动调用。

```java
enum Status {
    NOT_ASSIGNED(1),
    ASSIGNED(2),
    CONTACTED(3),
    SOLD(4);

    private final int code;

    Status (int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
```

枚举值即拥有了 `getCode` 方法。

```java
for(Status status: Status.values()) {
    System.out.printf("status %s %d%n", status, status.getCode());
}
```
