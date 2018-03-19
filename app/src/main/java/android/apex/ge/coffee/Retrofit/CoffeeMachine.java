package android.apex.ge.coffee.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nika on 16/03/2018.
 * This is a POJO class used for json serialization/deserialization.
 */

public class CoffeeMachine {
    @SerializedName("ResponseStatus")
    @Expose
    private ResponseStatus responseStatus;
    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class ResponseStatus {

    }

    public class Result {

        @SerializedName("Acc")
        @Expose
        private String acc;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Address")
        @Expose
        private String address;

        public String getAcc() {
            return acc;
        }

        public void setAcc(String acc) {
            this.acc = acc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder
                    .append("\n\n\nAcc: " + acc)
                    .append("\nName: " + name)
                    .append("\nAddress: " + address)
                    .toString();
        }
    }
}
