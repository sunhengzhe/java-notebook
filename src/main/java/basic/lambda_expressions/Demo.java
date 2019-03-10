package basic.lambda_expressions;

import java.util.Arrays;
import java.util.function.Predicate;

interface CheckPerson {
    boolean test(Person p);
}

class Person {
    private int age;
    private String name;

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
        Person younger = new Person("younger", 20);
        Person older = new Person("older", 60);

        CheckPerson checkPerson = p -> p.getAge() > 40;
        boolean test = checkPerson.test(younger);
        System.out.println(test);

        Predicate<Person> checkPerson2 = p -> p.getAge() > 40;
        boolean test1 = checkPerson2.test(older);
        System.out.println(test1);

        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        Arrays.stream(stringArray).forEach(e -> System.out.println(e));

    }
}
