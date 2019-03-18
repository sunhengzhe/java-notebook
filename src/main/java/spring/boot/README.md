# Spring Boot 2.x

## Getting Started Guides

### 1. 搭建一个 RESTful Web 服务

地址：[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)

#### 创建 DTO

DTO 就是一个简单的 POJO， Spring 会使用 [Jackson JSON](https://github.com/FasterXML/jackson) 库自动转换 JSON

#### 创建 Controller

Controller 使用 `@RestController` 注解标识，然后通过给方法添加 Mapping 注解即可映射路由。`RestController` 是 `@Controller` 和 `@ResponseBody` 相结合的简写。

- `@RequestMapping` 将 HTTP Request 映射到对应的方法。默认会映射所有的 HTTP 请求方式，如果需要只映射 GET，需要指定 `@RequestMapping(method=GET)`

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


