package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "role_user")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;
    private String meaning;

    public Role() {}

    public Role(String meaning) {
        this.meaning = meaning;
    }

    public Integer getId() {
        return role_id;
    }

    public void setId(Integer role_id) {
        this.role_id = role_id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
