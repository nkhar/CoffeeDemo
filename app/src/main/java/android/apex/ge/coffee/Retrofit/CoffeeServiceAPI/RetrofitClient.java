package android.apex.ge.coffee.Retrofit.CoffeeServiceAPI;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nika on 19/03/2018.
 * This is used to send back retrofit instance with okHttpClient so that API interface can be
 * implemented.
 */

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String LOG_TAG = "RetrofitClient";

    /*
    http://support.apex.ge:83/
    user:"rpl"
    password: "9"
     */
    public static Retrofit getRetrofitClient(String clientURL, String clientUserName, String clientPassword) {
        /*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("sa", "client"))
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://92.241.94.50:8181/")
                .client(client)
                .build();

        return retrofit;*/


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(clientUserName, clientPassword))
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpUrl httpURL = checkURL(clientURL);

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(httpURL)
                .client(client)
                .build();


        return retrofit;


    }

    private static HttpUrl checkURL(String passedURL) {
        HttpUrl httpUrl = null;
        String endPoint;
        if (!TextUtils.isEmpty(passedURL)) {
            try {
                httpUrl = HttpUrl.parse(passedURL);
            } catch (Exception ex) {
                Log.e(LOG_TAG, "Failed to parse endpoint:" + passedURL, ex);
            }
        }
        if (httpUrl != null
                && ("http".equalsIgnoreCase(httpUrl.scheme())
                || "https".equalsIgnoreCase(httpUrl.scheme()))
                && !TextUtils.isEmpty(httpUrl.host())) {
            endPoint = httpUrl.toString();
        } else {
            endPoint = "http://127.0.0.1";
            httpUrl = HttpUrl.parse(endPoint);
        }

        return httpUrl;
    }



}
