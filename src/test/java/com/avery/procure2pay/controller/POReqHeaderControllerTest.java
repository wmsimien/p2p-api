package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.POReqDetail;
import com.avery.procure2pay.model.POReqHeader;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.POReqHeaderRepository;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(POReqHeaderController.class)
class POReqHeaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    POReqHeaderRepository poReqHeaderRepository;

    // create standard objects
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
    Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");
    POReqHeader poReqHeader = new POReqHeader(1L,null,LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-01"), "","","", "po notes","req notes inter", "req notes ext", 1L, null, 1L, LocalDate.parse("2023-06-01"), null,null);


    @Test
    void createPOReqHeader_success() throws Exception {
        poReqHeader.setSupplier(SUPPLIER_1);
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
        poReqHeader.setSupplier(SUPPLIER_1);
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
                new POReqHeader(LocalDate.parse("2023-06-05"), LocalDate.parse("2023-06-13"), "","","", "po notes again","req notes inter again", "req notes ext again", 1L, null, 1L, LocalDate.parse("2023-06-06"), null,null)
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
        POReqHeader poReqHeader = new POReqHeader(1L,null,LocalDate.parse("2023-06-01"), LocalDate.parse("2023-06-01"), "","","", "po notes","req notes inter", "req notes ext", 1L, null, 1L, LocalDate.parse("2023-06-01"), null,null);

        poReqHeader.setSupplier(SUPPLIER_1);

        when(poReqHeaderRepository.findById(anyLong())).thenReturn(Optional.of(poReqHeader));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/po-req/1/poReqDetailList/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(poReqHeader));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(poReqHeader.getId()))
//                .andExpect(jsonPath("$.data.supplier").value(poReqHeader.getSupplier()))
//                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

}