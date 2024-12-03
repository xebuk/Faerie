package dnd.values.characteristicsvalues;

import dnd.characteristics.RaceDnD;

import java.io.Serializable;
import java.util.Map;

public enum RacesDnD implements Serializable {
    Dragonborn,
    Dwarf,
    Elf,
    Gnome,
    Half_Elf,
    Halfling,
    Half_Orc,
    Human,
    Human_Variant,
    Tiefling,

    Homebrew;

    private static final Map<String, RacesDnD> races = Map.ofEntries(Map.entry("Драконорожденный", Dragonborn),
            Map.entry("Дварф", Dwarf), Map.entry("Эльф", Elf),
            Map.entry("Гном", Gnome), Map.entry("Полуэльф", Half_Elf),
            Map.entry("Полурослик", Halfling), Map.entry("Полуорк", Half_Orc),
            Map.entry("Человек", Human), Map.entry("Человек (Вариант)", Human_Variant),
            Map.entry("Тифлинг", Tiefling), Map.entry("Своя раса", Homebrew));

    private static final Map<RacesDnD, RaceDnD> racesAsClasses = Map.ofEntries(Map.entry(Dragonborn, new RaceDnD.DragonbornDnD()),
            Map.entry(Dwarf, new RaceDnD.DwarfDnD()), Map.entry(Elf, new RaceDnD.ElfDnD()),
            Map.entry(Gnome, new RaceDnD.GnomeDnD()), Map.entry(Half_Elf, new RaceDnD.HalfElfDnD()),
            Map.entry(Halfling, new RaceDnD.HalflingDnD()), Map.entry(Half_Orc, new RaceDnD.HalfOrcDnD()),
            Map.entry(Human, new RaceDnD.HumanDnD()), Map.entry(Human_Variant, new RaceDnD.HumanVariantDnD()),
            Map.entry(Tiefling, new RaceDnD.TieflingDnD()));

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