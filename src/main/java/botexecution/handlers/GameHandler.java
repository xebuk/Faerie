package botexecution.handlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.MediaHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import common.Constants;
import game.characteristics.Job;
import game.characteristics.jobs.*;
import game.entities.PlayerCharacter;
import game.environment.Drawer;
import game.environment.DungeonController;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.*;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final MediaHandler pager;
    private final DiceHandler diceHoarder;

    public final HashMap<String, Job> jobAllocatorGame = new HashMap<>();
    public final HashMap<String, BiConsumer<PlayerCharacter, Integer>> statAllocatorGame = new HashMap<>();
    public final HashMap<String, Consumer<DungeonController>> movementAllocatorGame = new HashMap<>();
    public final Drawer fieldOfView = new Drawer();

    public GameHandler(DataHandler knowledge, TextHandler walkieTalkie, MediaHandler pager, DiceHandler diceHoarder) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.pager = pager;
        this.diceHoarder = diceHoarder;

        jobAllocatorGame.put(Constants.CREATION_MENU_FIGHTER, new Fighter());
        jobAllocatorGame.put(Constants.CREATION_MENU_CLERIC, new Cleric());
        jobAllocatorGame.put(Constants.CREATION_MENU_MAGE, new Mage());
        jobAllocatorGame.put(Constants.CREATION_MENU_ROGUE, new Rogue());
        jobAllocatorGame.put(Constants.CREATION_MENU_RANGER, new Ranger());

        statAllocatorGame.put(Constants.CREATION_MENU_STRENGTH, PlayerCharacter::initStrength);
        statAllocatorGame.put(Constants.CREATION_MENU_DEXTERITY, PlayerCharacter::initDexterity);
        statAllocatorGame.put(Constants.CREATION_MENU_CONSTITUTION, PlayerCharacter::initConstitution);
        statAllocatorGame.put(Constants.CREATION_MENU_INTELLIGENCE, PlayerCharacter::initIntelligence);
        statAllocatorGame.put(Constants.CREATION_MENU_WISDOM, PlayerCharacter::initWisdom);
        statAllocatorGame.put(Constants.CREATION_MENU_CHARISMA, PlayerCharacter::initCharisma);

        movementAllocatorGame.put("Вперед", DungeonController::stepForward);
        movementAllocatorGame.put("Влево", DungeonController::stepLeft);
        movementAllocatorGame.put("Вправо", DungeonController::stepRight);
        movementAllocatorGame.put("Назад", DungeonController::stepBackward);
        movementAllocatorGame.put("Повернуться налево", DungeonController::turnLeft);
        movementAllocatorGame.put("Повернуться направо", DungeonController::turnRight);
    }

    public void createPlayer(MessageContext ctx) {
        ChatSession newUser = knowledge.getSession(ctx.chatId().toString());
        walkieTalkie.patternExecute(newUser, Constants.CREATION_MENU_CHOOSE_NAME, null, false);

        newUser.creationOfPlayerCharacter = true;
        knowledge.renewListChat(newUser);
    }

    public void startGame(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        if (currentUser.playerCharacter == null) {
            walkieTalkie.patternExecute(currentUser, Constants.GAME_RESTRICTED, null, false);
            return;
        }

        currentUser.crawler = new DungeonController(Constants.gameMazeWidth, Constants.gameMazeHeight,
                            Constants.gameRoomMinSize, Constants.gameRoomMaxSize, Constants.gameRoomCount);

        fieldOfView.setMaze(currentUser);
        fieldOfView.startDrawing(Color.BLACK, currentUser.crawler.getSceneObjects(),
                currentUser.crawler.getLights(), currentUser.crawler.getVisionLight());
        fieldOfView.drawScene();
        fieldOfView.endDrawing();

        walkieTalkie.patternExecute(currentUser, Constants.GAME_START, null, false);
        currentUser.lastDungeonMessageId = pager.sendPic(currentUser);

        currentUser.gameInSession = true;

        knowledge.renewListChat(currentUser);
    }

    public void pauseGame(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        currentUser.pauseGame = !currentUser.pauseGame;

        if (currentUser.pauseGame) {
            DeleteMessage del = new DeleteMessage(currentUser.getChatId().toString(), currentUser.lastDungeonMessageId);
            walkieTalkie.getSilent().execute(del);
            walkieTalkie.patternExecute(currentUser, Constants.GAME_PAUSE, null, false);
        }
        else {
            walkieTalkie.patternExecute(currentUser, Constants.GAME_CONTINUE, null, false);
            fieldOfView.setMaze(currentUser);
            fieldOfView.startDrawing(Color.BLACK, currentUser.crawler.getSceneObjects(),
                    currentUser.crawler.getLights(), currentUser.crawler.getVisionLight());
            fieldOfView.drawScene();
            fieldOfView.endDrawing();
            currentUser.lastDungeonMessageId = pager.sendPic(currentUser);
        }
        knowledge.renewListChat(currentUser);
    }

    public void expungeGame(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        currentUser.pauseGame = false;
        currentUser.gameInSession = false;
        currentUser.crawler = null;

        walkieTalkie.patternExecute(currentUser, Constants.GAME_EXPUNGE, null, false);
        knowledge.renewListChat(currentUser);
    }

    public void gameMainLoop(ChatSession cs, Update update) {
        try {
            movementAllocatorGame.get(update.getCallbackQuery().getData()).accept(cs.crawler);
            fieldOfView.setMaze(cs);
            fieldOfView.startDrawing(Color.BLACK, cs.crawler.getSceneObjects(),
                    cs.crawler.getLights(), cs.crawler.getVisionLight());
            fieldOfView.drawScene();
            fieldOfView.endDrawing();
            pager.gamePovUpdater(cs, update.getCallbackQuery().getMessage().getMessageId());
        } catch (Exception e) {
            walkieTalkie.patternExecute(cs, "Попробуйте ещё раз.", null, false);
        } finally {
            knowledge.renewListChat(cs);
        }
    }

    public void characterCreationStart(ChatSession cs, Update update) {
        cs.playerCharacter = new PlayerCharacter();
        cs.playerCharacter.setName(update.getMessage().getText());
        cs.nameIsChosen = true;

        walkieTalkie.patternExecute(cs, Constants.CREATION_MENU_CHOOSE_JOB, KeyboardFactory.jobSelectionBoard(), false);

        knowledge.renewListChat(cs);
    }

    public void characterCreationMainLoop(ChatSession cs, Update update) {
        if (cs.statProgress.isEmpty()) {
                cs.playerCharacter.setJob(jobAllocatorGame.get(update.getCallbackQuery().getData()));
                walkieTalkie.patternExecute(cs, Constants.CREATION_MENU_SET_STATS, null, false);

                cs.statProgress.add(update.getCallbackQuery().getData());
                cs.luck = diceHoarder.D6FourTimesCreation();

                walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(cs.luck), KeyboardFactory.assignStatsBoardGame(cs.statProgress), false);
                knowledge.renewListChat(cs);
            }
            else {
                if (cs.statProgress.size() == 6) {
                    statAllocatorGame.get(update.getCallbackQuery().getData()).accept(cs.playerCharacter, cs.luck.get(4));

                    cs.playerCharacter.initHealth();
                    cs.playerCharacter.setArmorClass();
                    cs.playerCharacter.setAttackDice();

                    walkieTalkie.patternExecute(cs, cs.playerCharacter.statWindow(), null, false);

                    cs.statProgress.clear();
                    cs.creationOfPlayerCharacter = false;
                    cs.nameIsChosen = false;
                    knowledge.renewListChat(cs);
                }
                else {
                    statAllocatorGame.get(update.getCallbackQuery().getData()).accept(cs.playerCharacter, cs.luck.get(4));
                    cs.statProgress.add(update.getCallbackQuery().getData());
                    cs.luck = diceHoarder.D6FourTimesCreation();

                    walkieTalkie.patternExecute(cs, diceHoarder.D6FourTimes(cs.luck), KeyboardFactory.assignStatsBoardGame(cs.statProgress), false);
                    knowledge.renewListChat(cs);
                }
            }
    }
}
