package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "complex_molecular_system")
public class ComplexMolecularSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sms_id;
    private String name;

    public ComplexMolecularSystem() {}

    public ComplexMolecularSystem(String name) {
        this.name = name;

    }

    public Integer getComplexSystemId() {
        return sms_id;
    }

    public void setComplexSystemId(Integer sms_id) {
        this.sms_id = sms_id;
    }

    public String getName() {
        return name;
    }
}

