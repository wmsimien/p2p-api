package com.avery.procure2pay.service;

import com.avery.procure2pay.exception.InformationNotFoundException;
import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.repository.ItemFavoritesRepository;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    ItemFavoritesRepository itemFavoritesRepository;

    @Autowired
    public void setPurchaseOrderRepository(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    /**
     * Method obtains list of purchase order
     * @return List of purchase orders.
     */
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    public List<PurchaseOrder> getAllPurchaseReqs() {
       List<PurchaseOrder> poList = purchaseOrderRepository.findAll();
       poList.stream().filter(rec -> rec.getReqNo() != null);
       return poList;
    }

    /**
     * Method obtains purchase order by id.
     * @param purchaseOrderId Specific purchase order value/id.
     * @return Purchase order record for the specified id.
     */
    public Optional<PurchaseOrder> getPurchaseOrdersById(Long purchaseOrderId) {
        return purchaseOrderRepository.findById(purchaseOrderId);
    }

    /**
     * Method created purchase order record for data elements provided.
     * @param purchaseOrderObject Data elements to create new purchase order.
     * @return New purchase order record.
     */
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrderObject) {
        return purchaseOrderRepository.save(purchaseOrderObject);
    }

    public PurchaseOrder addItemToPurchaseOrder(Long purchaseOrderId, ItemFavorites itemFavorites) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).get();
        if (purchaseOrder != null) {
            Optional<ItemFavorites> founditemFav = itemFavoritesRepository.findById(itemFavorites.getId());
            if (founditemFav.isPresent()) {
                purchaseOrder.getItems().add(founditemFav.get());
            }
        } else {
            {
                throw new InformationNotFoundException("purchase order with id " + purchaseOrderId + " not found");
            }
        }
        return purchaseOrderRepository.save(purchaseOrder);
    }

    /**
     * Method updates the specified purchase order with the data elements provided.
     * @param purchaseOrderId Specified purchase order id to modify.
     * @param purchaseOrderObject Purchase order data elements to update specified id.
     * @return Updated purchase order record.
     * @throws InformationNotFoundException Response custom message.
     */
    public Optional<PurchaseOrder> updatePurchaseOrderById(Long purchaseOrderId, PurchaseOrder purchaseOrderObject) throws InformationNotFoundException {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (purchaseOrder.isPresent()) {
//            purchaseOrder.get().setItem(purchaseOrderObject.getItem());
//            ItemFavorites itemFavorites = itemFavoritesRepository.

            purchaseOrder.get().setApprovedBy(purchaseOrderObject.getApprovedBy());
            purchaseOrder.get().setApprovedDate(purchaseOrderObject.getApprovedDate());
            purchaseOrder.get().setDeliveryDate(purchaseOrderObject.getDeliveryDate());
            purchaseOrder.get().setGlAcctNo(purchaseOrderObject.getGlAcctNo());
            purchaseOrder.get().setPaymentTerms(purchaseOrderObject.getPaymentTerms());
            purchaseOrder.get().setQty(purchaseOrderObject.getQty());
            purchaseOrder.get().setPrice(purchaseOrderObject.getPrice());
            purchaseOrder.get().setCreatedBy(purchaseOrderObject.getCreatedBy());
            purchaseOrder.get().setCreatedDate(purchaseOrderObject.getCreatedDate());
            purchaseOrder.get().setPoNotes(purchaseOrderObject.getPoNotes());
            purchaseOrder.get().setReqNo(purchaseOrderObject.getReqNo());
            purchaseOrder.get().setReqDate(purchaseOrderObject.getReqDate());
            purchaseOrder.get().setReqNotesExternal(purchaseOrderObject.getReqNotesExternal());
            purchaseOrder.get().setReqNotesInternal(purchaseOrderObject.getReqNotesInternal());
            purchaseOrder.get().setShipTo(purchaseOrderObject.getShipTo());
            purchaseOrder.get().setStatus(purchaseOrderObject.getStatus());
            purchaseOrder.get().setSupplier(purchaseOrderObject.getSupplier());
            purchaseOrderRepository.save(purchaseOrder.get());
            return purchaseOrder;
        } else {
            throw new InformationNotFoundException("purchase order with id " + purchaseOrderId + " not found");
        }
    }
}
