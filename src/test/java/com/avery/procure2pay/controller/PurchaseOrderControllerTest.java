package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.service.PurchaseOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseOrderController.class)
class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PurchaseOrderService purchaseOrderService;


    // create standard objects
    PurchaseOrder PO_1 = new PurchaseOrder();
    PurchaseOrder PO_2 = new PurchaseOrder();
    PurchaseOrder PO_3 = new PurchaseOrder();
    ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
    ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
    Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

    @Test
    @DisplayName("get all purchase order success")
    void getAllPurchaseOrders_success() throws Exception {
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setSupplier(SUPPLIER_1);
        PO_1.setPaymentTerms(SUPPLIER_1.getPaymentMethod());
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));
        PO_2.setId(2L);
//        PO_2.setItem(FAVITEM_2);
        PO_2.addItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setSupplier(SUPPLIER_2);
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

    @Test
    @DisplayName("get purchase order by id success")
    void getPurchaseOrderById_success() throws Exception {
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setSupplier(SUPPLIER_1);
        PO_1.setPaymentTerms(SUPPLIER_1.getPaymentMethod());
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));

        when(purchaseOrderService.getPurchaseOrdersById(PO_1.getId())).thenReturn(Optional.of(PO_1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase-orders/{purchaseOrderId}/", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(PO_1.getId()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 201 when a purchase order is successfully created")
    void createPurchaseOrder_success() throws Exception {
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));

        when(purchaseOrderService.createPurchaseOrder(Mockito.any(PurchaseOrder.class))).thenReturn(PO_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/purchase-orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(PO_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(PO_1.getId()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 404 when purchase order by id is not a success")
    void updatePurchaseOrderById_recordNotFount() throws Exception {
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));

        when(purchaseOrderService.updatePurchaseOrderById(anyLong(), Mockito.any(PurchaseOrder.class))).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/purchase-orders/{purchaseOrderId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find purchase order with id 1"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 200 when purchase order by id is success")
    void updatePurchaseOrderById_success() throws Exception {
        PO_1.setId(1L);
//        PO_1.setItem(FAVITEM_1);
        PO_1.addItem(FAVITEM_1);
        PO_1.setQty(2.0);
        PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));

        PO_2.setId(2L);
//        PO_2.setItem(FAVITEM_2);
        PO_2.addItem(FAVITEM_2);
        PO_2.setQty(2.0);
        PO_2.setCreatedDate(LocalDate.parse("2023-06-02"));

        when(purchaseOrderService.updatePurchaseOrderById(anyLong(), Mockito.any(PurchaseOrder.class))).thenReturn(Optional.of(PO_2));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/purchase-orders/{purchaseOrderId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(PO_1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(PO_2.getId()))
                .andExpect(jsonPath("$.message").value("purchase order with id 1 has been successfully updated"))
                .andDo(print());
    }
}