package kr.or.wds.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.entity.ApplicationEntity;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

}
