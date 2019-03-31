# I/O

## 字节流 Byte Streams

所有字节流的类都是出自 InputStream 和 OutputStream。使用 8 位比特来完成输入输出流。

字节流类有很多，在文件 I/O 里，使用 FileInputStream 和 FileOutputStream。
            
不过使用字节流过于低级别，应该避免直接使用。

其他所有的流类型都是基于字节流。

[Demo](./CopyBytes.java)

## 字符流 Character Streams

所有字符流的类都是出自 Reader 和 Writer。字符流 IO 自动将数据与本地字符集进行转化。

与字节流类似，在文件 I/O 中，有 FileReader 和 FileWriter。

[Demo](./CopyCharacters.java)

上面两个例子非常像，只是使用的输入输出流的类不一样，但需要注意的是字符流里面的 `int c` 使用它的后 16 位存储字符，而字节流使用它的后 8 位存储字节。

字符流一般都包装着字节流，比如 FileReader 包装了 InputStream。字符流使用字节流执行物理 I/O，字节流来处理字符与字节之间的转换。

有两个多用途的字节转换为字符流的中间流：InputStreamReader 和 OutputStreamWriter。当没有预包装好的字符流时，可以使用它创建字符流。

## 缓冲流 Buffered Streams

以上的流都使用的是无缓冲（unbuffered ） I/O，这意味着每个读和写的请求都是直接被底层操作系统处理的，这会让程序非常低效，因为这样的请求通常会触发磁盘读取、网络活动或其他相当昂贵的操作。

为了减少这种开销，Java 平台提供了缓冲（buffered） I/O 流。缓冲输入流从内存中称为缓冲区（buffer）的区域读取数据，只有当缓冲区为空时，native input API 才会被调用。与之相似，缓冲输出流将数据写入缓冲区，只有当缓冲区满时，native output API 才会被调用。

无缓冲流可以通过传入缓冲流转化为缓冲流，如：

```java
inputStream = new BufferedReader(new FileReader("xanadu.txt"));
outputStream = new BufferedWriter(new FileWriter("characteroutput.txt"));
```

有 4 种缓冲流类可以封装无缓冲流：BufferedInputStream 和 BufferedOutputStream 创建缓冲字节流，BufferedReader and BufferedWriter 创建缓冲字符流。

## 扫描和格式化 Scanning and Formatting

### Scanning

#### 输入数据切分为 token

Scanner 默认使用空白符分隔 token，空白符包括空格、制表符和行结束符等，完整列表查看 [Character.isWhitespace](https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#isWhitespace-char-)

可以使用 `useDelimiter()` 指定分隔符，接受一个正则表达式。

[Demo](./ScanXan.java)

### Formatting

实现输出格式化的流有字符流 PrintWriter 和字节流 PrintStream。

唯一可能会用到的 PrintStream 可能只有 System.out 和 System.err。当需要对输出进行格式化时，建议使用 PrintWriter。

## 数据流 Data Stream

数据流支持基本数据类型和字符串的二进制 I/O，所有的数据流都实现自 DataInput 或 DataOutput 接口。

如 DataInputStream 和 DataOutputStream。

## 对象流 Object Stream

就如数据流支持基本数据类型的 I/O 一样，对象流支持对象的 I/O。大多数，但不是所有的标准类支持序列化它们的对象。能序列化的类实现了 Serializable 接口。

对象流类有 ObjectInputStream 和 ObjectOutputStream，它们分别实现了 ObjectInput 和 ObjectOutput，而这两个接口又分别是 DataInput 和 DataOutput 的子接口。
