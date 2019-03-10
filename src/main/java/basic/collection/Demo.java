package basic.collection;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        Iterator<String> iterator = strings.iterator();
        String next = iterator.next();
        System.out.println("next is " + next);
        iterator.remove();
        System.out.println("and next is " + iterator.next());

        Object[] objects = strings.toArray();
        System.out.println(objects[0]);

        String[] strings1 = strings.toArray(new String[0]);
        System.out.println(strings1[1]);
    }
}
