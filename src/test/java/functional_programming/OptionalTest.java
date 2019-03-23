package functional_programming;

import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalTest {

    @Test
    public void create_optional_with_empty() {
        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());
    }

    @Test
    public void create_optional_with_of() {
        Optional<String> java = Optional.of("Java");
        assertTrue(java.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void create_optional_with_of_with_null() {
        Optional.of(null);
    }

    @Test
    public void create_optional_with_ofNullable() {
        Optional<String> java = Optional.ofNullable("Java");
        assertTrue(java.isPresent());

        Optional<Object> aNull = Optional.ofNullable(null);
        assertFalse(aNull.isPresent());
    }

    @Test
    public void check_optional_with_isPresent() {
        Optional<String> java = Optional.ofNullable("java");
        Optional<Object> aNull = Optional.ofNullable(null);

        assertTrue(java.isPresent());
        assertFalse(aNull.isPresent());
    }

    @Test
    public void condition_action_ifPresent() {
        Optional<String> java = Optional.ofNullable("java");
        java.ifPresent(value -> System.out.println("ifPresent accept " + value));

        Optional<Object> aNull = Optional.ofNullable(null);
        aNull.ifPresent(value -> System.out.println("this will never execute"));
    }

    @Test
    public void condition_action_orElse() {
        assertTrue(Optional.ofNullable("java")
                           .orElse("javascript")
                           .equals("java"));
        assertTrue(Optional.ofNullable(null)
                           .orElse("java")
                           .equals("java"));
    }

    @Test
    public void condition_action_orElseGet() {
        assertTrue(Optional.ofNullable("java")
                           .orElseGet(() -> "javascript")
                           .equals("java"));
        assertTrue(Optional.ofNullable(null)
                           .orElseGet(() -> "java")
                           .equals("java"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void condition_action_orElseThrow() {
        Optional.ofNullable(null)
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getDefaultName() {
        System.out.println("You got a default name");
        return "default";
    }

    @Test
    public void difference_between_orElse_and_orElseGet() {
        Optional<String> java = Optional.of("java");

        System.out.println("orElse:");
        assertEquals("java", java.orElse(getDefaultName()));
        System.out.println("orElseGet:");
        assertEquals("java", java.orElseGet(this::getDefaultName));
    }

    @Test
    public void get_optional_with_of() {
        Optional<String> java = Optional.of("java");
        assertEquals("java", java.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void get_optional_with_of_with_null() {
        Optional.empty()
                .get();
    }

    @Test
    public void test_optional_by_filter() {
        Integer nullYear = null;
        Optional<Integer> integer = Optional.ofNullable(nullYear)
                                            .filter(value -> value == 2018);
        assertEquals(Optional.empty(), integer);

        Integer year = 2019;
        Optional<Integer> integer1 = Optional.ofNullable(year)
                                             .filter(value -> value == 2018);
        assertEquals(Optional.empty(), integer1);

        Optional<Integer> integer2 = Optional.ofNullable(year)
                                             .filter(value -> value == 2019);
        assertEquals("Optional[2019]", integer2.toString());
    }

    @Test
    public void map_optional() {
        Optional<String> java = Optional.of("java");
        String result = java.map(String::toUpperCase)
                            .orElse("");
        assertEquals("JAVA", result);
    }

    public class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }
    }

    @Test
    public void flatMap_optional() {
        Person person = new Person("john");
        Optional<Person> personOptional = Optional.of(person);

        String byMap = personOptional.map(Person::getName)
                                     // 需要手动打开包装
                                     .orElse(Optional.empty())
                                     .orElse("");

        String byFlatMap = personOptional.flatMap(Person::getName)
                                         .orElse("");

        assertEquals("john", byMap);
        assertEquals("john", byFlatMap);
    }
}
