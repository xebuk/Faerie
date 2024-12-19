package dnd.values.characteristicsvalues;

import dnd.characteristics.BackgroundDnD;

import java.io.Serializable;
import java.util.Map;

public enum BackgroundsDnD implements Serializable {
    ACOLYTE,
    CHARLATAN,
    CRIMINAL,
    ENTERTAINER,
    FOLK_HERO,
    GUILD_ARTISAN,
    HERMIT,
    NOBLE,
    OUTLANDER,
    PIRATE,
    SAGE,
    SAILOR,
    SOLDIER,
    URCHIN,

    HOMEBREW;

    private static final Map<String, BackgroundsDnD> backgrounds = Map.ofEntries(Map.entry("Прислужник", ACOLYTE),
            Map.entry("Шарлатан", CHARLATAN), Map.entry("Преступник", CRIMINAL),
            Map.entry("Артист", ENTERTAINER), Map.entry("Народный герой", FOLK_HERO),
            Map.entry("Гильдейский ремесленник", GUILD_ARTISAN), Map.entry("Отшельник", HERMIT),
            Map.entry("Благородный", NOBLE), Map.entry("Чужеземец", OUTLANDER),
            Map.entry("Пират", PIRATE), Map.entry("Мудрец", SAGE),
            Map.entry("Моряк", SAILOR), Map.entry("Солдат", SOLDIER),
            Map.entry("Беспризорник", URCHIN), Map.entry("Своя предыстория", HOMEBREW));

    private static final Map<BackgroundsDnD, BackgroundDnD> backgroundsAsClasses = Map.ofEntries(Map.entry(ACOLYTE, new BackgroundDnD.AcolyteDnD()),
            Map.entry(CHARLATAN, new BackgroundDnD.CharlatanDnD()), Map.entry(CRIMINAL, new BackgroundDnD.CriminalDnD()),
            Map.entry(ENTERTAINER, new BackgroundDnD.EntertainerDnD()), Map.entry(FOLK_HERO, new BackgroundDnD.FolkHeroDnD()),
            Map.entry(GUILD_ARTISAN, new BackgroundDnD.GuildArtisanDnD()), Map.entry(HERMIT, new BackgroundDnD.HermitDnD()),
            Map.entry(NOBLE, new BackgroundDnD.NobleDnD()), Map.entry(OUTLANDER, new BackgroundDnD.OutlanderDnD()),
            Map.entry(PIRATE, new BackgroundDnD.PirateDnD()), Map.entry(SAGE, new BackgroundDnD.SageDnD()),
            Map.entry(SAILOR, new BackgroundDnD.SailorDnD()), Map.entry(SOLDIER, new BackgroundDnD.SoldierDnD()),
            Map.entry(URCHIN, new BackgroundDnD.UrchinDnD()), Map.entry(HOMEBREW, new BackgroundDnD.HomebrewBackgroundDnD()));

    public static BackgroundDnD getBackgroundAsClass(BackgroundsDnD background) {
        return backgroundsAsClasses.get(background);
    }

    public static Map<String, BackgroundsDnD> getBackgrounds() {
        return backgrounds;
    }

    public static BackgroundsDnD getBackground(String background) {
        return backgrounds.get(background);
    }
}
