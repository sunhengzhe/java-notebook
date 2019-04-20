package test.mockito;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.springframework.util.NumberUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MockitoTest {
    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public void say (String msg) {
            System.out.println(name + " say " + msg);
        }

        public String getName() {
            return name;
        }

        public Object echo(Object any) {
            return any;
        }
    }

    @Test
    public void verify_method_invoke_times() {
        Person person = mock(Person.class);

        person.say("boy");
        person.say("girl");
        person.say("girl");

        verify(person).say("boy");
        verify(person, times(1)).say("boy");
        verify(person, times(2)).say("girl");
    }

    @Test
    public void change_method_behavior() {
        Person person = mock(Person.class);

        when(person.getName()).thenReturn("mock!");

        assertEquals("mock!", person.getName());
    }

    @Test
    public void define_param_matcher() {
        Person person = mock(Person.class);

        when(person.echo(anyBoolean())).thenReturn(false);
        when(person.echo(anyInt())).thenReturn(99);
        when(person.echo(argThat(new ArgumentMatcher<>() {
            @Override
            public boolean matches(Object o) {
                return o.toString().startsWith("x");
            }
        }))).thenReturn("xxx");

        assertFalse((Boolean) person.echo(true));
        assertEquals(99, person.echo(100));
        assertEquals("xxx", person.echo("xyz"));
    }
}
