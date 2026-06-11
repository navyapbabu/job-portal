
package com.navya.job_portal.controller;

import com.navya.job_portal.dto.request.JobRequest;
import com.navya.job_portal.dto.response.JobResponse;
import com.navya.job_portal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.createJob(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobResponse>> getMyJobs(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.getMyJobs(userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.updateJob(id, request, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobService.deleteJob(id, userDetails.getUsername()));
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<JobResponse>> searchByTitle(
            @RequestParam String keyword) {
        return ResponseEntity.ok(jobService.searchByTitle(keyword));
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<JobResponse>> searchByLocation(
            @RequestParam String keyword) {
        return ResponseEntity.ok(jobService.searchByLocation(keyword));
    }
}