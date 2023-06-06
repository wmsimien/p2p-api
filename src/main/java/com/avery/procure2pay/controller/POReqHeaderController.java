package com.avery.procure2pay.controller;


import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.model.POReqHeader;
import com.avery.procure2pay.repository.POReqHeaderRepository;
import com.avery.procure2pay.service.POReqDetailService;
import com.avery.procure2pay.service.POReqHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class POReqHeaderController{

    @Autowired
    private POReqHeaderRepository poReqHeaderRepository;
    @Autowired
    POReqHeaderService poReqHeaderService;
    @Autowired
    POReqDetailService poReqDetailService;

    static HashMap<String, Object> message = new HashMap<>();


    /**
     *
     * @param poReqHeader
     * @return
     */
    @PostMapping(path="/po-req/")
    public ResponseEntity<?> createPOReqHeader(@RequestBody POReqHeader poReqHeader) {
        POReqHeader newPurchaseReq = poReqHeaderService.createPOReqHeader(poReqHeader);
        if (newPurchaseReq != null) {
            message.put("message", "success");
            message.put("data", newPurchaseReq);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a purchase req at this time");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @return
     */
    @GetMapping(path="/po-req/")
    public ResponseEntity<?> getPOReqs() {
        List<POReqHeader> poReqLists = poReqHeaderService.getPOReqs();
        if (poReqLists.isEmpty()) {
            message.put("message", "cannot find any purchase reqs");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", poReqLists);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     *
     * @param poReqHeaderId
     * @return
     */
    @GetMapping(path="/po-req/{poReqHeaderId}/")
    public ResponseEntity<?> getPOReqById(@PathVariable(value="poReqHeaderId") Long poReqHeaderId) {
        Optional<POReqHeader> poReqHeader = poReqHeaderService.getPOReqById(poReqHeaderId);
        if (poReqHeader.isPresent()) {
            message.put("message", "success");
            message.put("data", poReqHeader);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find po-req with id " + poReqHeaderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param poReqHeaderId
     * @param poReqHeader
     * @return
     * @throws InformationNotFoundException
     */
    @PutMapping(path="/po-req/{poReqHeaderId}/")
    public ResponseEntity<?> updatePOReqById(@PathVariable(value="poReqHeaderId") Long poReqHeaderId, @RequestBody POReqHeader poReqHeader) throws InformationNotFoundException {
        Optional<POReqHeader> poReqToUpdate = poReqHeaderRepository.findById(poReqHeaderId);//poReqHeaderService.getPOReqById(poReqHeaderId);
        if (poReqToUpdate.isEmpty()) {
            message.put("message", "cannot find po-req with id " + poReqHeaderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
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
            message.put("message", "purchase order with id " + poReqHeaderId + " has been successfully updated");
            message.put("data", poReqToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping(path="/po-req-details/")
    public ResponseEntity<?> getPOReqDetails() {
        List<POReqDetail> poReqDetailList = poReqDetailService.getPOReqDetails();
        if (poReqDetailList.isEmpty()) {
            message.put("message", "cannot find any purchase req details");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "success");
            message.put("data", poReqDetailList);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     *
     * @param poReqHeaderId
     * @param poReqDetail
     * @return
     */
    @PostMapping(path="/po-req/{poReqHeaderId}/poReqDetailList")
    public ResponseEntity<?> addPOReqDetailToPOReqHeader(@PathVariable(value="poReqHeaderId") Long poReqHeaderId, @RequestBody POReqDetail poReqDetail) {
        Optional<POReqHeader> poReqToUpdate = poReqHeaderRepository.findById(poReqHeaderId);
        if (poReqToUpdate.isPresent()) {
            poReqToUpdate.get().addPOReqDetail(poReqDetail);
            poReqHeaderRepository.save(poReqToUpdate.get());
            message.put("message", "purchase order with id " + poReqHeaderId + " has been successfully updated");
            message.put("data", poReqToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find po-req with id " + poReqHeaderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     *
     * @param poReqHeaderId
     * @return
     */
    @DeleteMapping(path="/po-req/{poReqHeaderId}/")
    public ResponseEntity<?> deletePOReqById(@PathVariable(value="poReqHeaderId") Long poReqHeaderId) {
        Optional<POReqHeader> poReqHeaderToDelete = poReqHeaderRepository.findById(poReqHeaderId);
        if (poReqHeaderToDelete.isPresent()) {
            poReqHeaderRepository.deleteById(poReqHeaderId);
            message.put("message", "success");
            message.put("data", poReqHeaderToDelete);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message.put("message", "cannot find po-req with id " + poReqHeaderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
}
