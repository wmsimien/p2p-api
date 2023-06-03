package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import com.avery.procure2pay.service.PurchaseOrderService;
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
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;


    @GetMapping(path="/hello/")
    public String hello() {
        return "Hello";
    }

    static HashMap<String, Object> message = new HashMap<>();

    /**
     * Endpoint calls method to obtain a list of purchase orders.
     * @return Respond custom message with httpstatus 200 if successful; otherwise, httpstatus 404.
     */
    @GetMapping(path="/purchase-orders/")
    public ResponseEntity<?> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrderList = purchaseOrderService.getAllPurchaseOrders();
        if (purchaseOrderList.isEmpty()) {
            message.put("message", "cannot find any purchase orders");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", purchaseOrderList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
