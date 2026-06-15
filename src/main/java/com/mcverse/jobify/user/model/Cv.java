package com.mcverse.jobify.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "cvs")
public class Cv extends File {

    protected Cv() {}

    public Cv(String fileUrl, String fileType, LocalDateTime fileDate) {
        super(fileUrl, fileType, fileDate);
    }
}
