package android.apex.ge.coffee;

import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class CoffeeApp extends Application {

    private static final String LOG_TAG = "CoffeeApp";

    public static CoffeeApp AppInstance;
    SharedPreferences settings;


    //RetrofitClient service
    CoffeeService service;
   /*
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
    */

    // private static
    private static final String PREFERENCES_NAME = "MyPreferencesFile";
    private static final String SERVER_URL = "SERVER_URL";
    private static final String SERVER_USER = "SERVER_USER";
    private static final String SERVER_PASSWORD = "SERVER_PASSWORD";
    private static final String VAN_ACCOUNT = "VAN_ACCOUNT";

    private static final String NO_SERVER_URL = "NO_SERVER_URL/";
    private static final String NO_SERVER_USER = "NO_SERVER_USER";
    private static final String NO_SERVER_PASSWORD = "NO_SERVER_PASSWORD";
    private static final String NO_VAN_ACCOUNT = "NO_VAN_ACCOUNT";


    private String vanAccount;
    private String clientURL;
    private String clientUserName;
    private String clientPassword;


    public void onCreate() {
        super.onCreate();
        // get user preferred language set locale accordingly new locale(language,country)
        Locale locale = new Locale("ka", "GE");
        Log.d("ApplicationClass", "We are in the Application class:" + locale.toString());
        LocaleUtils.setLocale(locale);
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());
        init();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.updateConfig(this, newConfig);
    }

    private void init() {
        AppInstance = this;
        initPreferenceFile();
        initPreferenceChangeListener();
        initPreferenceFileValues();
        initRetrofitService();
    }

    private void initPreferenceFile() {
         settings = getSharedPreferences(PREFERENCES_NAME, 0); // 0 - for private mode same as MODE_PRIVATE
    }

    private void initPreferenceChangeListener() {
        settings.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(LOG_TAG,  "\n\n Preferences Listener method Has been called, some preference has changed.");

                if(key.equals(SERVER_URL)){
                    clientURL = sharedPreferences.getString(SERVER_URL, NO_SERVER_URL);
                }
                if(key.equals(SERVER_USER)){
                    clientUserName = sharedPreferences.getString(SERVER_USER, NO_SERVER_USER);
                }
                if(key.equals(SERVER_PASSWORD)){
                    clientPassword = sharedPreferences.getString(SERVER_PASSWORD, NO_SERVER_PASSWORD);
                }
                if(key.equals(VAN_ACCOUNT)){
                    vanAccount = sharedPreferences.getString(VAN_ACCOUNT, NO_VAN_ACCOUNT);
                }

            }
        });
    }

    private void initRetrofitService() {
        service = RetrofitClient.getRetrofitClient(clientURL, clientUserName, clientPassword).create(CoffeeService.class);
    }

    private void initPreferenceFileValues() {

        clientURL = settings.getString(SERVER_URL, NO_SERVER_URL);
        clientUserName = settings.getString(SERVER_USER, NO_SERVER_USER);
        clientPassword = settings.getString(SERVER_PASSWORD, NO_SERVER_PASSWORD);
        vanAccount = settings.getString(VAN_ACCOUNT, NO_VAN_ACCOUNT);
    }

    public CoffeeService getRetrofitService() {
        return service;
    }

    public String getVanAccountFromApp() {
        return vanAccount;
    }
}
