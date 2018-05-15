package android.apex.ge.coffee.Retrofit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of CrmOrderView object
 */

public class CrmOrderView {

    @SerializedName("Acc")
    @Expose
    private String acc;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SN")
    @Expose
    private String sN;
    @SerializedName("WarehouseAddress")
    @Expose
    private String warehouseAddress;

    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("AccWithName")
    @Expose
    private String accWithName;
    @SerializedName("VatType")
    @Expose
    private int vatType;
}
