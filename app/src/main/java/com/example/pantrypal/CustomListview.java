package com.example.pantrypal;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kong.unirest.Callback;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class CustomListview implements ListAdapter {

    private Context rContext;
    private ArrayList<Recipe> recipeList;
    private LayoutInflater layoutInflater;
    public CustomListview(@NonNull Context context, ArrayList<Recipe> list) {
//        super(context, 0, list);
        rContext = context;
        recipeList = list;
        layoutInflater = LayoutInflater.from(rContext);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        if (!recipeList.isEmpty()){
            return recipeList.size();
        }else{
            return -1;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_layout, parent, false);

            ImageView image = (ImageView) convertView.findViewById(R.id.recipeImage);
            TextView name = (TextView) convertView.findViewById(R.id.recipeName);
            Recipe currentRecipe = (Recipe) recipeList.get(position);

            Picasso.get().load(currentRecipe.getImageUrl()).fit().into(image);
            name.setText(currentRecipe.getTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsyncAPICall(currentRecipe);
                }
            });
        }
        return convertView;
    }

    private void AsyncAPICall(Recipe recipe) {
        CompletableFuture<HttpResponse<JsonNode>> future = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"+recipe.getID()+"/information")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "e36c30b306msh838dc00ee2e2c05p182428jsn1e5dfc3f5174")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        // Do something if the request failed
                    }

                    public void completed(HttpResponse<JsonNode> response) {

                        FragmentActivity activity = (FragmentActivity)(rContext);
                        RecipeDialogFragment recipeDialogFragment = new RecipeDialogFragment(recipe, response);
                        recipeDialogFragment.show(activity.getSupportFragmentManager(), "MyFragment");
                    }

                    public void cancelled() {
                        // Do something if the request is cancelled
                    }

                });
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return recipeList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
