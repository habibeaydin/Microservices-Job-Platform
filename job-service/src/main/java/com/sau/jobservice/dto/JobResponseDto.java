package com.sau.jobservice.dto;

import com.sau.jobservice.model.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDto {
    private Long id;
    private String title;
    private String description;
    private String location;
    private Status status;
    private Long employerId;
    private String companyName;
}

