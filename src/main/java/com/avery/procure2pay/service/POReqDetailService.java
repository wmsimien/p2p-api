package com.avery.procure2pay.service;

import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.repository.PoReqDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POReqDetailService {

    @Autowired
    PoReqDetailRepository poReqDetailRepository;

    @Autowired
    public void setPoReqDetailRepository(PoReqDetailRepository poReqDetailRepository) {
        this.poReqDetailRepository = poReqDetailRepository;
    }

    /**
     * Method obtains a listing of all po req detail records
     * @return
     */
    public List<POReqDetail> getPOReqDetails() {
        return poReqDetailRepository.findAll();
    }

}
