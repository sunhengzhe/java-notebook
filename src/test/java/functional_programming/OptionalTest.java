package functional_programming;

import org.junit.Test;

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
        assertTrue(Optional.ofNullable("java").orElse("javascript").equals("java"));
        assertTrue(Optional.ofNullable(null).orElse("java").equals("java"));
    }

    @Test
    public void condition_action_orElseGet() {
        assertTrue(Optional.ofNullable("java").orElseGet(() -> "javascript").equals("java"));
        assertTrue(Optional.ofNullable(null).orElseGet(() -> "java").equals("java"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void condition_action_orElseThrow() {
        Optional.ofNullable(null).orElseThrow(IllegalArgumentException::new);
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
        Optional<String> java = Optional.of("Java");
        assertEquals("java", java.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void get_optional_with_of_with_null() {
        Optional.empty().get();
    }
}
