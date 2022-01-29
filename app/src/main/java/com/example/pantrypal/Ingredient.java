package com.example.pantrypal;

public class Ingredient {
    
    float amount;
    String name; //non-null
    int ID;
    String image; //non-null
    String unit;
    
    public Ingredient(String inName, String inImage) {
        this.name = inName;
        this.image = "https//whatever/" + inImage;
    }

    public void setAmount(float inAmount) {
        this.amount = inAmount;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setName(String inName) {
        this.name = inName;
    }

    public String getName() {
        return this.name;
    }

    public void setID(int inID) {
        this.ID = inID;
    }

    public int setID() {
        return this.ID;
    }

    public void setImage(String inImage) {
        this.image = inImage;
    }

    public String getImage() {
        return this.image;
    }

    public void setUnit(String inUnit) {
        this.unit = inUnit;
    }

    public String getUnit() {
        return this.unit;
    }

}