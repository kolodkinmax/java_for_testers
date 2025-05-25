package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.stqa.addressbook.manager.ApplicationManager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() throws IOException {
        if (app == null) {
            var properties = new Properties();
            properties.load(new FileReader(System.getProperty("target", "local.properties")));
            app = new ApplicationManager();
            app.init(properties.getProperty("web.browser", "chrome"), properties);
        }
    }

//    @AfterEach
//    void checkDatabaseConsistency() {
//        app.jbdc().checkConsistency();
//    }

}
