package kr.or.wds.project.dto.request;

import kr.or.wds.project.common.enums.Status;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data   
public class ExpertProductRequest {

    @NotNull(message = "expertId is required")
    @Schema(description = "전문가 ID")
    private Long expertId;  

    @NotNull(message = "productNm is required")
    @Schema(description = "상품명")
    private String productNm;

    @NotNull(message = "price is required")
    @Schema(description = "가격")
    private String price;

    @NotNull(message = "order is required")
    @Schema(description = "순서")
    private Integer order;

    @NotNull(message = "productType is required")
    @Schema(description = "상품 타입")
    private String productType;

    @NotNull(message = "useYn is required")
    @Schema(description = "사용 여부")
    private String useYn;

    @NotNull(message = "postingYn is required")
    @Schema(description = "게시 여부")
    private String postingYn;

    @NotNull(message = "badge is required")
    @Schema(description = "뱃지 명")
    private String badge;

    @NotNull(message = "description is required")
    @Schema(description = "설명")
    private String description;

    @NotNull(message = "durationHours is required")
    @Schema(description = "서비스 소요 시간")
    private Integer durationHours;

}
