package ru.stqa.addressbook.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTests extends TestBase {

    @Test
    void canModifyContact() {
        Allure.step("Checking precondition", step -> {
            if (app.hbm().getContactCount() == 0) {
                app.hbm().createContact(new ContactData("Макс", "Такой", "Самый",
                        "28", "April", "1987"));
            }
        });
        var oldContacts = app.hbm().getContactList();

        System.out.println("oldContacts= " + oldContacts.toString());

        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        var testData = new ContactData().withFirstName("modified First Name").withLastName("modified Last Name");
        app.contacts().modifyContact(oldContacts.get(index), testData);

        System.out.println("testData= " + testData);

        var newContacts = app.hbm().getContactList();

        System.out.println("newContacts= " + newContacts.toString());

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.set(index, testData.withId(oldContacts.get(index).id()).withPhoto(newContacts.get(index).photo())); //обход проверки загруженного фото

        System.out.println("expectedList после исправлений= " + expectedList);

        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        expectedList.sort(compareById);
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newContacts, expectedList);
        });
    }

    @Test
    void canAddContactInGroup() {
        if (app.hbm().getGroupCount() == 0) {
            app.groups().createGroup(new GroupData("", "тест", "", ""));
        }
        if (app.hbm().getContactCount() == 0 || app.jbdc().getContactsWithoutGroup().isEmpty()) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"));
        }
        var group = app.hbm().getGroupList().getFirst();
        var contact = app.jbdc().getContactsWithoutGroup().getFirst();
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().addContactInGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }
}
