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
            Supplier SUPPLIER_1 = new Supplier("ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address1","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
            Supplier SUPPLIER_2 = new Supplier("Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
            Supplier SUPPLIER_3 = new Supplier("Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

            supplierRepository.save(SUPPLIER_1);
            supplierRepository.save(SUPPLIER_2);
            supplierRepository.save(SUPPLIER_3);
        }


        logger.info("Count of seeded Suppliers records from SupplierDataLoader:  " + supplierRepository.count());
    }
}
