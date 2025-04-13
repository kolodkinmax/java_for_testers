package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
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
        for (int i = 0; i < 5; i++) {
            var months = List.of("-", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            result.add(new ContactData("", randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    Integer.toString(new Random().nextInt(1, 31)),
                    new ArrayList<String>(months).get(new Random().nextInt(new ArrayList<String>(months).size())),
                    Integer.toString(new Random().nextInt(1950, 2030))));
        }
        return result;
    }


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

    @Test
    void canCreateContactWithAllEmptyFields() {
        app.contacts().createContact(new ContactData());
    }

    @Test
    void canCreateContactWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("Ivan"));
    }
}
