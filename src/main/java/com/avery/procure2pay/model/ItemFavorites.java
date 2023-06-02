package com.avery.procure2pay.model;

import javax.persistence.*;

@Entity
@Table(name="itemfavs")
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
    private Float unit_price;

    public ItemFavorites() {
    }

    public ItemFavorites(Long id, String name, String description, Float unit_price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit_price = unit_price;
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

    public Float getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Float unit_price) {
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
}
