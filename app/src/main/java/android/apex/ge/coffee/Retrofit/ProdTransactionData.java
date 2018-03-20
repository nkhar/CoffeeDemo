package android.apex.ge.coffee.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of ProdTransactionData object.
 */

public class ProdTransactionData {


    @SerializedName("ProdPPID")
    @Expose
    private String prodPPID;
    @SerializedName("CurICount")
    @Expose
    private float curICount;
    @SerializedName("CurSCount")
    @Expose
    private float curSCount;

    public String getProdPPID() {
        return prodPPID;
    }

    public void setProdPPID(String prodPPID) {
        this.prodPPID = prodPPID;
    }

    public float getCurICount() {
        return curICount;
    }

    public void setCurICount(float curICount) {
        this.curICount = curICount;
    }

    public float getCurSCount() {
        return curSCount;
    }

    public void setCurSCount(float curSCount) {
        this.curSCount = curSCount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\nprodPPID: " + prodPPID)
                .append("\ncurICount: " + curICount)
                .append("\ncurSCount: " + curSCount)
                .toString();
    }
}
