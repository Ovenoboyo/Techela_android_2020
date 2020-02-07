package com.sitfest.techela.ui.Contributors;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.sitfest.techela.R;

import java.util.Objects;


public class ContributorsFragment extends PreferenceFragmentCompat {
    private int clicks = 0;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.contributors, rootKey);

        Preference egg = findPreference("egg");
        egg.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                clicks++;
                if (clicks == 1) {
                    Toast.makeText(ContributorsFragment.this.getContext(), "Shhh...", Toast.LENGTH_SHORT).show();
                } else if (clicks == 2) {
                    Toast.makeText(ContributorsFragment.this.getContext(), "Don't click....", Toast.LENGTH_SHORT).show();
                } else if (clicks == 3) {
                    Toast.makeText(ContributorsFragment.this.getContext(), "I said don't click!!", Toast.LENGTH_SHORT).show();
                } else if (clicks > 3) {
                    DevelopersFragment nextFrag = new DevelopersFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left,
                                    android.R.anim.slide_out_right, android.R.anim.slide_out_right )
                            .replace(R.id.nav_host_fragment, nextFrag, null)
                            .addToBackStack(null)
                            .commit();
                    clicks--;
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        Objects.requireNonNull(view).setBackgroundColor(Color.WHITE);
        return view;
    }

}
