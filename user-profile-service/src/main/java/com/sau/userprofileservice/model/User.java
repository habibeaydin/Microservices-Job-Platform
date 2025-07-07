package com.sau.userprofileservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles", uniqueConstraints = @UniqueConstraint(columnNames = "auth_user_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_user_id", nullable = false, unique = true)
    private Long authUserId;  // Auth-Service'teki kullanıcının ID’si

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Opsiyonel alanlar
    @Column(name = "resume")
    private String resume;

    @Column(name = "company_name")
    private String companyName;

    // Eğitim Bilgileri
    @Column(name = "education")
    private String education;

    @Column(name = "department")
    private String department;

    @Column(name = "graduation_year")
    private String graduationYear;

    // Deneyim Bilgileri
    @Column(name = "experience_company")
    private String experienceCompany;

    @Column(name = "experience_title")
    private String experienceTitle;

    @Column(name = "experience_years")
    private String experienceYears;
}
