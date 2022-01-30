package com.example.pantrypal;

import android.content.Context;
import android.util.Log;

import android.content.SharedPreferences;
import android.content.*;

import androidx.annotation.Nullable;


import java.io.*;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Pantry implements SharedPreferences{

    ArrayList<Ingredient> myPantry = new ArrayList<Ingredient>();
    boolean recipesUpToDate = false;
    Recipe[] currentRecipes = new Recipe[15];

    public void saveIngredient(Ingredient i) {
        if (null == this.myPantry) {
            this.myPantry = new ArrayList<Ingredient>();
        }
        this.myPantry.add(i);

        // save the task list to preference

    }

    public Pantry(Context ctx) {
        //read pantry that is saved in text file and intialize
        //add each item to myPantry[]
        int charInt;
        char currChar;
        String currString = "";
        String currName = "";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(ctx.openFileInput("savedPantry.txt"));
            while(true) {
                charInt = inputStreamReader.read();
                if (charInt == -1) {
                    break;
                }
                currChar = (char)charInt;

                if (currChar == ',') {
                    currName = currString;
                    currString = "";
                }
                else if (currChar == '-') {
                    addToPantry(currName, currString);
                    currString = "";
                } else {
                    currString = currString + currChar;
                }

            }
            ctx.deleteFile("savedPantry.txt");
            inputStreamReader.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }


    }

    public void writeToFile(Context ctx) {
        String saveData = "";
        int mPLength = myPantry.size();
        for (int i = 0; i < mPLength; i++) {
            saveData = saveData + myPantry.get(i).getName() + "," + myPantry.get(i).getImage() + "-";
        }

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput("savedPantry.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(saveData);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //api call for user input "item" that returns a list of ingredients
    //parse json
    //create ingred objects for each ingrdiant (using name and img)
    //return ingred array
    public ArrayList<Ingredient> search(String item) {

        ArrayList<Ingredient> ingrs = new ArrayList<Ingredient>();

        HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?query=" + item + "&number=15")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "0135d0b49cmsh637396520474835p1dcc8ajsnf406561e2803")
                .asJson();

        JSONArray myJson = response.getBody().getArray();

        String currName;
        String currImage;

        for (int i = 0; i < 15; i++) {
            currName = myJson.getJSONObject(i).getString("name");
            currImage = myJson.getJSONObject(i).getString("image");
            Ingredient currIng = new Ingredient(currName, currImage);
            ingrs.add(currIng);
        }
        return ingrs;
    }

    public void addToPantry(String ingr, String img) {
        //appends ingr to myPantry[]
        Ingredient ingrObj = new Ingredient(ingr, img);
        myPantry.add(ingrObj);
        recipesUpToDate = false;
    }

    public Recipe[] displayRecipes() {

        if (recipesUpToDate = false) {
            this.currentRecipes = findRecipes();
            recipesUpToDate = true;
        }
        return this.currentRecipes;
    }

    //use api call "search recipe by ingrediants" using the array of names ingrNames
    //create a recipe object for each returned recipe
    //return array of these recipe objects (each recipe will have many null attributes because full summary api call is not made yet)
    public Recipe[] findRecipes() {

        //parse myPantry array for ingredients and their names
        int mPLength = myPantry.size();
        String ingrNames = "";
        Recipe[] recipes = new Recipe[15];
        
        for (int i = 0; i < mPLength; i++) {
            if (i < mPLength-1) {
                ingrNames = ingrNames + myPantry.get(i).getName() + ",";
            } else {
                ingrNames = ingrNames + myPantry.get(i).getName();
            }
        }

        //ignore pantry is set to true, idk if we want that or not
        HttpResponse<JsonNode> response = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients=" + ingrNames + "&number=15&ignorePantry=true&ranking=2")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "0135d0b49cmsh637396520474835p1dcc8ajsnf406561e2803")
                .asJson();

        JSONArray myJson = response.getBody().getArray();

        int currID;
        String currTitle;
        String currImage;

        for (int i = 0; i < 15; i++) {
            currID = myJson.getJSONObject(i).getInt("id");
            currTitle = myJson.getJSONObject(i).getString("title");
            currImage = myJson.getJSONObject(i).getString("image");
            Recipe currRecipe = new Recipe(currID, currImage, currTitle);
            recipes[i] = currRecipe;
        }

        return recipes;
    }

    public ArrayList<Ingredient> getPantry() {
        return this.myPantry;
    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String s, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        return null;
    }

    @Override
    public int getInt(String s, int i) {
        return 0;
    }

    @Override
    public long getLong(String s, long l) {
        return 0;
    }

    @Override
    public float getFloat(String s, float v) {
        return 0;
    }

    @Override
    public boolean getBoolean(String s, boolean b) {
        return false;
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }
}