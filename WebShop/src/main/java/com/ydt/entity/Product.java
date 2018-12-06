package com.ydt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Menu_ID")
    @JsonIgnore
    private Long Menu_ID;
    @JsonProperty("Menu_name")
    @JsonIgnore
    private String Menu_name;
    @JsonProperty("Price")
    @JsonIgnore
    private float Price;
    @JsonProperty("Menu_image")
    @JsonIgnore
    private String Menu_image;
    @ManyToOne
    @JoinColumn(name = "Category_ID")
    @JsonIgnore
    private Category id_cate;

    public Product() {
    }

    public Product(String menu_name, float price, String menu_image, Category id_cate) {
        Menu_name = menu_name;
        Price = price;
        Menu_image = menu_image;
        this.id_cate = id_cate;
    }

    public Long getMenu_ID() {
        return Menu_ID;
    }

    public void setMenu_ID(Long menu_ID) {
        Menu_ID = menu_ID;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public void setMenu_name(String menu_name) {
        Menu_name = menu_name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    public void setMenu_image(String menu_image) {
        Menu_image = menu_image;
    }

    public Category getId_cate() {
        return id_cate;
    }

    public void setId_cate(Category id_cate) {
        this.id_cate = id_cate;
    }
}
