package common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public enum SearchCategories implements Serializable {
    NONE,
    SPELLS,
    ITEMS,
    BESTIARY,
    RACES,
    FEATS,
    BACKGROUNDS;

    private static final List<SearchCategories> searchCategory = List.of(SPELLS, ITEMS, BESTIARY,
            RACES, FEATS, BACKGROUNDS);

    private static final Map<SearchCategories, String> siteSearchCategories = Map.of(NONE, "", SPELLS, "spells",
            ITEMS, "items", BESTIARY, "bestiary", RACES, "race",
            FEATS, "feats", BACKGROUNDS, "backgrounds");

    public static Map<SearchCategories, String> getSiteSearchCategories() {
        return siteSearchCategories;
    }

    public static SearchCategories getSearchCategory(int index) {
        return searchCategory.get(index);
    }

    public boolean isEmpty() {
        return this == NONE;
    }

    @Override
    public String toString() {
        return siteSearchCategories.get(this);
    }
}
