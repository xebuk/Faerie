package common;

import java.io.Serializable;
import java.util.Map;

public enum SearchCategories implements Serializable {
    NONE,
    SPELLS,
    ITEMS,
    BESTIARY,
    RACES,
    FEATS,
    BACKGROUNDS;

    private static final Map<SearchCategories, String> categories = Map.of(NONE, "", SPELLS, "spells",
            ITEMS, "items", BESTIARY, "bestiary", RACES, "race",
            FEATS, "feats", BACKGROUNDS, "backgrounds");

    public boolean isEmpty() {
        return this == NONE;
    }

    @Override
    public String toString() {
        return categories.get(this);
    }
}
