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
    @SerializedName("recipient_branch_name")
    @Expose
    private String recipientBranchName;

    @SerializedName("to_address")
    @Expose
    private String toAddress;

    @SerializedName("total_amount")
    @Expose
    private double totalAmount;


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

    public String getRecipientBranchName() {
        return recipientBranchName;
    }

    public void setRecipientBranchName(String recipientBranchName) {
        this.recipientBranchName = recipientBranchName;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\norderId: " + orderId)
                .append("\norderDate: " + orderDate)
                .append("\nrecipientCompanyName: " + recipientBranchName)
                .toString();
    }

    @Override
    public int compareTo(@NonNull CrmOrderView o) {
        return this.getRecipientBranchName().compareTo(o.getRecipientBranchName());
    }
}
