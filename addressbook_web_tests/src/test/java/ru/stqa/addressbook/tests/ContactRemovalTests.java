package ru.stqa.addressbook.tests;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        Allure.step("Checking precondition", step -> {
            if (app.hbm().getContactCount() == 0) {
                app.hbm().createContact(new ContactData("Макс", "Такой", "Самый",
                        "28", "April", "1987"));
            }
        });
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void canRemoveContactInGroup() {
        if (app.jbdc().getContactsInGroup().isEmpty()) {
            if (app.hbm().getGroupCount() == 0) {
                app.hbm().createGroup(new GroupData("", "Test", "", ""));
            }
            var group = app.hbm().getGroupList().get(0);
            app.contacts().create(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"), group);
        }
        var contactNumber = app.jbdc().getContactsInGroup().keySet().stream().findFirst().get();
        var groupNumber = app.jbdc().getContactsInGroup().get(contactNumber);
        var group = new GroupData().withId("" + groupNumber);
        var contact = new ContactData().withId("" + contactNumber);
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().removeInGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(newRelated.size() + 1, oldRelated.size());
    }

    @Test
    public void canRemoveAllContactAtOnce() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"));
        }
        app.contacts().selectAllContacts();
        app.contacts().removeSelectedContact();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, app.hbm().getContactCount());
        });
    }
}
