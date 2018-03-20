package android.apex.ge.coffee.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of LastCoffeeCupsCount parameter from Apex web service
 */

public class LastCoffeeCupsCount {

    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("LastCount")
    @Expose
    private int lastCount = 0;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public int getResult() {
        return lastCount;
    }

    public void setResult(int lastCount) {
        this.lastCount = lastCount;
    }
}
