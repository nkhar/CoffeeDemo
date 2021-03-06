package android.apex.ge.coffee.Retrofit.Model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of ProdData object.
 */

public class ProductData implements Comparable<ProductData> {

    @SerializedName("ProdPPID")
    @Expose
    private String prodPPID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("BCode")
    @Expose
    private String bCode;
    @SerializedName("InCode")
    @Expose
    private String inCode;

    @SerializedName("PackCount")
    @Expose
    private short packCount;
    @SerializedName("RCount")
    @Expose
    private float rCount;
    @SerializedName("VanRCount")
    @Expose
    private float vanRCount;


    public String getProdPPID() {
        return prodPPID;
    }

    public void setProdPPID(String prodPPID) {
        this.prodPPID = prodPPID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBCode() {
        return bCode;
    }

    public void setBCode(String bCode) {
        this.bCode = bCode;
    }

    public String getInCode() {
        return inCode;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public short getPackCount() {
        return packCount;
    }

    public void setPackCount(short packCount) {
        this.packCount = packCount;
    }

    public float getRCount() {
        return rCount;
    }

    public void setRCount(float rCount) {
        this.rCount = rCount;
    }

    public float getVanRCount() {
        return vanRCount;
    }

    public void setVanRCount(float vanRCount) {
        this.vanRCount = vanRCount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\nprodPPID: " + prodPPID)
                .append("\nname: " + name)
                .append("\nbCode:" + bCode)
                .append("\ninCode: " + inCode)
                .append("\npackCount: " + packCount)
                .append("\nrCount: " + rCount)
                .append("\nvanRCount: " + vanRCount)
                .toString();
    }


    @Override
    public int compareTo(@NonNull ProductData o) {

        return this.getName().compareTo(o.getName());
    }
}


/*
public class ProdDataInput extends ProductData {

    public double AfterQuantity;
    public double BeforeQuantity;
}*/
