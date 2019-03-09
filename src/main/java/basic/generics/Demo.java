package basic.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class Person {
    public abstract void say();
}

class Driver extends Person {

    @Override
    public void say() {
        System.out.println("I am a driver");
    }
}

class Student extends Person {

    @Override
    public void say() {
        System.out.println("I am a student");
    }
}

public class Demo {

    public static void printCollection(Collection<?> c) {
        for (Object e : c) {
            System.out.println(e);
        }
    }

    public static void printPersons(Collection<? extends Person> c) {
        for (Person e : c) {
            e.say();
        }
    }

    static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o : a) {
            c.add(o);
        }
    }

    public static void main(String[] args) {
        // 通配符
        ArrayList<String> strings = new ArrayList<>();
        strings.add("aaa");
        strings.add("bb");
        strings.add("cc");
        Demo.printCollection(strings);

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        Demo.printCollection(integers);

        // 限制型通配符
        List<Driver> drivers = List.of(new Driver(), new Driver());
        List<Student> students = List.of(new Student(), new Student());
        Demo.printPersons(drivers);
        Demo.printPersons(students);

        // 泛型函数
        String[] sa = new String[100];
        Collection<String> cs = new ArrayList<>();
        Demo.fromArrayToCollection(sa, cs);
    }
}
