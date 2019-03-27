# Java 中的异常

Java 中一切都是对象，所以毫不意外地，异常也是对象。所以在 Java 中抛出异常，实际上就是抛出一个对象，但这个对象必须继承自 `java.lang.Throwable`。

Throwable 有两个子类，分别是 Exception 和 Error。

抛 Exception 一般都是应用出现了一些意外的情况，需要被相应的 catch 捕获的，比如 NullPointerException。而 Error 一般由 Java 的 API 或者 JVM 抛出，一般都是出现了比较严重的、无法恢复的错误，比如 OutOfMemoryError。

有时候需要根据业务需要自定义异常，这时一般需要新建自定义异常类，并 **继承 Exception**。

## Checked exceptions 和 Unchecked exceptions

Java 的异常分为两类：checked 和 unchecked。只有 checked exceptions 需要出现在 throws 关键字中。总的原则是：一个方法中可能抛出的所有 checked exceptions，要么需要被 catch 捕获，要么需要被 throws 抛出。

一个异常是否是 checked exception 取决于它在 Throwable 家族树中的位置：

![](https://images.techhive.com/images/idge/imported/article/jvw/1998/07/exceptfig4-100158198-orig.gif)

如果要创建一个 checked exception，只需要继承任何一个 checked exception 即可。由图可知，是 Exception 的子类且不是 RuntimeException 的子类都是 checked exception。

### 区别

从概念上讲，checked exception 和 unchecked exception 的区别在于，checked exception 代表了你希望程序的使用方需要处理的异常，而 unchecked exception 不需要他们关心。
就好比某个方法里面有 new 关键字，这代表它需要申请内存，也就意味着它有可能抛出 OutOfMemoryError，但你一定不希望使用方还需要直接去处理内存溢出的问题。
而 check exception 是使用方必须需要处理的——这也很容易看出来，因为一旦在方法上使用 throws 声明异常，如果调用你的方法的人不处理，则编译会通不过。

```java
class MyUncheckedException extends RuntimeException {

}

class MyCheckedException extends IOException {

}

class User {
    // 无须 throws
    public void say() {
        throw new MyUncheckedException();
    }

    // 需要 throws
    public void read() throws MyCheckedException {
        throw new MyCheckedException();
    }
}
```

大多数 unchecked exception（Error 的子类和 RuntimeException 的子类）都是会被 JVM 检测到的问题。

- Error：通常代表不需要程序处理的错误，比如 NoClassDefFoundError、StackOverflowError 等，它们在程序运行的任何地方都可能发生。
- RuntimeException：虽然大多数 RuntimeException 都是 JVM 抛出的，但它们一般都代表着程序本身的问题，比如访问数组越界 ArrayIndexOutOfBoundsException、非法参数 IllegalArgumentException 等。
它们也可能在程序的任何地方发生。而且一般这种异常被抛出，我们都会希望去修复它，但你肯定不会希望在每个数组操作的地方都捕获一下 ArrayIndexOutOfBoundsException。

要决定抛出 checked exception 还是 unchecked exception，这取决于要表示的异常场景。如果抛出的异常代表的是使用上的问题，这抛出的是一个软件异常，你可能需要抛一个 RuntimeException 的子类；
除此之外，抛出的非软件异常，而是每次使用这个方法都必须要处理的，那么就需要抛出一个 checked exception。

## try-with-resources

在 Java 7 中引入了 try-with-resources，可以保证在 try 语句块中声明的资源能够在执行完后自动关闭。前提是资源实现了 `AutoCloseable` 接口。

### 使用

在 try 语句中对资源进行声明和初始化：

```java
try (Scanner scanner = new Scanner(new File("test.txt"))) {
    while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException fnfe) {
    fnfe.printStackTrace();
}
```

这与原有的 try-catch-finally 作用是相同的，但相对而言更简洁：

```java
Scanner scanner = null;
try {
    scanner = new Scanner(new File("test.txt"));
    while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
} finally {
    if (scanner != null) {
        scanner.close();
    }
}
```

如果有多个资源需要关闭，只需要在 try 语句中使用分号分隔即可：

```java
try (Scanner scanner = new Scanner(new File("testRead.txt"));
    PrintWriter writer = new PrintWriter(new File("testWrite.txt"))) {
    while (scanner.hasNext()) {
        writer.print(scanner.nextLine());
    }
}
```

### Closeable

在 try 语句中声明的资源必须要实现 Closeable 或者 AutoCloseable（Closeable 继承自 AutoCloseable），在 try 语句块执行结束后会自动调用资源的 close 方法。

另外 try-resource 语句同样也支持 catch 和 finally 语句，它们的行为和传统的行为是一样的。

```java
// try-with-resource
try (AutoCloseableResourcesFirst af = new AutoCloseableResourcesFirst();
     AutoCloseableResourcesSecond as = new AutoCloseableResourcesSecond()) {
    af.doSomething();
    as.doSomething();
    throw new MyUncheckedException();
} catch (MyUncheckedException e) {
    System.out.println("try-with-resource end");
} finally {
    System.out.println("this also works");
}
```

## 参考

- [Exceptions in Java](https://www.javaworld.com/article/2076700/exceptions-in-java.html)
- [Java – Try with Resources](https://www.baeldung.com/java-try-with-resources)
