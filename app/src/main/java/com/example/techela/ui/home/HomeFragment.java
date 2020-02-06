package com.example.techela.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techela.MainActivity;
import com.example.techela.R;

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
                Uri.parse("https://www.google.com"))));
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
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,
                R.drawable.placeholder_banner,

        };

        int[] imageArray = new int[]{
                R.drawable.pubg_icon,
                R.drawable.csgo_icon,
                R.drawable.cod_mobile,
                R.drawable.mini_militia_icon,
                R.drawable.clashroyale_icon,
                R.drawable.rainbow_icon,
                R.drawable.placeholder,
                R.drawable.placeholder,
                R.drawable.placeholder,
        };

        recyclerView.setAdapter(new RecyclerAdapter(getActivity(), listArray1, listArray2, imageArray, bgimageArray, listArray3, listArray4, listArray5));
        recyclerView.setNestedScrollingEnabled(true);
        return root;
    }
}
