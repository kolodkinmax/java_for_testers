package ru.stqa.addressbook.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

    public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        for (var name : List.of("", "group name")) {
            for (var header : List.of("", "group header")) {
                for (var footer : List.of("", "group footer")) {
                    result.add(new GroupData().withName(name).withHeader(header).withFooter(footer));
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData()
                    .withName(CommonFunctions.randomString(i * 10))
                    .withHeader(CommonFunctions.randomString(i * 10))
                    .withFooter(CommonFunctions.randomString(i * 10)));
        }

        // Вариант с чтением файла по строчно
        var json = "";
        try (var reader = new FileReader("groups.json");
             var breader = new BufferedReader(reader)
        ) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line + "\n";
                line = breader.readLine();
            }
        }
//        var json = Files.readString(Paths.get("groups.json")); // вариант с чтением файла целиком
        ObjectMapper mapper = new JsonMapper();
//        var value = mapper.readValue(new File("groups.json"), new TypeReference<List<GroupData>>() {});
        var value = mapper.readValue(json, new TypeReference<List<GroupData>>() {
        });
        result.addAll(value);
        return result;
    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "group header", "group header")));
        return result;
    }

//    @ParameterizedTest
//    @ValueSource(strings = {"group name", "group name'"})         // в тестовый параметризованный метод передаются по очереди строки из аннотации @ValueSource в переменную name
//    public void canCreateGroup(String name) {
//        int groupCount = app.groups().getCount();
//        app.groups().createGroup(new GroupData(name, "group header", "group header"));
//        int newGroupCount = app.groups().getCount();
//        Assertions.assertEquals(groupCount + 1, newGroupCount);
//
//    }

    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateMultipleGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroups.sort(compareById);

        var expectedList = new ArrayList<>(oldGroups);
        expectedList.add(group.withId(newGroups.getLast().id())
                .withHeader(newGroups.getLast().header())
                .withFooter(newGroups.getLast().footer()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroups, expectedList);


        Assertions.assertEquals(oldGroups.size() + 1, newGroups.size());

    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        var oldGroups = app.groups().getList();
        app.groups().createGroup(group);
        var newGroups = app.groups().getList();
        Assertions.assertEquals(newGroups, oldGroups);

    }
}
