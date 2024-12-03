package dnd.characteristics;

import java.io.Serializable;

public class FeatDnD implements Serializable {
    public String title;
    public String summary;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public FeatDnD() {
        this.title = "Своя черта";
        this.summary = "Введите своё описание.";
    }

    @Override
    public String toString() {
        StringBuilder feat = new StringBuilder();
        feat.append(title).append("\n");
        feat.append(summary).append("\n");
        return feat.toString();
    }
}
