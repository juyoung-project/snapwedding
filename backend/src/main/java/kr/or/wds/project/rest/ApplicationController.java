package kr.or.wds.project.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.or.wds.project.dto.request.ApplicationRequest;
import kr.or.wds.project.dto.request.ApplicationReviewRequest;
import kr.or.wds.project.service.ApplicationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    @Operation(summary = "전문가 신청", description = "전문가 신청을 합니다.")
    public ResponseEntity<Void> apply(@RequestBody ApplicationRequest request) {
        applicationService.apply(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/review")
    @Operation(summary = "전문가 신청 리뷰", description = "전문가 신청 리뷰를 합니다.")
    public ResponseEntity<Void> review(@RequestBody ApplicationReviewRequest request) {
        applicationService.review(request);
        return ResponseEntity.ok().build();
    }

}
