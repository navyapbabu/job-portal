package com.navya.job_portal.controller;

import com.navya.job_portal.dto.request.StatusUpdateRequest;
import com.navya.job_portal.dto.response.ApplicationResponse;
import com.navya.job_portal.service.ApplicationService;
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
@RequestMapping("/api/applications")
@Tag(name = "Applications", description = "Job application APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Operation(summary = "Apply for a job",
            description = "Only JOBSEEKER can apply")
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<ApplicationResponse> applyForJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                applicationService.applyForJob(jobId, userDetails.getUsername()));
    }

    @Operation(summary = "View my applications",
            description = "Jobseeker views all jobs they applied to")
    @GetMapping("/my-applications")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                applicationService.getMyApplications(userDetails.getUsername()));
    }

    @Operation(summary = "View applicants for a job",
            description = "Employer views all applicants for their job")
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicantsForJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                applicationService.getApplicantsForJob(jobId, userDetails.getUsername()));
    }

    @Operation(summary = "Update application status",
            description = "Employer updates status: APPLIED, REVIEWED, ACCEPTED, REJECTED")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody StatusUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                applicationService.updateStatus(applicationId, request, userDetails.getUsername()));
    }

    @Operation(summary = "Withdraw application",
            description = "Jobseeker withdraws their own application")
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<String> withdrawApplication(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                applicationService.withdrawApplication(applicationId, userDetails.getUsername()));
    }
}