package dnd.equipment;

import dnd.values.CurrencyDnD;

public class ItemDnD {
    public String name;
    public String summary;

    public int amount;

    public int value;
    public CurrencyDnD currencyGrade;

    public double weight;

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
