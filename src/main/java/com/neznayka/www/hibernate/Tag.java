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
    @Column(name = "ID",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "TAG", nullable = false)
    private String tag;


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


}
