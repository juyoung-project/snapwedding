package kr.or.wds.project.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.or.wds.project.dto.request.PortfoliosRequest;
import kr.or.wds.project.entity.PortfolioEntity;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.exception.CustomException;
import kr.or.wds.project.exception.ExceptionType;
import kr.or.wds.project.entity.ExpertEntity;
import kr.or.wds.project.entity.PortfolioCategoryEntity;
import kr.or.wds.project.common.enums.PortfolioStatus;
import lombok.RequiredArgsConstructor;
import kr.or.wds.project.repository.UserRepository;
import kr.or.wds.project.repository.ExpertRepository;
import kr.or.wds.project.repository.PortfolioCategoryRepository;
import kr.or.wds.project.repository.PortfolioRegionRepository;
import kr.or.wds.project.repository.RegionRepository;
import kr.or.wds.project.entity.PortfolioRegionEntity;
import kr.or.wds.project.entity.RegionEntity;

@Component
@RequiredArgsConstructor
public class PortfolioMapper {

    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;
    private final PortfolioCategoryRepository portfolioCategoryRepository;
    private final RegionRepository regionRepository;

    public PortfolioEntity toEntity(PortfoliosRequest request) {
        return PortfolioEntity.builder()
                .user(this.getUserEntity(request.getUserId()))
                .expert(this.getExpertEntity(request.getExpertId()))
                .category(this.getPortfolioCategoryEntity(request.getCategoryId()))
                .title(request.getTitle())
                .description(request.getDescription())
                .shootingDate(request.getShootingDate())
                .status(PortfolioStatus.DRAFT)
                .build();
    }

    public List<PortfolioRegionEntity> toPortfolioRegionList(PortfolioEntity portfolio, List<Long> regionIds) {

        // 1. 모든 region 정보를 한번에 조회
        List<RegionEntity> regions = regionRepository.findAllById(regionIds);

        // 2. PortfolioRegion 엔티티들을 생성
        List<PortfolioRegionEntity> portfolioRegions = regions.stream()
                .map(region -> PortfolioRegionEntity.builder()
                        .portfolio(portfolio)
                        .region(region)
                        .isPrimary("Y")
                        .displayOrder(0)
                        .build())
                .collect(Collectors.toList());

        // 3. 한번에 저장
        return portfolioRegions;
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));
    }

    private ExpertEntity getExpertEntity(Long expertId) {
        return expertRepository.findById(expertId)
                .orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));
    }

    private PortfolioCategoryEntity getPortfolioCategoryEntity(Long categoryId) {
        return portfolioCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ExceptionType.DATA_NOT_FOUND));
    }

}
