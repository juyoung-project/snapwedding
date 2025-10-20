package kr.or.wds.project.repository;

import kr.or.wds.project.entity.FileMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileMasterRepository extends JpaRepository<FileMasterEntity, Long> {

}
