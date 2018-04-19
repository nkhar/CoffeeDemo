package android.apex.ge.coffee;

import android.apex.ge.coffee.DataBase.DatabaseHelper;
import android.apex.ge.coffee.Fragments.CoffeeFragmentPagerAdapter;
import android.apex.ge.coffee.JavaToJSON.SaveCoffeeStatsJSON;
import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;
import android.apex.ge.coffee.Retrofit.Model.SaveCoffeeStats;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

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
    private SaveCoffeeStatsJSON saveCoffeeStatsJSON;

    // Reference of DatabaseHelper class to access its DAOs and other components pushing a
    protected DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding Author table
    protected Dao<SaveCoffeeStatsJSON, UUID> saveCoffeeStatsJSONDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "We are in onCreate method of CoffeeMachineDetailActivity");
        setContentView(R.layout.activity_coffee_machine_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_coffee_detail);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


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

        if(checkIfSaveCoffeeStatsObjectHasToBeCreated()){
            createSaveCoffeeStatsObject();
        }
        else{
            createFromDatabaseSaveCoffeeStatsObject();
        }



    }



    private boolean checkIfSaveCoffeeStatsObjectHasToBeCreated() {

        boolean flag = true;

        try {
            saveCoffeeStatsJSONDao = getDatabaseHelper().getSaveCoffeeStatsJSONDao();
            Log.d(LOG_TAG, "WE got saveCoffeeStatsJSONDao in checkIfSaveCoffeeStatsObjectHasToBeCreated");
            if(saveCoffeeStatsJSONDao.queryForAll().size() == 1){
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return flag;
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

        Log.d(LOG_TAG, "\n\n We have created a new SaveCoffeeStats object");
    }




    private void createFromDatabaseSaveCoffeeStatsObject() {
        try {
            saveCoffeeStatsJSON = saveCoffeeStatsJSONDao.queryForAll().get(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        saveCoffeeStats =  convertFromJSON(saveCoffeeStatsJSON.getSaveCoffeeStatsString());
        Log.d(LOG_TAG, "\n\n We have created from DATABASE a SaveCoffeeStats object");
    }







    private void saveToDatabase() {
        Log.d(LOG_TAG, "\n\n We are saving to DATABASE");
        String convertedString = convertToJSON();

        createSaveCoffeeStatsJSONObject(convertedString);

        try {
            saveCoffeeStatsJSONDao = getDatabaseHelper().getSaveCoffeeStatsJSONDao();
            Log.d(LOG_TAG, "WE got saveCoffeeStatsJSONDao");
            if (saveCoffeeStatsJSONDao.queryForAll().size() == 1) {
                saveCoffeeStatsJSONDao.delete(saveCoffeeStatsJSONDao.queryForAll().get(0));
            }
            saveCoffeeStatsJSONDao.create(saveCoffeeStatsJSON);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "Done with saveCoffeeStatsJSONDao " + System.currentTimeMillis());

    }

    private String convertToJSON() {
        Gson gson = new Gson();
        return  gson.toJson(saveCoffeeStats);
    }
    private SaveCoffeeStats convertFromJSON( String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, SaveCoffeeStats.class);
    }

    private void createSaveCoffeeStatsJSONObject(String conString) {
        saveCoffeeStatsJSON = new SaveCoffeeStatsJSON();
        saveCoffeeStatsJSON.setSaveCoffeeStatsId(UUID.randomUUID());
        saveCoffeeStatsJSON.setSaveCoffeeStatsString(conString);
    }



    public SaveCoffeeStats getSaveCoffeeStats() {
        return saveCoffeeStats;
    }


    /**
     * getDatabaseHelper returns instance of DatabaseHelper class
     */
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "\n\n We are in onPause \n\n");
        saveToDatabase();
        //Check if activity is finishing.
       /* if(isFinishing()){
            Log.d(LOG_TAG, "\n\n is Finishing \n\n");
            saveToDatabase();
        }*/

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

