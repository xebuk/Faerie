package dnd.values.characteristicsvalues;

import dnd.characteristics.RaceDnD;

import java.io.Serializable;
import java.util.Map;

public enum RacesDnD implements Serializable {
    DRAGONBORN,
    DWARF,
    ELF,
    GNOME,
    HALF_ELF,
    HALFLING,
    HALF_ORC,
    HUMAN,
    HUMAN_VARIANT,
    TIEFLING,

    HOMEBREW;

    private static final Map<String, RacesDnD> races = Map.ofEntries(Map.entry("Драконорожденный", DRAGONBORN),
            Map.entry("Дварф", DWARF), Map.entry("Эльф", ELF),
            Map.entry("Гном", GNOME), Map.entry("Полуэльф", HALF_ELF),
            Map.entry("Полурослик", HALFLING), Map.entry("Полуорк", HALF_ORC),
            Map.entry("Человек", HUMAN), Map.entry("Человек (Вариант)", HUMAN_VARIANT),
            Map.entry("Тифлинг", TIEFLING), Map.entry("Своя раса", HOMEBREW));

    private static final Map<RacesDnD, RaceDnD> racesAsClasses = Map.ofEntries(Map.entry(DRAGONBORN, new RaceDnD.DragonbornDnD()),
            Map.entry(DWARF, new RaceDnD.DwarfDnD()), Map.entry(ELF, new RaceDnD.ElfDnD()),
            Map.entry(GNOME, new RaceDnD.GnomeDnD()), Map.entry(HALF_ELF, new RaceDnD.HalfElfDnD()),
            Map.entry(HALFLING, new RaceDnD.HalflingDnD()), Map.entry(HALF_ORC, new RaceDnD.HalfOrcDnD()),
            Map.entry(HUMAN, new RaceDnD.HumanDnD()), Map.entry(HUMAN_VARIANT, new RaceDnD.HumanVariantDnD()),
            Map.entry(TIEFLING, new RaceDnD.TieflingDnD()));

    public static RaceDnD getRaceAsClass(RacesDnD race) {
        return racesAsClasses.get(race);
    }

    public static Map<String, RacesDnD> getRaces() {
        return races;
    }

    public static RacesDnD getRace(String race) {
        return races.get(race);
    }

}