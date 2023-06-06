package com.avery.procure2pay.seed;

import com.avery.procure2pay.model.*;
import com.avery.procure2pay.repository.POReqHeaderRepository;
import com.avery.procure2pay.repository.PoReqDetailRepository;
import com.avery.procure2pay.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
public class PurchaseRequisitionDataLoader implements CommandLineRunner {
    Logger logger = Logger.getLogger(PurchaseRequisitionDataLoader.class.getName());

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    POReqHeaderRepository poReqHeaderRepository;
    @Autowired
    PoReqDetailRepository poReqDetailRepository;

    @Override
    public void run(String... args) throws Exception {
        loadPurchaseOrderRequisition();
    }

    private void loadPurchaseOrderRequisition() {
        if (poReqHeaderRepository.count() == 0) {
            Employee employee1 = new Employee("Cindy", "Smith", "Sales", "Rep", "cindy.smith@gmail.com", "");
            Employee employee2 = new Employee("Connie", "Jones", "Cooperate", "Receptionist", "connie.jones@gmail.com", "");
            Employee employee3 = new Employee("Timmy", "Blue", "Facilities", "Manager", "timmy.blue@gmail.com", "878787-001");
            ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
            ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
            ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

            Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
            Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
            Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");
            List<ItemFavorites> itemsList = Arrays.asList(FAVITEM_1, FAVITEM_2, FAVITEM_3);

            POReqHeader reqHeader = new POReqHeader();
            POReqDetail reqDetail = new POReqDetail();
            reqHeader.setReqDate(LocalDate.parse("2023-06-04"));
//            reqHeader.setSupplier(SUPPLIER_1);
            reqHeader.setApprovedBy(null);
            reqHeader.setApprovedDate(null);
            reqHeader.setCreatedDate(LocalDate.parse("2023-06-04"));
            reqHeader.setCreatedBy(employee1.getId());
            reqHeader.setDeliveryDate(LocalDate.parse("2023-06-13"));
            reqHeader.setReqNotesExternal("external req note");
            reqHeader.setReqNotesInternal("internal req note");
//            reqHeader.setStatus();
//            reqHeader.setPaymentTerms();
            reqHeader.setShipTo(employee2.getId());
//            reqHeader.setSupplier();
            reqHeader.setPoNotes("po notes");
            reqHeader.setGlAcctNo(null);
            poReqHeaderRepository.save(reqHeader);
            logger.info("reqHeader" + reqHeader);
            List<POReqHeader> poReqHeaders = new ArrayList<>();
            poReqHeaders.add(reqHeader);


            reqDetail.setItems(itemsList);
        }
        logger.info("Count of seeded Purchase Order Requisition records from poReqHeaderDataLoader:  " + poReqHeaderRepository.count());


        if (purchaseOrderRepository.count() == 0) {

            ItemFavorites FAVITEM_1 = new ItemFavorites(1L, "Tubing", "Long Heavy Tubing", 25.75, "each");
            ItemFavorites FAVITEM_2 = new ItemFavorites(2L, "Small Tubing", "Small Lite Tubing", 15.75, "pounds");
            ItemFavorites FAVITEM_3 = new ItemFavorites(3L, "XSmall Tubing", "Xtra-Small Heavy Tubing", 5.75, "skids");

            Supplier SUPPLIER_1 = new Supplier(1L, "ZBiotics", "Tim Berry", "123-456-7890", "1234 Some Address","Austin","Texas","75600","234-567-8900","tim.berry@gmail.com");
            Supplier SUPPLIER_2 = new Supplier(2L, "Bright.md", "Joshua Landy", "213-546-8790", "2135 Some Address2","Austin","Texas","75610","214-657-9800","joshua.landy@gmail.com");
            Supplier SUPPLIER_3 = new Supplier(3L, "Cirrus Logic", "Susan Carrie", "312-654-0957", "3126 Some Address3","Austin","Texas","75630","432-675-0870","susan.carrie@gmail.com");

            PurchaseOrder PO_1 = new PurchaseOrder();
            PO_1.setReqDate(LocalDate.parse("2023-06-04"));
//            PO_1.setItem(FAVITEM_1);
//            PO_1.setItem(new ItemFavorites(FAVITEM_1.getId(), FAVITEM_1.getName(), FAVITEM_1.getDescription(),FAVITEM_1.getUnitPrice(), FAVITEM_1.getUom()));
            PO_1.addItem(FAVITEM_1);
            PO_1.setQty(11.0);
            PO_1.setPrice(FAVITEM_1.getUnitPrice());
//            PO_1.setSupplier(SUPPLIER_1);
//            PO_1.setSupplier(new Supplier("new super duper supplier", "Trapper John", "","","","","","",""));
            PO_1.setPaymentTerms(SUPPLIER_1.getPaymentMethod());
            PO_1.setShipTo(1L);
            PO_1.setDeliveryDate(LocalDate.parse("2023-06-13"));
            PO_1.setReqNotesInternal("Internal Note Here...");
            PO_1.setReqNotesExternal("External Note Here...");
            PO_1.setCreatedBy(1L);
            PO_1.setCreatedDate(LocalDate.parse("2023-06-04"));
            purchaseOrderRepository.save(PO_1);
            logger.info("PO1" + PO_1);
            logger.info("PO1 - items " + PO_1.getItems());
            PurchaseOrder PO_2 = new PurchaseOrder();
            PO_2.setReqDate(LocalDate.parse("2023-06-04"));
//            PO_2.setItem(FAVITEM_2);
            PO_2.addItem(FAVITEM_2);
            PO_2.addItem(FAVITEM_1);
            PO_2.setQty(22.0);
            PO_2.setPrice(FAVITEM_2.getUnitPrice());
            logger.info("PO2 - items " + PO_2.getItems());
//            PO_2.setSupplier(SUPPLIER_2);
            PO_2.setPaymentTerms(SUPPLIER_2.getPaymentMethod());
            PO_2.setShipTo(2L);
            PO_2.setDeliveryDate(LocalDate.parse("2023-06-14"));
            PO_2.setReqNotesInternal("Internal Note Here...");
            PO_2.setReqNotesExternal("External Note Here...");
            PO_2.setCreatedBy(2L);
            PO_2.setCreatedDate(LocalDate.parse("2023-06-04"));
            purchaseOrderRepository.save(PO_2);
            PurchaseOrder PO_3 = new PurchaseOrder();
            PO_3.setReqDate(LocalDate.parse("2023-06-04"));
//            PO_3.setItem(FAVITEM_3);
            PO_3.addItem(FAVITEM_3);
            logger.info("PO3 - items " + PO_3.getItems());
            PO_3.setQty(33.0);
            PO_3.setPrice(FAVITEM_3.getUnitPrice());
//            PO_3.setSupplier(SUPPLIER_3);
            PO_3.setPaymentTerms(SUPPLIER_3.getPaymentMethod());
            PO_3.setShipTo(3L);
            PO_3.setDeliveryDate(LocalDate.parse("2023-06-15"));
            PO_3.setReqNotesInternal("Internal Note Here...");
            PO_3.setReqNotesExternal("External Note Here...");
            PO_3.setCreatedBy(3L);
            PO_3.setCreatedDate(LocalDate.parse("2023-06-04"));
            purchaseOrderRepository.save(PO_3);
        }
        logger.info("Count of seeded Purchase Order Requisition records from PurchaseRequisitionDataLoader:  " + purchaseOrderRepository.count());
    }
}
