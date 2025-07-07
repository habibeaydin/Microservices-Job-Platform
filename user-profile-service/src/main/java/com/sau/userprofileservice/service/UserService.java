package com.sau.userprofileservice.service;

import com.sau.userprofileservice.dto.UserCreateRequest;
import com.sau.userprofileservice.dto.UserDto;
import com.sau.userprofileservice.dto.UserResponse;
import com.sau.userprofileservice.dto.UserUpdateRequest;
import com.sau.userprofileservice.exception.UserAlreadyExistsException;
import com.sau.userprofileservice.exception.UserNotFoundException;
import com.sau.userprofileservice.model.User;
import com.sau.userprofileservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserProfile(Long authUserId) {
        User user = userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new UserNotFoundException());

        return UserDto.builder()
                .authUserId(user.getAuthUserId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .resume(user.getResume())
                .companyName(user.getCompanyName())
                .build();
    }

    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByAuthUserId(request.getAuthUserId())) {
            throw new UserAlreadyExistsException();
        }

        User user = User.builder()
                .authUserId(request.getAuthUserId())
                .fullName(request.getFullName())
                .role(request.getRole())
                .companyName(request.getCompanyName())
                .phone(request.getPhone())
                .build();

        userRepository.save(user);
    }

    public UserDto updateUserProfile(Long authUserId, UserUpdateRequest request) {
        User user = userRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Güncellenen alanlar
        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getCompanyName() != null) user.setCompanyName(request.getCompanyName());
        if (request.getResume() != null) user.setResume(request.getResume());

        if (request.getEducation() != null) user.setEducation(request.getEducation());
        if (request.getDepartment() != null) user.setDepartment(request.getDepartment());
        if (request.getGraduationYear() != null) user.setGraduationYear(request.getGraduationYear());

        if (request.getExperienceCompany() != null) user.setExperienceCompany(request.getExperienceCompany());
        if (request.getExperienceTitle() != null) user.setExperienceTitle(request.getExperienceTitle());
        if (request.getExperienceYears() != null) user.setExperienceYears(request.getExperienceYears());

        userRepository.save(user);

        return UserDto.builder()
                .authUserId(user.getAuthUserId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .role(user.getRole())
                .resume(user.getResume())
                .companyName(user.getCompanyName())
                .education(user.getEducation())
                .department(user.getDepartment())
                .graduationYear(user.getGraduationYear())
                .experienceCompany(user.getExperienceCompany())
                .experienceTitle(user.getExperienceTitle())
                .experienceYears(user.getExperienceYears())
                .build();

    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}

