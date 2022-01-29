package com.example.pantrypal;

import java.io.*;
import java.util.ArrayList;
import java.net.URLEncoder;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Pantry {

    ArrayList<Ingredient> myPantry = new ArrayList<Ingredient>();
    boolean recipesUpToDate = false;
    Recipe[] currentRecipes = new Recipe[15];

    public Pantry() throws IOException {
        //read pantry that is saved in text file and intialize
        //add each item to myPantry[]
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("savedPantry.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        int commaIndex;
        while ((line = br.readLine()) != null) {
            commaIndex = line.indexOf(",");
            myPantry.add(new Ingredient(line.substring(0,commaIndex),line.substring(commaIndex + 1)));
        }   
    }

    public void saveToFile() {
        //saves myPantry to a text file so that myPantry can persist between sessions
        //could be called after every addToPantry operation, at set time intervals, or upon closure of the app
        PrintStream fileStream = null;
        try {
            fileStream = new PrintStream(new File("savedPantry.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int mPLength = myPantry.size();
        for (int i = 0; i < mPLength; i++) {
            fileStream.println(myPantry.get(i).getName() + "," + myPantry.get(i).getImage());
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

        for (int i = 0; i < 15; i++) {
            String currName = myJson.getJSONObject(i).getString("name");
            String currImage = myJson.getJSONObject(i).getString("image");
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
            currentRecipes = findRecipes();
            recipesUpToDate = true;
        }
        return currentRecipes;
    }

    public Recipe[] findRecipes() {

        //parse myPantry array for ingredients and their names
        int mPLength = myPantry.size();
        String[] ingrNames = new String[mPLength];
        
        for (int i = 0; i < mPLength; i++) {
            ingrNames[i] = myPantry.get(i).getName();
        }
        Recipe[] recipes = new Recipe[15];



        //use api call "search recipe by ingrediants" using the array of names ingrNames
        //create a recipe object for each returned recipe
        //return array of these recipe objects (each recipe will have many null attributes because full summary api call is not made yet)
        return recipes;
    }

    public ArrayList<Ingredient> getPantry() {
        return this.myPantry;
    }

}