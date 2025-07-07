package com.sau.authservice.feign.dto;

import com.sau.authservice.model.Role;
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

