package botexecution.commands;

import java.io.Serializable;

public enum CurrentProcess implements Serializable {
    FREE,

    SEARCHING_AN_ARTICLE,
    SEARCHING_AN_ARTICLE_SUCCESS,

    CREATING_A_CHARACTER,
    IN_GAME,

    CREATING_A_CHARACTER_DND,
    CAMPAIGN_MODE,
    REQUESTING_A_ROLL_DND,
    ROLLING_A_DICE_DND,
    EDITING_A_NOTE_DND,
    EDITING_A_LOOK_DND,
    EDITING_A_QUEST_DND,
    ADDING_AN_ASPECT_DND,
    EDITING_AN_ASPECT_DND,
    EDITING_A_PRESTIGE_JOB_DND;
}
