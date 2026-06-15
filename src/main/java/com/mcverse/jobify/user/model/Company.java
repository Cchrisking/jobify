package com.mcverse.jobify.user.model;

public class Company {

    private String id;
    private String name;

    public Company(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId()   { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
