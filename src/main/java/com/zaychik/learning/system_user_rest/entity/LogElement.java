package com.zaychik.learning.system_user_rest.entity;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("LogEntity")
public class LogElement {

    @Id
    private String id;
    @ArangoId
    private String arangoId;
    private String userEmail;
    private String url;
    private String method;
    private LocalDateTime dtEvent;
}
