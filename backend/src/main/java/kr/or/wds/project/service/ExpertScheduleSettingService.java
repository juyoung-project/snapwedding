package kr.or.wds.project.service;

import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.ExpertScheduleSettingRequest;
import kr.or.wds.project.entity.ExpertScheduleSettingEntity;
import kr.or.wds.project.mapper.ExpertScheduleSettingMapper;
import kr.or.wds.project.repository.ExpertScheduleSettingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpertScheduleSettingService {

    private final ExpertScheduleSettingRepository expertScheduleSettingRepository;
    private final ExpertScheduleSettingMapper expertScheduleSettingMapper;

    public ExpertScheduleSettingEntity createExpertScheduleSetting(ExpertScheduleSettingRequest request) {
        ExpertScheduleSettingEntity expertScheduleSetting = expertScheduleSettingMapper.toEntity(request);
        expertScheduleSettingRepository.save(expertScheduleSetting);
        return expertScheduleSetting;
    }

    public boolean updateExpertScheduleSetting(Long id, ExpertScheduleSettingRequest request) {
        ExpertScheduleSettingEntity expertScheduleSetting = expertScheduleSettingMapper.toUpdateEntity(id, request);
        expertScheduleSettingRepository.save(expertScheduleSetting);
        return true;
    }

}
