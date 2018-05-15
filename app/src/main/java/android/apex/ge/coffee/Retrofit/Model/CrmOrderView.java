package android.apex.ge.coffee.Retrofit.Model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of CrmOrderView object
 */

public class CrmOrderView  implements Comparable<CrmOrderView>{


    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("recipient_company_name")
    @Expose
    private String recipientCompanyName;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getRecipientCompanyName() {
        return recipientCompanyName;
    }

    public void setRecipientCompanyName(String recipientCompanyName) {
        this.recipientCompanyName = recipientCompanyName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\norderId: " + orderId)
                .append("\norderDate: " + orderDate)
                .append("\nrecipientCompanyName: " + recipientCompanyName)
                .toString();
    }

    @Override
    public int compareTo(@NonNull CrmOrderView o) {
        return this.getRecipientCompanyName().compareTo(o.getRecipientCompanyName());
    }
}
