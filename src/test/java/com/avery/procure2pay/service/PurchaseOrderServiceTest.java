package com.avery.procure2pay.service;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PurchaseOrderServiceTest {

    @MockBean
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    // create standard objects
    PurchaseOrder PO_1 = new PurchaseOrder();
    PurchaseOrder PO_2 = new PurchaseOrder();
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");


    @Test
    @DisplayName("get a list of all purchase orders success")
    void getAllPurchaseOrders_success() {
        List<PurchaseOrder> purchaseOrderLists = new ArrayList<>(Arrays.asList(PO_1, PO_2));
        // mock purchaseOrderRepository findAll()
        when(purchaseOrderRepository.findAll()).thenReturn(purchaseOrderLists);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderService.getAllPurchaseOrders();
        Assertions.assertEquals(2, purchaseOrderList.size());
    }

    @Test
    @DisplayName("get a purchase order by id success")
    void getPurchaseOrderById_success() {
        PO_2.setId(2L);
//        PO_2.setItem(FAVITEM_2);
        PO_2.addItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setCreatedDate(LocalDate.parse("2023-06-01"));
        // mock purchaseOrderRepository findById()
        when(purchaseOrderRepository.findById(anyLong())).thenReturn(Optional.of(PO_2));

        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrdersById(2L);

        Assertions.assertTrue(purchaseOrder.isPresent());
        Assertions.assertEquals(2, purchaseOrder.get().getId());
    }

    @Test
    @DisplayName("create new purchase order success")
    void createPurchaseOrder_success() {
        PO_2.setId(2L);
//        PO_2.setItem(FAVITEM_2);
        PO_2.addItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setCreatedDate(LocalDate.parse("2023-06-01"));

        // mock supplierRepository save()
        when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(PO_2);

        PurchaseOrder createdPO = purchaseOrderService.createPurchaseOrder(PO_2);

        when(purchaseOrderService.createPurchaseOrder(Mockito.any(PurchaseOrder.class))).thenReturn(createdPO);

        Assertions.assertNotNull(createdPO);
//        assertEquals(PO_2.getItem(), createdPO.getItem());
        assertEquals(PO_2.getItems(), createdPO.getItems());
        assertEquals(PO_2.getCreatedDate(), createdPO.getCreatedDate());
    }

    @Test
    @DisplayName("update a specific purchase order record successfully")
    void updatePurchaseById_success() {
        PO_2.setId(2L);
//        PO_2.setItem(FAVITEM_2);
        PO_2.addItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setCreatedDate(LocalDate.parse("2023-06-01"));

        when(purchaseOrderRepository.findById(anyLong())).thenReturn(Optional.of(PO_2));
        when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(PO_2);

        Supplier supplierObject = new Supplier("UpdateBright.md", "UpdatedJoshua", "UpdatedLandy", "213-546-8790","Austin","Texas","75610","214-657-9800","updatedjoshua.newlandy@gmail.com");
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));
//        Optional<PurchaseOrder> updatedPO = purchaseOrderService.updatePurchaseOrderById(1L, PO_1);
        PurchaseOrder updatedPOReq = purchaseOrderService.addItemToPurchaseOrder(1L,FAVITEM_2);

//        Assertions.assertEquals(updatedPO.get().getItem(), PO_1.getItem());
        Assertions.assertEquals(updatedPOReq.getItems(), PO_2.getItems());
//        Assertions.assertEquals(updatedPO.get().getPrice(), PO_1.getPrice());
    }

}