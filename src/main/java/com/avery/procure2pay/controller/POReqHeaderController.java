package com.avery.procure2pay.controller;


import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.model.POReqHeader;
import com.avery.procure2pay.repository.POReqHeaderRepository;
import com.avery.procure2pay.repository.PoReqDetailRepository;
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
    private PoReqDetailRepository poReqDetailRepository;
    @Autowired
    POReqHeaderService poReqHeaderService;

    static HashMap<String, Object> message = new HashMap<>();

    /**
     * Endpoint calls method to create a purchase order requisition header record.
     * @param poReqHeader Data element to create new req record.
     * @return  POReqHeader record that was created with a Httpstatus of 201; otherwise, if nat able to create a new req,
     * a Httpstatus of 404 is response provided.
     */
    @PostMapping(path="/po-req/")
    public ResponseEntity<?> createPOReqHeader(@RequestBody POReqHeader poReqHeader) {
        POReqHeader newPurchaseReqHeader = poReqHeaderService.createPOReqHeader(poReqHeader);
        if (newPurchaseReqHeader != null) {
            message.put("message", "success");
            message.put("data", newPurchaseReqHeader);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a purchase req at this time");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint calls method to create a purchase order requisition detail record.
     * @param poReqHeaderId POReqHeader id to associate with req detail.
     * @param poReqDetail Data elements to create new POReqDetail.
     * @return HttpStatus of 201 when req detail is created successfully and 404 when not.
     */
    @PostMapping(path="/po-req/{poReqHeaderId}/")
    public ResponseEntity<?> createPOReqDetail(@PathVariable(value="poReqHeaderId") Long poReqHeaderId, @RequestBody POReqDetail poReqDetail) {
        POReqHeader newPOReqHeader = poReqHeaderService.createPOReqDetail(poReqHeaderId, poReqDetail);
        if (newPOReqHeader != null) {
            message.put("message", "success");
            message.put("data", newPOReqHeader);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "unable to create a purchase req at this time");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint calls method to obtain all purchase order requisitions.
     * @return Listing of all purchase orders.
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
     * Endpoint calls method to obtain purchase order requisition for a specific POReqHeader.
     * @param poReqHeaderId POReqHeader to obtain.
     * @return HttpStatus of 200 on success and 404 when record is not found.
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
     * Endpoint calls method to update a specific POReqHeader record.
     * @param poReqHeaderId Specific req header record.
     * @param poReqHeader Specific data to use in update.
     * @return HttpStatus of 404 when record is not found for update and a HttpStatus
     * of 200 on successful update.
     * @throws InformationNotFoundException
     */
    @PutMapping(path="/po-req/{poReqHeaderId}/")
    public ResponseEntity<?> updatePOReqById(@PathVariable(value="poReqHeaderId") Long poReqHeaderId, @RequestBody POReqHeader poReqHeader) throws InformationNotFoundException {
        Optional<POReqHeader> poReqToUpdate = poReqHeaderService.updatePOReqById(poReqHeaderId, poReqHeader);
        if (poReqToUpdate.isEmpty()) {
            message.put("message", "cannot find po-req with id " + poReqHeaderId);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            message.put("message", "purchase order with id " + poReqHeaderId + " has been successfully updated");
            message.put("data", poReqToUpdate.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * Endpoint calls method to update req detail record(s) for a specific req header record.
     * @param poReqHeaderId Req header id of the associated req detail record.
     * @param poReqDetail Data elements to user for updating.
     * @return HttStatus of 200 on success and a 404 when the associated req header record
     * is not found.
     */
    @PutMapping(path="/po-req-detail/{poReqHeaderId}/")
    public ResponseEntity<?> updatePOReqDetailForPOReqHeader(@PathVariable(value="poReqHeaderId") Long poReqHeaderId, @RequestBody POReqDetail poReqDetail) {
        Optional<POReqHeader> poReqToUpdate = poReqHeaderRepository.findById(poReqHeaderId);
        if (poReqToUpdate.isPresent()) {
            poReqDetailRepository.save(poReqDetail);
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
     * Endpoint calls method to delete the specific req header record and associated req detail records.
     * @param poReqHeaderId ReqHeader record id to remove.
     * @return HttpStatus of 200 when successful and a 404 when the record is not found in order to be deleted.
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
