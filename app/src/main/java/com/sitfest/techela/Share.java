package com.sitfest.techela;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sitfest.techela.ui.home.HomeFragment;

public class Share extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nGet the official Techela 2020 app now!\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose a share method"));

            HomeFragment nextFrag = new HomeFragment();
            this.getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, nextFrag, null)
                    .addToBackStack(null)
                    .commit();
        } catch(Exception e) {
            Log.d("Share", "onCreate: "+e);
        }

    }
}
