package kr.or.wds.project.mapper;

import org.springframework.stereotype.Component;

import kr.or.wds.project.dto.request.ExpertProductRequest;
import kr.or.wds.project.dto.request.ExpertProductDiscountRequest;
import kr.or.wds.project.entity.ExpertProductDiscountEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.common.Status;

@Component
public class ExpertProductMapper {

    public ExpertProductEntity toEntity(ExpertProductRequest request) {
        return ExpertProductEntity.builder()
                .expertId(request.getExpertId())
                .productNm(request.getProductNm())
                .price(request.getPrice())
                .order(request.getOrder())
                .productType(request.getProductType())
                .status(Status.ACTIVE)
                .description(request.getDescription())
                .durationHours(request.getDurationHours())
                .build();
    }   

    public ExpertProductDiscountEntity toDiscountEntity(ExpertProductDiscountRequest request) {
        return ExpertProductDiscountEntity.builder()
                .expertProductId(request.getExpertProductId())
                .discountName(request.getDiscountName())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .minPrice(request.getMinPrice())
                .maxDiscountAmount(request.getMaxDiscountAmount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(Status.ACTIVE)
                .build();
    }
}
