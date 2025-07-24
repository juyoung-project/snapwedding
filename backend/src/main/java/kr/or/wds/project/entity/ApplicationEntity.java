package kr.or.wds.project.entity;

import java.time.LocalDateTime;

import kr.or.wds.project.common.ApproveStatus;
import kr.or.wds.project.common.ServiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expert_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "신청 ID")
    private Long id;

    @Column(name = "user_id")
    @NotNull
    @Schema(description = "유저 ID")
    private Long userId;

    @Column(name = "service_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    @Schema(description = "서비스 타입")
    private ServiceType serviceType;

    @Column(name = "region_id")
    @NotNull    
    @Schema(description = "지역 ID")
    private Long regionId;

    @Column(name = "intro")
    @Schema(description = "소개")
    private String intro;

    @Column(name = "portfolio_urls")
    @Schema(description = "포트폴리오 URL")
    private String portfolioUrls;

    @Column(name = "experience")
    @Schema(description = "경력")
    private String experience;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ApproveStatus status;

    @Column(name = "review_note")
    @Schema(description = "리뷰 내용")
    private String reviewNote;

    @Column(name = "reviewer_id")
    @Schema(description = "리뷰어 ID")
    private Long reviewerId;

    @Column(name = "reviewed_at")
    @Schema(description = "리뷰 일시")
    private LocalDateTime reviewedAt;

}
