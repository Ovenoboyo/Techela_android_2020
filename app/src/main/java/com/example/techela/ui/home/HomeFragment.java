package com.example.techela.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
        com.github.clans.fab.FloatingActionButton fab_instagram = root.findViewById(R.id.fab_insta);
        com.github.clans.fab.FloatingActionButton fab_website = root.findViewById(R.id.fab_website);
        com.github.clans.fab.FloatingActionButton fab_facebook = root.findViewById(R.id.fab_fb);

        fab_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/t_e_c_h_e_l_a/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/t_e_c_h_e_l_a/")));
                }
            }
        });

        fab_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.facebook.com/@techela.csit");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.facebook.com/@techela.csit")));
                }
            }
        });

        fab_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("Website link goes here")));
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.eventsView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] listArray1 = getResources().getStringArray(R.array.event_title);

        String[] listArray2 = getResources().getStringArray(R.array.event_desc);


        int[] bgimageArray = new int[]{
                R.drawable.pubg3,
                R.drawable.csgo3,
                R.drawable.cod1,
                R.drawable.mini1,
                R.drawable.cr1,
                R.drawable.rainbow1,
                R.drawable.images1,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner

        };

        int[] imageArray = new int[]{
                R.drawable.pubglogo2,
                R.drawable.csgologo2,
                R.drawable.codlogo1,
                R.drawable.mnlogo1,
                R.drawable.crlogo1,
                R.drawable.rainbowlogo1,
                R.drawable.slowcyclelogo1,
                R.drawable.placeholder,
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