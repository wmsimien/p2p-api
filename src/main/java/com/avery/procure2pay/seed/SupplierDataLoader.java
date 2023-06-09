package com.avery.procure2pay.seed;

import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SupplierDataLoader implements CommandLineRunner {
    Logger logger = Logger.getLogger(SupplierDataLoader.class.getName());

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSupplier();
    }

    private void loadSupplier() {
        if (supplierRepository.count() == 0) {
            Supplier SUPPLIER_1 = new Supplier("ZBiotics", "Tim Berry", "123-456-7890", "1234 Miller Court","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
            Supplier SUPPLIER_2 = new Supplier("Bright.md", "Joshua Landy", "213-546-8790", "2135 Summer Lane","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
            Supplier SUPPLIER_3 = new Supplier("Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Tunney Court","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

            Supplier SUPPLIER_4 = new Supplier("Long Star Material Handling", "Bill Gray", "214-379-3388", "3010 LBJ FWY","Dallas","Texas","75234","214-379-3388","bill.gray@gmail.com");
            Supplier SUPPLIER_5 = new Supplier("Target", "Katie Boylan", "800-775-3110", "2022 Corporate Circle","Austin","Texas","75610","214-657-9800","katie.boylan@gmail.com");
            Supplier SUPPLIER_6 = new Supplier("Walmart", "Gus Monroe", "877-294-7880", "702 SW 8th St","Bentonville","AR","72716","479-273-4941","gus.monroe@gmail.com");

            supplierRepository.save(SUPPLIER_1);
            supplierRepository.save(SUPPLIER_2);
            supplierRepository.save(SUPPLIER_3);

            supplierRepository.save(SUPPLIER_4);
            supplierRepository.save(SUPPLIER_5);
            supplierRepository.save(SUPPLIER_6);
        }


        logger.info("Count of seeded Suppliers records from SupplierDataLoader:  " + supplierRepository.count());
    }
}
