package android.apex.ge.coffee.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of CoffeeDocs parameter from Apex web service
 * returns the list of CoffeeDoc.
 */

public class CoffeeDocList {
    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<CoffeeDoc> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<CoffeeDoc> getResult() {
        return result;
    }

    public void setResult(List<CoffeeDoc> result) {
        this.result = result;
    }
}
