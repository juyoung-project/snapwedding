package kr.or.wds.project.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.wds.project.dto.request.BookingCustomerRequest;
import kr.or.wds.project.entity.BookingEntity;
import kr.or.wds.project.service.BookingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create-customer")
    public ResponseEntity<BookingEntity> createBookingCustomer(@RequestBody BookingCustomerRequest request) {
        return ResponseEntity.ok(bookingService.createBookingCustomer(request));
    }

    @PostMapping("/create-expert")
    public ResponseEntity<BookingEntity> createBookingExpert(@RequestBody BookingCustomerRequest request) {
        return ResponseEntity.ok(bookingService.createBookingExpert(request));
    }

}
