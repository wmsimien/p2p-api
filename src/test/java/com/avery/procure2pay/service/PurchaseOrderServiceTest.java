package com.avery.procure2pay.service;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

}