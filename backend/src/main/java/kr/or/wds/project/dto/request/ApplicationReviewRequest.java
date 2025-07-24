package kr.or.wds.project.dto.request;

import kr.or.wds.project.common.ApproveStatus;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApplicationReviewRequest {

    @NotNull(message = "applicationId is required")
    @Schema(description = "신청 ID")
    private Long applicationId;

    @NotNull(message = "reviewNote is required")
    @Schema(description = "리뷰 내용")
    private String reviewNote;

    @NotNull(message = "reviewerId is required")
    @Schema(description = "리뷰어 ID")
    private Long reviewerId;

    @NotNull(message = "status is required")
    @Schema(description = "상태")
    private ApproveStatus status;

    
}
