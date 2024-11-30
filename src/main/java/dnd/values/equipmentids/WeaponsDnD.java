package dnd.values.equipmentids;

import dnd.equipment.WeaponDnD;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum WeaponsDnD implements Serializable {
    MELEE,
    RANGED,

    SIMPLE,
    MARTIAL,

    CUSTOM,

    //SIMPLE_MELEE,
    QUARTERSTAFF,
    MACE,
    CLUB,
    DAGGER,
    SPEAR,
    LIGHT_HAMMER,
    JAVELIN,
    GREATCLUB,
    HANDAXE,
    SICKLE,

    //SIMPLE_RANGED,
    LIGHT_CROSSBOW,
    DART,
    SHORTBOW,
    SLING,

    //MARTIAL_MELEE
    HALBERG,
    WAR_PICK,
    WARHAMMER,
    BATTLEAXE,
    GLAIVE,
    GREATSWORD,
    LANCE,
    LONGSWORD,
    WHIP,
    SHORTSWORD,
    MAUL,
    MORNINGSTAR,
    PIKE,
    RAPIER,
    GREATAXE,
    SCIMITAR,
    TRIDENT,
    FLAIL,

    //MARTIAL_RANGED,
    HAND_CROSSBOW,
    HEAVY_CROSSBOW,
    LONGBOW,
    BLOWGUN,
    NET;


    private static final Map<String, WeaponsDnD> types = Map.of("Простое", SIMPLE, "Боевое", MARTIAL);
    private static final Map<WeaponsDnD, Set<WeaponsDnD>> typesToWeapons = Map.of(SIMPLE, Set.of(QUARTERSTAFF, MACE,
            CLUB, DAGGER, SPEAR, LIGHT_HAMMER, JAVELIN, GREATCLUB, HANDAXE, SICKLE, LIGHT_CROSSBOW, DART, SHORTBOW, SLING),
            MARTIAL, Set.of(HALBERG, WAR_PICK, WARHAMMER, BATTLEAXE, GLAIVE, GREATSWORD, LANCE, LONGSWORD, WHIP,
                    SHORTSWORD, MAUL, MORNINGSTAR, PIKE, RAPIER, GREATAXE, SCIMITAR, TRIDENT,
                    FLAIL, HAND_CROSSBOW, HEAVY_CROSSBOW, LONGBOW, BLOWGUN, NET));

    private static final Map<String, WeaponsDnD> ranges = Map.of("Ближнего боя", MELEE, "Дальнего боя", RANGED);
    private static final Map<WeaponsDnD, Set<WeaponsDnD>> rangesToWeapons = Map.of(MELEE, Set.of(QUARTERSTAFF, MACE,
            CLUB, DAGGER, SPEAR, LIGHT_HAMMER, JAVELIN, GREATCLUB, HANDAXE, SICKLE,HALBERG, WAR_PICK, WARHAMMER,
            BATTLEAXE, GLAIVE, GREATSWORD, LANCE, LONGSWORD, WHIP, SHORTSWORD, MAUL, MORNINGSTAR, PIKE, RAPIER,
            GREATAXE, SCIMITAR, TRIDENT, FLAIL),
            RANGED, Set.of(LIGHT_CROSSBOW, DART, SHORTBOW, SLING, HAND_CROSSBOW, HEAVY_CROSSBOW, LONGBOW, BLOWGUN, NET));

    private static final Map<WeaponsDnD, WeaponDnD> weaponsAsClasses = Map.ofEntries(
            Map.entry(QUARTERSTAFF, new WeaponDnD.QuarterstaffDnD()), Map.entry(MACE, new WeaponDnD.MaceDnD()),
            Map.entry(CLUB, new WeaponDnD.ClubDnD()), Map.entry(DAGGER, new WeaponDnD.DaggerDnD()),
            Map.entry(SPEAR, new WeaponDnD.SpearDnD()), Map.entry(LIGHT_HAMMER, new WeaponDnD.LightHammerDnD()),
            Map.entry(JAVELIN, new WeaponDnD.JavelinDnD()), Map.entry(GREATCLUB, new WeaponDnD.GreatclubDnD()),
            Map.entry(HANDAXE, new WeaponDnD.HandaxeDnD()), Map.entry(SICKLE, new WeaponDnD.SickleDnD()),
            Map.entry(LIGHT_CROSSBOW, new WeaponDnD.LightCrossbowDnD()), Map.entry(DART, new WeaponDnD.DartDnD()),
            Map.entry(SHORTBOW, new WeaponDnD.ShortbowDnD()), Map.entry(SLING, new WeaponDnD.SlingDnD()),
            Map.entry(HALBERG, new WeaponDnD.HalbergDnD()), Map.entry(WAR_PICK, new WeaponDnD.WarPickDnD()),
            Map.entry(WARHAMMER, new WeaponDnD.WarhammerDnD()), Map.entry(BATTLEAXE, new WeaponDnD.BattleaxeDnD()),
            Map.entry(GLAIVE, new WeaponDnD.GlaiveDnD()), Map.entry(GREATSWORD, new WeaponDnD.GreatswordDnD()),
            Map.entry(LANCE, new WeaponDnD.LanceDnD()), Map.entry(LONGSWORD, new WeaponDnD.LongswordDnD()),
            Map.entry(WHIP, new WeaponDnD.WhipDnD()), Map.entry(SHORTSWORD, new WeaponDnD.ShortswordDnD()),
            Map.entry(MAUL, new WeaponDnD.MaulDnD()), Map.entry(MORNINGSTAR, new WeaponDnD.MorningstarDnD()),
            Map.entry(PIKE, new WeaponDnD.PikeDnD()), Map.entry(RAPIER, new WeaponDnD.RapierDnD()),
            Map.entry(GREATAXE, new WeaponDnD.GreataxeDnD()), Map.entry(SCIMITAR, new WeaponDnD.ScimitarDnD()),
            Map.entry(TRIDENT, new WeaponDnD.TridentDnD()), Map.entry(FLAIL, new WeaponDnD.FlailDnD()),
            Map.entry(HAND_CROSSBOW, new WeaponDnD.HandCrossbowDnD()),
            Map.entry(HEAVY_CROSSBOW, new WeaponDnD.HeavyCrossbowDnD()), Map.entry(LONGBOW, new WeaponDnD.LongbowDnD()),
            Map.entry(BLOWGUN, new WeaponDnD.BlowgunDnD()), Map.entry(NET, new WeaponDnD.NetDnD()));

    public static Map<String, WeaponsDnD> getTypes() {
        return types;
    }

    public static WeaponsDnD getType(String type) {
        return types.get(type);
    }

    public static Map<String, WeaponsDnD> getRanges() {
        return ranges;
    }

    public static WeaponsDnD getRange(String range) {
        return ranges.get(range);
    }

    public static WeaponDnD getWeaponAsClass(WeaponsDnD weapon) {
        return weaponsAsClasses.get(weapon);
    }

    public static boolean isMastered(HashSet<WeaponsDnD> mastered, WeaponsDnD requested) {
        return mastered.contains(requested)
                || mastered.contains(weaponsAsClasses.get(requested).type)
                || mastered.contains(weaponsAsClasses.get(requested).range);
    }
}
