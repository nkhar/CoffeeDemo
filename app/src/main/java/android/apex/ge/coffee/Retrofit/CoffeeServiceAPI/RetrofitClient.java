package android.apex.ge.coffee.Retrofit.CoffeeServiceAPI;

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

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://92.241.94.50:8181/")
                .client(client)
                .build();

        return retrofit;
    }

}
