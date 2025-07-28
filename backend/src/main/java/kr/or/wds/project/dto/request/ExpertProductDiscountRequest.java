package kr.or.wds.project.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ExpertProductDiscountRequest {

    @NotNull(message = "expertProductId is required")
    @Schema(description = "전문가 상품 ID")
    private Long expertProductId;

    @NotNull(message = "discountName is required")
    @Schema(description = "할인명")
    private String discountName;

    @NotNull(message = "discountType is required")
    @Schema(description = "할인 타입")
    private String discountType;

    @NotNull(message = "discountValue is required")
    @Schema(description = "할인 값")
    private String discountValue;

    @NotNull(message = "minPrice is required")
    @Schema(description = "최소 가격")
    private String minPrice;

    @NotNull(message = "maxDiscountAmount is required")
    @Schema(description = "최대 할인 금액")
    private String maxDiscountAmount;

    @NotNull(message = "startDate is required")
    @Schema(description = "시작 날짜")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    @Schema(description = "종료 날짜")
    private LocalDate endDate;

}
