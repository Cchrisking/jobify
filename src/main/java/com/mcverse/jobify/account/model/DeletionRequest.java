package com.mcverse.jobify.account.model;

import com.mcverse.jobify.auth.model.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deletion_requests")
public class DeletionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role requesterRole;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeletionRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime resolvedAt;
    private String resolutionNote;

    protected DeletionRequest() {}

    public DeletionRequest(String username, Role requesterRole, String reason) {
        this.username = username;
        this.requesterRole = requesterRole;
        this.reason = reason;
        this.status = DeletionRequestStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    public String getId()                       { return id; }
    public String getUsername()                 { return username; }
    public Role getRequesterRole()               { return requesterRole; }
    public String getReason()                    { return reason; }
    public DeletionRequestStatus getStatus()     { return status; }
    public LocalDateTime getRequestedAt()        { return requestedAt; }
    public LocalDateTime getResolvedAt()         { return resolvedAt; }
    public String getResolutionNote()            { return resolutionNote; }

    public void resolve(DeletionRequestStatus status, String note) {
        this.status = status;
        this.resolutionNote = note;
        this.resolvedAt = LocalDateTime.now();
    }
}
