package com.avery.procure2pay.controller;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path="/api")
public class SupplierController {

    @Autowired
    SupplierRepository supplierRepository;

    // handles custom message and data object
    static HashMap<String, Object> message = new HashMap<>();

    // test endpoint
    @GetMapping(path="/suppliers/")
    public ResponseEntity<?> getAllSuppliers() throws InformationNotFoundException {
       List<Supplier> supplierList =  supplierRepository.findAll();
       if (supplierList.isEmpty()) {
           message.put("message", "cannot find any suppliers");
           return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
       } else {
           message.put("message", "success");
           message.put("data", supplierList);
           return new ResponseEntity<>(message, HttpStatus.OK);

       }
    }
}
