package kr.or.wds.project.mapper;

import org.springframework.stereotype.Component;

import kr.or.wds.project.common.enums.Day;
import kr.or.wds.project.common.enums.Status;
import kr.or.wds.project.dto.request.ExpertScheduleSettingRequest;
import kr.or.wds.project.entity.ExpertEntity;
import kr.or.wds.project.entity.ExpertScheduleSettingEntity;
import kr.or.wds.project.exception.CustomException; 
import kr.or.wds.project.exception.ExceptionType;
import kr.or.wds.project.repository.ExpertRepository;
import kr.or.wds.project.repository.ExpertScheduleSettingRepository;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class ExpertScheduleSettingMapper {

    private final ExpertRepository expertRepository;
    private final ExpertScheduleSettingRepository expertScheduleSettingRepository;

    private boolean isValidDayOfWeek(ExpertEntity expert, Day dayOfWeek) {
        return expertScheduleSettingRepository.existsByExpertIdAndDayOfWeek(expert.getId(), dayOfWeek);
    }

    public ExpertScheduleSettingEntity toEntity(ExpertScheduleSettingRequest request) {
        System.out.println("request: " + request);
        ExpertEntity expert = expertRepository.findById(request.getExpertId())
                .orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));
        System.out.println("expert: " + expert);
        if (isValidDayOfWeek(expert, request.getDayOfWeek())) {
            throw new CustomException(ExceptionType.INVALID_DAY_OF_WEEK);
        }
        
        return ExpertScheduleSettingEntity.builder()
                .expert(expert)
                .settingDate(request.getSettingDate())
                .dayOfWeek(request.getDayOfWeek())
                .availableStartTime(request.getAvailableStartTime())
                .availableEndTime(request.getAvailableEndTime())
                .minBookingDurationHours(request.getMinBookingDurationHours())  
                .maxBookingDurationHours(request.getMaxBookingDurationHours())
                .availableDays(request.getAvailableDays())
                .preferredTimeRanges(request.getPreferredTimeRanges())
                .status(Status.ACTIVE)
                .build();
    }

    public ExpertScheduleSettingEntity toUpdateEntity(Long id, ExpertScheduleSettingRequest request) {
        ExpertScheduleSettingEntity expertScheduleSetting = expertScheduleSettingRepository.findById(id).orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));
        expertScheduleSetting.setSettingDate(request.getSettingDate());
        expertScheduleSetting.setDayOfWeek(request.getDayOfWeek());
        expertScheduleSetting.setAvailableStartTime(request.getAvailableStartTime());
        expertScheduleSetting.setAvailableEndTime(request.getAvailableEndTime());
        expertScheduleSetting.setMinBookingDurationHours(request.getMinBookingDurationHours());
        expertScheduleSetting.setMaxBookingDurationHours(request.getMaxBookingDurationHours());
        expertScheduleSetting.setAvailableDays(request.getAvailableDays());
        expertScheduleSetting.setPreferredTimeRanges(request.getPreferredTimeRanges());
        expertScheduleSetting.setStatus(Status.ACTIVE);
        return expertScheduleSetting;
    }
  
    
}