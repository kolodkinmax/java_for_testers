package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.stqa.addressbook.manager.ApplicationManager;

public class TestBase {

    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() {
        if (app == null) {
            app = new ApplicationManager();
        }
        app.init(System.getProperty("browser","chrome"));
    }

}
