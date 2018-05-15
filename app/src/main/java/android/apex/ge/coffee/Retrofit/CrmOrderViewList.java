package android.apex.ge.coffee.Retrofit;

import android.apex.ge.coffee.Retrofit.Model.CrmOrderView;
import android.apex.ge.coffee.Retrofit.Model.ResponseStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 16/03/2018.
 * This is a POJO class used for json serialization/deserialization.
 */

public class CrmOrderViewList {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<CrmOrderView> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<CrmOrderView> getResult() {
        return result;
    }

    public void setResult(List<CrmOrderView> result) {
        this.result = result;
    }
}
