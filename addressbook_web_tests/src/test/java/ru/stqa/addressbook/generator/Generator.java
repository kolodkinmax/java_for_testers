package ru.stqa.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--output", "-o"})
    String output;

    @Parameter(names = {"--format", "-f"})
    String format;

    @Parameter(names = {"--count", "-n"})
    int count;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();

    }

    private void run() throws IOException {
        var data = generate();
        save(data);
    }

    private Object generate() {
        if ("groups".equals(type)) {
            return generateGroups();
        } else if ("contacts".equals(type)) {
            return generateContacts();
        } else {
            throw new IllegalArgumentException("Неизвестный тип данных " + type);
        }
    }

    private Object generateGroups() {
        var result = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            result.add(new GroupData()
                    .withName(CommonFunctions.randomString(i * 10))
                    .withHeader(CommonFunctions.randomString(i * 10))
                    .withFooter(CommonFunctions.randomString(i * 10)));
        }
        return result;
    }

    private Object generateContacts() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            var months = List.of("-", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
            result.add(new ContactData("", CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10),
                    CommonFunctions.randomString(i * 10), CommonFunctions.randomFile("src/test/resources/images"), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10),
                    CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10),
                    CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10),
                    CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10), CommonFunctions.randomString(i * 10),
                    Integer.toString(new Random().nextInt(1, 31)),
                    new ArrayList<String>(months).get(new Random().nextInt(new ArrayList<String>(months).size())),
                    Integer.toString(new Random().nextInt(1950, 2030))));
        }
        return result;
    }

    private void save(Object data) throws IOException {
        if ("json".equals(format)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
//            mapper.writeValue(new File(output), data); // запись в создаваемый файл с переданным названием и переданными данными
            var json = mapper.writeValueAsString(data);

            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
        } else if ("yaml".equals(format)) {
            var mapper = new YAMLMapper();
            mapper.writeValue(new File(output), data);
        } else if ("xml".equals(format)) {
            var mapper = new XmlMapper();
            mapper.writeValue(new File(output), data);
        } else {
            throw new IllegalArgumentException("Неизвестный формат данных " + format);
        }
    }
}
