package com.example.pantrypal;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.squareup.picasso.Picasso;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class RecipeDialogFragment extends DialogFragment {

    private Recipe recipe;
    private HttpResponse<JsonNode> response;
    public RecipeDialogFragment(Recipe recipe, HttpResponse<JsonNode> response) {
        this.recipe = recipe;
        this.response = response;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        populate(recipe, response);
        View view = inflater.inflate(R.layout.recipe_dialog, container, false);
        TextView recipeSteps = (TextView) view.findViewById(R.id.recipeSteps);
        recipeSteps.setText(recipe.getInstructions());
        recipeSteps.setMovementMethod(new ScrollingMovementMethod());

        TextView recipeName = (TextView) view.findViewById(R.id.recipeDialogName);
        recipeName.setText(recipe.getTitle());

        TextView recipeServings = (TextView) view.findViewById(R.id.servings);
        Integer i = new Integer(recipe.getServings());
        recipeServings.setText("Servings: " + i.toString());

        Integer l = new Integer(recipe.getMinutes());
        TextView recipeMinutes = (TextView) view.findViewById(R.id.prep_time);
        recipeMinutes.setText("Minutes: " + l.toString());

        ImageView image = (ImageView) view.findViewById(R.id.recipeDialogImage);
        Picasso.get().load(recipe.getImageUrl()).fit().into(image);

        return view;


    }
    public void populate(Recipe recipe, HttpResponse<JsonNode>response){
        JSONObject obj = response.getBody().getObject();



        recipe.setServings(obj.getInt("servings"));
        recipe.setMinutes(obj.getInt("readyInMinutes"));
        recipe.setSourceURL(obj.getString("sourceUrl"));
        recipe.setInstructions(obj.getString("instructions"));

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
            recipe.ingredients.add(currIng);
        }


    }
}
