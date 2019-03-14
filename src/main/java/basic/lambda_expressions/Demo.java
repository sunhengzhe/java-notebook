package basic.lambda_expressions;

import java.util.Arrays;
import java.util.function.Predicate;

interface CheckPerson {
    boolean test(Person p);
}

class Person {
    private int age;
    private String name;

    public static boolean checkPersonIfAgeBiggerThan(Person p, int age) {
        CheckPerson checkPerson = person -> {
            if (age < 0) {
                throw new Error();
            }

            return person.getAge() > age;
        };

        return checkPerson.test(p);
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}

public class Demo {
    public static void main(String[] args) {
        // Lambda 表达式
        Person younger = new Person("younger", 20);
        Person older = new Person("older", 60);

        CheckPerson checkPerson = p -> p.getAge() > 40;
        boolean test = checkPerson.test(younger);
        System.out.println(test);

        // 使用 Predicate
        Predicate<Person> predicate = p -> p.getAge() > 40;
        boolean test1 = predicate.test(older);
        System.out.println(test1);

        // Class::instance 方法引用
        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        Arrays.stream(stringArray).forEach(e -> System.out.println(e));

        // 闭包作用域
        System.out.println("scope demo: " + Person.checkPersonIfAgeBiggerThan(new Person("hah", 20), 26));
    }
}
