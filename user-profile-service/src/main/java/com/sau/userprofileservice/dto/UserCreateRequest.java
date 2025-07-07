package com.sau.userprofileservice.dto;

import com.sau.userprofileservice.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {
    private Long authUserId;
    private String fullName;
    private Role role;
    private String phone;
    private String companyName;
}

