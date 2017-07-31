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
@Table(name="TAGS")
public class Tag {
    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TAG", nullable = false)
    private String tag;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "message_tags",  joinColumns = {
            @JoinColumn(name = "TAG_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "MESSAGE_ID",
                    nullable = false, updatable = false) })

   private Set<Message> message = new HashSet<Message>();


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

    public Set<Message> getMessage() {
        return message;
    }

    public void setMessage(Set<Message> message) {
        this.message = message;
    }
}
