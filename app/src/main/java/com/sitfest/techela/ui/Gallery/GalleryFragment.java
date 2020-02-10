package com.sitfest.techela.ui.Gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.sitfest.techela.MainActivity;
import com.sitfest.techela.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class GalleryFragment extends Fragment {

    private static final String endpoint = "https://gist.githubusercontent.com/Ovenoboyo/6bb7d9cac549a79b20c9a4850e2e91f9/raw/7a7157dab50c6d0e10f7e6d414cf98ef2f01783e/TechelaGallery.json";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
        pDialog = new ProgressDialog(this.getContext());
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(this.getContext(), images);

        fetchImages();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.gallery_recycler);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Gallery");

        ((MainActivity)getActivity()).setDrawerEnabled(true);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return root;
    }

    private void fetchImages() {

        pDialog.setMessage("Fetching Images...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                response -> {
                    pDialog.hide();

                    images.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            Image image = new Image();
                            image.setName(object.getString("name"));

                            JSONObject url = object.getJSONObject("url");
                            image.setSmall(url.getString("small"));
                            image.setMedium(url.getString("medium"));
                            image.setLarge(url.getString("large"));
                            image.setTimestamp(object.getString("timestamp"));

                            images.add(image);

                        } catch (JSONException e) {
                            Log.e("ImageLoader", "Json parsing error: " + e.getMessage());
                        }
                    }

                    mAdapter.notifyDataSetChanged();
                }, error -> {
                    Log.e("ImageLoader", "Error: " + error.getMessage());
                    pDialog.hide();
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}