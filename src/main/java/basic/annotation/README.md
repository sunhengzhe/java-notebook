# 注解

使用 `@interface` 定义

```java
public @interface MyAnnotation {
    
}
```

## 元注解

- Retention
- Documented
- Target
- Inherited
- Repeatable

### Retention

规定注解的的存活时间，取值为：

 - `RetentionPolicy.SOURCE` 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
 - `RetentionPolicy.CLASS` 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
 - `RetentionPolicy.RUNTIME` 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

### Documented

将注解中的元素包含到 Javadoc 中去。

### Target

规定注解使用的地方，取值为：

- `ElementType.ANNOTATION_TYPE` 可以给一个注解进行注解
- `ElementType.CONSTRUCTOR` 可以给构造方法进行注解
- `ElementType.FIELD` 可以给属性进行注解
- `ElementType.LOCAL_VARIABLE` 可以给局部变量进行注解
- `ElementType.METHOD` 可以给方法进行注解
- `ElementType.PACKAGE` 可以给一个包进行注解
- `ElementType.PARAMETER` 可以给一个方法内的参数进行注解
- `ElementType.TYPE` 可以给一个类型进行注解，比如类、接口、枚举

### Inherited

如果一个超类被 `@Inherited` 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。

### Repeatable

Java 1.8+

可重复添加注解。

```java
@interface Roles {
    Person[] value();
}

@Repeatable(Roles.class)
@interface Person {
    String role() default "";
}

@Person(role = "teacher")
@Person(role = "student")
public class Admin {
    
}
```

## Java 预定义注解

- Deprecated 标记为过时
- Override 标记为重写
- SuppressWarnings 阻止警告
- SafeVarargs
- FunctionalInterface

## 注解的提取

使用反射可以获取注解

- Class 对象的 `isAnnotationPresent()` 方法判断它是否应用了某个注解。

```java
public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {}
```

- getAnnotation() 方法来获取指定类型的 Annotation 对象。

```java
public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}
```

- getAnnotations() 方法获取这个元素上的所有注解。

```java
public Annotation[] getAnnotations() {}
```

## 参考

- [秒懂，Java 注解 （Annotation）你可以这样学](https://blog.csdn.net/briblue/article/details/73824058)

