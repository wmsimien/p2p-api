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
     * Method calls repository to create a po req header record.
     * @param poReqHeader Data elements used to create po req header record.
     * @return Updated POReqHeader record.
     */
    public POReqHeader createPOReqHeader(POReqHeader poReqHeader) {
        return poReqHeaderRepository.save(poReqHeader);
    }

    /**
     * Method calls repository to create purchase order requisition detail record for data elements provided.
     * @param poReqHeaderId POReqHeader id to associated to new po req detail record.
     * @param poReqDetail POReqDetail data elements.
     * @return Created POReqHeader record.
     */
    public POReqHeader createPOReqDetail(Long poReqHeaderId, POReqDetail poReqDetail) {
        Optional<POReqHeader> foundPOReqHeader = poReqHeaderRepository.findById(poReqHeaderId);
        if (foundPOReqHeader.isPresent()) {
            POReqDetail newPOReqDetail = poReqDetailRepository.save(poReqDetail);
            foundPOReqHeader.get().addPOReqDetail(newPOReqDetail);
            foundPOReqHeader = Optional.of(poReqHeaderRepository.save(foundPOReqHeader.get()));
        }
        return foundPOReqHeader.get();
    }

    /**
     * Method calls repository to obtain all POReqs.
      * @return List of POReq records.
     */
    public List<POReqHeader> getPOReqs() {
        return poReqHeaderRepository.findAll();
    }

    /**
     * Method calls repository to obtains specific POReq record by id.
     * @param poReqHeaderId Specific po req id
     * @return
     */
    public Optional<POReqHeader> getPOReqById(Long poReqHeaderId) {
        return poReqHeaderRepository.findById(poReqHeaderId);
    }

    /**
     * Method calls repository to update a specific POReq based on id.
     * @param poReqHeaderId Specific POReqHeader id
     * @param poReqHeader POReqHeader data for updating.
     * @return Updated POReqHeader data.
     * @throws InformationNotFoundException
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
     * Method calls repository to obtain all POReqDetails.
     * @return Listing of all POReqDetail records.
     */
    public Optional<POReqDetail> getPOReqDetailsById(Long id) {
        return poReqDetailRepository.findById(id);
    }
}
