package com.sau.jobservice.service;

import com.sau.jobservice.dto.JobRequestDto;
import com.sau.jobservice.dto.JobResponseDto;
import com.sau.jobservice.model.Job;
import com.sau.jobservice.model.Status;
import com.sau.jobservice.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public JobResponseDto createJob(JobRequestDto request, Long employerId) {
        Job job = Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .employerId(employerId)
                .companyName(request.getCompanyName())
                .build();

        Job saved = jobRepository.save(job);

        return mapToDto(saved);
    }

    public List<JobResponseDto> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    private JobResponseDto mapToDto(Job job) {
        return JobResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .status(job.getStatus())
                .employerId(job.getEmployerId())
                .companyName(job.getCompanyName())
                .build();
    }

    public List<JobResponseDto> getJobsByEmployer(Long employerId) {
        return jobRepository.findAllByEmployerId(employerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public boolean updateStatus(Long jobId, Long employerId, Status newStatus) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if (optionalJob.isEmpty()) return false;

        Job job = optionalJob.get();
        if (!job.getEmployerId().equals(employerId)) return false;

        job.setStatus(newStatus);
        jobRepository.save(job);
        return true;
    }

    public Optional<JobResponseDto> getJobById(Long id) {
        return jobRepository.findById(id)
                .map(this::mapToDto);
    }

    public boolean updateJob(Long jobId, Long employerId, JobRequestDto updateRequest) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);

        if (optionalJob.isEmpty()) return false;

        Job job = optionalJob.get();

        // Yetki kontrolü
        if (!job.getEmployerId().equals(employerId)) return false;

        job.setTitle(updateRequest.getTitle());
        job.setDescription(updateRequest.getDescription());
        job.setLocation(updateRequest.getLocation());
        job.setCompanyName(updateRequest.getCompanyName());

        jobRepository.save(job);
        return true;
    }

    public void deleteJob(Long jobId, Long employerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("İlan bulunamadı"));

        if (!job.getEmployerId().equals(employerId)) {
            throw new RuntimeException("Sadece kendi ilanınızı silebilirsiniz.");
        }

        jobRepository.deleteById(jobId);
    }
}

