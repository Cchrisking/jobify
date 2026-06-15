package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    private LocalDateTime creationDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_last_updates", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "updated_at")
    private List<LocalDateTime> lastUpdates;

    protected User() {}

    public User(String name, String lastName, String username) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.creationDate = LocalDateTime.now();
        this.lastUpdates = new ArrayList<>();
    }

    public String getId()                        { return id; }
    public String getUsername()                  { return username; }
    public String getName()                      { return name; }
    public String getLastName()                  { return lastName; }
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
}
