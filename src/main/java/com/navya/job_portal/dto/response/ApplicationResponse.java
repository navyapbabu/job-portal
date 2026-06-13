
package com.navya.job_portal.dto.response;

import com.navya.job_portal.entity.JobApplication;
import lombok.Data;
import java.util.Date;

@Data
public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private Long applicantId;
    private String applicantName;
    private String applicantEmail;
    private String status;
    private Date appliedAt;

    public ApplicationResponse(JobApplication application) {
        this.id = application.getId();
        this.jobId = application.getJob().getId();
        this.jobTitle = application.getJob().getTitle();
        this.company = application.getJob().getCompany();
        this.applicantId = application.getApplicant().getId();
        this.applicantName = application.getApplicant().getFullName();
        this.applicantEmail = application.getApplicant().getEmail();
        this.status = application.getStatus().name();
        this.appliedAt = application.getAppliedAt();
    }
}