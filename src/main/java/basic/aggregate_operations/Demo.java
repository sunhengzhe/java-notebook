package basic.aggregate_operations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

class Person {
    public enum Sex {
        MALE, FEMALE
    }

    LocalDate birthday;
    Sex gender;
    String emailAddress;


    private int age;
    private String name;

    public Person(String name, int age, Sex gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Sex getGender() {
        return gender;
    }
}

class Averager {
    private int total = 0;
    private int count = 0;

    public double average() {
        return count > 0 ? ((double) total)/count : 0;
    }

    public void accept(int i) { total += i; count++; }
    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}

public class Demo {

    public static void main(String[] args) {
        // ---------
        List<Person> roster = List.of(
                new Person("张三", 20, Person.Sex.FEMALE),
                new Person("张武", 22, Person.Sex.FEMALE),
                new Person("张柳", 24, Person.Sex.FEMALE),
                new Person("李四", 17, Person.Sex.MALE),
                new Person("李武", 20, Person.Sex.MALE)
        );

        Averager averageCollect = roster.stream()
                                 .filter(p -> p.getGender() == Person.Sex.MALE)
                                 .map(p -> p.getAge())
                                 .collect(Averager::new, Averager::accept, Averager::combine);

        System.out.println("Average age of male members: " +
                                   averageCollect.average());

        // ----------
        List<String> namesOfMaleMembersCollect = roster
                .stream()
                .filter(p -> p.getGender() == Person.Sex.MALE)
                .map(p -> p.getName())
                .collect(Collectors.toList());
        System.out.println(namesOfMaleMembersCollect);

        // ----------
        Map<Person.Sex, List<Person>> byGender = roster
                .stream()
                .collect(Collectors.groupingBy(Person::getGender));
        System.out.println(byGender.keySet());

        // ----------
        Map<Person.Sex, Double> totalAgeByGender =
                roster
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        Person::getGender,
                                        Collectors.averagingInt(Person::getAge)));
        System.out.println(totalAgeByGender.get(Person.Sex.MALE));
    }
}
