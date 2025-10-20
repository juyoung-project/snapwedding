package kr.or.wds.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.entity.ExpertProductEntity;

import java.util.List;

public interface ExpertProductRepository extends JpaRepository<ExpertProductEntity, Long> {
    List<ExpertProductEntity> findAllByDelYnOrderByOrderAsc(String delYn);

    //Page<ExpertProductResponse> selectAllPage(org.springframework.data.domain.Pageable pageable);
}
