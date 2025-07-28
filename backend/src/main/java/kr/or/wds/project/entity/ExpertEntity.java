package kr.or.wds.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.wds.project.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experts")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    @Schema(description = "사용자 ID")
    private Long userId;

    @Column(name = "application_id")
    @Schema(description = "신청 ID")
    private Long applicationId;
    
    @Column(name = "service_type")
    @Schema(description = "서비스 타입")
    private String serviceType;

    @Column(name = "region_id")
    @Schema(description = "지역 ID")
    private Long regionId;

    @Column(name = "intro")
    @Schema(description = "소개")
    private String intro;

    @Column(name = "available_dates")
    @Schema(description = "가능한 날짜")
    private String availableDates;

    @Column(name = "status")
    @Schema(description = "상태")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "approved_at")
    @Schema(description = "승인 날짜")
    private LocalDateTime approvedAt;
    
}
