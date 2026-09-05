package com.mcverse.jobify.cms.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "content_entries")
public class ContentEntry {

    @Id
    @Column(name = "content_key")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentCategory category;

    @Column(name = "content_value", nullable = false, length = 4000)
    private String value;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected ContentEntry() {}

    public ContentEntry(String key, ContentCategory category, String value) {
        this.key = key;
        this.category = category;
        this.value = value;
        this.updatedAt = LocalDateTime.now();
    }

    public String getKey()               { return key; }
    public ContentCategory getCategory()  { return category; }
    public String getValue()             { return value; }
    public LocalDateTime getUpdatedAt()  { return updatedAt; }

    public void setValue(String value) {
        this.value = value;
        this.updatedAt = LocalDateTime.now();
    }
}
