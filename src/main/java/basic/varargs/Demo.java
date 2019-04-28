package basic.varargs;

import java.util.stream.IntStream;

public class Demo {

    public static int add(int a, int b) {
        System.out.println("--- fixed params");
        return a - b;
    }

    public static int add(int... data) {
        return IntStream.of(data).sum();
    }

    public static int add(String name, int... data) {
        System.out.println("--- " + name);
        return IntStream.of(data).sum();
    }

    public static void main(String[] args) {
        System.out.println(add());
        System.out.println(add(1, 2));
        System.out.println(add(new int[] {3, 3, 3}));
        System.out.println(add("hah", 1, 2, 3));
    }
}
