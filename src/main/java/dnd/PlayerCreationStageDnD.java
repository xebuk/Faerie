package dnd;

public enum PlayerCreationStageDnD {
    NAME,
    RACE,
    RACE_PERSONALITY,
    RACE_IDEAL,
    RACE_BOND,
    RACE_FLAW,
    JOB,
    BACKGROUND,
    BACKGROUND_PERSONALITY,
    BACKGROUND_IDEAL,
    BACKGROUND_BOND,
    BACKGROUND_FLAW,
    ALIGNMENT,
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

    private static final PlayerCreationStageDnD[] valuesArray = values();
    public PlayerCreationStageDnD next() {
        return valuesArray[(this.ordinal() + 1) % valuesArray.length];
    }
}
