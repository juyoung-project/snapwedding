package kr.or.wds.project.service;

import jakarta.transaction.Transactional;
import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.records.FileAfterContext;
import kr.or.wds.project.dto.request.ExpertProductOrderRequest;
import kr.or.wds.project.dto.response.ExpertProductResponse;
import kr.or.wds.project.exception.CustomException;
import kr.or.wds.project.exception.ExceptionType;
import kr.or.wds.project.helper.FileHelper;
import kr.or.wds.project.helper.UploadAfterProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.ExpertProductRequest;
import kr.or.wds.project.dto.request.ExpertProductDiscountRequest;
import kr.or.wds.project.entity.ExpertProductDiscountEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.mapper.ExpertProductMapper;
import kr.or.wds.project.repository.ExpertProductDiscountRepository;
import kr.or.wds.project.repository.ExpertProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertProductService implements UploadAfterProcessor {

    private final ExpertProductRepository expertProductRepository;
    private final ExpertProductMapper expertProductMapper;
    private final ExpertProductDiscountRepository expertProductDiscountRepository;
    private final FileHelper fileHelper;

    public ExpertProductService(
            ExpertProductRepository expertProductRepository,
            ExpertProductMapper expertProductMapper,
            ExpertProductDiscountRepository expertProductDiscountRepository,
            @Lazy FileHelper fileHelper
    ) {
        this.expertProductRepository = expertProductRepository;
        this.expertProductMapper = expertProductMapper;
        this.expertProductDiscountRepository = expertProductDiscountRepository;
        this.fileHelper = fileHelper;
    }

    public ExpertProductEntity getExpertProduct(Long id) {
        return expertProductRepository.findById(id)
                .orElseThrow(() -> new CustomException(ExceptionType.EXPERT_PRODUCT_NOT_FOUND));
    }

    @Transactional
    public void createExpertProduct(ExpertProductRequest request, MultipartFile file) {
        ExpertProductEntity expertProduct = expertProductMapper.toEntity(request);
        expertProductRepository.save(expertProduct);
        List<MultipartFile> files = List.of(file);
        fileHelper.upload(FileUploadDomainType.PRODUCT, files, expertProduct.getId(), expertProduct.getExpertId());
    }


    @Transactional
    public void updateExpertProduct(Long id,MultipartFile file, ExpertProductRequest request) {
        ExpertProductEntity product = this.getExpertProduct(id);
        product.setProductNm(request.getProductNm());
        product.setPrice(request.getPrice());
        product.setDurationHours(request.getDurationHours());
        product.setDescription(request.getDescription());

        Optional.ofNullable(file).ifPresent(f -> {
            List<MultipartFile> files = List.of(f);
            fileHelper.upload(FileUploadDomainType.PRODUCT, files, product.getId(), product.getExpertId());
        });
    }

    @Transactional
    public void deleteExpertProduct(Long id) {
        // 물리삭제는 진행하지 않음
        ExpertProductEntity product = this.getExpertProduct(id);
        product.setDelYn("Y");
    }


    public ExpertProductDiscountEntity createExpertProductDiscount(ExpertProductDiscountRequest request) {
        ExpertProductDiscountEntity expertProductDiscount = expertProductMapper.toDiscountEntity(request);
        return expertProductDiscountRepository.save(expertProductDiscount);
    }

    public List<ExpertProductResponse> getList() {
        return ExpertProductResponse.from(expertProductRepository.findAllByDelYnOrderByOrderAsc("N"));
    }

    public void updateOrder(List<ExpertProductOrderRequest> orders
    ) {
        List<Long> ids = orders.stream().map(ExpertProductOrderRequest::getId).toList();
        List<ExpertProductEntity> products = expertProductRepository.findAllById(ids);

        Map<Long, Integer> orderMap = orders.stream()
                .collect(Collectors.toMap(
                        ExpertProductOrderRequest::getId,
                        ExpertProductOrderRequest::getOrder
                ));

        products.forEach(entity -> {
            Integer newOrder = orderMap.get(entity.getId());
            if (newOrder != null) {
                entity.setOrder(newOrder);
            }
        });

        expertProductRepository.saveAll(products);

    }

    @Override
    public boolean supports(FileUploadDomainType domain) {
        return FileUploadDomainType.PRODUCT == domain;
    }

    @Override
    @Transactional
    public String process(FileAfterContext context) {
        ExpertProductEntity expertProduct = expertProductRepository.findById(context.aggregateId())
                .orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));

        Optional.ofNullable(expertProduct.getThumbnailFileId()).ifPresent(fileHelper::deleteFile);
        expertProduct.setThumbnailFileId(context.fileIds().get(0));
        expertProductRepository.save(expertProduct);
        // save() 호출 또는 변경 감지(Dirty Checking)로 UPDATE 실행됨
        return "Product thumbnail updated: " + expertProduct.getId();
    }

}
