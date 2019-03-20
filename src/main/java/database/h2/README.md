# H2

H2 是一个关系型内存数据库（In memory database），使用 Java 编写。

## 内存数据库

内存数据库会在应用启动的时候创建，并在应用停止时销毁。为什么会有内存数据库呢？它有下列好处：

- 几乎不用搭建基础设施
- 几乎不用配置
- 几乎不用维护
- 非常方便学习、POC 和单元测试

## 使用

引入依赖（Maven）：

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
</dependency>
```

打开 web console（src/main/resources/application.properties）：

```properties
# Enabling H2 Console
spring.h2.console.enabled=true
```

声明 Entity：

```java
@Entity
public class Student {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String passportNumber;
}
```

启动应用，访问 `http://localhost:8080/h2-console` 即可连接到 H2 控制台，注意选择 JDBC URL 为 `jdbc:h2:mem:testdb`

Spring 默认设置 H2 的配置为：

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

如果需要启动前插入数据，可以在 resources 目录下创建 `data.sql` 文件，应用启动后将加载这些文件。

## 持久化保存

H2 也可以将数据持久化保存，不过这个功能很少被使用，持久化存储应该使用 mysql 等其他数据库。

配置：

```properties
spring.datasource.name=yourdbname
spring.datasource.driverClassName=org.h2.Driver
 
spring.datasource.initialize=false
spring.datasource.url=jdbc:h2:file:~/yourdbname;DB_CLOSE_ON_EXIT=FALSE;IFEXISTS=TRUE;DB_CLOSE_DELAY=-1;
 
spring.jpa.hibernate.ddl-auto = update
```

## 在单元测试中使用 H2

上面也提到了，H2 经常在测试中被使用，这时候需要区分生产环境和测试环境，在不同文件夹下创建 `application.properties`。

引入依赖，非测试环境使用 mysql，测试环境使用 h2，h2 依赖 scope 被声明为 test：

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

`src/main/resources/application.properties` 文件使用 mysql：

```properties
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/person_example
spring.datasource.username=personuser
spring.datasource.password=YOUR_PASSWORD
```

`src/test/resources/application.properties` 文件使用 h2：

```properties
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```

## 参考

- [Spring Boot and H2 in memory database - Why, What and How?](http://www.springboottutorial.com/spring-boot-and-h2-in-memory-database)
