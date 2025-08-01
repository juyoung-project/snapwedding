package kr.or.wds.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookingCustomerRequest {

    @Schema(description = "예약 이름")
    private String bookingName;

    @Schema(description = "고객 ID")
    private Long customerId;

    @Schema(description = "전문가 ID")
    private Long expertId;

    @Schema(description = "상품 ID")
    private Long productId;

    @Schema(description = "예약 희망일")
    private String desiredDate;

    @Schema(description = "예약 희망 시작 시간")
    private String desiredStartTime;

    @Schema(description = "예약 희망 종료 시간")
    private String desiredEndTime;

    @Schema(description = "예약 희망 시간 메모")
    private String desiredTimeNote;

    @Schema(description = "예약 금액")
    private String bookingAmount;

}
