# 接口 Interface

## 标记接口 Marker Interfaces

标记接口是一种没有任何方法和常量的接口。它用于提供对象的运行时类型信息，以让编译器和 JVM 得到关于对象的额外信息。

虽然标记接口还在被使用，但应该谨慎使用。主要原因是它让接口代表什么的界限变得越来越模糊，因为它没有定义任何行为。现在的开发者更喜欢用注解来完成相同的事情。

### 内置标记接口

Java 中有很多内置的标记接口，比如 Serializable, Cloneable 和 Remote。

拿 Cloneable 举例，如果我们尝试克隆一个没有实现 Cloneable 的对象，JVM 会抛出 CloneNotSupportedException 异常。因此，Cloneable 这个标记接口是一种告诉 JVM 我们可以调用 `Object.clone()` 方法的指示。

相似的，当调用 `ObjectOutputStream.writeObject()` 方法时，JVM 会检查对象有没有实现 Serializable 接口，否则会抛出 NotSerializableException 异常。

### 自定义标记接口

假设要定义一个标记接口 Deletable，只有实体实现这个接口时，才允许在数据库中删除它。

```java
public interface Deletable {
}
```

然后在删除操作里做一下校验即可：

```java
public class ShapeDao {
 
    // other dao methods
 
    public boolean delete(Object object) {
        if (!(object instanceof Deletable)) {
            return false;
        }
 
        // delete implementation details
         
        return true;
    }
}
```

### 与其他实现方式的区别

从举的例子来看，这个功能完全可以使用注解实现。但标记接口利用了多态的特性，可以对某一类的对象进行限制。

### 参考

- [Marker Interfaces in Java](https://www.baeldung.com/java-marker-interfaces)
