package android.apex.ge.coffee.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of CoffeeMachine object
 */

public class CoffeeMachine {

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
