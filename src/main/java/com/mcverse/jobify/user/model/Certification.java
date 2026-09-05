package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "certifications")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id", nullable = false)
    private Seeker seeker;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String issuingOrganization;

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate expirationDate;

    private String credentialId;

    private String credentialUrl;

    protected Certification() {}

    public Certification(Seeker seeker, String name, String issuingOrganization, LocalDate issueDate,
                          LocalDate expirationDate, String credentialId, String credentialUrl) {
        this.seeker = seeker;
        this.name = name;
        this.issuingOrganization = issuingOrganization;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.credentialId = credentialId;
        this.credentialUrl = credentialUrl;
    }

    public String getId()                    { return id; }
    public Seeker getSeeker()                { return seeker; }
    public String getName()                  { return name; }
    public String getIssuingOrganization()   { return issuingOrganization; }
    public LocalDate getIssueDate()          { return issueDate; }
    public LocalDate getExpirationDate()     { return expirationDate; }
    public String getCredentialId()          { return credentialId; }
    public String getCredentialUrl()         { return credentialUrl; }

    public void setName(String name)                             { this.name = name; }
    public void setIssuingOrganization(String issuingOrganization) { this.issuingOrganization = issuingOrganization; }
    public void setIssueDate(LocalDate issueDate)                { this.issueDate = issueDate; }
    public void setExpirationDate(LocalDate expirationDate)      { this.expirationDate = expirationDate; }
    public void setCredentialId(String credentialId)             { this.credentialId = credentialId; }
    public void setCredentialUrl(String credentialUrl)           { this.credentialUrl = credentialUrl; }
}
