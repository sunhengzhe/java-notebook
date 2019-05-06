# 依赖注入

使用依赖注入（Dependency Injection）减少对象间耦合。在 Spring 中，通过容器来管理 Java Bean。

## 简单依赖注入

实现一个简单的 IoC 容器，可以实例化 Bean 并提供使用。

```java
public class IoCContainer {

    private Map<String, Object> beans = new ConcurrentHashMap<String, Object>();

    /**
     * 获取 Bean
     * @param beanId bean Id
     * @return bean
     */
    public Object getBean(String beanId) {
        return beans.get(beanId);
    }

    /**
     * 委托 ioc 容器创建一个 bean
     * @param clazz 需要创建的 bean 的 class
     * @param beanId bean Id
     * @param dependencyBeanIds 依赖的 bean id 们
     */
    public void setBean(Class<?> clazz, String beanId, String... dependencyBeanIds) {
        // 1. 组装依赖的 bean
        Object[] dependencyBean = new Object[dependencyBeanIds.length];

        for (int i = 0; i < dependencyBean.length; i++) {
            dependencyBean[i] = getBean(dependencyBeanIds[i]);
        }

        // 2. 调用构造函数实例化 bean
        Object bean = null;
        for (Constructor<?> constructor : clazz.getConstructors()) {
            try {
                bean = constructor.newInstance(dependencyBean);
            } catch (Exception e) {
                // Do nothing
            }
        }

        if (bean == null) {
            throw new RuntimeException("Could not find suitable constructor");
        }

        // 3. 放入 beans
        beans.put(beanId, bean);
    }
}
```
