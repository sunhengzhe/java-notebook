package basic.functional_interface;

import java.util.function.Predicate;

@FunctionalInterface
interface MyFunctionalInterface {
    void doIt();
}

public class Demo {
    public static void main(String[] args) {
        boolean isEqual = Predicate.isEqual("functional interface")
                                   .or(Predicate.isEqual("lambda expression"))
                                   .test("functional interface");
        System.out.println(isEqual);
    }
}
