# 中介者模式 Mediator Pattern

中介者模式用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。

所以中介者模式又称为调停者模式，它是一种对象行为型模式。

## 角色

- Mediator: 抽象中介者
- ConcreteMediator: 具体中介者
- Colleague: 抽象同事类
- ConcreteColleague: 具体同事类

![UML](https://design-patterns.readthedocs.io/zh_CN/latest/_images/Mediator.jpg)

## 场景

中介者模式主要解决的问题就是对象之间相互依赖、结构混乱，采用中介者模式把这些对象相互的交互管理起来，各个对象都只需要和中介者交互，从而使得各个对象松散耦合，结构也更清晰易懂。

![](https://images0.cnblogs.com/blog/381060/201310/01151529-0027d98f7c304706bd85a53e3deb597f.jpg)

## 实现

[Demo.java](./Demo.java)

## 优缺点

优点：

- 简化了对象之间的交互。
- 将各同事解耦。
- 减少子类生成。
- 可以简化各同事类的设计和实现。

缺点：

在具体中介者类中包含了同事之间的交互细节，可能会导致具体中介者类非常复杂，使得系统难以维护。

## 迪米特法则

中介者模式就是迪米特法则的一个典型应用。

迪米特法则（Law of demeter,缩写是LOD）要求：一个对象应该对其他对象保持最少了解， 通缩的讲就是一个类对自己依赖的类知道的越少越好，也就是对于被依赖的类，向外公开的方法应该尽可能的少。

迪米特法则还有一种解释：Only talk to your immediate friends，即只与直接朋友通信。两个对象之间的耦合关系称之为朋友，直接朋友通常以成员变量、方法的参数和返回值的形式出现。出现在方法体内部的类不属于朋友类。


## 参考

- [设计模式读书笔记-----中介者模式](https://www.cnblogs.com/chenssy/p/3348520.html)
- [设计模式 ——— 中介者模式](https://www.jianshu.com/p/21d27fd06e86?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
