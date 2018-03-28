package android.apex.ge.coffee.Retrofit;

import android.apex.ge.coffee.Retrofit.Model.AccountInfo;
import android.apex.ge.coffee.Retrofit.Model.ResponseStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of PreOrderAccounts parameter from Apex web service
 */

public class PreOrderAccounts {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<AccountInfo> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<AccountInfo> getResult() {
        return result;
    }

    public void setResult(List<AccountInfo> result) {
        this.result = result;
    }

}
