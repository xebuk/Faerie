package dnd.equipment;

import common.Constants;
import dnd.values.CurrencyDnD;

import java.io.Serializable;

public class ItemDnD implements Serializable {
    public String name;
    public String summary;

    public int amount;

    public int value;
    public CurrencyDnD currencyGrade;

    public double weight;

    public ItemDnD() {
        this.name = "";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amount = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
