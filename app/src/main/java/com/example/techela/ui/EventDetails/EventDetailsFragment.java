package com.example.techela.ui.EventDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.techela.MainActivity;
import com.example.techela.R;

import java.util.Objects;

public class EventDetailsFragment extends Fragment {
    private String eventName = "", eventDesc = "", eventTime = "", eventVenue= "";
    private int eventBanner = R.drawable.logo_banner;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.eventName = bundle.getString("eventName");
            this.eventDesc = bundle.getString("eventDesc");
            this.eventTime = bundle.getString("eventTime");
            this.eventVenue = bundle.getString("eventVenue");
            this.eventBanner = bundle.getInt("eventBanner");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_details, container, false);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();
        toolbar.setTitle("Events");

        TextView eventNameT = root.findViewById(R.id.eventName);
        TextView eventDescT = root.findViewById(R.id.eventDesc);
        TextView eventTimeT = root.findViewById(R.id.eventTime);
        TextView eventVenueT = root.findViewById(R.id.eventVenue);

        ImageView eventBannerI = root.findViewById(R.id.eventBanner);

        eventNameT.setText(this.eventName);
        eventDescT.setText(this.eventDesc);
        eventTimeT.setText(this.eventTime);
        eventVenueT.setText(this.eventVenue);

        eventBannerI.setImageDrawable(Objects.requireNonNull(getContext()).getDrawable(eventBanner));

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

}