package kr.or.wds.project.entity;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.or.wds.project.common.enums.Day;
import kr.or.wds.project.common.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expert_schedule_settings")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertScheduleSettingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    /* @Column(name = "expert_id") */
    @Schema(description = "전문가 ID")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "expert_id")
    private ExpertEntity expert;

    @Column(name = "setting_date")
    @Schema(description = "설정 날짜")
    private String settingDate;

    @Column(name = "day_of_week")
    @Schema(description = "요일")
    @Enumerated(EnumType.STRING)
    private Day dayOfWeek;

    @Column(name = "available_start_time")
    @Schema(description = "가능한 시작 시간")
    private String availableStartTime;

    @Column(name = "available_end_time")
    @Schema(description = "가능한 종료 시간")
    private String availableEndTime;

    @Column(name = "min_booking_duration_hours")
    @Schema(description = "최소 예약 시간")
    private String minBookingDurationHours;

    @Column(name = "max_booking_duration_hours")
    @Schema(description = "최대 예약 시간")
    private String maxBookingDurationHours;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json") 
    @Schema(description = "가능한 요일")
    private Map<String, Object> availableDays;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json") 
    @Schema(description = "시간 범위")
    private Map<String, Object> preferredTimeRanges;

    @Column(name = "status")
    @Schema(description = "상태")
    @Enumerated(EnumType.STRING)
    private Status status;


}
