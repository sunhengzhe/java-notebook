package basic.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionTest {

    @Test
    public void use_asList_to_trans_array_to_list() {
        String[] strs = {"apple", "banana"};
        List<String> strings = Arrays.asList(strs);
        Assert.assertEquals(2, strings.size());
    }

    @Test
    public void use_int_stream_to_trans_array_to_list() {
        int[] numbers = {1, 2, 3};
        List<Integer> collect = IntStream.of(numbers)
                                         .boxed()
                                         .map(n -> n * 2)
                                         .collect(Collectors.toList());
        Assert.assertEquals(4, collect.get(1).intValue());
    }
}
