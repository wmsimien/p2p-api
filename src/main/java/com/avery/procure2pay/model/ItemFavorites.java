package com.avery.procure2pay.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="itemfavorites")
public class ItemFavorites {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double unit_price;

    @Column
    private String uom;
    // one favorite item can exist on multiple POs
    @OneToMany(mappedBy = "item", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PurchaseOrder> purchaseOrderList;

    public ItemFavorites() {
    }

    public ItemFavorites(Long id, String name, String description, Double unit_price, String uom) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.uom = uom;
    }

    public ItemFavorites(String name, String description, Double unit_price, String uom) {
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
        this.uom = uom;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Double unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public String toString() {
        return "ItemFavorites{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unit_price=" + unit_price +
                '}';
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
