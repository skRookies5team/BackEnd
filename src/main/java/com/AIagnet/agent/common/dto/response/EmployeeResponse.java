package com.AIagnet.agent.common.dto.response;

import com.AIagnet.agent.common.entity.Employee;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    /** 직원 ID (DB: employee_id) */
    private Integer employeeId;

    /** 이름 */
    private String name;

    /** 부서 */
    private String dept;

    /** 직무/직책 */
    private String jobTitle;

    /** 지역(도/시) */
    private String addressState;

    /** 이메일 */
    private String email;

    /** 성별 */
    private String gender;

    /** 고용 형태 */
    private String employmentStatus;

    /** 연봉(백만원 단위) */
    private Integer salaryMillionWon;

    /** 도시 */
    private String city;

    /** 역할 */
    private String jobRole;

    /** 성과 점수 */
    private Integer performanceScore;

    /** 평가 등급 */
    private String evaluationGrade;

    /** 직급 */
    private String position;

    /** 근무지 주소(시/도) */
    private String dateOfBirth;

    /** 근속년수 */
    private Double yearsOfService;

    /** 입사일 */
    private String hireDate;

    /** Entity → DTO */
    public static EmployeeResponse from(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .dept(employee.getDept())
                .jobTitle(employee.getJobTitle())
                .addressState(employee.getAddressState())
                .email(employee.getEmail())
                .gender(employee.getGender())
                .employmentStatus(employee.getEmploymentStatus())
                .salaryMillionWon(employee.getSalaryMillionWon())
                .city(employee.getCity())
                .jobRole(employee.getJobRole())
                .performanceScore(employee.getPerformanceScore())
                .evaluationGrade(employee.getEvaluationGrade())
                .position(employee.getPosition())
                .dateOfBirth(employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : null)
                .yearsOfService(employee.getYearsOfService())
                .hireDate(employee.getHireDate() != null ? employee.getHireDate().toString() : null)
                .build();
    }
}
