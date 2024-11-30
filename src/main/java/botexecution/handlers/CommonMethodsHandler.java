package botexecution.handlers;

import botexecution.commands.CoreMessages;
import botexecution.commands.KeyboardValues;
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

    public final HashMap<String, String> commandsSummariesAllocator = new HashMap<>();
    public final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    public CommonMethodsHandler(DataHandler knowledge, TextHandler walkieTalkie) {
        this.knowledge = knowledge;
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

        commandsSummariesAllocator.put("help", CoreMessages.COMMAND_MESSAGE_HELP);
        commandsSummariesAllocator.put("mofu", CoreMessages.COMMAND_MESSAGE_MOFU);
        commandsSummariesAllocator.put("search", CoreMessages.COMMAND_MESSAGE_SEARCH);
        commandsSummariesAllocator.put("roll", CoreMessages.COMMAND_MESSAGE_ROLL);
        commandsSummariesAllocator.put("credits", CoreMessages.COMMAND_MESSAGE_CREDITS);
        commandsSummariesAllocator.put("startagame", CoreMessages.COMMAND_MESSAGE_START_A_GAME);
        commandsSummariesAllocator.put("pauseagame", CoreMessages.COMMAND_MESSAGE_PAUSE_A_GAME);
        commandsSummariesAllocator.put("endagame", CoreMessages.COMMAND_MESSAGE_END_A_GAME);
        commandsSummariesAllocator.put("createacharacter", CoreMessages.COMMAND_MESSAGE_CREATE_A_CHARACTER);
        commandsSummariesAllocator.put("createaplayer", CoreMessages.COMMAND_MESSAGE_CREATE_A_PLAYER);
        commandsSummariesAllocator.put("haltcreation", CoreMessages.COMMAND_MESSAGE_HALT_CREATION);
        commandsSummariesAllocator.put("createacampaign", CoreMessages.COMMAND_MESSAGE_CREATE_A_CAMPAIGN);
        commandsSummariesAllocator.put("endacampaign", CoreMessages.COMMAND_MESSAGE_END_A_CAMPAIGN);
        commandsSummariesAllocator.put("showcampaigns", CoreMessages.COMMAND_MESSAGE_SHOW_CAMPAIGNS);
        commandsSummariesAllocator.put("setcampaign", CoreMessages.COMMAND_MESSAGE_SET_CAMPAIGN);
        commandsSummariesAllocator.put("setcampaignname", CoreMessages.COMMAND_MESSAGE_SET_CAMPAIGN_NAME);
        commandsSummariesAllocator.put("setpassword", CoreMessages.COMMAND_MESSAGE_SET_PASSWORD);
        commandsSummariesAllocator.put("setmulticlasslimit", CoreMessages.COMMAND_MESSAGE_SET_MULTICLASS_LIMIT);
        commandsSummariesAllocator.put("showplayers", CoreMessages.COMMAND_MESSAGE_SHOW_PLAYERS);
        commandsSummariesAllocator.put("showplayerprofile", CoreMessages.COMMAND_MESSAGE_SHOW_PLAYER_PROFILE);
        commandsSummariesAllocator.put("requestaroll", CoreMessages.COMMAND_MESSAGE_REQUEST_A_ROLL);
        commandsSummariesAllocator.put("addanitem", CoreMessages.COMMAND_MESSAGE_ADD_AN_ITEM);
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
        if (ctx.firstArg().isEmpty() || commandsSummariesAllocator.get(ctx.firstArg()) == null) {
            walkieTalkie.patternExecute(currentUser, currentUser.currentKeyboard.toString(), null, false);
        }
        else {
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

        String[] dices = update.getMessage().getText().trim().split("d");
        walkieTalkie.patternExecute(cs, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);

        cs.rollCustom = false;
        knowledge.renewListChat(cs);
    }

    public void onRollCustomPreset(ChatSession cs, String responseQuery) {
        String[] dices = responseQuery.trim().split("d");
        try {
            walkieTalkie.patternExecute(cs, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);
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