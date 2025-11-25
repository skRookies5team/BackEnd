package com.AIagnet.agent.recruitment.dto.response;

import com.AIagnet.agent.recruitment.entity.RecruitmentApplication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 채용 지원서 응답 DTO.
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RecruitmentApplicationResponse {
    private Long id;
    private Integer applicantId;
    private LocalDate applicationDate;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String educationLevel;
    private Integer experienceYears;
    private String expectedSalary;
    private String appliedPosition;
    private String applicationStatus;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RecruitmentApplicationResponse from(RecruitmentApplication application) {
        return RecruitmentApplicationResponse.builder()
                .id(application.getId())
                .applicantId(application.getApplicantId())
                .applicationDate(application.getApplicationDate())
                .name(application.getName())
                .gender(application.getGender())
                .birthDate(application.getBirthDate())
                .phoneNumber(application.getPhoneNumber())
                .email(application.getEmail())
                .educationLevel(application.getEducationLevel())
                .experienceYears(application.getExperienceYears())
                .expectedSalary(application.getExpectedSalary())
                .appliedPosition(application.getAppliedPosition())
                .applicationStatus(application.getApplicationStatus())
                .address(application.getAddress())
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
}

