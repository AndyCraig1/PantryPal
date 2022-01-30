package com.example.pantrypal;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter2 extends MainActivity implements ListAdapter{
    ArrayList<Ingredient> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    public CustomAdapter2(Context context, ArrayList<Ingredient> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
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
        //Ingredient subjectData = arrayList.get(position);
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_row2, parent, false);

            TextView title = convertView.findViewById(R.id.list_text);
            ImageView imag = convertView.findViewById(R.id.list_image);
            Ingredient currentRow = (Ingredient) this.arrayList.get(position);

            title.setText(currentRow.getName());


            Picasso.get()
                    .load(currentRow.getUrl())
                    .into(imag);
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}