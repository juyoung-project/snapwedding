package kr.or.wds.project.service;

import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.BookingCustomerRequest;
import kr.or.wds.project.dto.request.BookingRequest;
import kr.or.wds.project.entity.BookingEntity;
import kr.or.wds.project.mapper.BookingMapper;
import kr.or.wds.project.repository.BookingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingEntity createBookingCustomer(BookingCustomerRequest request) {
        return bookingRepository.save(bookingMapper.toEntityCustomer(request));
    }

    public BookingEntity createBookingExpert(BookingCustomerRequest request) {
        return bookingRepository.save(bookingMapper.toEntityCustomer(request));
    }

}
