package ru.stqa.addressbook.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstName : List.of("", "firstName")) {
//            for (var middleName : List.of("", "middleName")) {
//                for (var lastName : List.of("", "lastName")) {
//                    for (var nickname : List.of("", "nickname")) {
//                        for (var title : List.of("", "title")) {
//                            for (var company : List.of("", "company")) {
//                                for (var address : List.of("", "address")) {
//                                    for (var home : List.of("", "home")) {
//                                        for (var mobile : List.of("", "123")) {
//                                            for (var work : List.of("", "work")) {
//                                                for (var fax : List.of("", "123")) {
//                                                    for (var email : List.of("", "email")) {
//                                                        for (var email2 : List.of("", "email2")) {
//                                                            for (var email3 : List.of("", "email3")) {
//                                                                for (var homepage : List.of("", "homepage")) {
//                                                                    for (var bDay : List.of("", "-", "28")) {
//                                                                        for (var bMonth : List.of("", "-", "April")) {
//                                                                            for (var bYear : List.of("", "1987", "2026")) {
//                                                                                result.add(new ContactData(firstName, middleName, lastName, nickname, title, company, address, home, mobile, work, fax, email, email2, email3, homepage, bDay, bMonth, bYear));
//
//                                                                            }
//
//                                                                        }
//
//                                                                    }
//
//                                                                }
//
//                                                            }
//
//                                                        }
//
//                                                    }
//
//                                                }
//
//                                            }
//
//                                        }
//
//                                    }
//
//                                }
//
//                            }
//
//                        }
//
//                    }
//
//                }
//            }
//        }
        var json = "";
        try (var reader = new FileReader("contacts.json");
             var breader = new BufferedReader(reader)
        ) {
            var line = breader.readLine();
            while (line != null) {
                json = json + line + "\n";
                line = breader.readLine();
            }
        }
        ObjectMapper mapper = new JsonMapper();
        var value = mapper.readValue(json, new TypeReference<List<ContactData>>() {
        });
        result.addAll(value);
        return result;
    }

    public static List<ContactData> singleRandomGroup() {
        return List.of(new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(20))
                .withAddress(CommonFunctions.randomString(30)));
    }

    //старый тест и проверки по WEBу
    @ParameterizedTest
    @MethodSource("contactProvider")
    void canCreateMultipleContact(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.getLast().id())
                .withFirstName(newContacts.getLast().firstName())
                .withLastName(newContacts.getLast().lastName())
                .withDefaultValueExceptIdAndFirstNameAndLastName());
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    void canCreateContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.getLast().id())
                .withFirstName(newContacts.getLast().firstName())
                .withLastName(newContacts.getLast().lastName())
                .withPhoto(newContacts.getLast().photo()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @ParameterizedTest
    @MethodSource("singleRandomGroup")
    void canCreateContactInGroup(ContactData contact) {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "тест", "", ""));
        }
        var group = app.hbm().getGroupList().get(0);

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().create(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() + 1, newRelated.size());
    }

    @Test
    void canCreateContactWithAllEmptyFields() {
        app.contacts().createContact(new ContactData().withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
    }

    @Test
    void canCreateContactWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("Ivan").withPhoto(CommonFunctions.randomFile("src/test/resources/images")));
    }
}
