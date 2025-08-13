package kr.or.wds.project.dto.request;

import java.util.List;

import kr.or.wds.project.common.enums.PortfolioStatus;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class PortfoliosRequest {

    @Schema(description = "작성자 ID")
    private Long userId; // 작성자 ID

    @Schema(description = "전문가 ID")
    private Long expertId; // 전문가 ID

    @Schema(description = "촬영지역 ID")
    private Long regionId; // 촬영지역 ID

    @Schema(description = "카테고리 ID")
    private Long categoryId; // 카테고리 ID

    @Schema(description = "촬영지역 ID")
    private List<Long> regionIds; // 촬영지역 ID

    @Schema(description = "포트폴리오 제목")
    private String title; // 포트폴리오 제목

    @Schema(description = "포트폴리오 설명")
    private String description; // 포트폴리오 설명

    @Schema(description = "검색용 태그 (JSON 배열)")
    private String tags; // 검색용 태그 (JSON 배열)

    @Schema(description = "대표 이미지 URL")
    private String thumbnailUrl; // 대표 이미지 URL

    @Schema(description = "전시 순서")
    private String displayOrder; // 전시 순서

    @Schema(description = "대표작품 여부 (Y/N)")
    private String isFeatured; // 대표작품 여부 

    @Schema(description = "공개여부 (Y/N)")
    private String isPublic; // 공개여부 

    @Schema(description = "촬영일자")
    private String shootingDate; // 촬영일자

    @Schema(description = "상태 (DRAFT/PUBLISHED/HIDDEN)")
    private PortfolioStatus status; // 상태 (DRAFT/PUBLISHED/HIDDEN)
    
}
