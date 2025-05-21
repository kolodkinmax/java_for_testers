package ru.stqa.mantis.manager.developermail;

import ru.stqa.mantis.model.DeveloperMailUser;

import java.time.LocalDateTime;
import java.util.Date;

public record AddUserResponse(String id, String address, Long quota, Integer used, Boolean isDisabled, Boolean isDeleted, Date createdAt, Date updatedAt) {
}