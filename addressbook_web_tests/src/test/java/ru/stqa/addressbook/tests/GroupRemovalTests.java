package ru.stqa.addressbook.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.stqa.addressbook.manager.ApplicationManager;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

    @Disabled("Тест приводит к ошибке \"DB is corrupted\"")
    @Test
    public void CanRemoveGroup() {
        Allure.step("Checking precondition", step -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "", "", ""));
            }
        });
        var oldGroups = app.hbm().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.hbm().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newGroups, expectedList);
        });
    }

    @Test
    void canRemoveAllGroupsAtOnce() throws InterruptedException {
        Allure.step("Checking precondition", step -> {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
            }
        });
        app.groups().removeAllGroups();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, app.hbm().getGroupCount());
        });
    }

}
