package com.avery.procure2pay.repository;

import com.avery.procure2pay.model.POReqDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoReqDetailRepository extends JpaRepository<POReqDetail,Long> {
}
