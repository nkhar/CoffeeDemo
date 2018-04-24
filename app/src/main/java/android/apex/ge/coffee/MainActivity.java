package android.apex.ge.coffee;

import android.apex.ge.coffee.Fragments.DocumentFragment;
import android.apex.ge.coffee.Fragments.MachineFragment;
import android.apex.ge.coffee.Fragments.PreOrderFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected final String LOG_TAG = "MainActivity";
    private NavigationView navigationView;

    private final String SELECTED_NAV_ITEM = "SELECTED_NAV_ITEM";
    private int selectedNavDrawerItemIndex;




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        Saving activity state
         */
        outState.putInt(SELECTED_NAV_ITEM, selectedNavDrawerItemIndex);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "we are in the onCreate method of main activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "savedInstanceState is not null");
            selectedNavDrawerItemIndex = savedInstanceState.getInt(SELECTED_NAV_ITEM, 0);
        }
        checkIfLoggedIn();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation drawer item clicks here.
        int id = item.getItemId();


        // get support fragment manager.
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_first) {
            Log.d(LOG_TAG, "First item was clicked");
            selectedNavDrawerItemIndex = 0;

            // Insert the fragment by replacing FrameLayout.
            MachineFragment machineFragment = new MachineFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, machineFragment, "MACHINE_TAG").commit();

        } else if (id == R.id.nav_second) {
            Log.d(LOG_TAG, "second item was clicked");
            selectedNavDrawerItemIndex = 1;

            // Insert the fragment by replacing FrameLayout.
            PreOrderFragment preOrderFragment = new PreOrderFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, preOrderFragment, "PREORDER_TAG").commit();
        } else if (id == R.id.nav_third) {
            Log.d(LOG_TAG, "third item was clicked");
            selectedNavDrawerItemIndex = 2;

            // Insert the fragment by replacing FrameLayout.
            DocumentFragment documentFragment = new DocumentFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, documentFragment, "Document_TAG").commit();
        } else if (id == R.id.nav_login) {
            Log.d(LOG_TAG, "login item was clicked");
           /* Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();*/
        } else if (id == R.id.nav_logout) {
            Log.d(LOG_TAG, "log out item was clicked");
            logOut();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "\n\n We are in onResume \n\n");
        super.onResume();
        /* checkIfLoggedIn();*/
    }

    private void checkIfLoggedIn() {

        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFERENCES_NAME, 0);
        //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean(LoginActivity.IS_USER_LOGGED_IN, false);
        if (hasLoggedIn) {
            onNavigationItemSelected(navigationView.getMenu().getItem(selectedNavDrawerItemIndex));
            navigationView.getMenu().getItem(selectedNavDrawerItemIndex).setChecked(true);
        } else {
            /*
            onNavigationItemSelected(navigationView.getMenu().getItem(3).getSubMenu().getItem(0));*/

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    private void logOut() {
        Log.d(LOG_TAG, "We are Logging the user out from MainActivity method logOut");
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        //Set "hasLoggedIn" to true
        editor.putBoolean(LoginActivity.IS_USER_LOGGED_IN, false);

        // Commit the edits!
        editor.commit();
        checkIfLoggedIn();
    }


}
