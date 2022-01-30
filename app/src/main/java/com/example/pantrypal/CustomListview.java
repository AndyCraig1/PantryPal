package com.example.pantrypal;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import android.app.Activity;
import android.app.FragmentManager;

import com.squareup.picasso.Picasso;

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
        return recipeList.size();
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

            Picasso.get().load(currentRecipe.getImageID()).fit().into(image);
            name.setText(currentRecipe.getTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentActivity activity = (FragmentActivity)(rContext);
                    RecipeDialogFragment recipeDialogFragment = new RecipeDialogFragment(recipeList.get(position));
                    recipeDialogFragment.show(activity.getSupportFragmentManager(), "MyFragment");
                }
            });
        }
        return convertView;
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
