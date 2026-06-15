package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class File {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fileUrl;
    private String fileType;
    private LocalDateTime fileDate;

    protected File() {}

    public File(String fileUrl, String fileType, LocalDateTime fileDate) {
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileDate = fileDate;
    }

    public String getId()              { return id; }
    public String getFileUrl()         { return fileUrl; }
    public String getFileType()        { return fileType; }
    public LocalDateTime getFileDate() { return fileDate; }

    public void setFileUrl(String fileUrl)          { this.fileUrl = fileUrl; }
    public void setFileType(String fileType)        { this.fileType = fileType; }
    public void setFileDate(LocalDateTime fileDate) { this.fileDate = fileDate; }
}
