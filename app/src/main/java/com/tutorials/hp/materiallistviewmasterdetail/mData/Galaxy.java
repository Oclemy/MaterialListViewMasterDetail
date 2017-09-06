package com.tutorials.hp.materiallistviewmasterdetail.mData;

import java.io.Serializable;
/**
 - Our Galaxy class.
 - Our data object. Properties are name,description and image.
 - Implements Serializable class.
 */
public class Galaxy implements Serializable{

    private String name,description;
    private int id,image;
    /*
    - Constructor
     */
    public Galaxy(String name, String description, int id, int image) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.image = image;
    }
    /*
    - Getters
     */
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public int getImage() {
        return image;
    }
}
