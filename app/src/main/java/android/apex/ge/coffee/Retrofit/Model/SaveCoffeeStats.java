package android.apex.ge.coffee.Retrofit.Model;

import android.apex.ge.coffee.Retrofit.Model.ProdTransactionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Nika on 21/03/2018.
 * This class is used for (de)serialization of SaveCoffeeStats parameter from Apex
 * SaveCoffeeStats web service
 */


@DatabaseTable(tableName = "saveCoffeeStats")
public class SaveCoffeeStats {

    @SerializedName("CoffeeAcc")
    @Expose
    @DatabaseField
    private String coffeeAcc;

    @SerializedName("VanAcc")
    @Expose
    private String vanAcc;

    @SerializedName("SaleAndTransit")
    @Expose
    private List<ProdTransactionData> saleAndTransit;

    @SerializedName("SaleProduced")
    @Expose
    private List<ProdTransactionData> saleProduced;

    @SerializedName("TransitRawMaterials")
    @Expose
    private List<ProdTransactionData> transitRawMaterials;

    @SerializedName("MoneyTaken")
    @Expose
    private float moneyTaken;

    @SerializedName("CoffeeCupsMade")
    @Expose
    private int coffeeCupsMade;


    public String getCoffeeAcc() {
        return coffeeAcc;
    }

    public void setCoffeeAcc(String coffeeAcc) {
        this.coffeeAcc = coffeeAcc;
    }

    public String getVanAcc() {
        return vanAcc;
    }

    public void setVanAcc(String vanAcc) {
        this.vanAcc = vanAcc;
    }

    public List<ProdTransactionData> getSaleAndTransit() {
        return saleAndTransit;
    }

    public void setSaleAndTransit(List<ProdTransactionData> saleAndTransit) {
        this.saleAndTransit = saleAndTransit;
    }

    public List<ProdTransactionData> getSaleProduced() {
        return saleProduced;
    }

    public void setSaleProduced(List<ProdTransactionData> saleProduced) {
        this.saleProduced = saleProduced;
    }

    public List<ProdTransactionData> getTransitRawMaterials() {
        return transitRawMaterials;
    }

    public void setTransitRawMaterials(List<ProdTransactionData> transitRawMaterials) {
        this.transitRawMaterials = transitRawMaterials;
    }

    public float getMoneyTaken() {
        return moneyTaken;
    }

    public void setMoneyTaken(float moneyTaken) {
        this.moneyTaken = moneyTaken;
    }

    public int getCoffeeCupsMade() {
        return coffeeCupsMade;
    }

    public void setCoffeeCupsMade(int coffeeCupsMade) {
        this.coffeeCupsMade = coffeeCupsMade;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\ncoffeeAcc: " + coffeeAcc)
                .append("\nvanAcc: " + vanAcc)
                .append("\nsaleAndTransit:" + saleAndTransit)
                .append("\nsaleProduced: " + saleProduced)
                .append("\ntransitRawMaterials: " + transitRawMaterials)
                .append("\nmoneyTaken: " + moneyTaken)
                .append("\ncoffeeCupsMade: " + coffeeCupsMade)
                .toString();
    }

}
