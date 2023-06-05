package com.avery.procure2pay.repository;

import com.avery.procure2pay.model.POReqHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POReqHeaderRepository extends JpaRepository<POReqHeader, Long> {
}
