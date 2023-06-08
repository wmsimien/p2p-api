package com.avery.procure2pay.controller;

import com.avery.procure2pay.service.POReqDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class POReqDetailController_NotUsing {

    @Autowired
    private POReqDetailService poReqDetailService;


    static HashMap<String, Object> message = new HashMap<>();

//    @GetMapping(path="/po-req-details/")
//    public ResponseEntity<?> getPOReqDetails() {
//        List<POReqDetail> poReqDetailList = poReqDetailService.getPOReqDetails();
//        if (poReqDetailList.isEmpty()) {
//            message.put("message", "cannot find any purchase req details");
//            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//        } else {
//            message.put("message", "success");
//            message.put("data", poReqDetailList);
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        }
//    }

}
