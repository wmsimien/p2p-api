package com.avery.procure2pay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="poReqHeaders")
public class POReqHeader {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // po-req no
    @Column
    private Long poNo;  // poNo associated to req
    // one poReqHeader record to many poReqDetail records
    @ManyToMany
    @JoinTable(
            name="poReqHeader_detail",
            joinColumns = @JoinColumn(name = "poReqHeader_id"),
            inverseJoinColumns = @JoinColumn(name="poReqDetail_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<POReqDetail> poReqDetailList = new ArrayList<>();
    @Column
    private LocalDate reqDate;
    @Column
    private LocalDate deliveryDate;
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
    // only one supplier purchase req
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="supplier_id")
    @OneToMany(mappedBy = "poReqHeader")
    private List<Supplier> supplierLists = new ArrayList<>();
    @Column
    private Long createdBy;
    @Column
    private LocalDate createdDate;
    @Column
    private Long approvedBy;
    @Column
    private LocalDate approvedDate;


    public POReqHeader() {
    }
    public POReqHeader(Long id, Long poNo, List<POReqDetail> poReqDetailList, LocalDate reqDate, LocalDate deliveryDate, String glAcctNo, String status, String paymentTerms, String poNotes, String reqNotesInternal, String reqNotesExternal, Long shipTo, List<Supplier> supplierLists, Long createdBy, LocalDate createdDate, Long approvedBy, LocalDate approvedDate) {
        this.id = id;
        this.poNo = poNo;
        this.poReqDetailList = poReqDetailList;
        this.reqDate = reqDate;
        this.deliveryDate = deliveryDate;
        this.glAcctNo = glAcctNo;
        this.status = status;
        this.paymentTerms = paymentTerms;
        this.poNotes = poNotes;
        this.reqNotesInternal = reqNotesInternal;
        this.reqNotesExternal = reqNotesExternal;
        this.shipTo = shipTo;
        this.supplierLists = supplierLists;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
    }

    public POReqHeader(List<POReqDetail> poReqDetailList, LocalDate reqDate, LocalDate deliveryDate, String glAcctNo, String status, String paymentTerms, String poNotes, String reqNotesInternal, String reqNotesExternal, Long shipTo, List<Supplier> supplierLists, Long createdBy, LocalDate createdDate, Long approvedBy, LocalDate approvedDate) {
        this.poReqDetailList = poReqDetailList;
        this.reqDate = reqDate;
        this.deliveryDate = deliveryDate;
        this.glAcctNo = glAcctNo;
        this.status = status;
        this.paymentTerms = paymentTerms;
        this.poNotes = poNotes;
        this.reqNotesInternal = reqNotesInternal;
        this.reqNotesExternal = reqNotesExternal;
        this.shipTo = shipTo;
        this.supplierLists = supplierLists;
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

    public Long getPoNo() {
        return poNo;
    }

    public void setPoNo(Long poNo) {
        this.poNo = poNo;
    }

    public List<POReqDetail> getPoReqDetailList() {
        return poReqDetailList;
    }

    public void setPoReqDetailList(List<POReqDetail> poReqDetailList) {
        this.poReqDetailList = poReqDetailList;
    }

    public LocalDate getReqDate() {
        return reqDate;
    }

    public void setReqDate(LocalDate reqDate) {
        this.reqDate = reqDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
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

    public List<Supplier> getSupplierLists() {
        return supplierLists;
    }

    public void setSupplierLists(List<Supplier> supplierLists) {
        this.supplierLists = supplierLists;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDate approvedDate) {
        this.approvedDate = approvedDate;
    }

    public void addPOReqDetail(POReqDetail poReqDetail) {
        poReqDetailList.add(poReqDetail);
    }

    @Override
    public String toString() {
        return "POReqHeader{" +
                "id=" + id +
                ", poNo=" + poNo +
                ", poReqDetailList=" + poReqDetailList +
                ", reqDate=" + reqDate +
                ", deliveryDate=" + deliveryDate +
                ", glAcctNo='" + glAcctNo + '\'' +
                ", status='" + status + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", poNotes='" + poNotes + '\'' +
                ", reqNotesInternal='" + reqNotesInternal + '\'' +
                ", reqNotesExternal='" + reqNotesExternal + '\'' +
                ", shipTo=" + shipTo +
                ", supplierLists=" + supplierLists +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", approvedBy=" + approvedBy +
                ", approvedDate=" + approvedDate +
                '}';
    }
}
