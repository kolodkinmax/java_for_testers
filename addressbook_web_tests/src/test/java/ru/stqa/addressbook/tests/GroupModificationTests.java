package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;

public class GroupModificationTests extends TestBase {

//    @Test
//    void canModifyGroup() {
//        if (app.hbm().getGroupCount() == 0) {
//            app.hbm().createGroup(new GroupData("", "", "", ""));
//        }
//        var oldGroups = app.hbm().getGroupList();
//        var rnd = new Random();
//        var index = rnd.nextInt(oldGroups.size());
//        var testData = new GroupData().withName("modified name");
//        app.groups().modifyGroup(oldGroups.get(index), testData);
//        var newGroups = app.hbm().getGroupList();
//        var expectedList = new ArrayList<>(oldGroups);
//        expectedList.set(index, testData.withId(oldGroups.get(index).id()));
//        Comparator<GroupData> compareById = (o1, o2) -> {
//            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
//        };
//        newGroups.sort(compareById);
//        expectedList.sort(compareById);
//        Assertions.assertEquals(newGroups, expectedList);
//    }

    @Test
    void canModifyGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "", "", ""));
        }
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        var testData = new GroupData().withName(CommonFunctions.randomString(10));
        app.groups().modifyGroup(oldGroups.get(index), testData);
        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.set(index, testData.withId(oldGroups.get(index).id()));
        var a = Set.copyOf(newGroups);
        var b = Set.copyOf(expectedList);
        Assertions.assertEquals(Set.copyOf(newGroups), Set.copyOf(expectedList));
    }
}
