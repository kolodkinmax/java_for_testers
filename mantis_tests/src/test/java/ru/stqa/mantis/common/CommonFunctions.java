package ru.stqa.mantis.common;

import ru.stqa.mantis.model.MailMessage;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
//    public static String randomString(int n) {
//        var rnd = new Random();
//        var result = "";
//        for (int i = 0; i < n; i++) {
//            result = result + (char)('a' + rnd.nextInt(26));
//        }
//        return result;
//    }

    public static String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        var result = Stream.generate(randomNumbers)
                .limit(n)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
        return result;
    }

    public static String randomFile(String dir) {
        var fileNames = new File(dir).list();
        var rnd = new Random();
        var index = rnd.nextInt(fileNames.length);
        return Paths.get(dir, fileNames[index]).toAbsolutePath().toString();
    }

    public static String extractUrl(List<MailMessage> messages) {
        String result = "";
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            result = url;
        }
        return result;
    }

    public static String extractUrl(String message) {
        String result = "";
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(message);
        if (matcher.find()) {
            var url = message.substring(matcher.start(), matcher.end());
            result = url;
        }
        return result;
    }

    public static String extractNameFromEmail(String email) {
        String result = "";
        var pattern = Pattern.compile("\\S*@");
        var matcher = pattern.matcher(email);
        if (matcher.find()) {
            var name = email.substring(matcher.start(), matcher.end() - 1);
            result = name;
        }
        return result;
    }
}
