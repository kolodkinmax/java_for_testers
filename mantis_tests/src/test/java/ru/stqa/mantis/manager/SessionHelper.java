package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {

    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        type(By.name("username"), user);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public void addNewAccount(String user, String email) {
        click(By.cssSelector("a[href='signup_page.php']"));
        type(By.name("username"), user);
        click(By.cssSelector("input[name='username']"));
        type(By.name("email"), email);
        click(By.cssSelector("input[name='email']"));
        click(By.cssSelector("input[type='submit']"));
        click(By.linkText("Proceed"));
    }

    public void logout() {
        manager.driver().get(String.format("%s/logout_page.php", manager.property("web.baseUrl")));
    }

    public boolean isLoggedIn() {
        return isElementPresent(By.cssSelector("span.user-info"));
    }

    public void fillFormAfterCreateUser(String username, String password) {
        type(By.id("realname"), username);
        type(By.id("password"), password);
        type(By.id("password-confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }
}
