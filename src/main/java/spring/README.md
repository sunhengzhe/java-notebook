# Spring

核心特性：依赖注入（Dependency Injection, DI）和面向切面编程（Aspect-Oriented Programming, AOP）
诞生之初，主要目的是替代更加重量级的企业级 Java 技术，尤其是 EJB。
增强了简单老式 Java 对象（Plain Old Java Object, POJO）的功能。

4 种关键策略：
- 基于 POJO 的轻量级和最小侵入性特性
- 通过依赖注入和面向接口实现松耦合
- 基于切面和惯例进行声明式编程
- 通过切面和模板减少样板式代码

依赖注入：
- 构造器注入，并只通过接口来表明依赖关系。

容器是 Spring 框架的核心。在 Spring 应用中，应用对象生存于 Spring 容器（Container）。

Spring 容器可以归为两种不同的类型：
- bean 工厂：由 `org.springframework.beans.factory.BeanFactory` 接口定义，是最简单的容器，提供基本的 DI 支持。
- 应用上下文：由 `org.springframework.context.ApplicationContext` 接口定义，基于 BeanFactory 构建，并提供应用框架级别的服务。

Spring 6 个模块：
- Spring 核心容器：管理 Spring 应用中 bean 的创建、配置和管理。
- Spring 的 AOP 模块
- 数据访问与集成
- Web 与远程调用
- Instrumentation：提供了为 JVM 添加代理的功能。
- 测试

创建应用对象之间协作关系的行为通常称为装配（wiring），这也是依赖注入的本质。

## 依赖注入

[简单依赖注入](./DI.md)
