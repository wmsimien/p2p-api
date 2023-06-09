package com.avery.procure2pay.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String department;

    @Column
    private String role;

    @Column(unique = true)
    private String email;

    @Column
    private String gl_acct_no;

//    @OneToMany(mappedBy = "createdBy", orphanRemoval = true)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<PurchaseOrder> createdPurchaseOrderList;

//    @OneToMany(mappedBy = "approvedBy", orphanRemoval = true)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<PurchaseOrder> approvedPurchaseOrderList;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String department, String role, String email, String gl_acct_no) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.email = email;
        this.gl_acct_no = gl_acct_no;
    }

    public Employee(String firstName, String lastName, String department, String role, String email, String gl_acct_no) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.email = email;
        this.gl_acct_no = gl_acct_no;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getGl_acct_no() {
        return gl_acct_no;
    }

    public void setGl_acct_no(String gl_acct_no) {
        this.gl_acct_no = gl_acct_no;
    }
}
