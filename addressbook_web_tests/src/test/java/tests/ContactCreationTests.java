package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    void canCreateContact() {
        app.contacts().createContact(new ContactData("Максим", "Александрович", "Колодкин",
                "28", "April", "1987"));
    }

    @Test
    void canCreateContactWithAllEmptyFields() {
        app.contacts().createContact(new ContactData());
    }

    @Test
    void canCreateContactWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("Ivan"));
    }
}
