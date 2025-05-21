package ru.stqa.mantis.manager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import okhttp3.*;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.manager.developermail.AddUserResponse;
import ru.stqa.mantis.manager.developermail.GetIdsResponse;
import ru.stqa.mantis.manager.developermail.GetMessageResponse;
import ru.stqa.mantis.manager.developermail.GetToken;
import ru.stqa.mantis.model.DeveloperMailUser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.CookieManager;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DeveloperMailHelper extends HelperBase {

    OkHttpClient client;

    public static final MediaType JSON = MediaType.get("application/json");

    public DeveloperMailHelper(ApplicationManager manager) {
        super(manager);
        client = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager())).build();
    }

    public String getToken(String email, String password) {
        RequestBody body = RequestBody.create(
                String.format("{\"address\" : \""+email.trim().toLowerCase()+"\",\"password\" : \""+password.trim()+"\"}", email, password), JSON);
        Request request = new Request.Builder()
                .url("https://api.mail.tm/token")
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            var text = response.body().string();
            var getToken = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(text, GetToken.class);
            return getToken.token();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DeveloperMailUser addUser(String email, String password) {
        RequestBody body = RequestBody.create(
                String.format("{\"address\" : \""+email.trim().toLowerCase()+"\",\"password\" : \""+password.trim()+"\"}", email, password), JSON);
        Request request = new Request.Builder()
                .url("https://api.mail.tm/accounts")
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            var text = response.body().string();
            var addUserResponse = new ObjectMapper().readValue(text, AddUserResponse.class);
            if (addUserResponse.address().isEmpty()) {
                throw new RuntimeException(addUserResponse.address().toString());
            }
            return new DeveloperMailUser(addUserResponse.id(),  addUserResponse.address(), CommonFunctions.extractNameFromEmail(addUserResponse.address()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public void deleteUser(DeveloperMailUser user) {
//        Request request = new Request.Builder()
//                .url(String.format("https://www.developermail.com/api/v1/mailbox/%s", user.name()))
//                .header("X-MailboxToken", user.token())
//                .delete()
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public String receive(String token, Duration duration) {
        var start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + duration.toMillis()) {

            try {
                var text1 = get("https://api.mail.tm/messages", token);
                ArrayList<GetIdsResponse> response1 = new ObjectMapper().readValue(text1, new ObjectMapper().getTypeFactory().constructCollectionType(List.class, GetIdsResponse.class));
                if (response1.isEmpty()) {
                    throw new RuntimeException("Нет писем в ящике");
                }
                if (!response1.isEmpty()) {
                    var text2 = get(String.format("https://api.mail.tm/messages/%s", response1.getFirst().id()), token);
                    var response2 = new ObjectMapper().readValue(text2, GetMessageResponse.class);
                    if (response2.text().isEmpty()) {
                        throw new RuntimeException("Пустое тело письма");
                    }
                    return new String(MimeUtility.decode(
                            new ByteArrayInputStream(response2.text().getBytes()),
                            "quoted-printable").readAllBytes());
                }
            } catch (IOException | MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("No email");

    }

    String get(String url, String token) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
