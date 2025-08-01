package kr.or.wds.project.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.or.wds.project.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expert_products")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpertProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expert_id")
    @Schema(description = "전문가 ID")
    private Long expertId;

    @Column(name = "product_nm")
    @Schema(description = "상품명")
    private String productNm;

    @Column(name = "price")
    @Schema(description = "가격")
    private String price;

    @Column(name = "`order`")
    @Schema(description = "순서")
    private Integer order;

    @Column(name = "product_type")
    @Schema(description = "상품 타입")
    private String productType;

    @Column(name = "status")
    @Schema(description = "상태")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "description")
    @Schema(description = "설명")
    private String description;

    @Column(name = "duration_hours")
    @Schema(description = "서비스 소요 시간")
    private Integer durationHours;

    @Column(name = "thumbnail_image")
    @Schema(description = "썸네일 이미지")
    private String thumbnailImage;

    @Column(name = "detail_images")
    @Schema(description = "상세 이미지")
    private String detailImages;

    @Column(name = "is_featured")
    @Schema(description = "추천 상품 여부")
    private String isFeatured;

    @Column(name = "view_count")
    @Schema(description = "조회수")
    private Integer viewCount;

    @Column(name = "booking_count")
    @Schema(description = "예약 수")
    private Integer bookingCount;

}
