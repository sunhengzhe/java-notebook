package basic.exception;

import java.io.Closeable;
import java.io.IOException;

class MyUncheckedException extends RuntimeException {

}

class MyCheckedException extends IOException {

}

class User {
    // 无须 throws
    public void say() {
        throw new MyUncheckedException();
    }

    // 需要 throws
    public void read() throws MyCheckedException {
        throw new MyCheckedException();
    }
}

class MyResource implements AutoCloseable {
    @Override
    public void close() {
        System.out.println("Closed MyResource");
    }
}

class AutoCloseableResourcesFirst implements AutoCloseable, Closeable {

    public AutoCloseableResourcesFirst() {
        System.out.println("Constructor -> AutoCloseableResources_First");
    }

    public void doSomething() {
        System.out.println("Something -> AutoCloseableResources_First");
    }

    @Override
    public void close() {
        System.out.println("Closed AutoCloseableResources_First");
    }
}

class AutoCloseableResourcesSecond implements AutoCloseable, Closeable {

    public AutoCloseableResourcesSecond() {
        System.out.println("Constructor -> AutoCloseableResources_Second");
    }

    public void doSomething() {
        System.out.println("Something -> AutoCloseableResources_Second");
    }

    @Override
    public void close() {
        System.out.println("Closed AutoCloseableResources_Second");
    }
}


public class Demo {
    public static void main(String[] args) {
        User user = new User();
        try {
            user.say();
            user.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // try-with-resource
        try (AutoCloseableResourcesFirst af = new AutoCloseableResourcesFirst();
             AutoCloseableResourcesSecond as = new AutoCloseableResourcesSecond()) {
            af.doSomething();
            as.doSomething();
            throw new MyUncheckedException();
        } catch (MyUncheckedException e) {
            System.out.println("try-with-resource end");
        } finally {
            System.out.println("this also works");
        }
    }
}
