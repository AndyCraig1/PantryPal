package com.example.pantrypal;

import java.util.ArrayList;

import kong.unirest.JsonNode;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Recipe {
    
    int ID; //non-null
    String image; //non-null
    String sourceURL;
    int servings;
    int minutes;
    String title; //non-null
    String instructions;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

    public Recipe(int inID, String inImage, String inTitle) {
        this.ID = inID;
        this.image = inImage;
        this.title = inTitle;
    }
    //use get Recipe Information api call to populate attributes servings, minutes, etc.
    //loop through ingrediants given by that api call and put each one into an ingrediant object, then put those in arraylist ingredients
    public void initAttributes() {
        HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + this.getID() + "/information")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "0135d0b49cmsh637396520474835p1dcc8ajsnf406561e2803")
                .asJson();


    }

    public int getID() {
        return this.ID;
    }

    public void setID(int inID) {
        this.ID = inID;
    }

    public String getImageID() {
        return this.image;
    }
    public String getImageUrl() {
        return this.image;
    }

    public void setAttribute(String inImage) {
        this.image = inImage;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int inServings) {
        this.servings = inServings;
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    public void setSourceURL(String inSourceURL) {
        this.sourceURL = inSourceURL;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int inMinutes) {
        this.minutes = inMinutes;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String inTitle) {
        this.title = inTitle;
    }

    public String getInstructions() {
        return this.instructions;
    }
    public void setInstructions(String inst) {
        this.instructions = inst;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

}