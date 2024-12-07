package dnd.characteristics;

import dnd.values.masteryvalues.AdvantageTypeDnD;

import java.io.Serializable;
import java.util.HashSet;

public class FeatDnD implements Serializable {
    public String title;
    public String summary;
    public HashSet<AdvantageTypeDnD> advantages;
    public HashSet<AdvantageTypeDnD> disadvantages;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public FeatDnD() {
        this.title = "Своя черта";
        this.summary = "Введите своё описание.";
        this.advantages = new HashSet<>();
        this.disadvantages =new HashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder feat = new StringBuilder();
        feat.append(title).append("\n");
        feat.append(summary).append("\n");
        return feat.toString();
    }
}
