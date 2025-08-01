package kr.or.wds.project.mapper;

import org.springframework.stereotype.Component;

import kr.or.wds.project.dto.request.BookingCustomerRequest;
import kr.or.wds.project.entity.BookingEntity;
import kr.or.wds.project.common.enums.BookingStatus;
import kr.or.wds.project.common.enums.PaymentStatus;
import kr.or.wds.project.helper.MappingHelper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final MappingHelper mappingHelper;      
    public BookingEntity toEntityCustomer(BookingCustomerRequest request) {
        return BookingEntity.builder()
                .bookingName(request.getBookingName())
                .customer(mappingHelper.getUserInfo(request.getCustomerId()))
                .expert(mappingHelper.getExpertInfo(request.getExpertId()))
                .product(mappingHelper.getExpertProductInfo(request.getProductId()))
                .desiredDate(request.getDesiredDate())
                .desiredStartTime(request.getDesiredStartTime())
                .desiredEndTime(request.getDesiredEndTime())
                .desiredTimeNote(request.getDesiredTimeNote())
                .bookingStatus(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)
                .bookingAmount(request.getBookingAmount())
                .build();   
    }
}
