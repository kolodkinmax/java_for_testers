package ru.stqa.mantis.manager.developermail;

import java.util.Date;
import java.util.List;

public record GetMessageResponse(String id, String msgid, Object from, List<Object> to, List<Object> cc, List<Object> bcc, String subject, String intro, Boolean seen, Boolean flagged, Boolean isDeleted, Object verifications,
                                 Boolean retention, Date retentionDate, String text, Object html, Boolean hasAttachments, Integer size, String downloadUrl, String sourceUrl, Date createdAt, Date updatedAt, String accountId) {
}