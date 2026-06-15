package com.mcverse.jobify.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private String id;
    private String name;
    private String lastName;
    private String password;
    private final LocalDateTime creationDate;
    private final List<LocalDateTime> lastUpdates;

    public User(String id, String name, String lastName, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.creationDate = LocalDateTime.now();
        this.lastUpdates = new ArrayList<>();
    }

    public String getId()                        { return id; }
    public String getName()                      { return name; }
    public String getLastName()                  { return lastName; }
    public String getPassword()                  { return password; }
    public LocalDateTime getCreationDate()       { return creationDate; }
    public List<LocalDateTime> getLastUpdates()  { return lastUpdates; }

    public void setName(String name) {
        this.name = name;
        this.lastUpdates.add(LocalDateTime.now());
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        this.lastUpdates.add(LocalDateTime.now());
    }

    public void setPassword(String password) {
        this.password = password;
        this.lastUpdates.add(LocalDateTime.now());
    }
}
