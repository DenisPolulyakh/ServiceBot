package com.neznayka.www.hibernate;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Polulyakh Denis
 *
 */
@Entity
@Table(name="LOGGING")
public class Logging {
    @Id
    @Column(name = "LOG_ID")
    private Long id;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "MESSAGE")
    private String message;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIME")
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}



