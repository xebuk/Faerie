package botexecution.commands;

import java.util.Map;

public enum KeyboardValues {
    COMMON,
    GAME,
    DND,
    DM,
    CAMPAIGN;

    private static final Map<KeyboardValues, String> categories = Map.of(COMMON, CoreMessages.HELP_MESSAGE_COMMON,
            GAME, CoreMessages.HELP_MESSAGE_GAME, DND, CoreMessages.HELP_MESSAGE_DND,
            DM, CoreMessages.HELP_MESSAGE_DM, CAMPAIGN, CoreMessages.HELP_MESSAGE_CAMPAIGN);

    @Override
    public String toString() {
        return categories.get(this);
    }
}
