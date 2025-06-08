package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "status_order")
public class StatusOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusOrderId;

    @Column(nullable = false, length = 255)
    private String meaning;

    public StatusOrder() {}

    public StatusOrder(String meaning) {
        this.meaning = meaning;
    }

    public Integer getId() {
        return statusOrderId;
    }

    public void setId(Integer statusOrderId) {
        this.statusOrderId = statusOrderId;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
