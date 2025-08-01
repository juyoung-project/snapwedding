package kr.or.wds.project.helper;

import org.springframework.stereotype.Component;

import kr.or.wds.project.entity.ExpertEntity;
import kr.or.wds.project.entity.ExpertProductEntity;
import kr.or.wds.project.entity.UserEntity;
import kr.or.wds.project.exception.CustomException;
import kr.or.wds.project.exception.ExceptionType;
import kr.or.wds.project.repository.ExpertProductRepository;
import kr.or.wds.project.repository.ExpertRepository;
import kr.or.wds.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MappingHelper {    

    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;
    private final ExpertProductRepository expertProductRepository;

    public UserEntity getUserInfo(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }

    public ExpertEntity getExpertInfo(Long expertId) {
        return expertRepository.findById(expertId)
                .orElseThrow(() -> new CustomException(ExceptionType.EXPERT_NOT_FOUND));
    }

    public ExpertProductEntity getExpertProductInfo(Long productId) {
        return expertProductRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ExceptionType.EXPERT_PRODUCT_NOT_FOUND));
    }
}   
