package basic.reflection;

import java.lang.reflect.Modifier;

interface ABC {

}

class Person {
    public void say(String msg) {
        System.out.println("Hey, " + msg);
    }
}

public class Demo {
    public static void main(String[] args) throws ClassNotFoundException {
        // 获取实例的 4 种方式
        Class by_get_class_of_instance = new Person().getClass();
        Class by_class_of_type = Person.class;
        Class by_class_for_name = Class.forName("basic.reflection.Person");
        Class by_TYPE = Double.TYPE;

        System.out.printf("4 methods to obtain class: %b %n", by_get_class_of_instance == by_class_of_type);
        System.out.printf("4 methods to obtain class: %b %n", by_class_of_type == by_class_for_name);
        System.out.printf("4 methods to obtain class: %b %n", by_TYPE == double.class);

        // 获取修饰符
        int abc = ABC.class.getModifiers();
        String s = Modifier.toString(abc);
        System.out.printf("get modifiers: %d -> %s", abc, s);
    }
}
