package android.apex.ge.coffee.Retrofit.CoffeeServiceAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    /*
    http://support.apex.ge:83/
    user:"rpl"
    password: "9"
     */
    public static Retrofit getRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
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

        return retrofit;
    }

}
