package com.sitfest.techela.ui.home;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.sitfest.techela.MainActivity;
import com.sitfest.techela.R;

import java.util.Objects;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        com.github.clans.fab.FloatingActionButton fab_instagram = root.findViewById(R.id.fab_insta);
        com.github.clans.fab.FloatingActionButton fab_website = root.findViewById(R.id.fab_website);
        com.github.clans.fab.FloatingActionButton fab_facebook = root.findViewById(R.id.fab_fb);

        com.github.clans.fab.FloatingActionButton fab_youtube = root.findViewById(R.id.fab_youtube);

        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Events");

        fab_instagram.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/t_e_c_h_e_l_a/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/t_e_c_h_e_l_a/")));
            }
        });

        fab_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/channel/UCCWzAl2aHLxyOcApa--cmBw");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.google.android.youtube");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/channel/UCCWzAl2aHLxyOcApa--cmBw")));
                }
            }
        });

        fab_facebook.setOnClickListener(v -> {
            Uri uri = Uri.parse("http://www.facebook.com/@techela.csit");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.facebook.com/@techela.csit")));
            }
        });

        fab_website.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.sitfest.info/"))));
        RecyclerView recyclerView = root.findViewById(R.id.eventsView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] listArray1 = getResources().getStringArray(R.array.event_title);

        String[] listArray2 = getResources().getStringArray(R.array.event_desc);

        String[] listArray5 = getResources().getStringArray(R.array.event_desc_long);

        String[] listArray3 = getResources().getStringArray(R.array.event_time);

        String[] listArray4 = getResources().getStringArray(R.array.event_venue);


        int[] bgimageArray = new int[]{
                R.drawable.pubg,
                R.drawable.csgo,
                R.drawable.cod_mobile,
                R.drawable.mini_militia,
                R.drawable.clash_royale,
                R.drawable.rainbow_box,
                R.drawable.fifa_banner,
                R.drawable.escape_banner,
                R.drawable.tech_banner,
                R.drawable.graphic_banner,
                R.drawable.blind_banner,
                R.drawable.quiz_banner,
                R.drawable.physiothon_banner,
                R.drawable.treasure_banner,
                R.drawable.game_banner,


        };

        int[] imageArray = new int[]{
                R.drawable.pubg_icon,
                R.drawable.csgo_icon,
                R.drawable.cod_mobile,
                R.drawable.mini_militia_icon,
                R.drawable.clashroyale_icon,
                R.drawable.rainbow_icon,
                R.drawable.fifa_logo,
                R.drawable.escape_logo,
                R.drawable.tech_logo,
                R.drawable.graphic_logo,
                R.drawable.blind_logo,
                R.drawable.quiz_logo,
                R.drawable.physiothon_logo,
                R.drawable.treasure_logo,
                R.drawable.game__loho,

        };

        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), listArray1, listArray2, imageArray, bgimageArray, listArray3, listArray4, listArray5));
        recyclerView.setNestedScrollingEnabled(true);
        return root;
    }
}

