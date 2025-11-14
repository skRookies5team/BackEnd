package com.AIagnet.agent.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    /** PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    /** 이름 */
    @Column(name = "name", length = 100)
    private String name;

    /** 부서 */
    @Column(name = "dept", length = 100)
    private String dept;

    /** 직책(직무) */
    @Column(name = "job_title", length = 100)
    private String jobTitle;

    /** 주소(도/시) */
    @Column(name = "address_state", length = 50)
    private String addressState;

    /** 이메일 */
    @Column(name = "email", length = 255)
    private String email;

    /** 성별 */
    @Column(name = "gender", length = 10)
    private String gender;

    /** 고용형태 */
    @Column(name = "employment_status", length = 100)
    private String employmentStatus;

    /** 연봉(단위: 백만원) */
    @Column(name = "salary_million_won")
    private Integer salaryMillionWon;

    /** 시(지역) */
    @Column(name = "city", length = 100)
    private String city;

    /** 역할 */
    @Column(name = "job_role", length = 100)
    private String jobRole;

    /** 성과 점수 */
    @Column(name = "performance_score")
    private Integer performanceScore;

    /** 평가 등급 */
    @Column(name = "evaluation_grade")
    private Integer evaluationGrade;

    /** 직급 */
    @Column(name = "position", length = 100)
    private String position;

    /** 생년월일 */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /** 근속년수 */
    @Column(name = "years_of_service")
    private Integer yearsOfService;

    /** 입사일 */
    @Column(name = "hire_date")
    private LocalDate hireDate;
}
