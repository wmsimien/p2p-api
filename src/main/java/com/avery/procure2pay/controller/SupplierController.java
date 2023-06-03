package com.avery.procure2pay.controller;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    // handles custom message and data object
    static HashMap<String, Object> message = new HashMap<>();

    /**
     * Endpoint call method to obtain a listing of all suppliers.
     * @return Response httpStatus of 200 if successfully and 404 if not able to find any suppliers.
     * @throws InformationNotFoundException Respond with custom message.
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
     * @throws InformationNotFoundException Respond with custom message.
     */
    @GetMapping(path="/suppliers/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable(value="supplierId") Long supplierId) throws InformationNotFoundException {
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

    /**
     * Endpoint calls a method to create a new supplier record.
     * @param supplierObject Data utilized to create new supplier.
     * @return HttpStatus 201 returned on success and 200 (OK) if not able to create new supplier w/ data elements.
     * @throws InformationNotFoundException Respond with custom message.
     */
    @PostMapping(path="/suppliers/")
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplierObject) throws InformationNotFoundException {
        Supplier newSupplier =  supplierService.createSupplier(supplierObject);
        if (newSupplier != null) {
            message.put("message", "success");
            message.put("data", newSupplier);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create supplier at this time");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Endpoint calls method to update specific supplier based on supperId.
     * @param supplierId Specified supplier id to update.
     * @param supplierObject Specific data to update specified supplier data points with.
     * @return Updated supplier record.
     * @throws InformationNotFoundException HttpsStatus with custom message.
     */
    @PutMapping(path="/suppliers/{supplierId}")
    public ResponseEntity<?> updateSupplierById(@PathVariable(value="supplierId") Long supplierId, @RequestBody Supplier supplierObject) throws InformationNotFoundException {
        Optional<Supplier> supplierToUpdate = supplierService.updateSupplierById(supplierId, supplierObject);
        if (supplierToUpdate.isEmpty()) {
            message.put("message", "cannot find any supplier with id " + supplierId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "supplier with id " + supplierId + " has been successfully updated");
            message.put("data", supplierToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Endpoint call method to delete supplier based on supplier id
     * @param supplierId Specified supplier id.
     * @return Supplier recorded deleted.
     * @throws InformationNotFoundException Respond with custom message
     */
    @DeleteMapping(path="/suppliers/{supplierId}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable(value="supplierId") Long supplierId) throws InformationNotFoundException {
        Optional<Supplier> supplierToDelete = supplierService.deleteSupplierById(supplierId);
        if (supplierToDelete.isEmpty()) {
            message.put("message", "cannot find any supplier with id " + supplierId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "supplier with id " + supplierId + " has been successfully deleted");
            message.put("data", supplierToDelete.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
