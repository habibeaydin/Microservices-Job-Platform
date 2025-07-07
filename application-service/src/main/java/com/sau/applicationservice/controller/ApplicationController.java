package com.sau.applicationservice.controller;

import com.sau.applicationservice.dto.ApplicationRequest;
import com.sau.applicationservice.dto.ApplicationResponse;
import com.sau.applicationservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> applyToJob(
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role,
            @RequestBody ApplicationRequest request
    ) {
        if (!"CANDIDATE".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Sadece adaylar ilana başvurabilir.");
        }

        Long candidateId = Long.parseLong(userIdStr);
        try {
            ApplicationResponse response = applicationService.apply(candidateId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"CANDIDATE".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long candidateId = Long.parseLong(userIdStr);
        return ResponseEntity.ok(applicationService.getApplicationsByCandidate(candidateId));
    }

    @GetMapping("/employer")
    public ResponseEntity<?> getApplicationsForEmployer(
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Sadece işverenler başvuruları görebilir.");
        }

        Long employerId = Long.parseLong(userIdStr);
        List<ApplicationResponse> list = applicationService.getApplicationsForEmployer(employerId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/job/{jobId}/candidates")
    public ResponseEntity<?> getApplicationsByJob(
            @PathVariable Long jobId,
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Sadece işverenler başvuranları görebilir.");
        }

        Long employerId = Long.parseLong(userIdStr);

        try {
            return ResponseEntity.ok(
                    applicationService.getApplicationsByJob(jobId, employerId)
            );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

}

