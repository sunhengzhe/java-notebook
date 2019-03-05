package design.patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Sell {
    void sell();
    void ad();
}

/**
 * 委托类
 */
class Vendor implements Sell {
    public void sell() {
        System.out.println("vendor sell");
    }

    public void ad() {
        System.out.println("vendor ad");
    }
}

/**
 * 静态代理
 */
class StaticProxy implements Sell {
    private Sell vendor;

    public StaticProxy(Sell vendor) {
        this.vendor = vendor;
    }


    public void sell() {
        System.out.println("static before invoke");
        vendor.sell();
        System.out.println("static after invoke");
    }

    public void ad() {
        vendor.ad();
    }
}

/**
 * 动态代理
 */
class DynamicProxy implements InvocationHandler {

    // 委托类
    private Object obj;

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("dynamic before invoke");
        Object result = method.invoke(obj, args);
        System.out.println("dynamic after invoke");
        return result;
    }
}

public class Demo {
    public static void main(String[] args) {
        System.out.println("--- 静态代理 ---");
        StaticProxy staticProxy = new StaticProxy(new Vendor());
        staticProxy.ad();
        staticProxy.sell();

        System.out.println("--- 动态代理 ---");
        DynamicProxy dynamicProxy = new DynamicProxy(new Vendor());
        // 返回代理类
        Sell sell = (Sell) Proxy.newProxyInstance(Sell.class.getClassLoader(), new Class[]{Sell.class}, dynamicProxy);
        sell.ad();
        sell.sell();
    }
}
