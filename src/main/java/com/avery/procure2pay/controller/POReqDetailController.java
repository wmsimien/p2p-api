package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.model.POReqHeader;
import com.avery.procure2pay.repository.POReqHeaderRepository;
import com.avery.procure2pay.repository.PoReqDetailRepository;
import com.avery.procure2pay.service.POReqDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class POReqDetailController {

    @Autowired
    private POReqDetailService poReqDetailService;


    static HashMap<String, Object> message = new HashMap<>();

    @GetMapping(path="/po-req-details/")
    public ResponseEntity<?> getPOReqDetails() {
        List<POReqDetail> poReqDetailList = poReqDetailService.getPOReqDetails();
        if (poReqDetailList.isEmpty()) {
            message.put("message", "cannot find any purchase req details");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", poReqDetailList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

}
