
package com.navya.job_portal.dto.response;

import com.navya.job_portal.entity.Job;
import lombok.Data;
import java.util.Date;

@Data
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private String salary;
    private String jobType;
    private String status;
    private String postedBy;
    private Date createdAt;

    public JobResponse(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.company = job.getCompany();
        this.location = job.getLocation();
        this.salary = job.getSalary();
        this.jobType = job.getJobType();
        this.status = job.getStatus().name();
        this.postedBy = job.getPostedBy().getFullName();
        this.createdAt = job.getCreatedAt();
    }
}