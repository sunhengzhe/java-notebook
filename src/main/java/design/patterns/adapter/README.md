# 适配器模式 Adapter Pattern

通常情况下，客户端（Client）可以通过目标类的抽象接口（Target）访问它所提供的服务，但有时候现有的类（Adaptee）提供的接口不能满足客户类的要求，
这时就需要一个适配器（Adapter）来包装不兼容接口的对象。

如下图，B 零件为客户端（Client），它想安装的是 **带三角接口** 的零件（Target），但 A （Adaptee）只有矩形接口，不符合 B 的使用规范，
所以我们需要 C 零件（Adapter）来进行适配。

![](https://box.kancloud.cn/2016-08-14_57b003664cda3.jpg)

如下：

![](https://box.kancloud.cn/2016-08-14_57b0036660d1c.jpg)

## 角色

从上面的例子可以看出，适配器模式分为四个角色：

- Target：目标抽象类
- Adapter：适配器类
- Adaptee：适配者类
- Client：客户类

## 两种实现

适配器模式分为两种：类适配器和对象适配器。

类适配器：

![类适配器](https://design-patterns.readthedocs.io/zh_CN/latest/_images/Adapter_classModel.jpg)

对象适配器：

![对象适配器](https://design-patterns.readthedocs.io/zh_CN/latest/_images/Adapter.jpg)

从 UML 图里面可以看出，类适配器使用的是泛化关系，而对象适配器使用的是关联关系。

当 Adaptee 只有一个时，使用类适配器可以继承 Adaptee；而当 Adaptee 有多个时，因为单继承的限制，只能使用对象适配器，通过关联关系进行适配。
