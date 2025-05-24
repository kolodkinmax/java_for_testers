package ru.stqa.addressbook.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.stqa.addressbook.manager.ApplicationManager;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.Random;

public class GroupRemovalTests extends TestBase {

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
                app.hbm().createGroup(new GroupData("", "group name3", "group header2", "group footer2"));
            }
        });
        var groupList = app.groups().getList();
        System.out.println(groupList.toString());

        Thread.sleep(10000);
        var groups = app.hbm().getGroupCount();
        System.out.println("Количество групп до удаления : " + groups);
        var groupsByWeb = app.groups().getCount();
        System.out.println("Количество групп до удаления по версии ВЭБа : " + groupsByWeb);

        app.groups().removeAllGroups();

        var groupList2 = app.groups().getList();
        System.out.println(groupList2.toString());

        Thread.sleep(10000);

        var groupNow = app.hbm().getGroupCount();
        System.out.println("Количество групп после удаления: " + groupNow);
        var groupsByWeb2 = app.groups().getCount();
        System.out.println("Количество групп после удаления по версии ВЭБа : " + groupsByWeb2);
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, groupNow);
        });
    }

}
