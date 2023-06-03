package com.avery.procure2pay.service;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    /**
     * Method creates a new supplier record.
     * @param supplierObject Specified data elements to create new supplier record
     * @return Supplier record just created.
     * @throws InformationNotFoundException Custom response message.
     */
    public Supplier createSupplier(Supplier supplierObject) throws InformationNotFoundException {
        return supplierRepository.save(supplierObject);
    }

    /**
     * Method updates specified supplier record based on id with the specific data elements.
     * @param supplierId Specified supplier id.
     * @param supplierObject Specific data elements to update supplier record.
     * @return Update supplier record.
     * @throws InformationNotFoundException Respond with custom message.
     */
    public Optional<Supplier> updateSupplierById(Long supplierId, Supplier supplierObject) throws InformationNotFoundException {
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        if (supplier.isPresent()) {
            supplier.get().setName(supplierObject.getName());
            supplier.get().setContact_name(supplierObject.getContact_name());
            supplier.get().setContact_phone(supplierObject.getContact_phone());
            supplier.get().setAddress(supplierObject.getAddress());
            supplier.get().setCity(supplierObject.getCity());
            supplier.get().setState(supplierObject.getState());
            supplier.get().setZip(supplierObject.getZip());
            supplier.get().setPhone_no(supplierObject.getPhone_no());
            supplierRepository.save(supplier.get());
            return supplier;
        } else {
            throw new InformationNotFoundException("supplier with id " + supplierId + " not found");
        }
    }

    /**
     * Method deletes specified supplier based on supplier id.
     * @param supplierId Specified id of supplier.
     * @return Supplier record that was deleted.
     * @throws InformationNotFoundException Responds with custom message.
     */
    public Optional<Supplier> deleteSupplierById(Long supplierId) throws InformationNotFoundException {
        Optional<Supplier> supplier = supplierRepository.findById(supplierId);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(supplierId);
            return supplier;
        } else {
            throw new InformationNotFoundException("supplier with id " + supplierId + " not found");
        }
    }
}
