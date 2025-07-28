package kr.or.wds.project.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.wds.project.dto.request.ExpertProductRequest;
import kr.or.wds.project.dto.request.ExpertProductDiscountRequest;
import kr.or.wds.project.entity.ExpertProductDiscountEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.service.ExpertProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expert-products")
@RequiredArgsConstructor
public class ExpertProductController {

    private final ExpertProductService expertProductService;

    @PostMapping("/create")
    public ResponseEntity<ExpertProductEntity> createExpertProduct(@RequestBody ExpertProductRequest expertProduct) {
        return ResponseEntity.ok(expertProductService.createExpertProduct(expertProduct));
    }

    @PostMapping("/create-discount")
    public ResponseEntity<ExpertProductDiscountEntity> createExpertProductDiscount(@RequestBody ExpertProductDiscountRequest expertProductDiscount) {
        return ResponseEntity.ok(expertProductService.createExpertProductDiscount(expertProductDiscount));
    }

}
