package com.mcverse.jobify.user.model;

import java.time.LocalDateTime;

public class Cv extends File {

    public Cv(String id, String fileUrl, String fileType, LocalDateTime fileDate) {
        super(id, fileUrl, fileType, fileDate);
    }
}
