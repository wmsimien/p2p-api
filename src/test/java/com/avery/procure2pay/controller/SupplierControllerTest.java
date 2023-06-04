package com.avery.procure2pay.controller;

import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.service.SupplierService;
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

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private SupplierService supplierService;


    // create standard suppliers
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
    Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

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

    @Test
    @DisplayName("create supplier successfully")
    void createSupplier_success() throws Exception {
        // supplier service should return supplier
        when(supplierService.createSupplier(Mockito.any(Supplier.class))).thenReturn(SUPPLIER_2);
        // build endpoint which should return a json object
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/suppliers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(SUPPLIER_2));
        // make mock request
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(SUPPLIER_2.getId()))
                .andExpect(jsonPath("$.data.name").value(SUPPLIER_2.getName()))
                .andExpect(jsonPath("$.data.contact_name").value(SUPPLIER_2.getContact_name()))
                .andExpect(jsonPath("$.data.contact_phone").value(SUPPLIER_2.getContact_phone()))
                .andExpect(jsonPath("$.data.address").value(SUPPLIER_2.getAddress()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 404 as update supplier by id is not found")
    void updateSupplierById_notFoundRecord() throws Exception {
        // supplier service should return supplier
        when(supplierService.updateSupplierById(anyLong(), Mockito.any(Supplier.class))).thenReturn(Optional.empty());
        // build endpoint which should return a json object
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/suppliers/{supplierId}/", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find any supplier with id 1"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 200 as update supplier by id is successful")
    void updateSupplierById_success() throws Exception {
        // create new supplier records
        Supplier supplier = new Supplier("Cirrus Logic", "Susan", "Carrie", "312-654-0957","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");
        Supplier updatedSupplier = new Supplier("UpdatedCirrus Logic", "Susan", "Carrie", "312-654-0957","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

        // supplier repository should return supplier
        when(supplierService.updateSupplierById(anyLong(), Mockito.any(Supplier.class))).thenReturn(Optional.of(updatedSupplier));
        // build endpoint which should return a json object
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/suppliers/{supplierId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(supplier));;
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(updatedSupplier.getId()))
                .andExpect(jsonPath("$.data.name").value(updatedSupplier.getName()))
                .andExpect(jsonPath("$.data.contact_name").value(updatedSupplier.getContact_name()))
                .andExpect(jsonPath("$.data.contact_phone").value(updatedSupplier.getContact_phone()))
                .andExpect(jsonPath("$.data.address").value(updatedSupplier.getAddress()))
                .andExpect(jsonPath("$.message").value("supplier with id 1 has been successfully updated"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 404 as supplier by id to delete was not found")
    void deleteSupplierById_notFoundRecord() throws Exception {
        // supplier service should return supplier
        when(supplierService.deleteSupplierById(SUPPLIER_3.getId())).thenReturn(Optional.empty());
        // build endpoint which should return a json object
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/suppliers/{supplierId}/", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find any supplier with id 1"))
                .andDo(print());
    }

    @Test
    @DisplayName("return 200 as supplier by id was deleted successfully")
    void deleteSupplierById_success() throws Exception {
        // supplier service should return supplier
        when(supplierService.deleteSupplierById(SUPPLIER_2.getId())).thenReturn(Optional.of(SUPPLIER_2));
        // build endpoint which should return a json object
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/suppliers/{supplierId}/", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(SUPPLIER_2.getId()))
                .andExpect(jsonPath("$.data.name").value(SUPPLIER_2.getName()))
                .andExpect(jsonPath("$.data.contact_name").value(SUPPLIER_2.getContact_name()))
                .andExpect(jsonPath("$.data.contact_phone").value(SUPPLIER_2.getContact_phone()))
                .andExpect(jsonPath("$.data.address").value(SUPPLIER_2.getAddress()))
                .andExpect(jsonPath("$.message").value("supplier with id 2 has been successfully deleted"))
                .andDo(print());
    }
}