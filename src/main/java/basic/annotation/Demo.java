package basic.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface Test {

}

class DemoTest {
    @Test
    public void should_add_works_well() {
        throw new AssertionError("not correct");
    }

    @Test
    public void should_sub_works_well() {
        System.out.println("sub works well");
    }

    @Test
    public void should_mul_works_well() {
        throw new NullPointerException("ooooops");
    }

    @Test
    public void should_div_works_well() {
        System.out.println("div works well");
    }
}

public class Demo {
    public static void main(String[] args) {
        Method[] declaredMethods = DemoTest.class.getDeclaredMethods();

        for (Method method: declaredMethods) {
            if (method.isAnnotationPresent(Test.class)) {
                try {
                    method.invoke(new DemoTest(), null);
                } catch (Exception e) {
                    System.out.println(method.getName() + " failed. caused by " + e.getCause().getClass().getSimpleName());
                }
            }
        }
    }
}
