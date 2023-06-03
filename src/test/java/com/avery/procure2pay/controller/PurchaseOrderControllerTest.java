package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import com.avery.procure2pay.service.PurchaseOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseOrderController.class)
class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PurchaseOrderRepository purchaseOrderRepository;

    @MockBean
    private PurchaseOrderService purchaseOrderService;


    // create standard objects
    PurchaseOrder PO_1 = new PurchaseOrder();
    PurchaseOrder PO_2 = new PurchaseOrder();
    PurchaseOrder PO_3 = new PurchaseOrder();
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
    ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

    @Test
    @DisplayName("get all purchase order success")
    void getAllPurchaseOrders_success() throws Exception {
        PO_1.setId(1L);
        PO_1.setItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));
        PO_2.setId(2L);
        PO_2.setItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setCreatedDate(LocalDate.parse("2023-06-02"));
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>(Arrays.asList(PO_1, PO_2));
        when(purchaseOrderService.getAllPurchaseOrders()).thenReturn(purchaseOrderList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase-orders/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }


}