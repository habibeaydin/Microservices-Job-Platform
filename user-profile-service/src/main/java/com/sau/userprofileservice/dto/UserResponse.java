package com.sau.userprofileservice.dto;

import com.sau.userprofileservice.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private String resume;

    private String phone;
    private String education;
    private String department;
    private String graduationYear;
    private String experienceCompany;
    private String experienceTitle;
    private String experienceYears;
}

