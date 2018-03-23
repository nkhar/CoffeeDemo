package android.apex.ge.coffee.Retrofit.Model;

import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Nika on 21/03/2018.
 * This class is used for (de)serialization of SaveCoffeePreOrder parameter from Apex
 * SavePreorder web service
 */

public class SaveCoffeePreOrder {

    @SerializedName("DDate")
    @Expose
    private Date dDate;

    @SerializedName("BDate")
    @Expose
    private Date bDate;

    @SerializedName("DB")
    @Expose
    private String dB;

    @SerializedName("CR")
    @Expose
    private String cR;

    @SerializedName("Goods")
    @Expose
    private List<ProdTransactionData> goods;

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getdB() {
        return dB;
    }

    public void setdB(String dB) {
        this.dB = dB;
    }

    public String getcR() {
        return cR;
    }

    public void setcR(String cR) {
        this.cR = cR;
    }

    public List<ProdTransactionData> getGoods() {
        return goods;
    }

    public void setGoods(List<ProdTransactionData> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\ndDate: " + dDate)
                .append("\nbDate: " + bDate)
                .append("\ndB:" + dB)
                .append("\ncR: " + cR)
                .append("\ngoods: " + goods)
                .toString();
    }


}
