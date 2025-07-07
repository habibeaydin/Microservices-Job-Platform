package com.sau.applicationservice.dto;

import lombok.*;
import com.sau.applicationservice.feign.dto.JobResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    private Long id;
    private Long candidateId;
    private Long jobId;
    private LocalDateTime appliedAt;
    private JobResponse job;
}

