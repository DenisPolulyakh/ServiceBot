package com.neznayka.www.hibernate;




import javax.persistence.*;
import java.util.List;

/**
 * @author Polulyakh Denis
 *
 */
@Entity
@Table(name="TAGS")
public class Tag {
    @Id
    @Column(name = "TAG_ID",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TAG", nullable = false)
    private String tag;

   /* @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID", updatable = false, insertable = false, nullable=false)
    private Message message;*/


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

   /* public Message getMessageId() {
        return message;
    }

    public void setMessageId(Message message) {
        this.message = message;
    }*/
}
