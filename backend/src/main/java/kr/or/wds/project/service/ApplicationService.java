package kr.or.wds.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.or.wds.project.common.enums.ApproveStatus;
import kr.or.wds.project.dto.request.ApplicationRequest;
import kr.or.wds.project.dto.request.ApplicationReviewRequest;
import kr.or.wds.project.mapper.ApplicationMapper;
import kr.or.wds.project.repository.ApplicationRepository;
import kr.or.wds.project.repository.ExpertRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ExpertRepository expertRepository;

    public void apply(ApplicationRequest dto) {
        applicationRepository.save(applicationMapper.toEntity(dto));
    }

    public void review(ApplicationReviewRequest dto) {
        Optional.ofNullable(dto)
                .filter(d -> d.getStatus() == ApproveStatus.APPROVED)
                .map(applicationMapper::toExpertEntity)
                .ifPresent(expertRepository::save);

        applicationMapper.toReviewEntity(dto);
    }
}
