package botexecution;

import common.Constants;
import common.DiceNew;
import common.SearchCategories;
import common.UserDataHandler;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.HashMap;
import java.util.function.Consumer;

public class CommonMethodsHandler {
    private TextHandler walkieTalkie;

    public final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    public CommonMethodsHandler(TextHandler walkieTalkie) {
        this.walkieTalkie = walkieTalkie;

        Consumer<ChatSession> Spell = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_SPELLS, null, false);
            cs.sectionId = SearchCategories.SPELLS;
        };
        Consumer<ChatSession> Item = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_ITEMS, null, false);
            cs.sectionId = SearchCategories.ITEMS;
        };
        Consumer<ChatSession> Bestiary = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_BESTIARY, null, false);
            cs.sectionId = SearchCategories.BESTIARY;
        };
        Consumer<ChatSession> Race = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RACES, null, false);
            cs.sectionId = SearchCategories.RACES;
        };
        Consumer<ChatSession> Class = cs -> walkieTalkie.patternExecute(cs, Constants.CLASSES_LIST, null, true);
        Consumer<ChatSession> Feat = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_FEATS, null, false);
            cs.sectionId = SearchCategories.FEATS;
        };
        Consumer<ChatSession> Background = cs -> {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_BACKGROUNDS, null, false);
            cs.sectionId = SearchCategories.BACKGROUNDS;
        };

        Consumer<ChatSession> RollD20 = cs -> walkieTalkie.patternExecute(cs, DiceNew.D20(), null, false);
        Consumer<ChatSession> Roll2D20 = cs -> walkieTalkie.patternExecute(cs, Constants.ROLL_MESSAGE_ADVANTAGE, KeyboardFactory.rollAdvantageBoard(), false);
        Consumer<ChatSession> RollAdvantage = cs -> walkieTalkie.patternExecute(cs, DiceNew.D20TwoTimes(true), null, false);
        Consumer<ChatSession> RollDisadvantage = cs -> walkieTalkie.patternExecute(cs, DiceNew.D20TwoTimes(false), null, false);
        Consumer<ChatSession> RollD8 = cs -> walkieTalkie.patternExecute(cs, DiceNew.D8(), null, false);
        Consumer<ChatSession> RollD6 = cs -> walkieTalkie.patternExecute(cs, DiceNew.D6(), null, false);
        Consumer<ChatSession> Roll4D6 = cs -> walkieTalkie.patternExecute(cs, DiceNew.D6FourTimes(), null, false);
        Consumer<ChatSession> RollD4 = cs -> walkieTalkie.patternExecute(cs, DiceNew.D4(), null, false);
        Consumer<ChatSession> CustomDice = walkieTalkie::rollCustom;

        methodsAllocator.put(Constants.SPELLS, Spell);
        methodsAllocator.put(Constants.ITEMS, Item);
        methodsAllocator.put(Constants.BESTIARY, Bestiary);
        methodsAllocator.put(Constants.RACES, Race);
        methodsAllocator.put(Constants.CLASSES, Class);
        methodsAllocator.put(Constants.FEATS, Feat);
        methodsAllocator.put(Constants.BACKGROUNDS, Background);
        methodsAllocator.put(Constants.ROLL_D20, RollD20);
        methodsAllocator.put(Constants.ROLL_2D20, Roll2D20);
        methodsAllocator.put(Constants.ADVANTAGE, RollAdvantage);
        methodsAllocator.put(Constants.DISADVANTAGE, RollDisadvantage);
        methodsAllocator.put(Constants.ROLL_D8, RollD8);
        methodsAllocator.put(Constants.ROLL_D6, RollD6);
        methodsAllocator.put(Constants.ROLL_4D6, Roll4D6);
        methodsAllocator.put(Constants.ROLL_D4, RollD4);
        methodsAllocator.put(Constants.CUSTOM_DICE, CustomDice);
    }

    public void startNewUser(MessageContext ctx) {
        ChatSession newUser = new ChatSession(ctx);
        walkieTalkie.patternExecute(newUser, Constants.START_MESSAGE, KeyboardFactory.commonSetOfCommandsBoard(), false);

        UserDataHandler.createChatFile(ctx.chatId().toString());
        UserDataHandler.saveSession(newUser);
    }
}
