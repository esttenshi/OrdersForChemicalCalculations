package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "type_order")
public class TypeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeOrderId;
    
    private String meaning;

    public TypeOrder() {}

    public TypeOrder(String meaning) {
        this.meaning = meaning;
    }

    public Integer getId() {
        return typeOrderId;
    }

    public void setId(Integer typeOrderId) {
        this.typeOrderId = typeOrderId;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}

