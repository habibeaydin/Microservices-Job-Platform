package com.sau.userprofileservice.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String phone;
    private String companyName;
    private String resume;

    private String education;
    private String department;
    private String graduationYear;

    private String experienceCompany;
    private String experienceTitle;
    private String experienceYears;
}
