package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionsTests {

    @Test
    void arrayTests() {
        //var array = new String[]{"a", "b", "c"};
        var array = new String[3];
        Assertions.assertEquals(3, array.length);
        array[0] = "a";
        Assertions.assertEquals("a", array[0]);

        array[0] = "d";
        Assertions.assertEquals("d", array[0]);

    }

    @Test
    void listTests() {
        //var list = List.of("a", "b", "c"); // не модифицированный список, если таким образом инициализирован
        //var list = new ArrayList<String>(List.of("a", "b", "c")); // такой список изменяемый с указанием типа элементов, но он УЖЕ не обязательный т.к. мы указали копируемые данные из другого списка
        var list = new ArrayList<>(List.of("a", "b", "c"));
        Assertions.assertEquals(3, list.size());

        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("b", list.get(1));
        Assertions.assertEquals("c", list.get(2));


        list.set(0, "z");
        Assertions.assertEquals("z", list.get(0));

    }
}
