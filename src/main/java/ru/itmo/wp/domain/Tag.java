package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(indexes = {@Index(columnList = "name", unique = true), @Index(columnList = "name")})
public class Tag implements Comparable<Tag> {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    public Tag() {
    }

    public Tag(@NotNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Tag other) {
        if (other == null) {
            return 1;
        }

        return this.getName().compareTo(other.getName());
    }
}
