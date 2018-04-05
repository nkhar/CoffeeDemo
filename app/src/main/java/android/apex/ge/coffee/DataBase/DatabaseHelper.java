package android.apex.ge.coffee.DataBase;


import android.apex.ge.coffee.JavaToJSON.SaveCoffeeStatsJSON;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "Coffee.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the various data tables
    private Dao<SaveCoffeeStatsJSON, UUID> saveCoffeeStatsJSONDao = null;



    /* DatabaseHelper public constructor.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreateDatabaseHelper");
            TableUtils.createTable(connectionSource, SaveCoffeeStatsJSON.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            // throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        // RuntimeExceptionDao<SimpleData, Integer> dao = getSimpleDataDao();
        //  long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        // SimpleData simple = new SimpleData(millis);
        // dao.create(simple);
        // simple = new SimpleData(millis + 1);
        //   dao.create(simple);
        //  Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, SaveCoffeeStatsJSON.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            // throw new RuntimeException(e);
        }
    }

    /**
     * Create the getDao methods of all database tables to access those from android code.
     * Insert, delete, read, update everything will be happening through DAOs
     */
    public Dao<SaveCoffeeStatsJSON, UUID> getSaveCoffeeStatsJSONDao() throws SQLException {
        if (saveCoffeeStatsJSONDao == null) {
            saveCoffeeStatsJSONDao = getDao(SaveCoffeeStatsJSON.class);
        }
        return saveCoffeeStatsJSONDao;
    }


    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        saveCoffeeStatsJSONDao = null;

    }
}