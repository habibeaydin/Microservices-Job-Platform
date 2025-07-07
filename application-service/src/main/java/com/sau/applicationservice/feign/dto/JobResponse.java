package com.sau.applicationservice.feign.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String status; // OPEN / CLOSED / FILLED
    private Long employerId;
    private String companyName;
}

