package ru.stqa.mantis.manager.developermail;

import java.util.Date;
import java.util.List;

public record GetIdsResponse(String id, String msgid, Object from, List<Object> to, String subject, String intro, Boolean seen, Boolean isDeleted, Boolean hasAttachments, Integer size, String downloadUrl, String sourceUrl, Date createdAt, Date updatedAt, String accountId) {
}