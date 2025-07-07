package com.sau.authservice.dto;

import com.sau.authservice.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role; // CANDIDATE or EMPLOYER
    private String phone;
    private String companyName;
}
