package dnd.values.aspectvalues;

import dnd.equipment.WeaponDnD;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum ItemsIdsDnD implements Serializable {
    //ITEMS_SECTION
    CUSTOM_ITEM,

    ABACUS,
    VIAL_OF_ACID,
    FLASK_OF_ALCHEMIST_FIRE,
    ARROW,
    BLOWGUN_NEEDLE,
    CROSSBOW_BOLT,
    SLING_BULLET,
    VIAL_OF_ANTITOXIN,
    CRYSTAL,
    ORB,
    ROD,
    STAFF,
    WAND,
    BACKPACK,
    BALL_BEARING,
    BARREL,
    BASKET,
    BEDROLL,
    BELL,
    BLANKET,
    BLOCK_AND_TACKLE,
    BOOK,
    GLASS_BOTTLE,
    BUCKET,
    CALTROP,
    CANDLE,
    CASE_FOR_CROSSBOW_BOLTS,
    CASE_FOR_MAPS_AND_SCROLLS,
    CHAIN,
    CHALK,
    CHEST,
    CLIMBER_KIT,
    COMMON_CLOTHES,
    COSTUME_CLOTHES,
    FINE_CLOTHES,
    TRAVELER_CLOTHES,
    COMPONENT_POUCH,
    CROWBAR,
    SPRIG_OF_MISTLETOE,
    TOTEM,
    WOODEN_STAFF,
    YEW_WAND,
    FISHING_TACKLE,
    FLASK_OR_TANKARD,
    GRAPPLING_HOOK,
    HAMMER,
    SLEDGEHAMMER,
    HEALER_KIT,
    AMULET,
    EMBLEM,
    RELIQUARY,
    FLASK_OF_HOLY_WATER,
    HOURGLASS,
    HUNTING_TRAP,
    INK,
    INK_PEN,
    JUG_OR_PITCHER,
    LADDER,
    LAMP,
    BULLSEYE_LANTERN,
    HOODED_LANTERN,
    LOCK,
    MAGNIFYING_GLASS,
    MANACLES,
    MESS_KIT,
    STEEL_MIRROR,
    FLASK_OF_OIL,
    PAPER,
    PARCHMENT,
    VIAL_OF_PERFUME,
    MINERS_PICK,
    PITON,
    VIAL_OF_BASIC_POISON,
    POLE,
    IRON_POT,
    POTION_OF_HEALING,
    POUCH,
    QUIVER,
    PORTABLE_RAM,
    RATIONS,
    ROBES,
    HEMPEN_ROPE,
    SILK_ROPE,
    SACK,
    MERCHANT_SCALE,
    SEALING_WAX,
    SHOVEL,
    SIGNAL_WHISTLE,
    SIGNET_RING,
    SOAP,
    SPELL_BOOK,
    IRON_SPIKE,
    SPYGLASS,
    TWO_PERSON_TENT,
    TINDERBOX,
    TORCH,
    VIAL,
    WATERSKIN,
    WHETSTONE,

    //WEAPON_SECTION (WeaponsDnD)
    //TYPES
    MELEE,
    RANGED,

    SIMPLE,
    MARTIAL,

    CUSTOM_WEAPON,

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
    NET,

    //ARMOR_SECTION (ArmorsDnD)
    CUSTOM_ARMOR,

    LIGHT,
    MEDIUM,
    HEAVY,
    SHIELD,

    BREASTPLATE,
    CHAIN_MAIL,
    CHAIN_SHIRT,
    HALF_PLATE,
    HIDE_ARMOR,
    LEATHER_ARMOR,
    PADDED_ARMOR,
    PLATE_ARMOR,
    RING_MAIL,
    SCALE_MAIL,
    SPIKED_ARMOR,
    SPLINT_ARMOR,
    STUDDED_LEATHER_ARMOR,

    //INSTRUMENT_SECTION (InstrumentsDnD)
    CUSTOM_INSTRUMENT,

    THIEF_TOOLS,
    NAVIGATOR_TOOLS,
    POISONER_KIT,
    DISGUISE_KIT,
    FORGERY_KIT,
    HERBALISM_KIT,

    GAMING_SET,
    DRAGONCHESS_SET,
    PLAYING_CARDS_SET,
    DICE_SET,
    THREE_DRAGON_ANTE_SET,

    MUSICAL_INSTRUMENTS,
    DRUM,
    VIOL,
    BAGPIPES,
    LYRE,
    LYTE,
    HORN,
    PAN_FLUTE,
    FLUTE,
    DULCIMER,
    SHAWM,

    ALCHEMIST_SUPPLIES,
    POTTER_TOOLS,
    CALLIGRAPHER_SUPPLIES,
    MASON_TOOLS,
    CARTOGRAPHER_TOOLS,
    LEATHERWORKER_TOOLS,
    SMITH_TOOLS,
    BREWER_SUPPLIES,
    CARPENTER_TOOLS,
    COOK_UTENSILS,
    WOODCARVER_TOOLS,
    TINKER_TOOLS,
    COBBLER_TOOLS,
    GLASSBLOWER_TOOLS,
    WEAVER_TOOLS,
    PAINTER_TOOLS,
    JEWELER_TOOLS,

    VEHICLE,

    //KIT_SECTION (KitsDnD)
    CUSTOM_KIT,

    BURGLAR_PACK,
    DIPLOMAT_PACK,
    DUNGEONEER_PACK,
    ENTERTAINER_KIT,
    EXPLORER_PACK,
    MONSTER_HUNTER_PACK,
    PRIEST_PACK,
    SCHOLAR_PACK;

    //Часть для обычных предметов

    //Часть для оружия
    private static final Map<String, ItemsIdsDnD> types = Map.of("Простое", SIMPLE, "Боевое", MARTIAL);
    private static final Map<ItemsIdsDnD, Set<ItemsIdsDnD>> typesToWeapons = Map.of(SIMPLE, Set.of(QUARTERSTAFF, MACE,
            CLUB, DAGGER, SPEAR, LIGHT_HAMMER, JAVELIN, GREATCLUB, HANDAXE, SICKLE, LIGHT_CROSSBOW, DART, SHORTBOW, SLING),
            MARTIAL, Set.of(HALBERG, WAR_PICK, WARHAMMER, BATTLEAXE, GLAIVE, GREATSWORD, LANCE, LONGSWORD, WHIP,
                    SHORTSWORD, MAUL, MORNINGSTAR, PIKE, RAPIER, GREATAXE, SCIMITAR, TRIDENT,
                    FLAIL, HAND_CROSSBOW, HEAVY_CROSSBOW, LONGBOW, BLOWGUN, NET));

    private static final Map<String, ItemsIdsDnD> ranges = Map.of("Ближнего боя", MELEE, "Дальнего боя", RANGED);
    private static final Map<ItemsIdsDnD, Set<ItemsIdsDnD>> rangesToWeapons = Map.of(MELEE, Set.of(QUARTERSTAFF, MACE,
            CLUB, DAGGER, SPEAR, LIGHT_HAMMER, JAVELIN, GREATCLUB, HANDAXE, SICKLE,HALBERG, WAR_PICK, WARHAMMER,
            BATTLEAXE, GLAIVE, GREATSWORD, LANCE, LONGSWORD, WHIP, SHORTSWORD, MAUL, MORNINGSTAR, PIKE, RAPIER,
            GREATAXE, SCIMITAR, TRIDENT, FLAIL),
            RANGED, Set.of(LIGHT_CROSSBOW, DART, SHORTBOW, SLING, HAND_CROSSBOW, HEAVY_CROSSBOW, LONGBOW, BLOWGUN, NET));

    private static final Map<ItemsIdsDnD, WeaponDnD> weaponsAsClasses = Map.ofEntries(
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

    public static Map<String, ItemsIdsDnD> getTypes() {
        return types;
    }

    public static ItemsIdsDnD getType(String type) {
        return types.get(type);
    }

    public static Map<String, ItemsIdsDnD> getRanges() {
        return ranges;
    }

    public static ItemsIdsDnD getRange(String range) {
        return ranges.get(range);
    }

    public static WeaponDnD getWeaponAsClass(ItemsIdsDnD weapon) {
        return weaponsAsClasses.get(weapon);
    }

    public static ItemsIdsDnD whatType(ItemsIdsDnD weapon) {
        boolean isSimple = typesToWeapons.get(SIMPLE).contains(weapon);
        return (isSimple ? SIMPLE : MARTIAL);
    }

    public static ItemsIdsDnD whatRange(ItemsIdsDnD weapon) {
        boolean isMelee = rangesToWeapons.get(MELEE).contains(weapon);
        return (isMelee ? MELEE : RANGED);
    }

    public static boolean isMastered(HashSet<ItemsIdsDnD> mastered, ItemsIdsDnD requested) {
        return mastered.contains(requested)
                || mastered.contains(weaponsAsClasses.get(requested).type)
                || mastered.contains(weaponsAsClasses.get(requested).range);
    }

    //Часть для брони
    private static final Map<String, ItemsIdsDnD> armorTypes = Map.of("Легкая", LIGHT,
            "Средняя", MEDIUM, "Тяжелая", HEAVY, "Щит", SHIELD);

    public static Map<String, ItemsIdsDnD> getArmorTypes() {
        return armorTypes;
    }

    public static ItemsIdsDnD getArmorType(String armorType) {
        return armorTypes.get(armorType);
    }

    //Часть для инструментов
    private static Map<ItemsIdsDnD, String> parserIds = Map.ofEntries(
            Map.entry(THIEF_TOOLS, "Воровские инструменты [XGE]"), Map.entry(GAMING_SET, "Игровой набор [XGE]"),
            Map.entry(ALCHEMIST_SUPPLIES, "Инструменты алхимика [XGE]"), Map.entry(POTTER_TOOLS, "Инструменты гончара [XGE]"),
            Map.entry(CALLIGRAPHER_SUPPLIES, "Инструменты каллиграфа [XGE]"), Map.entry(MASON_TOOLS, "Инструменты каменщика [XGE]"),
            Map.entry(CARTOGRAPHER_TOOLS, "Инструменты картографа [XGE]"), Map.entry(LEATHERWORKER_TOOLS, "Инструменты Кожевника [XGE]"),
            Map.entry(SMITH_TOOLS, "Инструменты кузнеца [XGE]"), Map.entry(NAVIGATOR_TOOLS, "Инструменты навигатора [XGE]"),
            Map.entry(POISONER_KIT, "Инструменты отравителя [XGE]"), Map.entry(BREWER_SUPPLIES, "Инструменты пивовара [XGE]"),
            Map.entry(CARPENTER_TOOLS, "Инструменты плотника [XGE]"), Map.entry(COOK_UTENSILS, "Инструменты Повара [XGE]"),
            Map.entry(WOODCARVER_TOOLS, "Инструменты резчика по дереву [XGE]"), Map.entry(TINKER_TOOLS, "Инструменты ремонтника [XGE]"),
            Map.entry(COBBLER_TOOLS, "Инструменты сапожника [XGE]"), Map.entry(GLASSBLOWER_TOOLS, "Инструменты стеклодува [XGE]"),
            Map.entry(WEAVER_TOOLS, "Инструменты ткача [XGE]"), Map.entry(PAINTER_TOOLS, "Инструменты художника [XGE]"),
            Map.entry(JEWELER_TOOLS, "Инструменты ювелира [XGE]"), Map.entry(MUSICAL_INSTRUMENTS, "Музыкальный инструмент [XGE]"),
            Map.entry(DISGUISE_KIT, "Набор для грима [XGE]"), Map.entry(HERBALISM_KIT, "Набор травника [XGE]"),
            Map.entry(FORGERY_KIT, "Набор для фальсификации [XGE]"), Map.entry(VEHICLE, "Наземный и водный транспорт [XGE]"));

    public static String getParserId(ItemsIdsDnD instrument) {
        return parserIds.get(instrument);
    }

    //Часть для наборов

}
