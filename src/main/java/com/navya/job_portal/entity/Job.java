package com.navya.job_portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

    @Data
    @Entity
    @Table(name = "jobs")
    public class Job {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, length = 2000)
        private String description;

        @Column(nullable = false)
        private String company;

        @Column(nullable = false)
        private String location;

        @Column(nullable = false)
        private String salary;

        @Column(nullable = false)
        private String jobType;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private JobStatus status;

        @ManyToOne
        @JoinColumn(name = "employer_id", nullable = false)
        private User postedBy;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(updatable = false)
        private Date createdAt;

        @PrePersist
        protected void onCreate() {
            createdAt = new Date();
        }

        public enum JobStatus {
            OPEN, CLOSED
        }
    }

