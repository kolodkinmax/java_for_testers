package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.contacts().getList().isEmpty()) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"));
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
                    "28", "April", "1987"));
        }
        app.contacts().selectAllContacts();
        app.contacts().removeSelectedContact();
        Assertions.assertEquals(0, app.contacts().getList().size());
    }
}
