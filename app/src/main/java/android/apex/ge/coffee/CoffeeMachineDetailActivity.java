package android.apex.ge.coffee;

import android.apex.ge.coffee.DataBase.DatabaseHelper;
import android.apex.ge.coffee.Fragments.CoffeeFragmentPagerAdapter;
import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;
import android.apex.ge.coffee.Retrofit.Model.SaveCoffeeStats;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;

/**
 * This class represents Activity, which starts when an Item is selected in RecyclerView of
 * MachineFragment. MachineFragment is fragment displayed when one of Navigation drawer items is
 * selected.
 * This class itself is responsible for viewpager and various fragments it contains.
 *
 * @see android.apex.ge.coffee.Fragments.MachineFragment
 * @see CoffeeFragmentPagerAdapter
 */

public class CoffeeMachineDetailActivity extends AppCompatActivity {

    protected final String LOG_TAG = "CoffeeMachDetAct";

    private SaveCoffeeStats saveCoffeeStats;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "We are in onCreate method of CoffeeMachineDetailActivity");
        setContentView(R.layout.activity_coffee_machine_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_coffee_detail);
        setSupportActionBar(toolbar);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager_coffee_detail);
        viewPager.setAdapter(new CoffeeFragmentPagerAdapter(getSupportFragmentManager(),
                CoffeeMachineDetailActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*
        Creating SaveCoffeeStats object
         */

        createSaveCoffeeStatsObject();


    }

    private void createSaveCoffeeStatsObject() {
        saveCoffeeStats = new SaveCoffeeStats();
        saveCoffeeStats.setCoffeeAcc("1610003000");
        saveCoffeeStats.setVanAcc("1610007800");

        // set Primitive values
        saveCoffeeStats.setCoffeeCupsMade(1001);
        saveCoffeeStats.setMoneyTaken(231.5f);

        /*setting the ArrayLists*/
        // Sale list
        saveCoffeeStats.setSaleAndTransit(new ArrayList<ProdTransactionData>());
        // Sale Produced list
        saveCoffeeStats.setSaleProduced(new ArrayList<ProdTransactionData>());
        // Raw Materials list
        saveCoffeeStats.setTransitRawMaterials(new ArrayList<ProdTransactionData>());
    }


    public SaveCoffeeStats getSaveCoffeeStats() {
        return saveCoffeeStats;
    }


    /**
     * getDatabaseHelper returns instance of DatabaseHelper class
     */
    public DatabaseHelper getDatabaseHelper() {
        if(databaseHelper == null ) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
         *  You'll need this in your class to release the helper when done.
         */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}

