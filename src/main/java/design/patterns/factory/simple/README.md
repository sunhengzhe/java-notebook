# 简单工厂模式 Simple Factory Pattern

又称静态工厂方法（Static Factory Method）模式，属于 **创建型模式**。

## 角色

- Factory：工厂角色
  
  工厂角色负责实现创建所有实例的内部逻辑

- Product：抽象产品角色
  
  抽象产品角色是所创建的所有对象的父类，负责描述所有实例所共有的公共接口

- ConcreteProduct：具体产品角色

    具体产品角色是创建目标，所有创建的对象都充当这个角色的某个具体类的实例。
    
![简单工厂模式](https://design-patterns.readthedocs.io/zh_CN/latest/_images/SimpleFactory.jpg)

```java
class Factory {
    public static Product createProduct(String productName) {
        if (productName.equals("A")) {
            return new ProductA();
        } else if (productName.equals("B")) {
            return new ProductB();
        }
        
        return null;
    }
}
```

缺点：但工厂类职责相对过重，增加新的产品需要修改工厂类的判断逻辑，这一点违背了开闭原则。
