package com.example.pantrypal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import kong.unirest.Callback;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Context mainContext;
    public ArrayList<Ingredient> ingredients;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ingredients = new ArrayList<Ingredient>();
        ingredients.add(new Ingredient("apple","apple.jpg"));
        // your text box
        SearchView edit_txt = (SearchView) view.findViewById(R.id.searchBar);

        ListView listView = (ListView) view.findViewById(R.id.ingListView);
        edit_txt.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        whenAysncRequestShouldReturnOk(inflater, view, s);
                        new CountDownTimer(2000, 500) {
                            public void onFinish() {
                                CustomAdapter customAdapter = new CustomAdapter(mainContext, ingredients);
                                listView.setAdapter(customAdapter);
                            }

                            public void onTick(long millisUntilFinished) {
                                // millisUntilFinished    The amount of time until finished.
                            }
                        }.start();
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }
        );



        return view;
    }
    public void onAttach (Context context) {
        super.onAttach(context);
        mainContext = context;

    }
    public void whenAysncRequestShouldReturnOk(LayoutInflater inflater, View view, String s) {

        CompletableFuture<HttpResponse<JsonNode>> future = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/food/ingredients/autocomplete?query="+s+"&number=15")
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "e36c30b306msh838dc00ee2e2c05p182428jsn1e5dfc3f5174")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        // Do something if the request failed
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        JSONArray myJson = response.getBody().getArray();

                        ArrayList<Ingredient> ingrs = new ArrayList<Ingredient>();

                        String currName;
                        String currImage;

                        for (int i = 0; i < 15; i++) {
                            currName = myJson.getJSONObject(i).getString("name");
                            currImage = myJson.getJSONObject(i).getString("image");
                            Ingredient currIng = new Ingredient(currName, currImage);
                            ingrs.add(currIng);
                        }
                        ingredients = ingrs;


                    }

                    public void cancelled() {
                        // Do something if the request is cancelled
                    }

                });

    }


}