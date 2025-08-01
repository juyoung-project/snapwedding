package kr.or.wds.project.entity;

import java.time.LocalDateTime;

import kr.or.wds.project.common.enums.BookingStatus;
import kr.or.wds.project.common.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@Entity
@Table(name = "bookings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID")
    private Long id;

    @Column(name = "booking_name")
    @Schema(description = "예약 이름")
    private String bookingName;

    @ManyToOne
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Schema(description = "고객 ID")
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "expert_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Schema(description = "전문가 ID")
    private ExpertEntity expert;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Schema(description = "상품 ID")
    private ExpertProductEntity product;

    @Column(name = "desired_date")
    @Schema(description = "예약 희망일")
    private String desiredDate;

    @Column(name = "desired_start_time")
    @Schema(description = "예약 희망 시작 시간")
    private String desiredStartTime;

    @Column(name = "desired_end_time")
    @Schema(description = "예약 희망 종료 시간")
    private String desiredEndTime;

    @Column(name = "desired_time_note")
    @Schema(description = "예약 희망 시간 메모")
    private String desiredTimeNote;

    @Column(name = "confirmed_date")
    @Schema(description = "예약 확정일")
    private String confirmedDate;

    @Column(name = "confirmed_start_time")
    @Schema(description = "예약 확정 시작 시간")
    private String confirmedStartTime;

    @Column(name = "confirmed_end_time")
    @Schema(description = "예약 확정 종료 시간")
    private String confirmedEndTime;

    @Column(name = "booking_status")
    @Schema(description = "예약 상태")
    private BookingStatus bookingStatus;

    @Column(name = "booking_amount")
    @Schema(description = "예약 금액")
    private String bookingAmount;

    @Column(name = "final_amount")
    @Schema(description = "최종 금액")
    private String finalAmount;

    @Column(name = "booking_info")
    @Schema(description = "예약 정보")
    private String bookingInfo;

    @Column(name = "memo")
    @Schema(description = "메모")
    private String memo;

    @Column(name = "payment_status")
    @Schema(description = "결제 상태")
    private PaymentStatus paymentStatus; 

    @Column(name = "payment_id")
    @Schema(description = "결제 ID")
    private Long paymentId;

    @Column(name = "applied_at")
    @Schema(description = "예약 신청일")
    private LocalDateTime appliedAt;

    @Column(name = "confirmed_at")
    @Schema(description = "예약 확정일")
    private LocalDateTime confirmedAt;

    @Column(name = "completed_at")
    @Schema(description = "서비스 완료일일")    
    private LocalDateTime completedAt;

}

