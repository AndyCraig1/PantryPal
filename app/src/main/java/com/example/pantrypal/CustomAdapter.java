package com.example.pantrypal;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
class CustomAdapter extends MainActivity implements ListAdapter{
    ArrayList<Ingredient> arrayList;
    Context context;
    LayoutInflater layoutInflater;
    public CustomAdapter(Context context, ArrayList<Ingredient> arrayList) {
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
            convertView = layoutInflater.inflate(R.layout.list_view_row, parent, false);

            TextView title = convertView.findViewById(R.id.list_text);
            ImageView imag = convertView.findViewById(R.id.list_image);
            Ingredient currentRow = (Ingredient) this.arrayList.get(position);
            Button button = (Button) convertView.findViewById(R.id.list_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(context);
                    View popupView = inflater.inflate(R.layout.addtopantry_popup, null);

                    // create the popup window
                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                    // show the popup window
                    // which view you pass in doesn't matter, it is only used for the window tolken
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    TextView popupText = popupView.findViewById(R.id.ingredients_popup);
                    popupText.setText(currentRow.getName() + " has been added to your pantry");

                    // dismiss the popup window when touched
                    popupView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

                }
            });
            title.setText(currentRow.getName());


            Picasso.get()
                    .load(currentRow.getImage())
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