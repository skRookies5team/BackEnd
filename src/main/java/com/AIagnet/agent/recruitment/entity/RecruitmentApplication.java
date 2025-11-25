package com.AIagnet.agent.recruitment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 채용 지원서 엔터티.
 */
@Entity
@Table(name = "recruitment_applications")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitmentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "applicant_id", unique = true, nullable = false)
    private Integer applicantId;

    @Column(name = "application_date")
    private LocalDate applicationDate;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "education_level", length = 100)
    private String educationLevel;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "expected_salary", length = 50)
    private String expectedSalary;

    @Column(name = "applied_position", length = 200)
    private String appliedPosition;

    @Column(name = "application_status", length = 100)
    private String applicationStatus;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

