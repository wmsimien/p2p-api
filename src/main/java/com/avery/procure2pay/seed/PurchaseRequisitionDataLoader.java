package com.avery.procure2pay.seed;

import com.avery.procure2pay.model.ItemFavorites;
import com.avery.procure2pay.model.PurchaseOrder;
import com.avery.procure2pay.model.Supplier;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class PurchaseRequisitionDataLoader implements CommandLineRunner {
    Logger logger = Logger.getLogger(PurchaseRequisitionDataLoader.class.getName());

    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public void run(String... args) throws Exception {

    }

    private void loadPurchaseOrderRequisition() {
        if (purchaseOrderRepository.count() == 0) {

            PurchaseOrder PO_2 = new PurchaseOrder();
            PurchaseOrder PO_3 = new PurchaseOrder();
            ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
            ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
            ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

            Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
            Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
            Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

            PurchaseOrder PO_1 = new PurchaseOrder();
            PO_1.setReqDate(LocalDate.parse("2023-06-04"));
            PO_1.setItem(FAVITEM_1);
            PO_1.setQty(2.0);
            PO_1.setPrice(FAVITEM_1.getUnitPrice());
            PO_1.setSupplier(SUPPLIER_1);

            PO_1.setCreatedDate(LocalDate.parse("2023-06-01"));
            PO_2.setId(2L);
            PO_2.setItem(FAVITEM_2);
            PO_2.setQty(2.0);
            PO_2.setCreatedDate(LocalDate.parse("2023-06-02"));

        }
        logger.info("Count of seeded Purchase Order Requisition records from PurchaseRequisitionDataLoader:  " + purchaseOrderRepository.count());
    }
}
