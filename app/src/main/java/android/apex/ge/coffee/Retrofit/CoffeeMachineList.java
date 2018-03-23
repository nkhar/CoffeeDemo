package android.apex.ge.coffee.Retrofit;

import android.apex.ge.coffee.Retrofit.Model.CoffeeMachine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 16/03/2018.
 * This is a POJO class used for json serialization/deserialization.
 */

public class CoffeeMachineList {
    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<CoffeeMachine> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<CoffeeMachine> getResult() {
        return result;
    }

    public void setResult(List<CoffeeMachine> result) {
        this.result = result;
    }

}
