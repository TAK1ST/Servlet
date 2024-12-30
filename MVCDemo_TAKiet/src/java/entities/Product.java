/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author vothimaihoa
 */
public class Product {
    private int id;
    private String name;
    private float price;
    private int productYear;
    private String image;
    private Category category;
    
    public Product() {
    }

    public Product(int id, String name, float price, int productYear, String image, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productYear = productYear;
        this.image = image;
        this.category = category;
    }

    public Product(int id, String name, float price, int productYear, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productYear = productYear;
        this.image = image;
    }
    
    public Product(String name, float price, int productYear, String image, Category category) {
        this.name = name;
        this.price = price;
        this.productYear = productYear;
        this.image = image;
        this.category = category;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }  

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
