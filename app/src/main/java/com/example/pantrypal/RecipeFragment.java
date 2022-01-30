package com.example.pantrypal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import kong.unirest.Callback;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    // TODO: Rename and change types of parameters
//    private ListView recipeListView;
//    private CustomListview rAdapter;
//    private ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();

    public RecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ListView recipeListView = (ListView) view.findViewById(R.id.recipeList);

        this.AysncAPICall();
        new CountDownTimer(3000, 500) {
            public void onFinish() {
                CustomListview rAdapter = new CustomListview(getActivity(), recipes);
                recipeListView.setAdapter(rAdapter);
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

        return view;
    }

    //parse myPantry array for ingredients and their names


    //ignore pantry is set to true, idk if we want that or not

    public String getCommaDelimitedIngredients(Pantry myPantry){
        int mPLength = myPantry.getPantry().size();
        String ingrNames = "";

        for (int i = 0; i < mPLength; i++) {
            if (i < mPLength-1) {
                ingrNames = ingrNames + myPantry.getPantry().get(i).getName() + ",";
            } else {
                ingrNames = ingrNames + myPantry.getPantry().get(i).getName();
            }
        }
        return ingrNames;
    }
    public void AysncAPICall() {

        Pantry thePantry = new Pantry(getActivity());
        CompletableFuture<HttpResponse<JsonNode>> future = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients="+getCommaDelimitedIngredients(thePantry)+"&number=15&ignorePantry=true&ranking=1")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "e36c30b306msh838dc00ee2e2c05p182428jsn1e5dfc3f5174")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        // Do something if the request failed
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        JSONArray myJson = response.getBody().getArray();

                        int currID;
                        String currTitle;
                        String currImage;

                        for (int i = 0; i < 15; i++) {
                            currID = myJson.getJSONObject(i).getInt("id");
                            currTitle = myJson.getJSONObject(i).getString("title");
                            currImage = myJson.getJSONObject(i).getString("image");
                            Recipe currRecipe = new Recipe(currID, currImage, currTitle);
                            recipes.add(currRecipe);
                        }
                    }

                    public void cancelled() {
                        // Do something if the request is cancelled
                    }

                });

    }
}