package com.avery.procure2pay.service;


import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class SupplierServiceTest {

    @MockBean
    SupplierRepository supplierRepository;

    @Autowired
    SupplierService supplierService;

    // create standard suppliers
    Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim", "Berry", "123-456-7890","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
    Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua", "Landy", "213-546-8790","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");

    @Test
    @DisplayName("get a list of all suppliers")
    void getAllSuppliers_success() {
        List<Supplier> supplierLists = new ArrayList<>(Arrays.asList(SUPPLIER_1, SUPPLIER_2));
        // mock supplierRepository findAll()
        when(supplierRepository.findAll()).thenReturn(supplierLists);

        List<Supplier> supplierList = supplierService.getAllSuppliers();
        Assertions.assertEquals(2, supplierList.size());
    }


}