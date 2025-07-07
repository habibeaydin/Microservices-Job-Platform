package com.sau.applicationservice.service;

import com.sau.applicationservice.dto.ApplicationRequest;
import com.sau.applicationservice.dto.ApplicationResponse;
import com.sau.applicationservice.dto.ApplicationWithUserResponse;
import com.sau.applicationservice.exception.AlreadyAppliedException;
import com.sau.applicationservice.exception.JobClosedException;
import com.sau.applicationservice.feign.JobClient;
import com.sau.applicationservice.feign.UserClient;
import com.sau.applicationservice.feign.dto.JobResponse;
import com.sau.applicationservice.feign.dto.UserResponse;
import com.sau.applicationservice.model.Application;
import com.sau.applicationservice.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobClient jobClient;
    private final UserClient userClient;

    public ApplicationResponse apply(Long candidateId, ApplicationRequest request) {
        JobResponse job = jobClient.getJobById(request.getJobId());

        if (!"OPEN".equalsIgnoreCase(job.getStatus())) {
            throw new JobClosedException();
        }

        if (applicationRepository.existsByCandidateIdAndJobId(candidateId, request.getJobId())) {
            throw new AlreadyAppliedException();
        }

        Application app = Application.builder()
                .candidateId(candidateId)
                .jobId(request.getJobId())
                .appliedAt(LocalDateTime.now())
                .build();

        return mapToDto(applicationRepository.save(app));
    }

    public List<ApplicationResponse> getApplicationsByCandidate(Long candidateId) {
        return applicationRepository.findAllByCandidateId(candidateId)
                .stream().map(this::mapToDto).toList();
    }

    private ApplicationResponse mapToDto(Application app) {
        JobResponse job = null;
        try {
            job = jobClient.getJobById(app.getJobId());
        } catch (Exception e) {
            // ❗ Hata loglanmalı, ancak sistem patlamamalı
            System.err.println("Job bilgisi alınamadı: " + e.getMessage());
        }

        return ApplicationResponse.builder()
                .id(app.getId())
                .candidateId(app.getCandidateId())
                .jobId(app.getJobId())
                .appliedAt(app.getAppliedAt())
                .job(job)
                .build();
    }

    public List<ApplicationResponse> getApplicationsForEmployer(Long employerId) {
        // 1) İşverenin ilanlarını al
        List<JobResponse> jobs = jobClient.getJobsByEmployer(String.valueOf(employerId));
        List<Long> jobIds = jobs.stream()
                .map(JobResponse::getId)
                .toList();

        // 2) Bu ilanlara yapılan başvuruları çek
        List<Application> apps = applicationRepository.findAllByJobIdIn(jobIds);

        // 3) DTO’ya dönüştür
        return apps.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<ApplicationWithUserResponse> getApplicationsByJob(Long jobId, Long employerId) {
        JobResponse job = jobClient.getJobById(jobId);

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("Bu ilana erişim izniniz yok.");
        }

        return applicationRepository.findAllByJobId(jobId)
                .stream()
                .map(app -> {
                    UserResponse user = userClient.getUserById(app.getCandidateId());
                    return new ApplicationWithUserResponse(
                            app.getId(),
                            app.getCandidateId(),
                            user.getFullName(),
                            user.getEmail(),
                            app.getJobId(),
                            app.getAppliedAt()
                    );
                })
                .toList();
    }


}

