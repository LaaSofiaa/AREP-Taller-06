package com.edu.arep.Taller6.Entity;

import jakarta.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String address;
    private Double price;
    private Double size;
    private String description;

    public Property() {}

    public Property(String address, Double price, Double size, String description) {
        this.address = address;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Property[id=%d, address='%s', price='%s', size='%s', description='%s']",
                id, address, price, size, description);
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}