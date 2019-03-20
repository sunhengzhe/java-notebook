# Spring Boot 2.x

## 简单 Restful Web 服务

### Controller

给类添加 `@RestController` 注解，然后通过给方法添加 Mapping 注解即可映射路由。

`@RestController` 实际上等于 `@Controller` + `@ResponseBody`。

#### Request Mapping

常用 Mapping 注解有：

- `@RequestMapping` 将 HTTP Request 映射到对应的方法。默认会映射所有的 HTTP 请求方式，如果需要只映射 GET，需要指定 `@RequestMapping(method=GET)`
- `@GetMapping`、`@PostMapping`、`@DeleteMapping` 等分别对应相应的 HTTP 请求。

#### 接受参数

1）`@RequestParam` 从 query 里获取：

```java
@GetMapping("/hello")
public String hello(@RequestParam(value="message", defaultValue="World") String content) {
    return "Hello, " + content;
}
```

上面代码接受 message 参数，并设置默认参数为 World：`curl localhost:8080/hello?message=123`

2） `@PathVariable` 从路由里获取:

```java
@GetMapping("/hello/{message}")
public String hello(@PathVariable String message) {
    return "Hello, " + message;
}
```

上面代码接受 message 路由参数：`curl localhost:8080/hello/123`

3）`@RequestBody` 将请求体映射到 entity：

```java
@PostMapping("/employees")
Employee newEmployee(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
}
```

上面代码接受一个 json 串，使用 [Jackson JSON](https://github.com/FasterXML/jackson) 库转换为 Employee 对象。

## 启动应用

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

## 通过 JPA 访问数据

JPA（Java Persistence API）是 Sun 公司在 Java EE 5 规范中提出的 Java 持久化接口。
JPA 是一个标准，因此任何声称符合 JPA 标准的框架都遵循同样的架构，提供相同的访问 API，这保证了基于JPA开发的企业应用能够经过少量的修改就能够在不同的JPA框架下运行。

### 创建简单实体

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

### 创建简单查询

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

## 异常处理

### ExceptionHandler

如果在 Controller 里面抛出了异常，可以在被 `@ExceptionHandler` 注解的方法中进行处理：

```java
@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
    List<String> errors = Collections.singletonList(e.getMessage());
    return new ResponseEntity(errors, HttpStatus.NOT_FOUND);
}
```

该方法处理所在 Controller 内抛出的 `UserNotFoundException` 异常，并返回错误信息。

但这种方式会造成 Controller 间可能会有很多重复代码，因为不同的 Controller 可能都会处理相同的异常。
这种情况下可以使用 `@ControllerAdvice`，被它注解的类作为一个全局错误处理器（GlobalExceptionHandler）。
这样可以拦截应用内所有的控制器抛出的异常。

#### 编写 controller advice

1. 创建自定义异常类。虽然 Spring 内置很多通用的异常类，但是最佳实践的做法是创建自定义的异常或者继承已有异常。
2. 应用内只创建一个 controller advice。用单个类来封装所有的异常处理方法。
3. 编写一个通用的 handleException 方法，只有这个方法使用 `@ExceptionHandler` 注解。在这个方法里面会处理所有定义的异常，并分发到其他特定的 handler 方法中去。
4. 针对每种异常编写对应的 handler。比如有 `UserNotFoundException` 异常，则创建一个 `handleUserNotFoundException` 方法。
5. 创建一个方法用来响应给用户。handler 方法只是编写处理异常的逻辑，它们最后都会调用这个方法来发送响应给用户，这个方法会接受一个异常信息列表并指定 HTTP 状态码。

示例代码如下：

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({ UserNotFoundException.class, ContentNotAllowedException.class })
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleUserNotFoundException(unfe, headers, status, request);
        } else if (ex instanceof ContentNotAllowedException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ContentNotAllowedException cnae = (ContentNotAllowedException) ex;

            return handleContentNotAllowedException(cnae, headers, status, request);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    /** Customize the response for UserNotFoundException. */
    protected ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status, request);
    }

    /** Customize the response for ContentNotAllowedException. */
    protected ResponseEntity<ApiError> handleContentNotAllowedException(ContentNotAllowedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = ex.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new ApiError(errorMessages), headers, status, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
```

### 参考

- [Understanding Spring’s @ControllerAdvice](https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f)
