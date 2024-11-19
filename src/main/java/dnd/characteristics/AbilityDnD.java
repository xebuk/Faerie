package dnd.characteristics;

import java.io.Serializable;
import java.util.HashSet;

public class AbilityDnD implements Serializable {
    public String title;
    public int level;
    public HashSet<JobDnD> jobs = new HashSet<>();

    public String summary;

    public AbilityDnD() {
        this.title = "Своя способность";
        this.level = 1;

        this.summary = "Описание способности";
    }
}
