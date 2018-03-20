package android.apex.ge.coffee.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nika on 16/03/2018.
 * This is retrofit interface that should be used to retrieve data from server
 */

public interface CoffeeService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);


    @GET("CoffeeService/GetCoffeeList.json")
    Call <CoffeeMachineList> listCoffeeMachines(@Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetSaleGoods.json")
    Call<SaleGoods> listSaleGoods (@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetProducedGoods.json")
    Call <ProducedGoods> listProducedGoods(@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);





}
