package kr.or.wds.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import kr.or.wds.project.common.enums.ApproveStatus;

@Data
@AllArgsConstructor
public class ApplicationReviewResponse {

    private String reviewNote;
    private ApproveStatus status;
}
