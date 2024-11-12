package botexecution;

import common.*;

import game.entities.PlayerCharacter;
import org.telegram.telegrambots.abilitybots.api.objects.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.*;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.*;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

public class AbilBot extends AbilityBot {
    private final TextHandler walkieTalkie;
    private final MediaHandler pager;
    private final CommonMethodsHandler jackOfAllTrades;
    private final GameHandler dungeonCrawl;
    private final DnDHandler tableTop;

    public AbilBot() throws IOException {
        super(new OkHttpTelegramClient(DataReader.readToken()), "Faerie");
        this.walkieTalkie = new TextHandler(this.getSilent());
        this.pager = new MediaHandler(this.getTelegramClient());
        this.jackOfAllTrades = new CommonMethodsHandler(walkieTalkie);
        this.dungeonCrawl = new GameHandler(walkieTalkie, pager);
        this.tableTop = new DnDHandler(walkieTalkie);
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

        ChatSession currentUser;
        try {
            currentUser = UserDataHandler.readSession(update);
        } catch (Exception e) {
            UserDataHandler.createChatFile(getChatId(update).toString());
            currentUser = new ChatSession(update);
        }

        if (currentUser.creationOfPlayerCharacter && update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            if (currentUser.statProgress.isEmpty()) {
                currentUser.playerCharacter.setJob(dungeonCrawl.jobAllocatorGame.get(update.getCallbackQuery().getData()));
                silent.send(Constants.CREATION_MENU_SET_STATS, currentUser.getChatId());

                currentUser.statProgress.add(update.getCallbackQuery().getData());
                currentUser.luck = DiceNew.D6FourTimesCreation();

                walkieTalkie.patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardGame(currentUser.statProgress), false);
                UserDataHandler.saveSession(currentUser);
            }
            else {
                if (currentUser.statProgress.size() == 6) {
                    dungeonCrawl.statAllocatorGame.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));

                    currentUser.playerCharacter.initHealth();
                    currentUser.playerCharacter.setArmorClass();
                    currentUser.playerCharacter.setAttackDice();

                    silent.send(currentUser.playerCharacter.statWindow(), currentUser.getChatId());
                    UserDataHandler.savePlayerCharacter(currentUser.playerCharacter, update);

                    currentUser.statProgress.clear();
                    currentUser.creationOfPlayerCharacter = false;
                    currentUser.nameIsChosen = false;
                    UserDataHandler.saveSession(currentUser);
                }
                else {
                    dungeonCrawl.statAllocatorGame.get(update.getCallbackQuery().getData()).accept(currentUser.playerCharacter, currentUser.luck.get(4));
                    currentUser.statProgress.add(update.getCallbackQuery().getData());
                    currentUser.luck = DiceNew.D6FourTimesCreation();

                    walkieTalkie.patternExecute(currentUser, DiceNew.D6FourTimes(currentUser.luck), KeyboardFactory.assignStatsBoardGame(currentUser.statProgress), false);
                    UserDataHandler.saveSession(currentUser);
                }
            }
        }

        else if (currentUser.gameInSession && !currentUser.pauseGame && update.hasCallbackQuery()) {
            try {
                dungeonCrawl.movementAllocatorGame.get(update.getCallbackQuery().getData()).accept(currentUser.crawler);
                dungeonCrawl.fieldOfView.setMaze(currentUser);
                dungeonCrawl.fieldOfView.startDrawing(Color.BLACK, currentUser.crawler.getSceneObjects(),
                        currentUser.crawler.getLights(), currentUser.crawler.getVisionLight());
                dungeonCrawl.fieldOfView.drawScene();
                dungeonCrawl.fieldOfView.endDrawing();
                pager.gamePovUpdater(currentUser, update.getCallbackQuery().getMessage().getMessageId());
            } catch (Exception e) {
                silent.send("Попробуйте ещё раз.", currentUser.getChatId());
            }
            finally {
                UserDataHandler.saveSession(currentUser);
            }
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
                walkieTalkie.patternExecute(currentUser, "А? Если хотите сделать что-то другое, используйте /haltcreation", null, false);
            }
        }

        else if (update.hasCallbackQuery() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            CallbackQuery query = update.getCallbackQuery();
            String responseQuery = query.getData();

            currentUser.setUsername(query.getFrom().getUserName());

            if (currentUser.rollCustom) {
                String[] dices = responseQuery.trim().split("d");
                try {
                    walkieTalkie.patternExecute(currentUser, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);
                } catch (NumberFormatException e) {
                    silent.send(Constants.CUSTOM_DICE_ERROR, currentUser.getChatId());
                }
                currentUser.rollCustom = false;
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
            UserDataHandler.saveSession(currentUser);
        }

        else if (update.hasMessage() && update.getMessage().hasText() && !update.getMessage().isCommand() && Objects.equals(currentUser.getChatId(), getChatId(update))) {
            currentUser.setUsername(update.getMessage().getForwardSenderName());

            if (currentUser.campaignNameIsChosen) {
                currentUser.activeDm.campaignName = update.getMessage().getText();
                currentUser.campaignNameIsChosen = false;

                ChatSession dungeonMaster = UserDataHandler.readSession(currentUser.activeDm.chatId);
                dungeonMaster.campaigns.put(currentUser.activeDm.campaignName, currentUser.activeDm.chatId);
                UserDataHandler.saveSession(dungeonMaster);

                silent.send(Constants.CAMPAIGN_CREATION_CONGRATULATION, currentUser.getChatId());
                silent.send(Constants.PLAYER_CREATION_WARNING, currentUser.getChatId());
            }

            else if (currentUser.creationOfPlayerCharacter && !currentUser.nameIsChosen) {
                currentUser.playerCharacter = new PlayerCharacter();
                currentUser.playerCharacter.setName(update.getMessage().getText());
                currentUser.nameIsChosen = true;

                walkieTalkie.patternExecute(currentUser, Constants.CREATION_MENU_CHOOSE_JOB, KeyboardFactory.jobSelectionBoard(), false);

                UserDataHandler.saveSession(currentUser);
            }

            else if (currentUser.rollCustom) {
                try {
                    currentUser.dicePresets = UserDataHandler.readDicePresets(update);
                } catch (Exception ignored) {}

                if (!currentUser.dicePresets.contains(update.getMessage().getText())) {
                    currentUser.dicePresets.add(update.getMessage().getText());
                }
                UserDataHandler.saveDicePresets(currentUser.dicePresets, update);

                String[] dices = update.getMessage().getText().trim().split("d");
                walkieTalkie.patternExecute(currentUser, DiceNew.customDice(Integer.parseInt(dices[0]), Integer.parseInt(dices[1])), null, false);

                currentUser.rollCustom = false;
                UserDataHandler.saveSession(currentUser);
            }

            else if (currentUser.searchSuccess) {
                currentUser.title = update.getMessage().getText();
                try {
                    switch (currentUser.sectionId) {
                        case SPELLS:
                            walkieTalkie.articleMessaging(SiteParser.SpellsGrabber(currentUser.title), currentUser);
                            break;
                        case ITEMS:
                            walkieTalkie.articleMessaging(SiteParser.ItemsGrabber(currentUser.title), currentUser);
                            break;
                        case BESTIARY:
                            walkieTalkie.articleMessaging(SiteParser.BestiaryGrabber(currentUser.title), currentUser);
                            break;
                        case RACES:
                            walkieTalkie.articleMessaging(SiteParser.RacesGrabber(currentUser.title), currentUser);
                            break;
                        case FEATS:
                            walkieTalkie.articleMessaging(SiteParser.FeatsGrabber(currentUser.title), currentUser);
                            break;
                        case BACKGROUNDS:
                            walkieTalkie.articleMessaging(SiteParser.BackgroundsGrabber(currentUser.title), currentUser);
                            break;
                        default:
                            walkieTalkie.reportImpossible(currentUser);
                            break;
                    }
                    currentUser.sectionId = SearchCategories.NONE;
                    currentUser.searchSuccess = false;
                    currentUser.title = "";
                } catch (IOException e) {
                    silent.send(Constants.SEARCH_MESSAGE_FAIL_SECOND_STAGE, currentUser.getChatId());
                }
                UserDataHandler.saveSession(currentUser);
            }

            else if (!currentUser.sectionId.isEmpty()) {
                currentUser.searchSuccess = walkieTalkie.searchEngine(currentUser, update.getMessage().getText());
                UserDataHandler.saveSession(currentUser);
            }
        }
    UserDataHandler.saveSession(currentUser);
    }
}