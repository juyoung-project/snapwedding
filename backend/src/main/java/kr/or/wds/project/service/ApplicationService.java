package kr.or.wds.project.service;

import org.springframework.stereotype.Service;

import kr.or.wds.project.dto.request.ApplicationRequest;
import kr.or.wds.project.dto.request.ApplicationReviewRequest;
import kr.or.wds.project.mapper.ApplicationMapper;
import kr.or.wds.project.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    public void apply(ApplicationRequest dto) {
        applicationRepository.save(applicationMapper.toEntity(dto));
    }

    public void review(ApplicationReviewRequest dto) {
        applicationMapper.toReviewEntity(dto);
    }
}

