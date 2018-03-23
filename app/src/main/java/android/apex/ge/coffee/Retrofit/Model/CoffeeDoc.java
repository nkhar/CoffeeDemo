package android.apex.ge.coffee.Retrofit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Nika on 20/03/2018.
 * This class is used for (de)serialization of CoffeeDoc object.
 */

public class CoffeeDoc {

    @SerializedName("DocsID")
    @Expose
    private String docsID;

    @SerializedName("DocType")
    @Expose
    private int docType;

    @SerializedName("DDate")
    @Expose
    private Date dDate;

    @SerializedName("NumberIn")
    @Expose
    private String numberIn;

    @SerializedName("CorespondAcc")
    @Expose
    private String corespondAcc;

    @SerializedName("CorespondName")
    @Expose
    private String corespondName;

    @SerializedName("VG")
    @Expose
    private float vG;

    @SerializedName("WaybillNum")
    @Expose
    private String waybillNum;


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("\n\n\ndocsID: " + docsID)
                .append("\ndocType: " + docType)
                .append("\ndDate: " + dDate)
                .append("\nnumberIn: " + numberIn)
                .append("\ncorespondAcc: " + corespondAcc)
                .append("\ncorespondName: " + corespondName)
                .append("\nvG: " + vG)
                .append("\nwaybillNum: " + waybillNum)
                .toString();
    }

}
