package com.avery.procure2pay.service;

import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("get a supplier by id")
    void getSupplierById() {
        // mock supplierRepository findById()
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(SUPPLIER_2));

        Optional<Supplier> supplier = supplierService.getSupplierById(2L);

        Assertions.assertTrue(supplier.isPresent());
        Assertions.assertEquals(2, supplier.get().getId());
    }

    @Test
    @DisplayName("create new supplier")
    void createSupplier_success() {
        Supplier newSupplier = new Supplier("New Bright.md", "NewJoshua", "NewLandy", "213-546-8790","Austin","Texas","75610","214-657-9800","newjoshua.newlandy@gmail.com");
        // mock supplierRepository save()
        when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(newSupplier);

        Supplier createdSupplier = supplierService.createSupplier(newSupplier);

        when(supplierService.createSupplier(Mockito.any(Supplier.class))).thenReturn(newSupplier);

        Assertions.assertNotNull(createdSupplier);
        assertEquals(newSupplier.getName(), createdSupplier.getName());
        assertEquals(newSupplier.getAddress(), createdSupplier.getAddress());
    }

    @Test
    @DisplayName("update a specific supplier record successfully")
    void updateEmployeeById_success() {
        Supplier supplier = new Supplier("NewBright.md", "NewJoshua", "NewLandy", "213-546-8790","Austin","Texas","75610","214-657-9800","newjoshua.newlandy@gmail.com");

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(supplier);

        Supplier supplierObject = new Supplier("UpdateBright.md", "UpdatedJoshua", "UpdatedLandy", "213-546-8790","Austin","Texas","75610","214-657-9800","updatedjoshua.newlandy@gmail.com");
        Optional<Supplier> updatedSupplier = supplierService.updateSupplierById(1L, supplierObject);

        Assertions.assertEquals(updatedSupplier.get().getName(), supplierObject.getName());
        Assertions.assertEquals(updatedSupplier.get().getContact_name(), supplierObject.getContact_name());
    }

    @Test
    @DisplayName("delete a supplier by id")
    void deleteSupplierById_success() {
        // mock supplierRepository findById()
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(SUPPLIER_2));
        Optional<Supplier> supplier = supplierService.deleteSupplierById(SUPPLIER_2.getId());
        Assertions.assertNotNull(supplier);
    }


}