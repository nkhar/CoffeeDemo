package android.apex.ge.coffee.Retrofit;

import java.util.Date;
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

    @GET("CoffeeService/GetRawMaterials.json")
    Call <RawMaterials> listRawMaterials(@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetPreorderGoods.json")
    Call <PreorderGoods> listPreorderGoods(@Query("WarehouseAcc") String warehouseAcc, @Query("VanAcc") String vanAcc, @Query("BDate") Date bDate); //Not sure about Date type.

    @GET("CoffeeService/GetPreOrderAccounts.json")
    Call <PreOrderAccounts> listPreOrderAccounts();

    @GET("CoffeeService/GetCoffeeDocs.json")
    Call<CoffeeDocList> listCoffeeDocs(@Query("DDate") Date dDate, @Query("VanAcc") String vanAcc); //Not sure about Date type.

    @GET("CoffeeService/GetDocGoods.json")
    Call<DocGoods> listDocGoods(@Query("DocsID") String docsID);

    @GET("CoffeeService/GetLastCoffeCupsCount.json")
    Call<LastCoffeeCupsCount> listLastCoffeeCupsCount(@Query("CoffeeAcc") String coffeeAcc);

}
