package com.neznayka.www.hibernate;

import javax.persistence.*;
import java.util.List;

/**
 * @author Polulyakh Denis
 *
 */
@Entity
@Table(name="MESSAGE")
public class Message {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "VALUE")
    private String value;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "TAG")
    private List<Tag> tags;


    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}



