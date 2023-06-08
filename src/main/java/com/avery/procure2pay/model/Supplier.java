package com.avery.procure2pay.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;


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
    private String contactName;

    @Column
    private String contactPhone;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zip;

    @Column
    private String phoneNo;

    @Column
    private String contactEmail;

    @Column
    private String paymentMethod = "COD";

    @Column
    private String status = "Active";
    @ManyToOne
    @JoinColumn(name="supplier_id")
    @JsonIgnore
    private POReqHeader poReqHeader;

    public Supplier() {
    }

    public Supplier(Long id, String name, String contactName, String contactPhone, String address, String city, String state, String zip, String phoneNo, String contactEmail) {
        this.id = id;
        this.name = name;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNo = phoneNo;
        this.contactEmail = contactEmail;
    }

    public Supplier(String name, String contactName, String contactPhone, String address, String city, String state, String zip, String phoneNo, String contactEmail) {
        this.name = name;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNo = phoneNo;
        this.contactEmail = contactEmail;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact_name='" + contactName + '\'' +
                ", contact_phone='" + contactPhone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", contact_email='" + contactEmail + '\'' +
                ", payment_method='" + paymentMethod + '\'' +
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
