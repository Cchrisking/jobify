package com.mcverse.jobify.user.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id", nullable = false)
    private Seeker seeker;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String fieldOfStudy;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private String grade;

    protected Education() {}

    public Education(Seeker seeker, String institution, String degree, String fieldOfStudy,
                      LocalDate startDate, LocalDate endDate, String grade) {
        this.seeker = seeker;
        this.institution = institution;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
    }

    public String getId()             { return id; }
    public Seeker getSeeker()         { return seeker; }
    public String getInstitution()    { return institution; }
    public String getDegree()         { return degree; }
    public String getFieldOfStudy()   { return fieldOfStudy; }
    public LocalDate getStartDate()   { return startDate; }
    public LocalDate getEndDate()     { return endDate; }
    public String getGrade()          { return grade; }

    public void setInstitution(String institution)   { this.institution = institution; }
    public void setDegree(String degree)             { this.degree = degree; }
    public void setFieldOfStudy(String fieldOfStudy) { this.fieldOfStudy = fieldOfStudy; }
    public void setStartDate(LocalDate startDate)    { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate)        { this.endDate = endDate; }
    public void setGrade(String grade)               { this.grade = grade; }
}
