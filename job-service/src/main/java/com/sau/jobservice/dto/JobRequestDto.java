package com.sau.jobservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDto {
    private String title;
    private String description;
    private String location;
    private String companyName;
}
