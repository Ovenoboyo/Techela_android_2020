package com.example.techela.ui.EventDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.techela.MainActivity;
import com.example.techela.R;

public class EventDetailsFragment extends Fragment {

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_event_details, container, false);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();
        toolbar.setTitle("Events");

        return root;
    }


}