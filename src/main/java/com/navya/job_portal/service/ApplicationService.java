
package com.navya.job_portal.service;

import com.navya.job_portal.dto.request.StatusUpdateRequest;
import com.navya.job_portal.dto.response.ApplicationResponse;
import com.navya.job_portal.entity.Job;
import com.navya.job_portal.entity.JobApplication;
import com.navya.job_portal.entity.User;
import com.navya.job_portal.repository.ApplicationRepository;
import com.navya.job_portal.repository.JobRepository;
import com.navya.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // Jobseeker applies for a job
    public ApplicationResponse applyForJob(Long jobId, String email) {

        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (applicant.getRole() != User.Role.JOBSEEKER) {
            throw new RuntimeException("Only jobseekers can apply for jobs!");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found!"));

        if (job.getStatus() == Job.JobStatus.CLOSED) {
            throw new RuntimeException("This job is closed for applications!");
        }

        if (applicationRepository.existsByJobIdAndApplicantId(jobId, applicant.getId())) {
            throw new RuntimeException("You have already applied for this job!");
        }

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setApplicant(applicant);

        return new ApplicationResponse(applicationRepository.save(application));
    }

    // Jobseeker views their applications
    public List<ApplicationResponse> getMyApplications(String email) {
        User applicant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return applicationRepository.findByApplicantId(applicant.getId())
                .stream()
                .map(ApplicationResponse::new)
                .collect(Collectors.toList());
    }

    // Employer views applicants for a specific job
    public List<ApplicationResponse> getApplicantsForJob(Long jobId, String email) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found!"));

        if (!job.getPostedBy().getEmail().equals(email)) {
            throw new RuntimeException("You can only view applicants for your own jobs!");
        }

        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(ApplicationResponse::new)
                .collect(Collectors.toList());
    }

    // Employer updates application status
    public ApplicationResponse updateStatus(Long applicationId, StatusUpdateRequest request, String email) {

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found!"));

        if (!application.getJob().getPostedBy().getEmail().equals(email)) {
            throw new RuntimeException("You can only update applications for your own jobs!");
        }

        try {
            JobApplication.ApplicationStatus newStatus =
                    JobApplication.ApplicationStatus.valueOf(request.getStatus().toUpperCase());
            application.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status! Use APPLIED, REVIEWED, ACCEPTED or REJECTED");
        }

        return new ApplicationResponse(applicationRepository.save(application));
    }

    // Jobseeker withdraws application
    public String withdrawApplication(Long applicationId, String email) {

        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found!"));

        if (!application.getApplicant().getEmail().equals(email)) {
            throw new RuntimeException("You can only withdraw your own applications!");
        }

        applicationRepository.deleteById(applicationId);
        return "Application withdrawn successfully!";
    }
}