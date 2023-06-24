package com.zaychik.learning.system_user_rest.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document("tLogEntity")
public class LogElement {
    @Id
    private String id;

    @ArangoId
    private String arangoId;
    private String UserEmail;
    private String url;
    private String method;
    private String body;

    public LogElement() {
        super();
    }

    public LogElement(String userEmail, String url, String method, String body) {
        super();
        UserEmail = userEmail;
        this.url = url;
        this.method = method;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArangoId() {
        return arangoId;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
