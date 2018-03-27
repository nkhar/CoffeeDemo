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

    public String getDocsID() {
        return docsID;
    }

    public void setDocsID(String docsID) {
        this.docsID = docsID;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public Date getdDate() {
        return dDate;
    }

    public void setdDate(Date dDate) {
        this.dDate = dDate;
    }

    public String getNumberIn() {
        return numberIn;
    }

    public void setNumberIn(String numberIn) {
        this.numberIn = numberIn;
    }

    public String getCorespondAcc() {
        return corespondAcc;
    }

    public void setCorespondAcc(String corespondAcc) {
        this.corespondAcc = corespondAcc;
    }

    public String getCorespondName() {
        return corespondName;
    }

    public void setCorespondName(String corespondName) {
        this.corespondName = corespondName;
    }

    public float getvG() {
        return vG;
    }

    public void setvG(float vG) {
        this.vG = vG;
    }

    public String getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum;
    }

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
