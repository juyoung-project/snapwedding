package kr.or.wds.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.or.wds.project.entity.PortfolioEntity;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {

}
