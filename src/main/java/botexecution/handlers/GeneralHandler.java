package botexecution.handlers;

import botexecution.commands.CoreMessages;
import botexecution.commands.KeyboardValues;
import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.commands.CurrentProcess;
import botexecution.mainobjects.KeyboardFactory;
import common.*;
import logger.BotLogger;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Locality.USER;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;

public class GeneralHandler implements AbilityExtension {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final SiteParseHandler archive;
    private final DiceHandler diceHoarder;

    public final HashMap<String, String> commandsSummariesAllocator = new HashMap<>();
    public final HashMap<String, Consumer<ChatSession>> methodsAllocator = new HashMap<>();

    public GeneralHandler(DataHandler knowledge, TextHandler walkieTalkie,
                                SiteParseHandler archive, DiceHandler diceHoarder) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.archive = archive;
        this.diceHoarder = diceHoarder;

        Consumer<ChatSession> Spell = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_SPELLS);
            cs.sectionId = SearchCategories.SPELLS;
        };
        Consumer<ChatSession> Item = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_ITEMS);
            cs.sectionId = SearchCategories.ITEMS;
        };
        Consumer<ChatSession> Bestiary = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_BESTIARY);
            cs.sectionId = SearchCategories.BESTIARY;
        };
        Consumer<ChatSession> Race = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RACES);
            cs.sectionId = SearchCategories.RACES;
        };
        Consumer<ChatSession> Class = cs -> walkieTalkie.patternExecute(cs, Constants.CLASSES_LIST, null, true);
        Consumer<ChatSession> Feat = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_FEATS);
            cs.sectionId = SearchCategories.FEATS;
        };
        Consumer<ChatSession> Background = cs -> {
            if (cs.currentContext == CurrentProcess.SEARCHING_AN_ARTICLE) {
                walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_RESTRICT);
                return;
            }
            else if (cs.currentContext != CurrentProcess.FREE) {
                walkieTalkie.patternExecute(cs, Constants.CURRENT_COMMAND_RESTRICT);
                return;
            }
            cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE;
            walkieTalkie.patternExecute(cs, Constants.SEARCH_MESSAGE_BACKGROUNDS);
            cs.sectionId = SearchCategories.BACKGROUNDS;
        };

        Consumer<ChatSession> RollD20 = diceHoarder::D20;
        Consumer<ChatSession> Roll2D20 = cs -> walkieTalkie.patternExecute(cs, Constants.ROLL_MESSAGE_ADVANTAGE,
                KeyboardFactory.rollAdvantageBoard(), false);
        Consumer<ChatSession> RollAdvantage = cs -> diceHoarder.D20TwoTimes(cs,true);
        Consumer<ChatSession> RollDisadvantage = cs -> diceHoarder.D20TwoTimes(cs,false);
        Consumer<ChatSession> RollD8 = diceHoarder::D8;
        Consumer<ChatSession> RollD6 = diceHoarder::D6;
        Consumer<ChatSession> Roll4D6 = cs -> walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes());
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
        commandsSummariesAllocator.put("changestats", CoreMessages.COMMAND_MESSAGE_CHANGE_STATS);
        commandsSummariesAllocator.put("changeadv", CoreMessages.COMMAND_MESSAGE_CHANGE_ADVANTAGES);
        commandsSummariesAllocator.put("giveinsp", CoreMessages.COMMAND_MESSAGE_GIVE_INSPIRATION);
        commandsSummariesAllocator.put("setasecondaryjob", CoreMessages.COMMAND_MESSAGE_SET_SECONDARY_JOB);
        commandsSummariesAllocator.put("setprestigejob", CoreMessages.COMMAND_MESSAGE_SET_PRESTIGE_JOB);
        commandsSummariesAllocator.put("changelook", CoreMessages.COMMAND_MESSAGE_CHANGE_LOOKS);

        commandsSummariesAllocator.put("addaquest", CoreMessages.COMMAND_MESSAGE_ADD_A_QUEST);
        commandsSummariesAllocator.put("editaquest", CoreMessages.COMMAND_MESSAGE_EDIT_A_QUEST);
        commandsSummariesAllocator.put("postaquest", CoreMessages.COMMAND_MESSAGE_POST_A_QUEST);
        commandsSummariesAllocator.put("closeaquest", CoreMessages.COMMAND_MESSAGE_CLOSE_A_QUEST);
    }

    public void startNewUser(MessageContext ctx) {
        ChatSession newUser = new ChatSession(ctx);
        walkieTalkie.patternExecute(newUser, CoreMessages.START_MESSAGE,
                KeyboardFactory.commonSetOfCommandsBoard(), false);
        DataHandler.createChatFile(ctx.chatId().toString());

        newUser.setUsername("@" + ctx.user().getUserName());

        knowledge.renewListChat(newUser);
        knowledge.renewListUsername(newUser);
    }

    public void sendHelp(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());

        if (commandsSummariesAllocator.get(ctx.firstArg()) == null) {
            BotLogger.severe("Help doesn't recognise this argument: " + ctx.firstArg());
            walkieTalkie.patternExecute(currentUser, currentUser.currentKeyboard.toString());
        }
        else if (ctx.arguments().length == 0) {
            walkieTalkie.patternExecute(currentUser, currentUser.currentKeyboard.toString());
        }
        else {
            walkieTalkie.patternExecute(currentUser, commandsSummariesAllocator.get(ctx.firstArg()));
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
        walkieTalkie.patternExecute(cs, diceHoarder.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])));

        cs.rollCustom = false;
        knowledge.renewListChat(cs);
    }

    public void onRollCustomPreset(ChatSession cs, String responseQuery) {
        String[] dices = responseQuery.trim().split("d");
        try {
            walkieTalkie.patternExecute(cs, diceHoarder.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])));
        } catch (NumberFormatException e) {
            walkieTalkie.patternExecute(cs, Constants.CUSTOM_DICE_ERROR);
        }
        cs.rollCustom = false;
        knowledge.renewListChat(cs);
    }

    public void searchEngine(ChatSession cs, String entry) {
        ArrayList<String> matches;
        matches = knowledge.searchArticleIds(cs.sectionId.toString(), entry);

        if (matches.isEmpty()) {
            walkieTalkie.reportFail(cs);
            return;
        }

        else if (matches.size() == 2) {
            ArrayList<String> article;
            switch (cs.sectionId) {
                case SPELLS -> article = archive.spellsGrabber(matches.getFirst());
                case ITEMS -> article = archive.itemsGrabber(matches.getFirst());
                case BESTIARY -> article = archive.bestiaryGrabber(matches.getFirst());
                case RACES -> article = archive.racesGrabber(matches.getFirst());
                case FEATS -> article = archive.featsGrabber(matches.getFirst());
                case BACKGROUNDS -> article = archive.backgroundsGrabber(matches.getFirst());
                default -> {
                    walkieTalkie.reportImpossible(cs);
                    return;
                }
            }

            walkieTalkie.articleMessaging(article, cs);
            cs.sectionId = SearchCategories.NONE;
            cs.currentContext = CurrentProcess.FREE;
            cs.title = "";
            return;
        }

        walkieTalkie.patternExecute(cs, archive.addressWriter(matches, cs.sectionId.toString()), null, true);
        cs.currentContext = CurrentProcess.SEARCHING_AN_ARTICLE_SUCCESS;
    }

    public void onSearchSuccess(ChatSession cs, Update update) {
        cs.title = update.getMessage().getText();
        switch (cs.sectionId) {
            case SPELLS -> walkieTalkie.articleMessaging(archive.spellsGrabber(cs.title), cs);
            case ITEMS -> walkieTalkie.articleMessaging(archive.itemsGrabber(cs.title), cs);
            case BESTIARY -> walkieTalkie.articleMessaging(archive.bestiaryGrabber(cs.title), cs);
            case RACES -> walkieTalkie.articleMessaging(archive.racesGrabber(cs.title), cs);
            case FEATS -> walkieTalkie.articleMessaging(archive.featsGrabber(cs.title), cs);
            case BACKGROUNDS -> walkieTalkie.articleMessaging(archive.backgroundsGrabber(cs.title), cs);
            default -> walkieTalkie.reportImpossible(cs);
        }
        cs.sectionId = SearchCategories.NONE;
        cs.currentContext = CurrentProcess.FREE;
        cs.title = "";
        knowledge.renewListChat(cs);
    }
    
    public Ability startOut() {
        Consumer<MessageContext> start = this::startNewUser;
        //нет в coremessages

        return Ability
                .builder()
                .name("start")
                .info("starts up the bot")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(start)
                .build();
    }

    public Ability showHelp() {
        Consumer<MessageContext> helpHand = this::sendHelp;
        //есть в coremessages

        return Ability.builder()
                .name("help")
                .info("shows all commands")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(helpHand)
                .build();
    }

    //команды для вызова клавиатур
    public Ability moveToCommonKeyboard() {
        Consumer<MessageContext> commonKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.COMMON, KeyboardFactory.commonSetOfCommandsBoard(), Constants.CHANGE_TO_COMMON_KEYBOARD);

        return Ability
                .builder()
                .name("common")
                .info("shows a common keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(commonKeyboard)
                .build();
    }

    public Ability moveToGameKeyboard() {
        Consumer<MessageContext> gameKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.GAME, KeyboardFactory.gameSetOfCommandsBoard(), Constants.CHANGE_TO_GAME_KEYBOARD);

        return Ability
                .builder()
                .name("game")
                .info("shows a game keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(gameKeyboard)
                .build();
    }

    public Ability moveToDndKeyboard() {
        Consumer<MessageContext> dndKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.DND, KeyboardFactory.dndSetOfCommandsBoard(), Constants.CHANGE_TO_DND_KEYBOARD);

        return Ability
                .builder()
                .name("dnd")
                .info("shows a dnd keyboard")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(dndKeyboard)
                .build();
    }

    public Ability moveToPlayerBoard() {
        Consumer<MessageContext> playerKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.PLAYER, KeyboardFactory.playerSetOfCommands(), Constants.CHANGE_TO_PLAYER_KEYBOARD);

        return Ability
                .builder()
                .name("playerboard")
                .info("shows a player board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(playerKeyboard)
                .build();
    }

    public Ability moveToDMBoard() {
        Consumer<MessageContext> dmKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.DM, KeyboardFactory.dmSetOfCommandsBoard(), Constants.CHANGE_TO_DM_KEYBOARD);

        return Ability
                .builder()
                .name("dmboard")
                .info("shows a dm board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(dmKeyboard)
                .build();
    }

    public Ability moveToItemBoard() {
        Consumer<MessageContext> itemKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.ITEMS, KeyboardFactory.itemSetOfCommands(), Constants.CHANGE_TO_ITEMS_KEYBOARD);

        return Ability
                .builder()
                .name("itemboard")
                .info("shows a item board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(itemKeyboard)
                .build();
    }

    public Ability moveToCampaignBoard() {
        Consumer<MessageContext> campaignKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.CAMPAIGN, KeyboardFactory.campaignSetOfCommandsBoard(), Constants.CHANGE_TO_CAMPAIGN_KEYBOARD);

        return Ability
                .builder()
                .name("campaignboard")
                .info("shows a campaign board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignKeyboard)
                .build();
    }

    public Ability moveToStatBoard() {
        Consumer<MessageContext> campaignKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.STAT, KeyboardFactory.statSetOfCommands(), Constants.CHANGE_TO_STATS_KEYBOARD);

        return Ability
                .builder()
                .name("statboard")
                .info("shows a stat board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignKeyboard)
                .build();
    }

    public Ability moveToQuestBoard() {
        Consumer<MessageContext> campaignKeyboard = ctx -> changeKeyboard(ctx,
                KeyboardValues.QUEST, KeyboardFactory.questSetOfCommands(), Constants.CHANGE_TO_QUEST_KEYBOARD);

        return Ability
                .builder()
                .name("questboard")
                .info("shows a quest" +
                        " board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignKeyboard)
                .build();
    }
}
