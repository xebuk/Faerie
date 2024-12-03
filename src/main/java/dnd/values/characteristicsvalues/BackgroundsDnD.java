package dnd.values.characteristicsvalues;

import dnd.characteristics.BackgroundDnD;

import java.io.Serializable;
import java.util.Map;

public enum BackgroundsDnD implements Serializable {
    Acolyte,
    Charlatan,
    Criminal,
    Entertainer,
    Folk_Hero,
    Guild_Artisan,
    Hermit,
    Noble,
    Outlander,
    Pirate,
    Sage,
    Sailor,
    Soldier,
    Urchin,

    Homebrew;

    private static final Map<String, BackgroundsDnD> backgrounds = Map.ofEntries(Map.entry("Прислужник", Acolyte),
            Map.entry("Шарлатан", Charlatan), Map.entry("Преступник", Criminal),
            Map.entry("Артист", Entertainer), Map.entry("Народный герой", Folk_Hero),
            Map.entry("Гильдейский ремесленник", Guild_Artisan), Map.entry("Отшельник", Hermit),
            Map.entry("Благородный", Noble), Map.entry("Чужеземец", Outlander),
            Map.entry("Пират", Pirate), Map.entry("Мудрец", Sage),
            Map.entry("Моряк", Sailor), Map.entry("Солдат", Soldier),
            Map.entry("Беспризорник", Urchin), Map.entry("Своя предыстория", Homebrew));

    private static final Map<BackgroundsDnD, BackgroundDnD> backgroundsAsClasses = Map.ofEntries(Map.entry(Acolyte, new BackgroundDnD.AcolyteDnD()),
            Map.entry(Charlatan, new BackgroundDnD.CharlatanDnD()), Map.entry(Criminal, new BackgroundDnD.CriminalDnD()),
            Map.entry(Entertainer, new BackgroundDnD.EntertainerDnD()), Map.entry(Folk_Hero, new BackgroundDnD.FolkHeroDnD()),
            Map.entry(Guild_Artisan, new BackgroundDnD.GuildArtisanDnD()), Map.entry(Hermit, new BackgroundDnD.HermitDnD()),
            Map.entry(Noble, new BackgroundDnD.NobleDnD()), Map.entry(Outlander, new BackgroundDnD.OutlanderDnD()),
            Map.entry(Pirate, new BackgroundDnD.PirateDnD()), Map.entry(Sage, new BackgroundDnD.SageDnD()),
            Map.entry(Sailor, new BackgroundDnD.SailorDnD()), Map.entry(Soldier, new BackgroundDnD.SoldierDnD()),
            Map.entry(Urchin, new BackgroundDnD.UrchinDnD()), Map.entry(Homebrew, new BackgroundDnD.HomebrewBackgroundDnD()));

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
