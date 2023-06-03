package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import com.avery.procure2pay.service.SupplierService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierRepository supplierRepository;
    @MockBean
    private SupplierService supplierService;


    // create standard suppliers
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim", "Berry", "123-456-7890","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua", "Landy", "213-546-8790","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
    Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan", "Carrie", "312-654-0957","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

    @Test
    @DisplayName("return 200 when all suppliers are obtain successfully")
    void getAllSuppliers_success() throws Exception {
        // build data to retrieve
        List<Supplier> supplierList = new ArrayList<>(Arrays.asList(SUPPLIER_1, SUPPLIER_2, SUPPLIER_3));
        // supplier service should return three suppliers
        when(supplierService.getAllSuppliers()).thenReturn(supplierList);
        // build endpoint which should return a json object
        mockMvc.perform(MockMvcRequestBuilders.get("/api/suppliers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("get supplier by id successfully")
    void getSupplierById_success() throws Exception {
        // supplier service should return supplier
        when(supplierService.getSupplierById(SUPPLIER_2.getId())).thenReturn(Optional.of(SUPPLIER_2));
        // build endpoint which should return a json object
        mockMvc.perform(MockMvcRequestBuilders.get("/api/suppliers/{supplierId}/", 2L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(SUPPLIER_2.getId()))
                .andExpect(jsonPath("$.data.name").value(SUPPLIER_2.getName()))
                .andExpect(jsonPath("$.data.contact_name").value(SUPPLIER_2.getContact_name()))
                .andExpect(jsonPath("$.data.contact_phone").value(SUPPLIER_2.getContact_phone()))
                .andExpect(jsonPath("$.data.address").value(SUPPLIER_2.getAddress()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());

    }
}