package botexecution.handlers;

import botexecution.commands.CoreMessages;
import botexecution.commands.KeyboardValues;
import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.*;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommonMethodsHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final DiceHandler diceHoarder;

    public final HashMap<String, String> commandsSummariesAllocator = new HashMap<>();
    public final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    public CommonMethodsHandler(DataHandler knowledge, TextHandler walkieTalkie, DiceHandler diceHoarder) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.diceHoarder = diceHoarder;

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

        Consumer<ChatSession> RollD20 = diceHoarder::D20;
        Consumer<ChatSession> Roll2D20 = cs -> walkieTalkie.patternExecute(cs, Constants.ROLL_MESSAGE_ADVANTAGE, KeyboardFactory.rollAdvantageBoard(), false);
        Consumer<ChatSession> RollAdvantage = cs -> diceHoarder.D20TwoTimes(cs,true);
        Consumer<ChatSession> RollDisadvantage = cs -> diceHoarder.D20TwoTimes(cs,false);
        Consumer<ChatSession> RollD8 = diceHoarder::D8;
        Consumer<ChatSession> RollD6 = diceHoarder::D6;
        Consumer<ChatSession> Roll4D6 = cs -> walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(), null, false);
        Consumer<ChatSession> RollD4 = diceHoarder::D4;
        Consumer<ChatSession> CustomDice = this::rollCustom;

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

        commandsSummariesAllocator.put("start", CoreMessages.COMMAND_MESSAGE_START);
        commandsSummariesAllocator.put("help", CoreMessages.COMMAND_MESSAGE_HELP);
        commandsSummariesAllocator.put("credits", CoreMessages.COMMAND_MESSAGE_CREDITS);

        commandsSummariesAllocator.put("mofu", CoreMessages.COMMAND_MESSAGE_MOFU);
        commandsSummariesAllocator.put("search", CoreMessages.COMMAND_MESSAGE_SEARCH);
        commandsSummariesAllocator.put("roll", CoreMessages.COMMAND_MESSAGE_ROLL);

        commandsSummariesAllocator.put("startagame", CoreMessages.COMMAND_MESSAGE_START_A_GAME);
        commandsSummariesAllocator.put("pauseagame", CoreMessages.COMMAND_MESSAGE_PAUSE_A_GAME);
        commandsSummariesAllocator.put("endagame", CoreMessages.COMMAND_MESSAGE_END_A_GAME);
        commandsSummariesAllocator.put("createacharacter", CoreMessages.COMMAND_MESSAGE_CREATE_A_CHARACTER);

        commandsSummariesAllocator.put("showcampaigns", CoreMessages.COMMAND_MESSAGE_SHOW_CAMPAIGNS);
        commandsSummariesAllocator.put("showcurcampaign", CoreMessages.COMMAND_MESSAGE_SHOW_CURRENT_CAMPAIGN);
        commandsSummariesAllocator.put("setcampaign", CoreMessages.COMMAND_MESSAGE_SET_CAMPAIGN);
        commandsSummariesAllocator.put("createaplayer", CoreMessages.COMMAND_MESSAGE_CREATE_A_PLAYER);
        commandsSummariesAllocator.put("haltcreation", CoreMessages.COMMAND_MESSAGE_HALT_CREATION);
        commandsSummariesAllocator.put("createacampaign", CoreMessages.COMMAND_MESSAGE_CREATE_A_CAMPAIGN);
        commandsSummariesAllocator.put("endacampaign", CoreMessages.COMMAND_MESSAGE_END_A_CAMPAIGN);

        commandsSummariesAllocator.put("showplayerprofile", CoreMessages.COMMAND_MESSAGE_SHOW_PLAYER_PROFILE);
        commandsSummariesAllocator.put("bringanitem", CoreMessages.COMMAND_MESSAGE_BRING_AN_ITEM);
        commandsSummariesAllocator.put("equipanitem", CoreMessages.COMMAND_MESSAGE_EQUIP_AN_ITEM);
        commandsSummariesAllocator.put("unequipanitem", CoreMessages.COMMAND_MESSAGE_UNEQUIP_AN_ITEM);
        commandsSummariesAllocator.put("askforaroll", CoreMessages.COMMAND_MESSAGE_ASK_FOR_A_ROLL);
        commandsSummariesAllocator.put("showaquestboard", CoreMessages.COMMAND_MESSAGE_SHOW_QUEST_BOARD);
        commandsSummariesAllocator.put("showaquest", CoreMessages.COMMAND_MESSAGE_SHOW_CURRENT_QUEST);
        commandsSummariesAllocator.put("showmynotes", CoreMessages.COMMAND_MESSAGE_SHOW_NOTES);
        commandsSummariesAllocator.put("addanote", CoreMessages.COMMAND_MESSAGE_ADD_A_NOTE);
        commandsSummariesAllocator.put("editanote", CoreMessages.COMMAND_MESSAGE_EDIT_A_NOTE);

        commandsSummariesAllocator.put("showplayers", CoreMessages.COMMAND_MESSAGE_SHOW_PLAYERS);
        commandsSummariesAllocator.put("requestaroll", CoreMessages.COMMAND_MESSAGE_REQUEST_A_ROLL);

        commandsSummariesAllocator.put("showanitems", CoreMessages.COMMAND_MESSAGE_SHOW_AN_ITEMS);
        commandsSummariesAllocator.put("showcuritem", CoreMessages.COMMAND_MESSAGE_SHOW_CURRENT_ITEM);
        commandsSummariesAllocator.put("addanitem", CoreMessages.COMMAND_MESSAGE_ADD_AN_ITEM);
        commandsSummariesAllocator.put("setanitem", CoreMessages.COMMAND_MESSAGE_SET_AN_ITEM);
        commandsSummariesAllocator.put("editcuritem", CoreMessages.COMMAND_MESSAGE_EDIT_CURRENT_ITEM);
        commandsSummariesAllocator.put("deleteanitem", CoreMessages.COMMAND_MESSAGE_DELETE_AN_ITEM);
        commandsSummariesAllocator.put("giveanitem", CoreMessages.COMMAND_MESSAGE_GIVE_AN_ITEM);
        commandsSummariesAllocator.put("takeanitem", CoreMessages.COMMAND_MESSAGE_TAKE_AN_ITEM);
        commandsSummariesAllocator.put("lockvault", CoreMessages.COMMAND_MESSAGE_LOCK_VAULT);

        commandsSummariesAllocator.put("setcampaignname", CoreMessages.COMMAND_MESSAGE_SET_CAMPAIGN_NAME);
        commandsSummariesAllocator.put("setpassword", CoreMessages.COMMAND_MESSAGE_SET_PASSWORD);
        commandsSummariesAllocator.put("setmulticlasslimit", CoreMessages.COMMAND_MESSAGE_SET_MULTICLASS_LIMIT);

        commandsSummariesAllocator.put("changehealth", CoreMessages.COMMAND_MESSAGE_CHANGE_HEALTH);
        commandsSummariesAllocator.put("changedeathcounters", CoreMessages.COMMAND_MESSAGE_CHANGE_DEATH_COUNTERS);
        commandsSummariesAllocator.put("changeexp", CoreMessages.COMMAND_MESSAGE_CHANGE_EXPERIENCE);
        commandsSummariesAllocator.put("levelup", CoreMessages.COMMAND_MESSAGE_LEVEL_UP);
        commandsSummariesAllocator.put("giveinsp", CoreMessages.COMMAND_MESSAGE_GIVE_INSPIRATION);
        commandsSummariesAllocator.put("setasecondaryjob", CoreMessages.COMMAND_MESSAGE_SET_SECONDARY_JOB);
        commandsSummariesAllocator.put("setprestigejob", CoreMessages.COMMAND_MESSAGE_SET_PRESTIGE_JOB);

        commandsSummariesAllocator.put("addaquest", CoreMessages.COMMAND_MESSAGE_ADD_A_QUEST);
        commandsSummariesAllocator.put("editaquest", CoreMessages.COMMAND_MESSAGE_EDIT_A_QUEST);
        commandsSummariesAllocator.put("postaquest", CoreMessages.COMMAND_MESSAGE_POST_A_QUEST);
        commandsSummariesAllocator.put("closeaquest", CoreMessages.COMMAND_MESSAGE_CLOSE_A_QUEST);
    }

    public void startNewUser(MessageContext ctx) {
        ChatSession newUser = new ChatSession(ctx);
        walkieTalkie.patternExecute(newUser, CoreMessages.START_MESSAGE, KeyboardFactory.commonSetOfCommandsBoard(), false);

        DataHandler.createChatFile(ctx.chatId().toString());
        newUser.setUsername(ctx.user().getUserName());
        knowledge.renewListChat(newUser);
        knowledge.renewListUsername(newUser);
    }

    public void sendHelp(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (ctx.arguments().length == 0 || commandsSummariesAllocator.get(ctx.firstArg()) == null) {
            walkieTalkie.patternExecute(currentUser, currentUser.currentKeyboard.toString(), null, false);
        } else {
            walkieTalkie.patternExecute(currentUser, commandsSummariesAllocator.get(ctx.firstArg()), null, false);
        }
        knowledge.renewListChat(currentUser);
    }

    public void changeKeyboard(MessageContext ctx, KeyboardValues keys, ReplyKeyboard function, String changeMessage) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        currentUser.currentKeyboard = keys;
        walkieTalkie.patternExecute(currentUser, changeMessage, function, false);
        knowledge.renewListChat(currentUser);
    }

    public void rollCustom(ChatSession cs) {
        SendMessage rollVar;
        if (!cs.dicePresets.isEmpty()) {
            cs.checkPresetsSize();
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE_WITH_PRESETS);
            rollVar.setReplyMarkup(KeyboardFactory.rollCustomBoard(cs));
        }
        else {
            rollVar = new SendMessage(cs.getChatId().toString(), Constants.CUSTOM_DICE_MESSAGE);
        }
        walkieTalkie.getSilent().execute(rollVar);
        cs.rollCustom = true;
    }

    public void onRollCustom(ChatSession cs, Update update) {
        if (!cs.dicePresets.contains(update.getMessage().getText())) {
            cs.dicePresets.add(update.getMessage().getText());
        }

        String line = update.getMessage().getText().trim();
        if (update.getMessage().getText().startsWith("d")) {
            line = "1" + line;
        }
        String[] dices = line.split("d");
        walkieTalkie.patternExecute(cs, diceHoarder.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);

        cs.rollCustom = false;
        knowledge.renewListChat(cs);
    }

    public void onRollCustomPreset(ChatSession cs, String responseQuery) {
        String[] dices = responseQuery.trim().split("d");
        try {
            walkieTalkie.patternExecute(cs, diceHoarder.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);
        } catch (NumberFormatException e) {
            walkieTalkie.patternExecute(cs, Constants.CUSTOM_DICE_ERROR, null, false);
        }
        cs.rollCustom = false;
        knowledge.renewListChat(cs);
    }

    public boolean searchEngine(ChatSession cs, String entry) {
        ArrayList<String> matches;

        try {
            matches = SiteParser.searchArticleIds(cs.sectionId.toString(), entry);
        } catch (IOException e) {
            return walkieTalkie.reportIncorrect(cs);
        }

        if (matches.isEmpty()) {
            walkieTalkie.reportFail(cs);
        }

        else if (matches.size() == 2) {
            ArrayList<String> article;
            try {
                switch (cs.sectionId) {
                    case SPELLS:
                        article = SiteParser.SpellsGrabber(matches.getFirst());
                        break;
                    case ITEMS:
                        article = SiteParser.ItemsGrabber(matches.getFirst());
                        break;
                    case BESTIARY:
                        article = SiteParser.BestiaryGrabber(matches.getFirst());
                        break;
                    case RACES:
                        article = SiteParser.RacesGrabber(matches.getFirst());
                        break;
                    case FEATS:
                        article = SiteParser.FeatsGrabber(matches.getFirst());
                        break;
                    case BACKGROUNDS:
                        article = SiteParser.BackgroundsGrabber(matches.getFirst());
                        break;
                    default:
                        walkieTalkie.reportImpossible(cs);
                        return false;
                }
            } catch (Exception e) {
                return walkieTalkie.reportIncorrect(cs);
            }

            walkieTalkie.articleMessaging(article, cs);
            cs.sectionId = SearchCategories.NONE;
            cs.searchSuccess = false;
            cs.title = "";
            return false;
        }

        walkieTalkie.patternExecute(cs, SiteParser.addressWriter(matches, cs.sectionId.toString()), null, true);
        return true;
    }

    public void onSearchSuccess(ChatSession cs, Update update) {
        cs.title = update.getMessage().getText();
        try {
            switch (cs.sectionId) {
                case SPELLS:
                    walkieTalkie.articleMessaging(SiteParser.SpellsGrabber(cs.title), cs);
                    break;
                case ITEMS:
                    walkieTalkie.articleMessaging(SiteParser.ItemsGrabber(cs.title), cs);
                    break;
                case BESTIARY:
                    walkieTalkie.articleMessaging(SiteParser.BestiaryGrabber(cs.title), cs);
                    break;
                case RACES:
                    walkieTalkie.articleMessaging(SiteParser.RacesGrabber(cs.title), cs);
                    break;
                case FEATS:
                    walkieTalkie.articleMessaging(SiteParser.FeatsGrabber(cs.title), cs);
                    break;
                case BACKGROUNDS:
                    walkieTalkie.articleMessaging(SiteParser.BackgroundsGrabber(cs.title), cs);
                    break;
                default:
                    walkieTalkie.reportImpossible(cs);
                    break;
            }
            cs.sectionId = SearchCategories.NONE;
            cs.searchSuccess = false;
            cs.title = "";
        } catch (IOException e) {
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_FAIL_SECOND_STAGE, null, false);
        }
        knowledge.renewListChat(cs);
    }
}
