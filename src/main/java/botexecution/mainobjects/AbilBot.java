package botexecution.mainobjects;

import botexecution.commands.KeyboardValues;
import botexecution.handlers.*;
import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.MediaHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.handlers.dndhandlers.*;
import common.*;

import dnd.values.EditingParameters;
import dnd.values.RoleParameters;
import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private final TextHandler walkieTalkie;
    private final MediaHandler pager;
    private final DataHandler knowledge;
    private final DiceHandler diceHoarder;

    private final CommonMethodsHandler jackOfAllTrades;
    private final SiteParseHandler archive;
    private final GameHandler dungeonCrawl;

    private final DnDNotificationHandler secretMessages;
    private final DnDHandler tableTop;
    private final DnDCampaignHandler story;
    private final DnDPlayerHandler characterList;
    private final DnDItemHandler bagOfHolding;

    public AbilBot() {
        super(new OkHttpTelegramClient(DataReader.readToken()), "Faerie");
        this.walkieTalkie = new TextHandler(this.getSilent());
        this.pager = new MediaHandler(this.getTelegramClient());
        this.knowledge = new DataHandler(false);
        this.diceHoarder = new DiceHandler(knowledge, walkieTalkie);

        this.archive = new SiteParseHandler(knowledge);
        this.jackOfAllTrades = new CommonMethodsHandler(knowledge, walkieTalkie, archive, diceHoarder);
        this.dungeonCrawl = new GameHandler(knowledge, walkieTalkie, pager, diceHoarder);

        this.secretMessages = new DnDNotificationHandler(knowledge, walkieTalkie);
        this.tableTop = new DnDHandler(knowledge, walkieTalkie, secretMessages);
        this.story = new DnDCampaignHandler(knowledge, walkieTalkie, secretMessages);
        this.characterList = new DnDPlayerHandler(knowledge, walkieTalkie, diceHoarder);
        this.bagOfHolding = new DnDItemHandler(knowledge, walkieTalkie, secretMessages);
        super.onRegister();
    }

    @Override
    public long creatorId() {
        long id = DataReader.readCreatorId();
        if (id < 0) {
            id = 0;
        }
        return id;
    }

    //основные функции
    public Ability startOut() {
        Consumer<MessageContext> start = jackOfAllTrades::startNewUser;
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
        Consumer<MessageContext> helpHand = jackOfAllTrades::sendHelp;
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

    public Ability showCredits() {
        Consumer<MessageContext> credits =
                ctx -> silent.send(Constants.CREDITS, ctx.chatId());
        //есть в coremessages

        return Ability
                .builder()
                .name("credits")
                .info("shows authors and coders for this bot")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(credits)
                .build();
    }

    //команды для вызова клавиатур
    public Ability moveToCommonKeyboard() {
        Consumer<MessageContext> commonKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> gameKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> dndKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> playerKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> dmKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> itemKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> campaignKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> campaignKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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
        Consumer<MessageContext> campaignKeyboard = ctx -> jackOfAllTrades.changeKeyboard(ctx,
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

    //основной функционал
    public Ability sayMofu() {
        Consumer<MessageContext> mofu =
                ctx -> silent.send("Mofu Mofu!", ctx.chatId());
        //есть в coremessages

        return Ability
                .builder()
                .name("mofu")
                .info("mofu")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(mofu)
                .build();
    }

    public Ability requestArticle() {
        Consumer<MessageContext> search = ctx -> walkieTalkie.patternExecute(ctx,
                Constants.SEARCH_MESSAGE, KeyboardFactory.searchBoard(), false);
        //есть в coremessages

        return Ability
                .builder()
                .name("search")
                .info("searches article on DnD.su")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(search)
                .build();
    }

    public Ability diceRoll() {
        Consumer<MessageContext> roll = ctx -> walkieTalkie.patternExecute(ctx,
                Constants.ROLL_MESSAGE, KeyboardFactory.rollVariantsBoard(), false);
        //есть в coremessages

        return Ability
                .builder()
                .name("roll")
                .info("rolls a dice")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(roll)
                .build();
    }

    //функции для игры
    public Ability createPlayerCharacter() {
        Consumer<MessageContext> createNewPc = dungeonCrawl::createPlayer;
        //есть в coremessages

        return Ability
                .builder()
                .name("createacharacter")
                .info("creates a player character")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(createNewPc)
                .build();
    }

    public Ability startAGame() {
        Consumer<MessageContext> game = dungeonCrawl::startGame;
        //есть в coremessages

        return Ability
                .builder()
                .name("startagame")
                .info("starts a game")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(game)
                .build();
    }

    public Ability pauseGame() {
        Consumer<MessageContext> pause = dungeonCrawl::pauseGame;
        //есть в coremessages

        return Ability
                .builder()
                .name("pauseagame")
                .info("pauses a game")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(pause)
                .build();
    }

    public Ability expungeGame() {
        Consumer<MessageContext> expunge = dungeonCrawl::expungeGame;
        //есть в coremessages

        return Ability
                .builder()
                .name("endagame")
                .info("ends a game prematurely")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(expunge)
                .build();
    }

    //функции для менеджера компаний
    public Ability createCampaign() {
        Consumer<MessageContext> campaign = story::createCampaign;
        //есть в coremessages

        return Ability
                .builder()
                .name("createacampaign")
                .info("creates a campaign")
                .input(0)
                .locality(GROUP)
                .privacy(GROUP_ADMIN)
                .action(campaign)
                .build();
    }

    public Ability endCampaign() {
        Consumer<MessageContext> end = story::endCampaign;
        //есть в coremessages

        return Ability
                .builder()
                .name("endacampaign")
                .info("ends a campaign")
                .input(0)
                .locality(GROUP)
                .privacy(GROUP_ADMIN)
                .action(end)
                .build();
    }

    public Ability showCampaigns() {
        Consumer<MessageContext> campaigns = story::showCampaigns;
        //есть в coremessages

        return Ability
                .builder()
                .name("showcampaigns")
                .info("shows your campaigns")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaigns)
                .build();
    }

    public Ability showCampaignsGroup() {
        Consumer<MessageContext> campaign = story::showCampaignGroup;

        return Ability
                .builder()
                .name("showcurcampaign")
                .info("shows current campaign")
                .input(0)
                .locality(GROUP)
                .privacy(PUBLIC)
                .action(campaign)
                .build();
    }

    public Ability createPlayerDnD() {
        Consumer<MessageContext> player = characterList::addAPlayerDnD;
        //есть в coremessages

        return Ability
                .builder()
                .name("createaplayer")
                .info("creates a player")
                .input(0)
                .locality(GROUP)
                .privacy(PUBLIC)
                .action(player)
                .build();
    }

    public Ability haltCreation() {
        Consumer<MessageContext> halt = characterList::haltCreationOfPlayerDnD;
        //есть в coremessages

        return Ability
                .builder()
                .name("haltcreation")
                .info("puts player's creation on pause")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(halt)
                .build();
    }

    public Ability setCampaign() {
        Consumer<MessageContext> campaign = story::setCurrentCampaign;
        //есть в coremessages

        return Ability
                .builder()
                .name("setcampaign")
                .info("sets current campaign")
                .input(0)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(campaign)
                .build();
    }

    public Ability setCampaignName() {
        Consumer<MessageContext> campaignName = story::setCampaignName;
        //есть в coremessages

        return Ability
                .builder()
                .name("setcampaignname")
                .info("sets a campaign name")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignName)
                .build();
    }

    public Ability setPassword() {
        Consumer<MessageContext> password = story::setPassword;
        //есть в coremessages

        return Ability
                .builder()
                .name("setpassword")
                .info("sets a campaign password")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(password)
                .build();
    }

    public Ability setMulticlassLimit() {
        Consumer<MessageContext> multi = story::setMulticlassLimit;
        //есть в coremessages

        return Ability
                .builder()
                .name("setmulticlasslimit")
                .info("sets a campaign multiclass limit")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(multi)
                .build();
    }

    public Ability showPlayers() {
        Consumer<MessageContext> playersList = story::showPlayers;
        //есть в coremessages

        return Ability
                .builder()
                .name("showplayers")
                .info("show a players list")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(playersList)
                .build();
    }

    public Ability showPlayerProfile() {
        Consumer<MessageContext> profile = story::showPlayerProfile;
        //есть в coremessages

        return Ability
                .builder()
                .name("showplayerprofile")
                .info("show a players list")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(profile)
                .build();
    }

    public Ability requestARoll() {
        Consumer<MessageContext> rollRequest = tableTop::requestARoll;
        //есть в coremessages

        return Ability
                .builder()
                .name("requestaroll")
                .info("request a roll from a player")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(rollRequest)
                .build();
    }

    public Ability askDmForARoll() {
        Consumer<MessageContext> askDm = tableTop::askDmForARoll;

        return Ability
                .builder()
                .name("askforaroll")
                .info("asks dm for a roll")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(askDm)
                .build();
    }

    public Ability addAQuest() {
        Consumer<MessageContext> addQuest = tableTop::addQuest;

        return Ability
                .builder()
                .name("addaquest")
                .info("adds a quest to dm's roster")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(addQuest)
                .build();
    }

    public Ability editAQuest() {
        Consumer<MessageContext> editAQuest = tableTop::editQuest;

        return Ability
                .builder()
                .name("editaquest")
                .info("edits a quest in dm's roster")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(editAQuest)
                .build();
    }

    public Ability postAQuest() {
        Consumer<MessageContext> giveAQuest = tableTop::postQuest;

        return Ability
                .builder()
                .name("postaquest")
                .info("posts a quest on quest board")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(giveAQuest)
                .build();
    }

    public Ability closeAQuest() {
        Consumer<MessageContext> closeAQuest = tableTop::closeQuest;

        return Ability
                .builder()
                .name("closeaquest")
                .info("closes a quest on quest board")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(closeAQuest)
                .build();
    }

    public Ability showAQuestBoard() {
        Consumer<MessageContext> showAQuestBoard = tableTop::showQuestBoard;

        return Ability
                .builder()
                .name("showaquestboard")
                .info("shows a quest board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(showAQuestBoard)
                .build();
    }

    public Ability showAQuest() {
        Consumer<MessageContext> showAQuest = tableTop::showQuest;

        return Ability
                .builder()
                .name("showaquest")
                .info("shows a quest from a quest board")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(showAQuest)
                .build();
    }

    public Ability addANote() {
        Consumer<MessageContext> addANote = tableTop::addNote;

        return Ability
                .builder()
                .name("addanote")
                .info("adds a note")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(addANote)
                .build();
    }

    public Ability editANote() {
        Consumer<MessageContext> editANote = tableTop::editNote;

        return Ability
                .builder()
                .name("editanote")
                .info("adds a note")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(editANote)
                .build();
    }

    public Ability showNotes() {
        Consumer<MessageContext> showNotes = tableTop::showNotes;

        return Ability
                .builder()
                .name("showmynotes")
                .info("shows your notes")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(showNotes)
                .build();
    }

    public Ability addAnItem() {
        Consumer<MessageContext> addAnItem = bagOfHolding::addAspect;
        //есть в coremessages

        return Ability
                .builder()
                .name("addanitem")
                .info("adds an item to dm's roster")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(addAnItem)
                .build();
    }

    public Ability showAnItems() {
        Consumer<MessageContext> showAnItems = bagOfHolding::showAspect;

        return Ability
                .builder()
                .name("showanitems")
                .info("shows dm's rosters")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(showAnItems)
                .build();
    }

    public Ability setAnItem() {
        Consumer<MessageContext> setAnItem = bagOfHolding::setAspect;

        return Ability
                .builder()
                .name("setanitem")
                .info("sets an item from dm's roster")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(setAnItem)
                .build();
    }

    public Ability seeCurrentItem() {
        Consumer<MessageContext> showCurrentItem = bagOfHolding::seeCurrentAspect;

        return Ability
                .builder()
                .name("showcuritem")
                .info("shows selected item from dm's roster")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(showCurrentItem)
                .build();
    }

    public Ability editCurrentItem() {
        Consumer<MessageContext> editCurrentItem = bagOfHolding::editAspect;

        return Ability
                .builder()
                .name("editcuritem")
                .info("edits selected item from dm's roster")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(editCurrentItem)
                .build();
    }

    public Ability deleteAnItem() {
        Consumer<MessageContext> deleteAnItem = bagOfHolding::deleteAspect;

        return Ability
                .builder()
                .name("deleteanitem")
                .info("deletes an item from dm's roster")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(deleteAnItem)
                .build();
    }

    public Ability giveAnItem() {
        Consumer<MessageContext> giveAnItem = bagOfHolding::giveAspect;

        return Ability
                .builder()
                .name("giveanitem")
                .info("gives an item from dm's roster to a player's character")
                .input(3)
                .locality(USER)
                .privacy(PUBLIC)
                .action(giveAnItem)
                .build();
    }

    public Ability takeAnItem() {
        Consumer<MessageContext> takeAnItem = bagOfHolding::takeAspect;

        return Ability
                .builder()
                .name("takeanitem")
                .info("takes out an item from player's roster")
                .input(3)
                .locality(USER)
                .privacy(PUBLIC)
                .action(takeAnItem)
                .build();
    }

    public Ability bringAnItem() {
        Consumer<MessageContext> bring = bagOfHolding::bringAspectAlong;

        return Ability
                .builder()
                .name("bringanitem")
                .info("gets an item to be ready")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(bring)
                .build();
    }

    public Ability equipAnItem() {
        Consumer<MessageContext> equip = bagOfHolding::equipActiveAspect;

        return Ability
                .builder()
                .name("equipanitem")
                .info("equips an item from readied items")
                .input(3)
                .locality(USER)
                .privacy(PUBLIC)
                .action(equip)
                .build();
    }

    public Ability takeOffAnItem() {
        Consumer<MessageContext> takeOff = bagOfHolding::removeActiveAspect;

        return Ability
                .builder()
                .name("unequipanitem")
                .info("takes off an item from readied items")
                .input(3)
                .locality(USER)
                .privacy(PUBLIC)
                .action(takeOff)
                .build();
    }

    public Ability lockVault() {
        Consumer<MessageContext> lockVault = bagOfHolding::lockInventory;

        return Ability
                .builder()
                .name("lockvault")
                .info("locks/unlocks a vault of players' items")
                .input(3)
                .locality(USER)
                .privacy(PUBLIC)
                .action(lockVault)
                .build();
    }

    public Ability changeHealth() {
        Consumer<MessageContext> changeHealth = tableTop::changeHealth;

        return Ability
                .builder()
                .name("changehealth")
                .info("changes player's characters health")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(changeHealth)
                .build();
    }

    public Ability changeDeathSaveThrowCounter() {
        Consumer<MessageContext> changeDeathCounter = tableTop::changeDeathSaveThrowCounter;

        return Ability
                .builder()
                .name("changedeathcounters")
                .info("changes player's characters death save throw counters")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(changeDeathCounter)
                .build();
    }

    public Ability changeExperience() {
        Consumer<MessageContext> changeExp = tableTop::changeExperience;

        return Ability
                .builder()
                .name("changeexp")
                .info("changes player's characters experience")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(changeExp)
                .build();
    }

    public Ability giveInspiration() {
        Consumer<MessageContext> giveInsp = tableTop::giveInspiration;

        return Ability
                .builder()
                .name("giveinsp")
                .info("changes player's characters inspiration points")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(giveInsp)
                .build();
    }

    public Ability levelUp() {
        Consumer<MessageContext> levelup = tableTop::levelUp;

        return Ability
                .builder()
                .name("levelup")
                .info("increases character's level")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(levelup)
                .build();
    }

    public Ability giveASecondaryJob() {
        Consumer<MessageContext> secondaryJob = tableTop::giveASecondaryJob;

        return Ability
                .builder()
                .name("setasecondaryjob")
                .info("sets a secondary job for a player character")
                .input(2)
                .locality(USER)
                .privacy(PUBLIC)
                .action(secondaryJob)
                .build();
    }

    public Ability setPrestigeJob() {
        /*
         * Устанавливает подкласс у игрока. Ей может пользоваться только ДМ.
         * Для неё надо указывать, какой класс будет улучшаться: главный или дополнительный.
         */

        Consumer<MessageContext> prestige = tableTop::setPrestigeJob;

        return Ability
                .builder()
                .name("setprestigejob")
                .info("sets a prestige job for a player character")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(prestige)
                .build();
    }

    @Override
    public void consume(Update update) {
        super.consume(update);

        ChatSession currentUser = knowledge.getSession(getChatId(update).toString());
        if (currentUser == null) {
            knowledge.readSessionToList(getChatId(update).toString());
            currentUser = knowledge.getSession(getChatId(update).toString());
        }
        if (currentUser == null) {
            currentUser = new ChatSession(update);
            knowledge.renewListChat(currentUser);
        }

        if (currentUser.role == RoleParameters.DUNGEON_MASTER
                && update.hasCallbackQuery() && update.getCallbackQuery().getData().contains("@")) {
            tableTop.askDmForARollResponse(currentUser, update.getCallbackQuery().getData());
        }

        if (currentUser.editingANote && currentUser.editNote != EditingParameters.NONE
                && update.hasMessage() && update.getMessage().hasText()) {
            tableTop.editNoteSecondStage(currentUser, update.getMessage().getText());
        }

        if (currentUser.addingAnAspect && update.hasMessage() && update.getMessage().hasText()) {
            bagOfHolding.addAspectSecondStage(currentUser, update.getMessage().getText());
        }
        if (currentUser.editingAnAspect && update.hasMessage() && update.getMessage().hasText()) {
            bagOfHolding.editAspectSecondStage(currentUser, update.getMessage().getText());
        }

        if (currentUser.editingAQuest && update.hasMessage() && update.getMessage().hasText()) {
            tableTop.editQuestSecondStage(currentUser, update.getMessage().getText());
        }

        if (currentUser.editingAPrestigeJob && update.hasMessage() && update.getMessage().hasText()) {
            tableTop.setPrestigeJobSecondStage(currentUser, update.getMessage().getText());
        }

        if (currentUser.creationOfPlayerCharacter && update.hasCallbackQuery()
                && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            dungeonCrawl.characterCreationMainLoop(currentUser, update);
        }

        else if (currentUser.gameInSession && !currentUser.pauseGame && update.hasCallbackQuery()) {
            dungeonCrawl.gameMainLoop(currentUser, update);
        }

        else if (currentUser.creationOfPlayerDnD) {
            String response;
            try {
                if (update.hasMessage() && update.getMessage().hasText()) {
                    response = update.getMessage().getText();
                    characterList.playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

                } else if (update.hasCallbackQuery()) {
                    response = update.getCallbackQuery().getData();
                    characterList.playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

                } else {
                    walkieTalkie.patternExecute(currentUser, "А? Если хотите сделать что-то другое, используйте /haltcreation", null, false);
                }
            } catch (Exception e) {
                System.out.println(e);
                walkieTalkie.patternExecute(currentUser, "А? Если хотите сделать что-то другое, используйте /haltcreation", null, false);
            }
            knowledge.renewListChat(currentUser);
        }

        else if (update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            if (!Objects.equals(currentUser.whoIsRolling, "")
                    && Objects.equals(currentUser.whoIsRolling, "@" + query.getFrom().getUserName())) {
                ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
                diceHoarder.rollWithParameters(currentCampaign.activeDm.campaignParty.get(currentUser.username),
                    update.getCallbackQuery().getData());
            }

            else if (currentUser.rollCustom) {
                jackOfAllTrades.onRollCustomPreset(currentUser, responseQuery);
            }
            else if (currentUser.role == RoleParameters.CAMPAIGN_END_STAGE) {
                if (!Objects.equals(currentUser.activeDm.dungeonMasterUsername, currentUser.username)) {
                    silent.send(Constants.CAMPAIGN_END_RESTRICTION, currentUser.getChatId());
                }
                else {
                    switch (responseQuery) {
                        case "Да":
                            currentUser.activeDm = null;
                            currentUser.role = RoleParameters.NONE;
                            silent.send(Constants.CAMPAIGN_END, currentUser.getChatId());
                            break;
                        case "Нет":
                            currentUser.role = RoleParameters.CAMPAIGN;
                            silent.send(Constants.CAMPAIGN_END_FALSE_ALARM, currentUser.getChatId());
                            break;
                        default:
                            currentUser.role = RoleParameters.CAMPAIGN;
                            silent.send(Constants.CAMPAIGN_END_FALSE_ALARM, currentUser.getChatId());
                            break;
                    }
                }
            }
            else {
                jackOfAllTrades.methodsAllocator.get(responseQuery).accept(currentUser);
            }
            knowledge.renewListChat(currentUser);
        }

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand()
                && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            if (currentUser.creationOfPlayerCharacter && !currentUser.nameIsChosen) {
                dungeonCrawl.characterCreationStart(currentUser, update);
            }

            else if (currentUser.rollCustom) {
                jackOfAllTrades.onRollCustom(currentUser, update);
            }

            else if (currentUser.searchSuccess) {
                jackOfAllTrades.onSearchSuccess(currentUser, update);
            }

            else if (!currentUser.sectionId.isEmpty()) {
                currentUser.searchSuccess = jackOfAllTrades.searchEngine(currentUser, update.getMessage().getText());
                knowledge.renewListChat(currentUser);
            }
        }
    knowledge.renewListChat(currentUser);
    }
}