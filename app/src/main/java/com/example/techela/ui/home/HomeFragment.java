package com.example.techela.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techela.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.eventsView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] listArray1 = getResources().getStringArray(R.array.event_title);

        String[] listArray2 = getResources().getStringArray(R.array.event_desc);


        int[] bgimageArray = new int[]{
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner

        };

        int[] imageArray = new int[]{
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder
        };

        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), listArray1, listArray2, imageArray, bgimageArray));
        recyclerView.setNestedScrollingEnabled(true);
        return root;
    }
}