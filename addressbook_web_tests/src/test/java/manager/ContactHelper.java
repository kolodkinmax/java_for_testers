package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactData contact) {
        openContactPage();
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactPage();
    }

    public void removeContact() {
        openContactPage();
        selectContact();
        removeSelectedContact();
    }

    private void returnToContactPage() {
        manager.driver.findElement(By.linkText("home page")).click();

    }

    private void submitContactCreation() {
        click(By.name("submit"));

    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("middlename"), contact.middleName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("nickname"), contact.nickname());
        type(By.name("title"), contact.title());
        type(By.name("company"), contact.company());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.home());
        type(By.name("mobile"), contact.mobile());
        type(By.name("work"), contact.work());
        type(By.name("fax"), contact.fax());
        type(By.name("email"), contact.email());
        type(By.name("email2"), contact.email2());
        type(By.name("email3"), contact.email3());
        type(By.name("homepage"), contact.homepage());
        selectType(By.name("bday"), contact.bDay());
        selectType(By.name("bmonth"), contact.bMonth());
        type(By.name("byear"), contact.bYear());

    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void openContactPage() {
        click(By.linkText("home"));
    }

    public boolean isContactPresent() {
        openContactPage();
        return manager.isElementPresent(By.name("selected[]"));
    }

    public void removeSelectedContact() {
        click(By.xpath("//input[@value=\'Delete\']"));
    }

    private void selectContact() {
        click(By.name("selected[]"));
    }

    public int getCount() {
        openContactPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void selectAllContacts() {
        openContactPage();
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox : checkboxes) {
            checkbox.click();
        }
    }

    public String getRandomMonth() {
        var allMonths = new ArrayList<String>(List.of("-", "January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"));
        return allMonths.get(new Random().nextInt(allMonths.size()));
    }
}
