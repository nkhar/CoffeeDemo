package android.apex.ge.coffee.Retrofit;

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

    public static Retrofit getRetrofitClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor("rpl", "9"))
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://support.apex.ge:83/")
                .client(client)
                .build();

        return retrofit;
    }

}
