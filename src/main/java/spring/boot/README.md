# Spring Boot 2.x

## Getting Started Guides

### 1. 搭建一个 RESTful Web 服务

地址：[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

#### 创建 DTO

DTO 就是一个简单的 POJO， Spring 会使用 [Jackson JSON](https://github.com/FasterXML/jackson) 库自动转换 JSON

#### 创建 Controller

Controller 使用 `@RestController` 注解标识，然后通过给方法添加 Mapping 注解即可映射路由。`RestController` 是 `@Controller` 和 `@ResponseBody` 相结合的简写。

- `@RequestMapping` 将 HTTP Request 映射到对应的方法。默认会映射所有的 HTTP 请求方式，如果需要只映射 GET，需要指定 `@RequestMapping(method=GET)`

除此之外还有 `@GetMapping`、`@PostMapping` 等。

`@RequestParam` 用来绑定 query string 的值到方法的参数，并可以指定默认值。value 为 query 的值，defaultValue 为默认值。

```java
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
```

#### 启动应用

Application 是 Spring Boot 项目的入口，使用 `@SpringBootApplication` 注解标识。

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

`@SpringBootApplication` 是以下注解的集合：

- `@Configuration`
- `@EnableAutoConfiguration`
- `@EnableWebMvc`
- `@ComponentScan`

### 2. 通过 JPA 访问数据

地址：[Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

JPA（Java Persistence API）是 Sun 公司在 Java EE 5 规范中提出的 Java 持久化接口。
JPA 是一个标准，因此任何声称符合 JPA 标准的框架都遵循同样的架构，提供相同的访问 API，这保证了基于JPA开发的企业应用能够经过少量的修改就能够在不同的JPA框架下运行。

#### 创建简单实体

通过 `@Entity` 标注的 POJO 即为 JPA 的 entity，如果不标注 `@Table`，则表示该 entity 映射到同名的表。

给类的属性标注 `@Id` 指定该属性为 JPA 的 ID。与此同时指定 `@GeneratedValue` 表示该 ID 是自动创建的。

```java
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}
```

#### 创建简单查询

要创建简单查询，定义一个接口继承 `CrudRepository`：

```java
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
```

`CurdRepository` 的泛型指定了要查询的实体和 ID 类型。我们只需定义，无需实现接口的方法，这也是 Spring Data JPA 的强大之处。

然后即可以使用了：

```java
repository.save(new Customer("Jack", "Bauer"));
repository.findAll()
repository.findById(1L)
				.ifPresent(customer -> {})
repository.findByLastName("Bauer").forEach(bauer -> {});	
```
