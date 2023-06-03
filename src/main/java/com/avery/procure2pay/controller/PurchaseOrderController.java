package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import com.avery.procure2pay.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path="/purchase-orders/{purchaseOrderId}")
    public ResponseEntity<?> getPurchaseOrdersById(@PathVariable(value="purchaseOrderId") Long purchaseOrderId) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (purchaseOrder.isPresent()) {
            message.put("message", "success");
            message.put("data", purchaseOrder);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            message.put("message", "cannot find any purchase order with id " + purchaseOrderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}
