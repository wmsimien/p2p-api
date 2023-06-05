package com.avery.procure2pay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="poReqDetails")
public class POReqDetail {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Double qty;
    @Column
    private Double price;
    // many poReqDetail records can belong to a poReqHeader record
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "poReqHeader_id")
    private POReqHeader poReqHeader;
    // multiple POs can have the same favItem
    @ManyToMany
    @JoinTable(
            name="poReqDetail_Item",
            joinColumns = @JoinColumn(name = "poReqDetail_id"),
            inverseJoinColumns = @JoinColumn(name="itemFavorites_id")
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ItemFavorites> items = new ArrayList<>();




    public POReqDetail() {
    }

    public POReqDetail(Long id, Double qty, Double price, POReqHeader poReqHeader, List<ItemFavorites> items) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.poReqHeader = poReqHeader;
        this.items = items;
    }

    public POReqDetail(Double qty, Double price, POReqHeader poReqHeader, List<ItemFavorites> items) {
        this.qty = qty;
        this.price = price;
        this.poReqHeader = poReqHeader;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public POReqHeader getPoReqHeader() {
        return poReqHeader;
    }

    public void setPoReqHeader(POReqHeader poReqHeader) {
        this.poReqHeader = poReqHeader;
    }

    public List<ItemFavorites> getItems() {
        return items;
    }

    public void setItems(List<ItemFavorites> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "POReqDetail{" +
                "id=" + id +
                ", qty=" + qty +
                ", price=" + price +
                ", poReqHeader=" + poReqHeader +
                ", items=" + items +
                '}';
    }
}
