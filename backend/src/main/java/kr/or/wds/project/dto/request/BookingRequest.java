package kr.or.wds.project.dto.request;

import kr.or.wds.project.common.enums.BookingStatus;
import kr.or.wds.project.common.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookingRequest {

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

    @Schema(description = "예약 확정일")
    private String confirmedDate;

    @Schema(description = "예약 확정 시작 시간")
    private String confirmedStartTime;

    @Schema(description = "예약 확정 종료 시간")
    private String confirmedEndTime;

    @Schema(description = "예약 상태")
    private BookingStatus bookingStatus;

    @Schema(description = "예약 금액")
    private String bookingAmount;

    @Schema(description = "최종 금액")
    private String finalAmount;

    @Schema(description = "예약 정보")
    private String bookingInfo;

    @Schema(description = "메모")
    private String memo;

    @Schema(description = "결제 상태")
    private PaymentStatus paymentStatus;

    @Schema(description = "결제 ID")
    private Long paymentId;

    @Schema(description = "예약 신청일")
    private String appliedAt;

    @Schema(description = "예약 확정일")
    private String confirmedAt;

    @Schema(description = "서비스 완료일")
    private String completedAt;

}
