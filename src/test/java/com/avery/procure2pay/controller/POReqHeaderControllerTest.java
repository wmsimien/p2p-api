package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.*;
import com.avery.procure2pay.repository.POReqHeaderRepository;
import com.avery.procure2pay.seed.PurchaseRequisitionDataLoader;
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
import java.util.logging.Logger;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(POReqHeaderController.class)
class POReqHeaderControllerTest {

    Logger logger = Logger.getLogger(POReqHeaderControllerTest.class.getName());
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    POReqHeaderRepository poReqHeaderRepository;

    // create standard objects

    ItemFavorites FAVITEM_1 = new ItemFavorites("Tubing", "Long Heavy Tubing", 25.75, "each");
    ItemFavorites FAVITEM_2 = new ItemFavorites("Small Tubing", "Small Lite Tubing", 15.75, "pounds");
    ItemFavorites FAVITEM_3 = new ItemFavorites("XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");
//    public void setQty(Double qty) {
//        this.qty = qty;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public void setItems(List<ItemFavorites> items) {
//        this.items = items;
//    }
//    poReqDetails1.
//    this.qty = qty;
//        this.price = price;
//        this.items = items;
    Double qty = 2.0;
    Double price = 250.00;
    List<ItemFavorites> itemsList = Arrays.asList(FAVITEM_1, FAVITEM_2, FAVITEM_3);
//    List<POReqDetail> detailList = [qty, price, itemsList];
    POReqDetail poReqDetail = new POReqDetail(qty, price, itemsList);
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
    Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");
    POReqHeader poReqHeader = new POReqHeader(1L, (Long) null, null,LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-01"), "","","", "po notes","req notes inter", "req notes ext", 1L, null, null, LocalDate.parse("2023-06-01"), null, (LocalDate) null);



    @Test
    void createPOReqHeader_success() throws Exception {
        poReqHeader.setSupplierLists(Arrays.asList(SUPPLIER_1));

        when(poReqHeaderRepository.save(Mockito.any(POReqHeader.class))).thenReturn(poReqHeader);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/po-req/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(poReqHeader));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
//                .andExpect(jsonPath("$.data.supplier").value(poReqHeader.getSupplier()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    void getPOReqHeader_success() throws Exception {
//        poReqHeader.setSupplier(SUPPLIER_1);
        poReqHeader.setSupplierLists(Arrays.asList(SUPPLIER_1));
        when(poReqHeaderRepository.save(Mockito.any(POReqHeader.class))).thenReturn(poReqHeader);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/po-req/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(poReqHeader));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
//                .andExpect(jsonPath("$.data.supplier").value(poReqHeader.getSupplier()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("get all purchase reqs success")
    void getPOReqs_success() throws Exception {
        List<POReqHeader> poReqHeaderLists = new ArrayList<>(Arrays.asList(
                new POReqHeader(1L, null,null ,LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-01"), "","","", "po notes","req notes inter agin", "req notes ext again", 1L, null, null, LocalDate.parse("2023-06-01"), null,null)
        ));

        when(poReqHeaderRepository.findAll()).thenReturn(poReqHeaderLists);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/po-req/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("get po-req by id success")
    void getPOReqById_success() throws Exception {
        when(poReqHeaderRepository.findById(anyLong())).thenReturn(Optional.of(poReqHeader));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/po-req/{poReqHeaderId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test //Optional<POReqHeader> poReqToUpdate = poReqHeaderRepository.findById(poReqHeaderId);
    @DisplayName("return 200 when po-req by id is success")
    void updatePOReqById_success() throws Exception {
        when(poReqHeaderRepository.findById(anyLong())).thenReturn(Optional.of(poReqHeader));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/po-req/{poReqHeaderId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(poReqHeader));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
                .andExpect(jsonPath("$.message").value("purchase order with id 1 has been successfully updated"))
                .andDo(print());
    }

    @Test
    void addPOReqDetailToPOReqHeader_success() throws Exception {
        POReqHeader poReqHeader = new POReqHeader(1L,null,null,LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-01"), "","","", "po notes","req notes inter", "req notes ext", 1L, null, 1L, LocalDate.parse("2023-06-01"), null,null);
        logger.info("createPOreqHeader " + poReqDetail);
//        logger.info("createPOreqHeader " + poReqDetail.);
        poReqHeader.setSupplierLists(Arrays.asList(SUPPLIER_1));

        when(poReqHeaderRepository.findById(anyLong())).thenReturn(Optional.of(poReqHeader));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/po-req/1/poReqDetailList/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(poReqHeader));

//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data", notNullValue()))
//                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
////                .andExpect(jsonPath("$.data.supplier").value(poReqHeader.getSupplier()))
////                .andExpect(jsonPath("$.message").value("success"))
//                .andDo(print());
    }

}