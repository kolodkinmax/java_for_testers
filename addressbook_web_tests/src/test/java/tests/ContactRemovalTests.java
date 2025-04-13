package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"));
        }
        int contactCount = app.contacts().getCount();
        app.contacts().removeContact();
        int newContactCount = app.contacts().getCount();
        Assertions.assertEquals(contactCount - 1, newContactCount);
    }

    @Test
    public void canRemoveAllContactAtOnce() {
        if (app.contacts().getCount() == 0) {
            app.contacts().createContact(new ContactData("Макс", "Такой", "Самый",
                    "28", "April", "1987"));
        }
        app.contacts().selectAllContacts();
        app.contacts().removeSelectedContact();
        Assertions.assertEquals(0, app.contacts().getCount());
    }
}
