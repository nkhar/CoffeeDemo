package android.apex.ge.coffee.Retrofit.CoffeeServiceAPI;

import android.apex.ge.coffee.Retrofit.CoffeeDocList;
import android.apex.ge.coffee.Retrofit.CoffeeMachineList;
import android.apex.ge.coffee.Retrofit.DocGoods;
import android.apex.ge.coffee.Retrofit.LastCoffeeCupsCount;
import android.apex.ge.coffee.Retrofit.Model.CrmOrderView;
import android.apex.ge.coffee.Retrofit.Model.SaveCoffeePreOrder;
import android.apex.ge.coffee.Retrofit.Model.SaveCoffeeStats;
import android.apex.ge.coffee.Retrofit.PreOrderAccounts;
import android.apex.ge.coffee.Retrofit.PreorderGoods;
import android.apex.ge.coffee.Retrofit.ProducedGoods;
import android.apex.ge.coffee.Retrofit.RawMaterials;
import android.apex.ge.coffee.Retrofit.Repo;
import android.apex.ge.coffee.Retrofit.SaleGoods;
import android.apex.ge.coffee.Retrofit.SaveCoffeePreOrderResponse;
import android.apex.ge.coffee.Retrofit.SaveCoffeeStatsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nika on 16/03/2018.
 * This is retrofit interface that should be used to retrieve data from server
 */

public interface CoffeeService {

    /*
    VanAcc should be 1610007800 for testing.
     */

    /*GET methods*/

    @GET("CoffeeService/GetCoffeeList.json")
    Call<CoffeeMachineList> listCoffeeMachines(@Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetSaleGoods.json")
    Call<SaleGoods> listSaleGoods(@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetProducedGoods.json")
    Call<ProducedGoods> listProducedGoods(@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);

    @GET("CoffeeService/GetRawMaterials.json")
    Call<RawMaterials> listRawMaterials(@Query("CoffeeAcc") String coffeeAcc, @Query("VanAcc") String vanAcc);

    @GET("/Crm/Orders/View.json")
    Call<CrmOrderView> listCrmOrdersView(@Query("CUser") String cUser, @Query("order_date") String orderDate);

    @GET("CoffeeService/GetPreorderGoods.json")
    Call<PreorderGoods> listPreorderGoods(@Query("WarehouseAcc") String warehouseAcc, @Query("VanAcc") String vanAcc, @Query("BDate") String bDate); //Not sure about Date type.

    @GET("CoffeeService/GetPreOrderAccounts.json")
    Call<PreOrderAccounts> listPreOrderAccounts();

    @GET("CoffeeService/GetCoffeeDocs.json")
    Call<CoffeeDocList> listCoffeeDocs(@Query("DDate") String dDate, @Query("VanAcc") String vanAcc, @Query("jsconfig") String jsConfig); //Not sure about Date type.

    @GET("CoffeeService/GetDocGoods.json")
    Call<DocGoods> listDocGoods(@Query("DocsID") String docsID);

    @GET("CoffeeService/GetLastCoffeCupsCount.json")
    Call<LastCoffeeCupsCount> listLastCoffeeCupsCount(@Query("CoffeeAcc") String coffeeAcc);


    /*POST methods*/
    @POST("CoffeeService/SaveCoffeeStats")
    @FormUrlEncoded
    Call<SaveCoffeeStatsResponse> listSaveCoffeeStatsResponse(@Body SaveCoffeeStats saveCoffeeStats);

    @POST("CoffeeService/SavePreorder")
    @FormUrlEncoded
    Call<SaveCoffeePreOrderResponse> listSaveCoffeePreOrderResponse(@Body SaveCoffeePreOrder saveCoffeePreOrder);


}
