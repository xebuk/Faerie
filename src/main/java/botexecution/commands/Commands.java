package botexecution.commands;

import java.util.Map;

public enum Commands {
    HELP,

    MOFU,
    SEARCH,
    ROLL,
    CREDITS,

    START_A_GAME,
    PAUSE_A_GAME,
    END_A_GAME,
    CREATE_A_CHARACTER,

    CREATE_A_PLAYER,
    HALT_CREATION,
    CREATE_A_CAMPAIGN,
    END_A_CAMPAIGN,

    SHOW_CAMPAIGNS,
    SET_CAMPAIGN,

    SET_CAMPAIGN_NAME,
    SET_PASSWORD,
    SET_MULTICLASS_LIMIT,
    SHOW_PLAYERS,
    SHOW_PLAYER_PROFILE,
    REQUEST_A_ROLL,
    ADD_AN_ITEM;
}
