package kr.or.wds.project.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import kr.or.wds.project.common.Day;

@Data
public class ExpertScheduleSettingRequest {

    @Schema(description = "전문가 ID")
    @NotNull
    private Long expertId;

    @Schema(description = "설정 날짜")
    private String settingDate;

    @Schema(description = "요일")
    private Day dayOfWeek;

    @Schema(description = "가능한 시작 시간")
    private String availableStartTime;

    @Schema(description = "가능한 종료 시간")
    private String availableEndTime;

    @Schema(description = "최소 예약 시간")
    private String minBookingDurationHours;

    @Schema(description = "최대 예약 시간")
    private String maxBookingDurationHours;

    @Schema(description = "가능한 요일")
    private Map<String, Object> availableDays;

    @Schema(description = "시간 범위")
    private Map<String, Object> preferredTimeRanges;
}
