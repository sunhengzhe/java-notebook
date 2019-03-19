# Lombok

[Lombok](https://projectlombok.org/) 通过注解的方式在编译期自动生成 getter、setter 等方法，非常便于精简代码和提高编码效率。

## 使用

只需要加上注解，就可以完成对应需要编码的操作，如给类的所有属性添加 getter：

```java
@Getter
class Person {
    private String name;
    private int age;
}
```

Lombok 支持的所有注解见 [官方文档](https://projectlombok.org/features/all)

## 原理

Javac 从 Java 6 开始支持 JSR 269 规范，其中包含 Annotation processing API，只要程序实现了该 API，就能在 Javac 运行的时候得到调用。而 Lombok 就支持该 API：

### Annotation processing API

这是在编译时处理，而不是运行时。在运行时，我们可以通过反射来分析注解，然后自定义一些行为，但是编译时的注解处理是完全不同的。
借助 javac，我们可以插入一些自定义的注解 "处理器"，可以做以下事情：

- 通过代码中注解提供的信息，可以做一些校验并抛出自定义错误或警告。错误会直接令编译失败。另外还能保证代码符合设计规范。
- 生成新代码和创建新 `.java` 文件，它们会被运行中的编译器继续编译。实际上我们可以创建任何类型的文件。

## 参考

- [Java Pluggable Annotation Processor](https://www.logicbig.com/tutorials/core-java-tutorial/java-se-annotation-processing-api/annotation-processing-concepts.html)
