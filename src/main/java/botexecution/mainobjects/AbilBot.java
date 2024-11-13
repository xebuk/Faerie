package botexecution.mainobjects;

import botexecution.handlers.*;
import common.*;

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
    private final CommonMethodsHandler jackOfAllTrades;
    private final GameHandler dungeonCrawl;
    private final DnDHandler tableTop;

    public AbilBot() throws IOException {
        super(new OkHttpTelegramClient(DataReader.readToken()), "Faerie");
        this.walkieTalkie = new TextHandler(this.getSilent());
        this.pager = new MediaHandler(this.getTelegramClient());
        this.knowledge = new DataHandler(false);

        this.jackOfAllTrades = new CommonMethodsHandler(knowledge, walkieTalkie);
        this.dungeonCrawl = new GameHandler(knowledge, walkieTalkie, pager);
        this.tableTop = new DnDHandler(knowledge, walkieTalkie);
        super.onRegister();
    }

    @Override
    public long creatorId() {
        long id;
        try {
            id = DataReader.readCreatorId();
        } catch (IOException e) {
            id = 0;
        }
        return id;
    }

    public Ability startOut() {
        Consumer<MessageContext> start = jackOfAllTrades::startNewUser;

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
        Consumer<MessageContext> helpHand =
                ctx -> silent.send(Constants.HELP_MESSAGE, ctx.chatId());

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

    public Ability moveToCommonKeyboard() {
        Consumer<MessageContext> commonKeyboard =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_COMMON_KEYBOARD,
                        KeyboardFactory.commonSetOfCommandsBoard(), false);

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
        Consumer<MessageContext> gameKeyboard =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_GAME_KEYBOARD,
                        KeyboardFactory.gameSetOfCommandsBoard(), false);

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
        Consumer<MessageContext> dndKeyboard =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_DND_KEYBOARD,
                        KeyboardFactory.dndSetOfCommandsBoard(), false);

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

    public Ability moveToDMBoard() {
        Consumer<MessageContext> dmKeyboard =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_DM_KEYBOARD,
                        KeyboardFactory.dmSetOfCommandsBoard(), false);

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

    public Ability moveToCampaignBoard() {
        Consumer<MessageContext> campaignKeyboard =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.CHANGE_TO_CAMPAIGN_KEYBOARD,
                        KeyboardFactory.campaignSettingsBoard(), false);

        return Ability
                .builder()
                .name("campaignsettings")
                .info("shows a campaign board")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaignKeyboard)
                .build();
    }

    public Ability sayMofu() {
        Consumer<MessageContext> mofu =
                ctx -> silent.send("Mofu Mofu!", ctx.chatId());

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
        Consumer<MessageContext> search =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.SEARCH_MESSAGE,
                        KeyboardFactory.searchBoard(), false);

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
        Consumer<MessageContext> roll =
                ctx -> walkieTalkie.patternExecute(new ChatSession(ctx), Constants.ROLL_MESSAGE,
                        KeyboardFactory.rollVariantsBoard(), false);

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

    public Ability createPlayerCharacter() {
        Consumer<MessageContext> createNewPc = dungeonCrawl::createPlayer;

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

    public Ability createCampaign() {
        Consumer<MessageContext> campaign = tableTop::createCampaign;

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
        Consumer<MessageContext> end = tableTop::endCampaign;

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
        Consumer<MessageContext> campaigns = tableTop::showCampaigns;

        return Ability
                .builder()
                .name("showcampaigns")
                .info("shows your campaigns")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaigns)
                .build();
    }

    public Ability showCampaignsGroup() {
        Consumer<MessageContext> campaign = tableTop::showCampaignGroup;

        return Ability
                .builder()
                .name("showcampaign")
                .info("shows chat's campaign")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaign)
                .build();
    }

    public Ability setCampaign() {
        Consumer<MessageContext> campaign = tableTop::setCurrentCampaign;

        return Ability
                .builder()
                .name("setcampaign")
                .info("sets current campaign")
                .input(1)
                .locality(USER)
                .privacy(PUBLIC)
                .action(campaign)
                .build();
    }

    public Ability setCampaignName() {
        Consumer<MessageContext> campaignName = tableTop::setCampaignName;

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
        Consumer<MessageContext> password = tableTop::setPassword;

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
        Consumer<MessageContext> multi = tableTop::setMulticlassLimit;

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
        Consumer<MessageContext> playersList = tableTop::showPlayers;

        return Ability
                .builder()
                .name("showplayers")
                .info("show")
                .input(0)
                .locality(USER)
                .privacy(PUBLIC)
                .action(playersList)
                .build();
    }

    public Ability createPlayerDnD() {
        Consumer<MessageContext> player = tableTop::addAPlayerDnD;

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
        Consumer<MessageContext> halt = tableTop::haltCreationOfPlayerDnD;

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

        if (currentUser.creationOfPlayerCharacter && update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
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
                    tableTop.playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

                } else if (update.hasCallbackQuery()) {
                    response = update.getCallbackQuery().getData();
                    tableTop.playerDnDGeneratorAllocator.get(currentUser.creationStage).accept(currentUser, response);

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

            if (currentUser.rollCustom) {
                jackOfAllTrades.onRollCustomPreset(currentUser, responseQuery);
            }
            else if (currentUser.isEndingACampaign) {
                if (!Objects.equals(currentUser.activeDm.username, currentUser.username)) {
                    silent.send(Constants.CAMPAIGN_END_RESTRICTION, currentUser.getChatId());
                }
                else {
                    switch (responseQuery) {
                        case "Да":
                            currentUser.activeDm = null;
                            currentUser.isHavingACampaign = false;
                            silent.send(Constants.CAMPAIGN_END, currentUser.getChatId());
                            break;
                        case "Нет":
                            currentUser.isEndingACampaign = false;
                            silent.send(Constants.CAMPAIGN_END_FALSE_ALARM, currentUser.getChatId());
                            break;
                        default:
                            currentUser.isEndingACampaign = false;
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

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand() && Objects.equals(currentUser.getChatId(), getChatId(update))) {

            if (currentUser.campaignNameIsChosen) {
                currentUser.activeDm.campaignName = update.getMessage().getText();
                currentUser.campaignNameIsChosen = false;

                ChatSession dungeonMaster = knowledge.getSession(String.valueOf(currentUser.activeDm.chatId));
                dungeonMaster.campaigns.put(currentUser.activeDm.campaignName, currentUser.activeDm.chatId);
                knowledge.renewListChat(dungeonMaster);

                silent.send(Constants.CAMPAIGN_CREATION_CONGRATULATION, currentUser.getChatId());
                silent.send(Constants.PLAYER_CREATION_WARNING, currentUser.getChatId());
            }

            else if (currentUser.creationOfPlayerCharacter && !currentUser.nameIsChosen) {
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