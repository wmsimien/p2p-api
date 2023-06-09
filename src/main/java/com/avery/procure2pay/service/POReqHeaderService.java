package com.avery.procure2pay.service;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.model.POReqHeader;
import com.avery.procure2pay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class POReqHeaderService {

    @Autowired
    PoReqDetailRepository poReqDetailRepository;
    @Autowired
    POReqHeaderRepository poReqHeaderRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ItemFavoritesRepository itemFavoritesRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public void setPoReqHeaderRepository(POReqHeaderRepository poReqHeaderRepository,
                                         PoReqDetailRepository poReqDetailRepository, EmployeeRepository employeeRepository, SupplierRepository supplierRepository, ItemFavoritesRepository itemFavoritesRepository) {
        this.poReqHeaderRepository = poReqHeaderRepository;
        this.poReqDetailRepository = poReqDetailRepository;
        this.employeeRepository = employeeRepository;
        this.supplierRepository = supplierRepository;
        this.itemFavoritesRepository = itemFavoritesRepository;
    }

    /**
     * Method calls repo to create a po req header record
     * @param poReqHeader po req header data elements
     * @return update po req header record
     */
    public POReqHeader createPOReqHeader(POReqHeader poReqHeader) {
        return poReqHeaderRepository.save(poReqHeader);
    }

    /**
     * Method call po req header repo to obtain a list of all po reqs
     * @return a list of all reqs
     */
    public List<POReqHeader> getPOReqs() {
        return poReqHeaderRepository.findAll();
    }

    /**
     * Method call po req head repo to obtain the record of a specific po header record
     * @param poReqHeaderId po req header id of the associated record to obtain
     * @return po req record of the specified id
     */
    public Optional<POReqHeader> getPOReqById(Long poReqHeaderId) {
        return poReqHeaderRepository.findById(poReqHeaderId);
    }

    /**
     * Method call po req repo to update a specific po req record
     * @param poReqHeaderId specific po req record
     * @param poReqHeader po req header data elements
     * @return updated po req record
     * @throws InformationNotFoundException HttpStatus response
     */
    public Optional<POReqHeader> updatePOReqById(Long poReqHeaderId, POReqHeader poReqHeader) throws InformationNotFoundException {
        Optional<POReqHeader> poReqToUpdate = poReqHeaderRepository.findById(poReqHeaderId);
        if (poReqToUpdate.isEmpty()) {
            throw new InformationNotFoundException("cannot find po-req with id " + poReqHeaderId);

        } else {
            poReqToUpdate.get().setReqDate(poReqHeader.getReqDate());
            poReqToUpdate.get().setSupplierLists(poReqHeader.getSupplierLists());
            poReqToUpdate.get().setApprovedBy(poReqHeader.getApprovedBy());
            poReqToUpdate.get().setApprovedDate(poReqHeader.getApprovedDate());
            poReqToUpdate.get().setCreatedDate(poReqHeader.getCreatedDate());
            poReqToUpdate.get().setCreatedBy(poReqHeader.getCreatedBy());
            poReqToUpdate.get().setDeliveryDate(poReqHeader.getDeliveryDate());
            poReqToUpdate.get().setReqNotesExternal(poReqHeader.getReqNotesExternal());
            poReqToUpdate.get().setReqNotesInternal(poReqHeader.getReqNotesInternal());
            poReqToUpdate.get().setStatus(poReqHeader.getStatus());
            poReqToUpdate.get().setPaymentTerms(poReqHeader.getPaymentTerms());
            poReqToUpdate.get().setShipTo(poReqHeader.getShipTo());
            poReqToUpdate.get().setPoNotes(poReqHeader.getPoNotes());
            poReqToUpdate.get().setGlAcctNo(poReqHeader.getGlAcctNo());
            poReqHeaderRepository.save(poReqToUpdate.get());
            return poReqToUpdate;
        }
    }

    /**
     * Method returns list of po req details from repo
     * @return a list of all po req records
     */
    public List<POReqDetail> getPOReqDetails() {
        return poReqDetailRepository.findAll();
    }
}
