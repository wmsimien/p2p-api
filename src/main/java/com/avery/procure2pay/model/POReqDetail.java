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
    @ManyToMany(mappedBy = "poReqDetailList")
    private final List<POReqHeader> poReqHeaders = new ArrayList();
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

    public POReqDetail(Long id, Double qty, Double price, List<ItemFavorites> items) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.items = items;
    }

    public POReqDetail(Double qty, Double price, List<ItemFavorites> items) {
        this.qty = qty;
        this.price = price;
        this.items = items;
    }


    public Long getId() {
        return id;
    }

    public Double getQty() {
        return qty;
    }

    public Double getPrice() {
        return price;
    }

    public List<POReqHeader> getPoReqHeaders() {
        return poReqHeaders;
    }

    public List<ItemFavorites> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public void setPrice(Double price) {
        this.price = price;
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
                ", poReqHeaders=" + poReqHeaders +
                ", items=" + items +
                '}';
    }
}
