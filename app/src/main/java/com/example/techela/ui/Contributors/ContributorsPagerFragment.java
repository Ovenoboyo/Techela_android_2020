package com.example.techela.ui.Contributors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.techela.MainActivity;
import com.example.techela.R;

import java.util.Objects;

public class ContributorsPagerFragment extends Fragment{

    private FragmentPagerAdapter adapterViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Contributors");
        View view = inflater.inflate(R.layout.contributions, container, false);
        ViewPager vpPager = view.findViewById(R.id.vpPager);

        adapterViewPager = new MyPagerAdapter(this.getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        return view;

    }

    static class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            int NUM_ITEMS = 1;
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                default:
                    return new ContributorsFragment();
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                default:
                case 0:
                    return "Organising committee";
            }
        }
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
