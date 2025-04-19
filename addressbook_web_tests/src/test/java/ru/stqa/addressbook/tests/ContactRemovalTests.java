package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.contacts().getList().isEmpty()) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987").withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
        }
        var oldContacts = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.contacts().getList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void canRemoveAllContactAtOnce() {
        if (app.contacts().getList().isEmpty()) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987").withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
        }
        app.contacts().selectAllContacts();
        app.contacts().removeSelectedContact();
        Assertions.assertEquals(0, app.contacts().getList().size());
    }
}
