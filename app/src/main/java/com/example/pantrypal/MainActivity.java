package com.example.pantrypal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.pantrypalnew.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }


}