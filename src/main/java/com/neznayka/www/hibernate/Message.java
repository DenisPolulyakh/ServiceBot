package com.neznayka.www.hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Polulyakh Denis
 *
 */
@Entity
@Table(name="MESSAGE")
public class    Message {
    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "VALUE")
    private String value;





    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "message_tags",  joinColumns = {
            @JoinColumn(name = "MESSAGE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "TAG_ID",
                    nullable = false, updatable = false) })
    private Set<Tag> tags = new HashSet<Tag>();*/

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   /* @JoinTable(name="message_tags",
            joinColumns=@JoinColumn(name="MESSAGE_ID"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID"))*/
    private Set<Tag> tags;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", tags=" + tags +
                '}';
    }
}



