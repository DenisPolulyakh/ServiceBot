package com.neznayka.www.hibernate;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Polulyakh Denis
 */
@Entity
@Table(name = "TAGS")
public class Tag {
    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TAG", nullable = false)
    private String tag;

    @ManyToOne
    /*@JoinTable(name="message_tags",
            joinColumns=@JoinColumn(name="TAG_ID"),
            inverseJoinColumns=@JoinColumn(name="MESSAGE_ID"))*/
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
