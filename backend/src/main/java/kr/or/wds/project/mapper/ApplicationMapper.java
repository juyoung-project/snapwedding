package kr.or.wds.project.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import kr.or.wds.project.dto.request.ApplicationRequest;
import kr.or.wds.project.dto.request.ApplicationReviewRequest;
import kr.or.wds.project.entity.ApplicationEntity;
import kr.or.wds.project.common.ApproveStatus;
import kr.or.wds.project.common.Status;
import kr.or.wds.project.entity.ExpertEntity;
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
        ApplicationEntity application = this.getApplicationEntity(dto.getApplicationId());

        application.setStatus(dto.getStatus());
        application.setReviewNote(dto.getReviewNote());
        application.setReviewerId(dto.getReviewerId());
        application.setReviewedAt(LocalDateTime.now());
        applicationRepository.save(application);
    }

    public ExpertEntity toExpertEntity(ApplicationReviewRequest dto) {

        ApplicationEntity application = this.getApplicationEntity(dto.getApplicationId());

        return ExpertEntity.builder()
                .userId(application.getUserId())
                .applicationId(dto.getApplicationId())
                .serviceType(application.getServiceType().name())
                .regionId(application.getRegionId())
                .intro(application.getIntro())
                .status(Status.ACTIVE)
                .approvedAt(LocalDateTime.now())
                .build();
    }

    private ApplicationEntity getApplicationEntity(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

}
