package kr.or.wds.project.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kr.or.wds.project.dto.request.ApplicationRequest;
import kr.or.wds.project.dto.request.ApplicationReviewRequest;
import kr.or.wds.project.entity.ApplicationEntity;
import kr.or.wds.project.common.ApproveStatus;
import kr.or.wds.project.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;

@Component  
@RequiredArgsConstructor
public class ApplicationMapper {

    private final ApplicationRepository applicationRepository;  

    public ApplicationEntity toEntity(ApplicationRequest dto) {
        return ApplicationEntity.builder()
            .userId(dto.getUserId())
            .serviceType(dto.getServiceType())
            .regionId(dto.getRegionId())
            .intro(dto.getIntro())
            .portfolioUrls(dto.getPortfolioUrls())
            .experience(dto.getExperience())
            .status(ApproveStatus.PENDING)
            .build();
    }

    public void toReviewEntity(ApplicationReviewRequest dto) {
        ApplicationEntity application = applicationRepository.findById(dto.getApplicationId())
            .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setStatus(dto.getStatus());
        application.setReviewNote(dto.getReviewNote());
        application.setReviewerId(dto.getReviewerId());
        application.setReviewedAt(LocalDateTime.now());
        applicationRepository.save(application);

    }
}
