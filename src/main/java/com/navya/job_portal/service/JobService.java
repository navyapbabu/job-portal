
package com.navya.job_portal.service;

import com.navya.job_portal.dto.request.JobRequest;
import com.navya.job_portal.dto.response.JobResponse;
import com.navya.job_portal.entity.Job;
import com.navya.job_portal.entity.User;
import com.navya.job_portal.repository.JobRepository;
import com.navya.job_portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    // Create job — only employer can do this
    public JobResponse createJob(JobRequest request, String email) {

        User employer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (employer.getRole() != User.Role.EMPLOYER) {
            throw new RuntimeException("Only employers can post jobs!");
        }

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        job.setStatus(Job.JobStatus.OPEN);
        job.setPostedBy(employer);

        return new JobResponse(jobRepository.save(job));
    }

    // Get all open jobs — everyone can see
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(JobResponse::new)
                .collect(Collectors.toList());
    }

    // Get single job by id
    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found!"));
        return new JobResponse(job);
    }

    // Get all jobs posted by logged in employer
    public List<JobResponse> getMyJobs(String email) {
        User employer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return jobRepository.findByPostedById(employer.getId())
                .stream()
                .map(JobResponse::new)
                .collect(Collectors.toList());
    }

    // Update job — only the employer who posted it
    public JobResponse updateJob(Long id, JobRequest request, String email) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found!"));

        if (!job.getPostedBy().getEmail().equals(email)) {
            throw new RuntimeException(
                    "You can only update your own jobs!");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());

        return new JobResponse(jobRepository.save(job));
    }

    // Delete job — only the employer who posted it
    public String deleteJob(Long id, String email) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found!"));

        if (!job.getPostedBy().getEmail().equals(email)) {
            throw new RuntimeException(
                    "You can only delete your own jobs!");
        }

        jobRepository.deleteById(id);
        return "Job deleted successfully!";
    }

    // Search jobs by title
    public List<JobResponse> searchByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(JobResponse::new)
                .collect(Collectors.toList());
    }

    // Search jobs by location
    public List<JobResponse> searchByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location)
                .stream()
                .map(JobResponse::new)
                .collect(Collectors.toList());
    }
}