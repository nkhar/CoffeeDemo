package android.apex.ge.coffee.Retrofit;

import android.apex.ge.coffee.Retrofit.Model.ResponseStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Nika on 21/03/2018.
 * This class is used for (de)serialization of SaveCoffeeStatsResponse parameter from Apex
 * SaveCoffeeStats web service
 */

public class SaveCoffeeStatsResponse {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private boolean result = false;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

}
