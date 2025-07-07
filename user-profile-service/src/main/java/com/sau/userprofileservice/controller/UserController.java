package com.sau.userprofileservice.controller;

import com.sau.userprofileservice.dto.UserCreateRequest;
import com.sau.userprofileservice.dto.UserDto;
import com.sau.userprofileservice.dto.UserResponse;
import com.sau.userprofileservice.dto.UserUpdateRequest;
import com.sau.userprofileservice.model.User;
import com.sau.userprofileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Auth-Service'ten gelen istekle yeni kullanıcı profili oluşturur
     */
    @PostMapping("/create")
    public ResponseEntity<Void> createUserProfile(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Token doğrulaması geçmiş kullanıcının profilini döner
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(
            @RequestHeader("X-Auth-User-Id") String authUserIdStr
    ) {
        System.out.println(">>> [UserService] Header X-Auth-User-Id = " + authUserIdStr);

        Long authUserId = Long.parseLong(authUserIdStr);
        UserDto profile = userService.getUserProfile(authUserId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(
            @RequestHeader("X-Auth-User-Id") String authUserIdStr,
            @RequestBody UserUpdateRequest updateRequest
    ) {
        Long authUserId = Long.parseLong(authUserIdStr);
        UserDto updatedProfile = userService.updateUserProfile(authUserId, updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(null) // Email tutulmuyor ise null dön
                .role(user.getRole())
                .resume(user.getResume())
                .phone(user.getPhone())
                .education(user.getEducation())
                .department(user.getDepartment())
                .graduationYear(user.getGraduationYear())
                .experienceCompany(user.getExperienceCompany())
                .experienceTitle(user.getExperienceTitle())
                .experienceYears(user.getExperienceYears())
                .build();
        return ResponseEntity.ok(response);
    }


}
