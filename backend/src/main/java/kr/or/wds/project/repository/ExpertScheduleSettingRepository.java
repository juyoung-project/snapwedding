package kr.or.wds.project.repository;   

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.common.enums.Day;
import kr.or.wds.project.entity.ExpertScheduleSettingEntity;

public interface ExpertScheduleSettingRepository extends JpaRepository<ExpertScheduleSettingEntity, Long> {

    boolean existsByExpertIdAndDayOfWeek(Long expertId, Day dayOfWeek);
}