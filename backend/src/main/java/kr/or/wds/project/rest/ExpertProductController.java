package kr.or.wds.project.rest;

import jakarta.validation.Valid;
import kr.or.wds.project.dto.request.ExpertProductOrderRequest;
import kr.or.wds.project.dto.response.ExpertProductResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.or.wds.project.dto.request.ExpertProductRequest;
import kr.or.wds.project.dto.request.ExpertProductDiscountRequest;
import kr.or.wds.project.entity.ExpertProductDiscountEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.service.ExpertProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/expert-products")
@RequiredArgsConstructor
public class ExpertProductController {

    private final ExpertProductService expertProductService;

    @GetMapping
    public ResponseEntity<List<ExpertProductResponse>> getProducts() {
        return ResponseEntity.ok(expertProductService.getList());
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Boolean> createExpertProduct(
            @RequestPart ExpertProductRequest expertProduct,
            @RequestPart(required = false)  MultipartFile file
    ) {
        expertProductService.createExpertProduct(expertProduct, file);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/create-discount")
    public ResponseEntity<ExpertProductDiscountEntity> createExpertProductDiscount(@RequestBody ExpertProductDiscountRequest expertProductDiscount) {
        return ResponseEntity.ok(expertProductService.createExpertProductDiscount(expertProductDiscount));
    }


    @PutMapping("/order")
    public void updateOrder(@RequestBody List<ExpertProductOrderRequest> request) {
        expertProductService.updateOrder(request);
    }


}
