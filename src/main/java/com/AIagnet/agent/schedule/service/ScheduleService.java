package com.AIagnet.agent.schedule.service;

import com.AIagnet.agent.common.entity.Employee;
import com.AIagnet.agent.common.repository.EmployeeRepository;
import com.AIagnet.agent.exception.BusinessException;
import com.AIagnet.agent.exception.ErrorCode;
import com.AIagnet.agent.schedule.dto.request.ScheduleCancelRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleCreateRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleRecommendRequest;
import com.AIagnet.agent.schedule.dto.request.ScheduleSearchRequest;
import com.AIagnet.agent.schedule.dto.response.ScheduleRecommendResponse;
import com.AIagnet.agent.schedule.dto.response.ScheduleResponse;
import com.AIagnet.agent.schedule.entity.Schedule;
import com.AIagnet.agent.schedule.entity.ScheduleStatus;
import com.AIagnet.agent.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 일정 조회.
     */
    public List<ScheduleResponse> scanSchedules(ScheduleSearchRequest request) {
        ScheduleStatus status = Optional.ofNullable(request.getStatus()).orElse(ScheduleStatus.ACTIVE);

        List<Schedule> schedules;
        if (request.getEmployeeId() != null) {
            schedules = scheduleRepository.findByEmployeeAndRange(
                    request.getEmployeeId(),
                    request.getStartTime(),
                    request.getEndTime(),
                    status
            );
        } else {
            schedules = scheduleRepository.findByStatusAndRange(
                    status,
                    request.getStartTime(),
                    request.getEndTime()
            );
        }

        return schedules.stream()
                .map(ScheduleResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 일정 생성.
     */
    @Transactional
    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {
        validateConfirmation(request.getConfirmed(), "일정 등록은 사용자 확인이 필요합니다.");
        validateTimeRange(request.getStartTime(), request.getEndTime());

        Employee employee = getEmployee(request.getEmployeeId());
        boolean hasConflict = scheduleRepository.existsOverlap(
                employee.getEmployeeId(),
                request.getStartTime(),
                request.getEndTime(),
                ScheduleStatus.ACTIVE
        );

        if (hasConflict) {
            throw new BusinessException(ErrorCode.SCHEDULE_TIME_CONFLICT, "해당 시간에 이미 일정이 존재합니다.");
        }

        Schedule schedule = Schedule.builder()
                .employee(employee)
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .scheduleType(request.getScheduleType())
                .location(request.getLocation())
                .alertTime(request.getAlertTime())
                .status(ScheduleStatus.ACTIVE)
                .build();

        Schedule saved = scheduleRepository.save(schedule);
        log.info("일정 생성 완료: scheduleId={}", saved.getScheduleId());
        return ScheduleResponse.from(saved);
    }

    /**
     * 일정 취소.
     */
    @Transactional
    public ScheduleResponse cancelSchedule(Integer scheduleId, ScheduleCancelRequest request) {
        validateConfirmation(request.getConfirmed(), "일정 취소는 사용자 확인이 필요합니다.");

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND, "일정을 찾을 수 없습니다."));

        if (schedule.getStatus() == ScheduleStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.SCHEDULE_ALREADY_CANCELLED, "이미 취소된 일정입니다.");
        }

        schedule.setStatus(ScheduleStatus.CANCELLED);
        if (request.getReason() != null && !request.getReason().isBlank()) {
            String desc = schedule.getDescription() == null ? "" : schedule.getDescription() + "\n";
            schedule.setDescription(desc + "[취소 사유] " + request.getReason());
        }

        Schedule saved = scheduleRepository.save(schedule);
        log.info("일정 취소 완료: scheduleId={}", saved.getScheduleId());
        return ScheduleResponse.from(saved);
    }

    /**
     * 일정 추천.
     */
    public ScheduleRecommendResponse recommendSchedule(ScheduleRecommendRequest request) {
        Employee employee = getEmployee(request.getEmployeeId());

        LocalDateTime preferredStart = request.getPreferredStart();
        LocalDateTime preferredEnd = resolvePreferredEnd(request);
        validateTimeRange(preferredStart, preferredEnd);

        List<Schedule> schedules = scheduleRepository
                .findByEmployeeEmployeeIdAndStatusOrderByStartTimeAsc(employee.getEmployeeId(), ScheduleStatus.ACTIVE);

        Schedule conflict = schedules.stream()
                .filter(s -> overlaps(preferredStart, preferredEnd, s.getStartTime(), s.getEndTime()))
                .findFirst()
                .orElse(null);

        if (conflict == null) {
            return ScheduleRecommendResponse.builder()
                    .hasConflict(false)
                    .recommendedStart(preferredStart)
                    .recommendedEnd(preferredEnd)
                    .message("충돌 없이 해당 시간에 등록할 수 있습니다.")
                    .build();
        }

        Duration duration = Duration.between(preferredStart, preferredEnd);
        LocalDateTime candidateStart = conflict.getEndTime();
        LocalDateTime candidateEnd = candidateStart.plus(duration);

        for (Schedule existing : schedules) {
            if (!overlaps(candidateStart, candidateEnd, existing.getStartTime(), existing.getEndTime())) {
                continue;
            }
            candidateStart = existing.getEndTime();
            candidateEnd = candidateStart.plus(duration);
        }

        return ScheduleRecommendResponse.builder()
                .hasConflict(true)
                .conflictingScheduleId(conflict.getScheduleId())
                .recommendedStart(candidateStart)
                .recommendedEnd(candidateEnd)
                .message("요청 시간이 겹쳐 다른 시간대를 추천합니다.")
                .build();
    }

    private void validateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new BusinessException(ErrorCode.SCHEDULE_TIME_INVALID, "시작 시간은 종료 시간보다 빨라야 합니다.");
        }
    }

    private void validateConfirmation(Boolean confirmed, String message) {
        if (confirmed == null || !confirmed) {
            throw new BusinessException(ErrorCode.SCHEDULE_CONFIRMATION_REQUIRED, message);
        }
    }

    private LocalDateTime resolvePreferredEnd(ScheduleRecommendRequest request) {
        if (request.getPreferredEnd() != null) {
            return request.getPreferredEnd();
        }
        if (request.getDurationMinutes() == null) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "preferredEnd 또는 durationMinutes 중 하나는 필요합니다.");
        }
        return request.getPreferredStart().plusMinutes(request.getDurationMinutes());
    }

    private boolean overlaps(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    private Employee getEmployee(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMPLOYEE_NOT_FOUND, "직원을 찾을 수 없습니다."));
    }
}


