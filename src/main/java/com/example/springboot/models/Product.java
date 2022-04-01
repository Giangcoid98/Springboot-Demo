package com.example.springboot.models;

// POJO = Plan Oject Java Oject

import javax.persistence.*;

@Entity
@Table(name="tblProduct")
public class Product
{
    //đây là "primary key"
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 //increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )

    private long id;
    private String productName;
    private int year;
    private Double price;
    private String url;
    public Product(){}

    public Product( String productName, int year, Double price, String url) {

        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id="+id +
                ",productName='"+ productName + '\''+
                ",year=" +year+
                ",price="+ price +
                ", url='"+ url + '\''+
                '}';
    }
}
