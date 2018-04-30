package android.apex.ge.coffee;

import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.CoffeeService;
import android.apex.ge.coffee.Retrofit.CoffeeServiceAPI.RetrofitClient;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class CoffeeApp extends Application {


    public static CoffeeApp AppInstance;

    //RetrofitClient service
    CoffeeService service;

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
        initRetrofitService();
    }

    private void initRetrofitService() {
        service = RetrofitClient.getRetrofitClient().create(CoffeeService.class);
    }

    public CoffeeService getRetrofitService() {
        return service;
    }
}
