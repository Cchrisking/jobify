package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    protected Company() {}

    public Company(String name) {
        this.name = name;
    }

    public String getId()   { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
