package com.example.techela.ui.Contributors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.techela.R;

public class ContributorsPagerFragment extends Fragment{

    FragmentPagerAdapter adapterViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contributions, container, false);
        ViewPager vpPager = view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(this.getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        return view;

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return new DevelopersFragment();
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
                case 1:
                    return "Developers";
            }
        }
    }
}
