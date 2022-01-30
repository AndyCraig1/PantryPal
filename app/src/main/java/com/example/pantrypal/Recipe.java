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

        JSONObject obj = new JSONObject(response);

        this.servings = obj.getInt("servings");
        this.minutes = obj.getInt("readyInMinutes");
        this.sourceURL = obj.getString("sourceUrl");
        this.instructions = obj.getString("instructions");

        JSONArray ingArr = obj.getJSONArray("extendedIngredients");

        String currName;
        String currImage;
        float currAmount;
        String currUnit;

        for (int i = 0; i < ingArr.length(); i++) {
            currName = ingArr.getJSONObject(i).getString("name");
            currImage = ingArr.getJSONObject(i).getString("image");
            currAmount = ingArr.getJSONObject(i).getFloat("amount");
            currUnit = ingArr.getJSONObject(i).getString("unit");
            Ingredient currIng = new Ingredient(currName, currImage, currAmount, currUnit);
            this.ingredients.add(currIng);
        }


    }

    public int getID() {
        return this.ID;
    }

    public void setID(int inID) {
        this.ID = inID;
    }

    public String getImage() {
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

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

}