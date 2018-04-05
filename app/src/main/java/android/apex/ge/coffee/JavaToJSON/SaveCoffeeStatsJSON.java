package android.apex.ge.coffee.JavaToJSON;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * This class is used to save SaveCoffeeStats objects converted to JSON in database.
 * Conversion is done in CoffeeMachineDetailActivity for now.
 *
 * @see android.apex.ge.coffee.CoffeeMachineDetailActivity
 */
@DatabaseTable(tableName = "saveCoffeeStatsJSON")
public class SaveCoffeeStatsJSON {

    @DatabaseField(id = true)
    private UUID saveCoffeeStatsId;
    @DatabaseField
    private String saveCoffeeStatsString;

    public SaveCoffeeStatsJSON() {
    }

    public UUID getSaveCoffeeStatsId() {
        return saveCoffeeStatsId;
    }

    public void setSaveCoffeeStatsId(UUID saveCoffeeStatsId) {
        this.saveCoffeeStatsId = saveCoffeeStatsId;
    }

    public String getSaveCoffeeStatsString() {
        return saveCoffeeStatsString;
    }

    public void setSaveCoffeeStatsString(String saveCoffeeStatsString) {
        this.saveCoffeeStatsString = saveCoffeeStatsString;
    }
}


