package com.avery.procure2pay.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="suppliers")
public class Supplier {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String contact_name;

    @Column
    private String contact_phone;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zip;

    @Column
    private String phone_no;

    @Column
    private String contact_email;

    @Column
    private String payment_method = "COD";

    @Column
    private Boolean status = true;

    @OneToMany(mappedBy = "supplier", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PurchaseOrder> purchaseOrderList;

    public Supplier() {
    }

    public Supplier(Long id, String name, String contact_name, String contact_phone, String address, String city, String state, String zip, String phone_no, String contact_email) {
        this.id = id;
        this.name = name;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone_no = phone_no;
        this.contact_email = contact_email;
    }

    public Supplier(String name, String contact_name, String contact_phone, String address, String city, String state, String zip, String phone_no, String contact_email) {
        this.name = name;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone_no = phone_no;
        this.contact_email = contact_email;
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

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_phone='" + contact_phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", contact_email='" + contact_email + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", status=" + status +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
