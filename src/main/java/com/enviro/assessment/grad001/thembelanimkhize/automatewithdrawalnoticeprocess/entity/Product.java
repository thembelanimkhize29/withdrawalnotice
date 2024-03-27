package com.enviro.assessment.grad001.thembelanimkhize.automatewithdrawalnoticeprocess.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Assuming auto-increment
    private int productId;

    @Column(nullable = false)
    private double currentBalance;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Map to VARCHAR for limited values
    private ProductType type;

    @ManyToOne
    @JsonIgnore
    private Investor investor;

    // Constructors, getters, and setters

    public Product() {
    }

    public Product(int productId, double currentBalance, ProductType type, String name) {
        this.productId = productId;
        this.currentBalance = currentBalance;
        this.type = type;
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", currentBalance=" + currentBalance +
                '}';
    }

    // Enum for ProductType with allowed values
    public enum ProductType {
        RETIREMENT, SAVINGS
    }
}
