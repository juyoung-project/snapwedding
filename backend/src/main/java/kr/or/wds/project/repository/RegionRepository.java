package kr.or.wds.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.entity.RegionEntity;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

}
