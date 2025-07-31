package kr.or.wds.project.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.wds.project.service.ExpertScheduleSettingService;
import lombok.RequiredArgsConstructor;
import kr.or.wds.project.entity.ExpertScheduleSettingEntity;
import kr.or.wds.project.dto.request.ExpertScheduleSettingRequest;

@RestController
@RequestMapping("/api/expert-schedule-settings")
@RequiredArgsConstructor
public class ExpertScheduleSettingController {

    private final ExpertScheduleSettingService expertScheduleSettingService;

    @PostMapping("/create")
    public ResponseEntity<ExpertScheduleSettingEntity> createExpertScheduleSetting(@RequestBody ExpertScheduleSettingRequest request) {
        return ResponseEntity.ok(expertScheduleSettingService.createExpertScheduleSetting(request));
    }

}
