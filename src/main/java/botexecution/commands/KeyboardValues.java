package botexecution.commands;

import java.util.Map;

public enum KeyboardValues {
    COMMON,
    GAME,

    DND,
    PLAYER,
    DM,
    CAMPAIGN,
    ITEMS,
    STAT,
    QUEST;

    private static final Map<KeyboardValues, String> categories = Map.of(COMMON, CoreMessages.HELP_MESSAGE_COMMON,
            GAME, CoreMessages.HELP_MESSAGE_GAME, DND, CoreMessages.HELP_MESSAGE_DND,
            PLAYER, CoreMessages.HELP_MESSAGE_PLAYER, DM, CoreMessages.HELP_MESSAGE_DM,
            CAMPAIGN, CoreMessages.HELP_MESSAGE_CAMPAIGN, ITEMS, CoreMessages.HELP_MESSAGE_ITEMS,
            STAT, CoreMessages.HELP_MESSAGE_STAT, QUEST, CoreMessages.HELP_MESSAGE_QUEST);

    @Override
    public String toString() {
        return categories.get(this);
    }
}
