package dnd.values;

public enum PlayerDnDCreationStage {
    NAME,
    RACE,
    RACE_PERSONALITY,
    RACE_IDEAL,
    RACE_BOND,
    RACE_FLAW,
    JOB,
    BACKGROUND,
    BACKGROUND_SPECIAL_INFO,
    BACKGROUND_PERSONALITY,
    BACKGROUND_IDEAL,
    BACKGROUND_BOND,
    BACKGROUND_FLAW,
    ALIGNMENT,
    LANGUAGE,
    STATS1,
    STATS2,
    STATS3,
    STATS4,
    STATS5,
    STATS6,
    SKILLS,
    AGE,
    HEIGHT,
    WEIGHT,
    EYES,
    SKIN,
    HAIR;

    private static final PlayerDnDCreationStage[] valuesArray = values();
    public PlayerDnDCreationStage next() {
        return valuesArray[(this.ordinal() + 1) % valuesArray.length];
    }
    public PlayerDnDCreationStage previous() {
        return valuesArray[(this.ordinal() - 1) % valuesArray.length];
    }
}
