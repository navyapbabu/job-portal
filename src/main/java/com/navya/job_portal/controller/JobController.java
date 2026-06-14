package com.navya.job_portal.controller;

import com.navya.job_portal.dto.request.JobRequest;
import com.navya.job_portal.dto.response.JobResponse;
import com.navya.job_portal.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Job posting and search APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class JobController {

    @Autowired
    private JobService jobService;

    @Operation(summary = "Post a new job",
            description = "Only EMPLOYER can post jobs")
    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.createJob(request, userDetails.getUsername()));
    }

    @Operation(summary = "Get all jobs",
            description = "Public endpoint — no token required")
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @Operation(summary = "Get job by ID")
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @Operation(summary = "Get my posted jobs",
            description = "Returns all jobs posted by logged in employer")
    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getMyJobs(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.getMyJobs(userDetails.getUsername()));
    }

    @Operation(summary = "Update a job",
            description = "Only the employer who posted the job can update it")
    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.updateJob(id, request, userDetails.getUsername()));
    }

    @Operation(summary = "Delete a job",
            description = "Only the employer who posted the job can delete it")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.deleteJob(id, userDetails.getUsername()));
    }

    @Operation(summary = "Search jobs by title keyword")
    @GetMapping("/search/title")
    public ResponseEntity<List<JobResponse>> searchByTitle(
            @RequestParam String keyword) {
        return ResponseEntity.ok(jobService.searchByTitle(keyword));
    }

    @Operation(summary = "Search jobs by location keyword")
    @GetMapping("/search/location")
    public ResponseEntity<List<JobResponse>> searchByLocation(
            @RequestParam String keyword) {
        return ResponseEntity.ok(jobService.searchByLocation(keyword));
    }
}