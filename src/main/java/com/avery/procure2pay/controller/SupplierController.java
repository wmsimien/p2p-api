package com.avery.procure2pay.controller;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import com.avery.procure2pay.service.SupplierService;
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
@RequestMapping(path="/api")
public class SupplierController {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SupplierService supplierService;

    // handles custom message and data object
    static HashMap<String, Object> message = new HashMap<>();

    /**
     * Endpoint call method to obtain a listing of all suppliers.
     * @return Response httpStatus of 200 if successfully and 404 if not able to find any suppliers.
     * @throws InformationNotFoundException Response custom message.
     */
    @GetMapping(path="/suppliers/")
    public ResponseEntity<?> getAllSuppliers() throws InformationNotFoundException {
       List<Supplier> supplierList =  supplierService.getAllSuppliers();
       if (supplierList.isEmpty()) {
           message.put("message", "cannot find any suppliers");
           return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
       } else {
           message.put("message", "success");
           message.put("data", supplierList);
           return new ResponseEntity<>(message, HttpStatus.OK);

       }
    }

    /**
     * Endpoint calls method to obtain a specific supplier based on supperId.
     * @param supplierId Specified id of supplier.
     * @return A httpStatus of 200 if able to find specific supplier and status of 404 if not able to find the supplier specified.
     * @throws InformationNotFoundException Response custom message.
     */
    @GetMapping(path="/suppliers/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable Long supplierId) throws InformationNotFoundException {
        Optional<Supplier> supplier =  supplierService.getSupplierById(supplierId);
        if (supplier.isPresent()) {
            message.put("message", "success");
            message.put("data", supplier);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find any supplier with id " + supplierId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

}
