package com.avery.procure2pay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="purchaseOrders")
public class PurchaseOrder {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long reqNo;
    @Column
    private LocalDateTime reqDate;
    @Column
    private Double qty;
    @Column
    private Double price;
    @Column
    private LocalDateTime deliveryDate;
    @Column
    private String glAcctNo;
    @Column
    private String status = "Open";
    @Column
    private String paymentTerms;
    @Column
    private String poNotes;
    @Column
    private String reqNotesInternal;
    @Column
    private String reqNotesExternal;
    @Column
    private Long shipTo;
    // multiple POs can have the same favItem
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="item_id")
    private ItemFavorites item;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="employee_id")
//    private Employee createdBy;
    @Column
    private Long createdBy;
    @Column
    private LocalDateTime createdDate;
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="employee_id")
//    private Employee approvedBy;
    @Column
    private Long approvedBy;
    @Column
    private LocalDateTime approvedDate;


    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id, Long reqNo, LocalDateTime reqDate, Double qty, Double price, LocalDateTime deliveryDate, String glAcctNo, String status, String paymentTerms, String poNotes, String reqNotesInternal, String reqNotesExternal, Long shipTo, ItemFavorites item, Supplier supplier, Long createdBy, LocalDateTime createdDate, Long approvedBy, LocalDateTime approvedDate) {
        this.id = id;
        this.reqNo = reqNo;
        this.reqDate = reqDate;
        this.qty = qty;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.glAcctNo = glAcctNo;
        this.status = status;
        this.paymentTerms = paymentTerms;
        this.poNotes = poNotes;
        this.reqNotesInternal = reqNotesInternal;
        this.reqNotesExternal = reqNotesExternal;
        this.shipTo = shipTo;
        this.item = item;
        this.supplier = supplier;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqNo() {
        return reqNo;
    }

    public void setReqNo(Long reqNo) {
        this.reqNo = reqNo;
    }

    public LocalDateTime getReqDate() {
        return reqDate;
    }

    public void setReqDate(LocalDateTime reqDate) {
        this.reqDate = reqDate;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getGlAcctNo() {
        return glAcctNo;
    }

    public void setGlAcctNo(String glAcctNo) {
        this.glAcctNo = glAcctNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPoNotes() {
        return poNotes;
    }

    public void setPoNotes(String poNotes) {
        this.poNotes = poNotes;
    }

    public String getReqNotesInternal() {
        return reqNotesInternal;
    }

    public void setReqNotesInternal(String reqNotesInternal) {
        this.reqNotesInternal = reqNotesInternal;
    }

    public String getReqNotesExternal() {
        return reqNotesExternal;
    }

    public void setReqNotesExternal(String reqNotesExternal) {
        this.reqNotesExternal = reqNotesExternal;
    }

    public Long getShipTo() {
        return shipTo;
    }

    public void setShipTo(Long shipTo) {
        this.shipTo = shipTo;
    }

    public ItemFavorites getItem() {
        return item;
    }

    public void setItem(ItemFavorites item) {
        this.item = item;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", reqNo=" + reqNo +
                ", reqDate=" + reqDate +
                ", qty=" + qty +
                ", price=" + price +
                ", deliveryDate=" + deliveryDate +
                ", glAcctNo='" + glAcctNo + '\'' +
                ", status='" + status + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", poNotes='" + poNotes + '\'' +
                ", reqNotesInternal='" + reqNotesInternal + '\'' +
                ", reqNotesExternal='" + reqNotesExternal + '\'' +
                ", shipTo=" + shipTo +
                ", item=" + item +
                ", supplier=" + supplier +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", approvedBy=" + approvedBy +
                ", approvedDate=" + approvedDate +
                '}';
    }
}
