package com.sau.userprofileservice.dto;

import com.sau.userprofileservice.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long authUserId;
    private String fullName;
    private String phone;
    private Role role;
    private String resume;
    private String companyName;

    private String education;
    private String department;
    private String graduationYear;

    private String experienceCompany;
    private String experienceTitle;
    private String experienceYears;

}

