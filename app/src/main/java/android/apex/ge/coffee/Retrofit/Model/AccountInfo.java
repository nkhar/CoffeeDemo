package android.apex.ge.coffee.Retrofit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of AccountInfo object.
 */

public class AccountInfo {

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


    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsN() {
        return sN;
    }

    public void setsN(String sN) {
        this.sN = sN;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccWithName() {
        return accWithName;
    }

    public void setAccWithName(String accWithName) {
        this.accWithName = accWithName;
    }

    public int getVatType() {
        return vatType;
    }

    public void setVatType(int vatType) {
        this.vatType = vatType;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\nacc: " + acc)
                .append("\nname: " + name)
                .append("\nSN: " + sN)
                .append("\nwarehouseAddress: " + warehouseAddress)
                .append("\naddress: " + address)
                .append("\nphone: " + phone)
                .append("\naccWithName: " + accWithName)
                .append("\nvatType: " + vatType)
                .toString();
    }
}
