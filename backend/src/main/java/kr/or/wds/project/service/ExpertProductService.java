package kr.or.wds.project.service;

import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.ExpertProductRequest;
import kr.or.wds.project.dto.request.ExpertProductDiscountRequest;
import kr.or.wds.project.entity.ExpertProductDiscountEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.mapper.ExpertProductMapper;
import kr.or.wds.project.repository.ExpertProductDiscountRepository;
import kr.or.wds.project.repository.ExpertProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpertProductService {

    private final ExpertProductRepository expertProductRepository;
    private final ExpertProductMapper expertProductMapper;
    private final ExpertProductDiscountRepository expertProductDiscountRepository;

    public ExpertProductEntity createExpertProduct(ExpertProductRequest request) {
        ExpertProductEntity expertProduct = expertProductMapper.toEntity(request);
        return expertProductRepository.save(expertProduct);
    }

    public ExpertProductDiscountEntity createExpertProductDiscount(ExpertProductDiscountRequest request) {
        ExpertProductDiscountEntity expertProductDiscount = expertProductMapper.toDiscountEntity(request);
        return expertProductDiscountRepository.save(expertProductDiscount);
    }

}
