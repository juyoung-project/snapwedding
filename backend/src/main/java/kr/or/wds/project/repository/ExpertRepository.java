package kr.or.wds.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.entity.ExpertEntity;

public interface ExpertRepository extends JpaRepository<ExpertEntity, Long> {

}
