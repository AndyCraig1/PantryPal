package com.example.pantrypal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;


import java.io.IOException;
import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    public Pantry thePantry;

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pantry test = new Pantry(getApplicationContext());

        //test.writeToFile(getApplicationContext());

        // setup navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        NavController navController = navHostFragment.getNavController();

        // configure app bar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.myPantryFragment, R.id.recipeFragment, R.id.searchFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
        navController.navigate(R.id.myPantryFragment);
        SharedPreferences prefs = getSharedPreferences("data\\data\\shared_prefs\\", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(gfgPolicy);


    }
    public Pantry getThePantry(){
        return thePantry;
    }







}


