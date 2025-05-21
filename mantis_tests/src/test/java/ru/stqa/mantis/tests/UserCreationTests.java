package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.DeveloperMailUser;

import java.time.Duration;

public class UserCreationTests extends TestBase {

    DeveloperMailUser user;

    @Test
    void canCreateUser() {
        // создать пользователя (адрес) на почтовом сервере (DeveloperMailHelper)
        var password = "password";
        var name = CommonFunctions.randomString(10);
//        token = app.developerMail().getToken();
        var email = String.format("%s@dcpa.net", name);
        user = app.developerMail().addUser(email, password);

        // создание аккаунта в Баг Трекере
        if (app.session().isLoggedIn()) {
            app.session().logout();
        }
        app.session().addNewAccount(user.name(), email);

        // ждем почту (DeveloperMailHelper)
        var token = app.developerMail().getToken(email, password);
        var message = app.developerMail().receive(token, Duration.ofSeconds(10));
        // извлекаем ссылку из письма;
        var url = CommonFunctions.extractUrl(message);
        // проходим по ссылке и завершаем регистрацию пользователя (браузер)
        app.driver().get(url);
        app.session().fillFormAfterCreateUser(user.name(), password);
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.http().login(user.name(), password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @Test
    void canReadLastMessage() {
        var email = "ujdtywmkdz@dcpa.net";
        var password = "password";

        // ждем почту (DeveloperMailHelper)
        var token = app.developerMail().getToken(email, password);
        var message = app.developerMail().receive(token, Duration.ofSeconds(10));
        Assertions.assertEquals("Второе письмо", message);
//        // извлекаем ссылку из письма;
//        var url = CommonFunctions.extractUrl(message);
//        // проходим по ссылке и завершаем регистрацию пользователя (браузер)
//        app.driver().get(url);
//        app.session().fillFormAfterCreateUser(user.name(), password);
//        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
//        app.http().login(user.name(), password);
//        Assertions.assertTrue(app.http().isLoggedIn());
    }

//    @AfterEach
//    void deleteMailUser() {
//        app.developerMail().deleteUser(user);
//    }

}
