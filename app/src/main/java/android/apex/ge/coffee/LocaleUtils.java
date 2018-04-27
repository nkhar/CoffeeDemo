package android.apex.ge.coffee;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.ContextThemeWrapper;

import java.util.Locale;

/**
 * This class is responsible for managing locale of the application. Mainly language.
 */

public class LocaleUtils {


    private static Locale sLocale;

    public static Locale getCurrentLocale() {
        return sLocale;
    }

    public static void setLocale(Locale locale) {
        sLocale = locale;
        if(sLocale != null) {
            Locale.setDefault(sLocale);
        }
    }

    public static void updateConfig(ContextThemeWrapper wrapper) {
        if(sLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = new Configuration();
            configuration.setLocale(sLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(Application app, Configuration configuration) {
        if(sLocale != null ) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            setConfigCurrentLocale(config, sLocale);
            Resources res = app.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Locale getConfigCurrentLocale(Configuration configuration) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return configuration.getLocales().get(0);
        } else {
            return configuration.locale;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void setConfigCurrentLocale(Configuration configuration, Locale locale) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
    }
}
