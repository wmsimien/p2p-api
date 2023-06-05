package com.avery.procure2pay.controller;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

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

    @GetMapping(path="/purchase-orders/reqs/")
    public ResponseEntity<?> getAllPurchaseReqs() {
        List<PurchaseOrder> purchaseOrderReqsList = purchaseOrderService.getAllPurchaseReqs();
        if (purchaseOrderReqsList.isEmpty()) {
            message.put("message", "cannot find any purchase order requisitions");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", purchaseOrderReqsList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Endpoint calls method to obtain purchase order based on the specific id.
     * @param purchaseOrderId Specific if of the purchase order to obtain.
     * @return Response custom message with Httpstatus of 200 for successful operation and a 400 if not found.
     */
    @GetMapping(path="/purchase-orders/{purchaseOrderId}/")
    public ResponseEntity<?> getPurchaseOrdersById(@PathVariable(value="purchaseOrderId") Long purchaseOrderId) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrdersById(purchaseOrderId);
        if (purchaseOrder.isPresent()) {
            message.put("message", "success");
            message.put("data", purchaseOrder);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } else {
            message.put("message", "cannot find purchase order with id " + purchaseOrderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint calls method to create a purchase order record.
     * @param purchaseOrder Data elements to create new purchase order.
     * @return Purchase order record that was created with a Httpstatus of 201; otherwise, if nat able to create a new purchasse order as Httpstatus of 404 is response provided.
     */
    @PostMapping(path="/purchase-orders/")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        PurchaseOrder newPurchaseOrder = purchaseOrderService.createPurchaseOrder(purchaseOrder);
        if (newPurchaseOrder != null) {
            message.put("message", "success");
            message.put("data", newPurchaseOrder);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create any purchase order at this time");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint calls method update a specified purchase order.
     * @param purchaseOrderId Specified purchase order id to update.
     * @param purchaseOrder Purchase order data elements to utilize for update.
     * @return Httpstatus of 200 on success and 404 when not able to perform update.
     */
    @PutMapping(path="/purchase-orders/{purchaseOrderId}/")
    public ResponseEntity<?> updatePurchaseOrdersById(@PathVariable(value="purchaseOrderId") Long purchaseOrderId, @RequestBody PurchaseOrder purchaseOrder) throws InformationNotFoundException {
        Optional<PurchaseOrder> purchaseOrderToUpdate = purchaseOrderService.updatePurchaseOrderById(purchaseOrderId, purchaseOrder);
        if (purchaseOrderToUpdate.isEmpty()) {
            message.put("message", "cannot find purchase order with id " + purchaseOrderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "purchase order with id " + purchaseOrderId + " has been successfully updated");
            message.put("data", purchaseOrderToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PutMapping(path="/purchase-orders/{purchaseOrderId}/items")
    public ResponseEntity<?> addItemToPurchaseOrder(@PathVariable(value="purchaseOrderId") Long purchaseOrderId, @RequestBody ItemFavorites itemFav) throws InformationNotFoundException {
        PurchaseOrder purchaseOrderToUpdate = purchaseOrderService.addItemToPurchaseOrder(purchaseOrderId, itemFav);
        if (purchaseOrderToUpdate != null) {
            message.put("message", "purchase order with id " + purchaseOrderId + " has been successfully updated");
            message.put("data", purchaseOrderToUpdate);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find purchase order with id " + purchaseOrderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}
