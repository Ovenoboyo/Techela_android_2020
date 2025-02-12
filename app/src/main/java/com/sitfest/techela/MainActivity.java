package com.sitfest.techela;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.sitfest.techela.ui.Contributors.ContributorsPagerFragment;
import com.sitfest.techela.ui.Contributors.DevelopersFragment;
import com.sitfest.techela.ui.EventDetails.EventDetailsFragment;
import com.sitfest.techela.ui.Gallery.GalleryFragment;
import com.sitfest.techela.ui.GoogleForm.GoogleFormFragment;
import com.sitfest.techela.ui.Quiz.QuizFragment;
import com.sitfest.techela.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    public ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        drawer = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_navigate_up_description);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.events, R.id.qr_code, R.id.share, R.id.quiz, R.id.gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.contributors) {
            Fragment ContributorsFragment = new ContributorsPagerFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right );
            transaction.replace(R.id.nav_host_fragment, ContributorsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
            FragmentManager fm = getSupportFragmentManager();
            List<Fragment> allFragments = fm.getFragments();
            for (Fragment fragment : allFragments) {
                Log.d("test", "onBackPressed: "+fragment);
                if (fragment instanceof EventDetailsFragment) {
                    fm.popBackStack();
                    toolbar.setTitle("Events");
                    return;
                } else if (fragment instanceof ContributorsPagerFragment) {
                    fm.popBackStack();
                    setDrawerEnabled(true);
                    toolbar.setTitle("Events");
                    return;
                } else if (fragment instanceof DevelopersFragment) {
                    fm.popBackStack();
                    toolbar.setTitle("Contributors");
                    return;
                } else if((fragment instanceof HomeFragment || fragment instanceof NavHostFragment || fragment instanceof SupportRequestManagerFragment) && allFragments.size() <=2){
                    new AlertDialog.Builder(Objects.requireNonNull(this))
                            .setTitle("Exit?")
                            .setMessage("Are you sure you want to exit?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, (arg0, arg1) -> Objects.requireNonNull(this).finish()).create().show();
                }
            }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        if (drawer != null && mDrawerToggle != null) {
            drawer.setDrawerLockMode(lockMode);
            mDrawerToggle.setDrawerIndicatorEnabled(enabled);
        }
    }
}
