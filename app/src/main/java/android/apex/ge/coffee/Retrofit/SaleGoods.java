package android.apex.ge.coffee.Retrofit;

import android.apex.ge.coffee.Retrofit.Model.ProductData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of SaleGoods parameter from Apex web service
 */

public class SaleGoods {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<ProductData> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<ProductData> getResult() {
        return result;
    }

    public void setResult(List<ProductData> result) {
        this.result = result;
    }

}
