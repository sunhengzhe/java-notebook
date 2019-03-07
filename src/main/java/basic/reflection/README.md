# 反射 Reflection

## Class

谈论反射就离不开 `Class` 类。Oracle 文档上写到：

> For every type of object, the Java virtual machine instantiates an immutable instance of `java.lang.Class`

对于每一类对象，Java 虚拟机都会实例化一个不可变的 Class 实例。

Class 实例可以干嘛呢？

> which provides methods to examine the runtime properties of the object including its members and type information.

提供一些方法用来检查对应的对象运行时的属性，包括它的成员和类型信息。

> Most importantly, it is the entry point for all of the Reflection APIs.

最重要的是，Class 是所有反射 API 的进入点。

### 获取 Class 实例

获取 Class 实例有四种方式：

#### Object.getClass()

当一个对象的实例可以访问时，可以通过调用 `getClass()` 方法获取 Class 实例。

```java
Class a = "foo".getClass();

byte[] bytes = new byte[1024];
Class c = bytes.getClass();
```

#### .class

当可以获取到类型但是获取不到实例时，可以使用 `.class` 获取 Class 实例。这也是获取基本数据类型的 Class 实例最简单的办法。

```java
Class a = boolean.class;
Class b = String.class;
```

#### Class.forName()

当可以获取到类的全名时，可以通过 `Class.forName()` 获取。

```java
Class c = Class.forName()
```

#### 基本数据类型包装器 .TYPE

基本数据类型的 Class 实例获取除了使用 .class，还可以在包装器上使用 .TYPE。

```java
Class c = Double.TYPE;
```

它等同于 `double.class`。

另外 `Void.TYPE == void.class`

### 检查类的修饰符和类型

类可以被一个或多个修饰符修饰

- Access modifiers: public, protected, and private
- Modifier requiring override: abstract
- Modifier restricting to one instance: static
- Modifier prohibiting value modification: final
- Modifier forcing strict floating point behavior: strictfp
- Annotations

通过 `Class.getModifiers()` 可以获取类的修饰符。

### 获取成员变量


#### Class Methods for Locating Fields

| Class API	List of members? |	Inherited members? | Private members? |
| --- | --- | --- |
| getDeclaredField() |	no |	no	| yes |
| getField()	no	yes	no
| getDeclaredFields()	yes	no	yes
| getFields()	yes	yes	no
| 

#### Class Methods for Locating Methods

| Class API	List of members?	| Inherited members?| Private members?|
| --- | --- | --- |
| getDeclaredMethod() |	no| no|	yes|
| getMethod()	| no	|yes	|no|
| getDeclaredMethods() |	yes |	no	| yes |
| getMethods()|	yes	|yes	|no|
 
####  Class Methods for Locating Constructors

| Class API	List of members?	| Inherited members?| Private members?|
| --- | --- | --- |
| getDeclaredConstructor()|	no	|N/A	|yes|
| getConstructor()|	no|	N/A|	no|
| getDeclaredConstructors()	|yes|	N/A|	yes|
| getConstructors()|	yes	|N/A	|no|

## 反射

了解了 Class 的用法后，其实 Class 的大部分 API 都是为反射服务的。

Oracle 文档上的描述是：

> Reflection is commonly used by programs which require the ability to examine or modify the runtime behavior of applications running in the Java virtual machine. 

反射常常用于 Java 虚拟机在运行时查看或修改应用的运行行为。

在 [注解](../annotation/Demo.java) 这个例子里，其实也利用了反射，获取 DemoTest 的所有方法，并查看方法的注解，最后通过 `invoke` 调用了指定的方法。

## 参考

- [Lesson: Classes](https://docs.oracle.com/javase/tutorial/reflect/class/index.html)
- [深入解析Java反射（1） - 基础](https://www.sczyh30.com/posts/Java/java-reflection-1/)
- [Java 反射由浅入深 | 进阶必备](https://juejin.im/post/598ea9116fb9a03c335a99a4)
