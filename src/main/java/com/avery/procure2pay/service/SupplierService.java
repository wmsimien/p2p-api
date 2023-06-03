package com.avery.procure2pay.service;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    public void setSupplierRepository(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Method obtains a list of all suppliers from repository
     * @return List of all suppliers.
     * @throws InformationNotFoundException Response message of success or not found.
     */
    public List<Supplier> getAllSuppliers() throws InformationNotFoundException {
         return supplierRepository.findAll();
    }

    /**
     * Method obtains specified supplier based on supplier id
     * @param supplierId Specific supplier to obtain.
     * @return Specific supplier and httpStatus 200, if successfully; otherwise, httpStatus 404.
     * @throws InformationNotFoundException Response custom message
     */

    public Optional<Supplier> getSupplierById(Long supplierId) throws InformationNotFoundException {
        return supplierRepository.findById(supplierId);
    }

}
