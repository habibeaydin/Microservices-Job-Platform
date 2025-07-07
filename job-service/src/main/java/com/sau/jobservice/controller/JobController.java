package com.sau.jobservice.controller;

import com.sau.jobservice.dto.JobRequestDto;
import com.sau.jobservice.dto.JobResponseDto;
import com.sau.jobservice.model.Status;
import com.sau.jobservice.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    //iş ilanı oluşturma
    @PostMapping("/create")
    public ResponseEntity<?> createJob(
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role,
            @RequestBody JobRequestDto jobRequest
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Sadece işverenler ilan oluşturabilir.");
        }

        Long employerId = Long.parseLong(userIdStr);
        JobResponseDto created = jobService.createJob(jobRequest, employerId);

        return ResponseEntity.ok(created);
    }

    //tüm ilanları getirme
    @GetMapping
    public ResponseEntity<List<JobResponseDto>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    //işverenin ilanlarını getirme
    @GetMapping("/employer")
    public ResponseEntity<List<JobResponseDto>> getJobsByEmployer(
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long employerId = Long.parseLong(userIdStr);
        return ResponseEntity.ok(jobService.getJobsByEmployer(employerId));
    }

    //İlan durumu güncelleme:  OPEN, CLOSED, FILLED
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateJobStatus(
            @PathVariable Long id,
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role,
            @RequestParam("status") String statusStr
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Yalnızca işverenler ilan güncelleyebilir.");
        }

        Long employerId = Long.parseLong(userIdStr);
        try {
            Status newStatus = Status.valueOf(statusStr.toUpperCase());
            boolean updated = jobService.updateStatus(id, employerId, newStatus);
            if (updated)
                return ResponseEntity.ok("İlan durumu güncellendi:" + updated);
            else
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bu ilana erişim izniniz yok.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Geçersiz status: " + statusStr);
        }
    }

    // ilan detay için
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long id,
            @RequestHeader("X-Auth-User-Id") String userIdStr,
            @RequestHeader("X-User-Role") String role,
            @RequestBody JobRequestDto updateRequest
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Yalnızca işverenler ilan güncelleyebilir.");
        }

        Long employerId = Long.parseLong(userIdStr);
        boolean updated = jobService.updateJob(id, employerId, updateRequest);

        if (updated)
            return ResponseEntity.ok("İlan başarıyla güncellendi.");
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bu ilana erişim izniniz yok.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(
            @PathVariable Long id,
            @RequestHeader("X-Auth-User-Id") String employerIdStr,
            @RequestHeader("X-User-Role") String role
    ) {
        if (!"EMPLOYER".equalsIgnoreCase(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Sadece işverenler ilan silebilir.");
        }

        Long employerId = Long.parseLong(employerIdStr);

        try {
            jobService.deleteJob(id, employerId);
            return ResponseEntity.ok("İlan başarıyla silindi.");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

