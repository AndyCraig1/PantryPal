package com.example.pantrypal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPantryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPantryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyPantryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPantryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPantryFragment newInstance(String param1, String param2) {
        MyPantryFragment fragment = new MyPantryFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_pantry, container, false);
        ListView listView = (ListView) view.findViewById(R.id.ingListView);

        ArrayList<Ingredient> IngList = new ArrayList<Ingredient>();
       // IngList.add(new Ingredient("apple","apple.jpg"));
      //  IngList.add(new Ingredient("rhubarb","rhubarb.jpg"));

       // IngList.add(new Ingredient("pepper","pepper.jpg"));
       Pantry thePantry = new Pantry(getActivity());
       if (thePantry.getPantry().size() >0){
           CustomAdapter2 customAdapter = new CustomAdapter2(getActivity(),thePantry.getPantry());
           listView.setAdapter(customAdapter);
       }
        Button button = (Button) view.findViewById(R.id.button_clear);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().deleteFile("savedPantry.txt");
            }
        });
        return view;
    }

}