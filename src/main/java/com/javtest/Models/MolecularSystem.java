package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "molecular_system")
public class MolecularSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ms_id;
    private String name;

    public MolecularSystem() {}

    public MolecularSystem(String name, String rus_name, Integer id_folder) {
        this.name = name;

    }

    public Integer getSystemId() {
        return ms_id;
    }

    public void setSystemId(Integer ms_id) {
        this.ms_id = ms_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

