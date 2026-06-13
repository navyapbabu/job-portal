
package com.navya.job_portal.entity;

import jakarta.persistence.*;
        import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date appliedAt;

    @PrePersist
    protected void onCreate() {
        appliedAt = new Date();
        status = ApplicationStatus.APPLIED;
    }

    public enum ApplicationStatus {
        APPLIED, REVIEWED, ACCEPTED, REJECTED
    }
}