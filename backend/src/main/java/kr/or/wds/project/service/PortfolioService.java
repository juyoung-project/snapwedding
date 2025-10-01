package kr.or.wds.project.service;

import java.util.List;

import kr.or.wds.project.common.enums.FileUploadDomainType;
import kr.or.wds.project.common.records.FileAfterContext;
import kr.or.wds.project.helper.UploadAfterProcessor;
import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.PortfoliosRequest;
import kr.or.wds.project.entity.PortfolioEntity;
import kr.or.wds.project.entity.PortfolioRegionEntity;
import kr.or.wds.project.mapper.PortfolioMapper;
import kr.or.wds.project.repository.PortfolioRepository;
import kr.or.wds.project.repository.PortfolioRegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PortfolioService implements UploadAfterProcessor {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final PortfolioRegionRepository portfolioRegionRepository;

    public void createPortfolio(PortfoliosRequest request) {

        PortfolioEntity portfolio = portfolioMapper.toEntity(request);
        portfolioRepository.save(portfolio);

        List<PortfolioRegionEntity> portfolioRegions = portfolioMapper.toPortfolioRegionList(portfolio, request.getRegionIds());
        portfolioRegionRepository.saveAll(portfolioRegions);

    }

    public List<PortfolioEntity> getPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public boolean supports(FileUploadDomainType domain) {
        return domain.equals(FileUploadDomainType.PORTFOLIO);
    }

    @Override
    public String process(FileAfterContext context) {
        System.out.println(context.fileIds());
        return "";
    }
}
