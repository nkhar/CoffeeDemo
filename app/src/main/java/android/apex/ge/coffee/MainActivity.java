package android.apex.ge.coffee;

import android.apex.ge.coffee.Fragments.DocumentFragment;
import android.apex.ge.coffee.Fragments.MachineFragment;
import android.apex.ge.coffee.Fragments.PreOrderFragment;
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

            // Insert the fragment by replacing FrameLayout.
            MachineFragment machineFragment = new MachineFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, machineFragment, "MACHINE_TAG").commit();

        } else if (id == R.id.nav_second) {
            Log.d(LOG_TAG, "second item was clicked");

            // Insert the fragment by replacing FrameLayout.
            PreOrderFragment preOrderFragment = new PreOrderFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, preOrderFragment, "PREORDER_TAG").commit();
        } else if (id == R.id.nav_third) {
            Log.d(LOG_TAG, "third item was clicked");

            // Insert the fragment by replacing FrameLayout.
            DocumentFragment documentFragment = new DocumentFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, documentFragment, "Document_TAG").commit();
        } else if (id == R.id.nav_share) {
            Log.d(LOG_TAG, "share item was clicked");
        } else if (id == R.id.nav_send) {
            Log.d(LOG_TAG, "send item was clicked");
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
