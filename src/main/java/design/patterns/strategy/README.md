# 策略模式 Strategy Pattern

也叫做政策模式（Policy Pattern）。定义如下：

Define a family of algorithms, encapsulate each one, and make them interchangeable.

策略模式是一个比较容易理解和使用的设计模式，使用的就是面向对象的继承和多态机制。

## 角色

- Context: 环境类
- Strategy: 抽象策略类
- ConcreteStrategy: 具体策略类

![](https://design-patterns.readthedocs.io/zh_CN/latest/_images/Strategy.jpg)

## 示例

[Demo](./Demo.java)

## 优缺点

优点：

- 避免使用多重条件转移语句
- 策略模式提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法或行为，也可以灵活地增加新的算法或行为

缺点：

客户端必须知道所有的策略类，并自行决定使用哪一个策略类。
