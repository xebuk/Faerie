package dnd.values.equipmentids;

import java.io.Serializable;

public enum InstrumentsDnD implements Serializable {
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

    ARTISAN_TOOLS,
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
    JEWELER_TOOLS;

    private static final InstrumentsDnD[] valuesArray = values();
    public InstrumentsDnD get(int index) {
        return valuesArray[index];
    }
}
