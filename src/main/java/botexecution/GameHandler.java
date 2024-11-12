package botexecution;

import common.Constants;
import common.UserDataHandler;
import game.characteristics.Job;
import game.characteristics.jobs.*;
import game.entities.PlayerCharacter;
import game.environment.Drawer;
import game.environment.DungeonController;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.awt.*;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GameHandler {
    private final TextHandler walkieTalkie;
    private final MediaHandler pager;

    public final HashMap<String, Job> jobAllocatorGame = new HashMap<>();
    public final HashMap<String, BiConsumer<PlayerCharacter, Integer>> statAllocatorGame = new HashMap<>();
    public final HashMap<String, Consumer<DungeonController>> movementAllocatorGame = new HashMap<>();
    public final Drawer fieldOfView = new Drawer();

    public GameHandler(TextHandler walkieTalkie, MediaHandler pager) {
        this.walkieTalkie = walkieTalkie;
        this.pager = pager;

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
        ChatSession newUser = UserDataHandler.readSession(ctx.update());
        walkieTalkie.patternExecute(newUser, Constants.CREATION_MENU_CHOOSE_NAME, null, false);

        newUser.creationOfPlayerCharacter = true;
        UserDataHandler.saveSession(newUser);
    }

    public void startGame(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
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
        pager.sendPic(currentUser);

        currentUser.gameInSession = true;

        UserDataHandler.saveSession(currentUser);
    }

    public void pauseGame(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        currentUser.pauseGame = !currentUser.pauseGame;

        if (currentUser.pauseGame) {
            walkieTalkie.patternExecute(currentUser, Constants.GAME_PAUSE, null, false);
        }
        else {
            walkieTalkie.patternExecute(currentUser, Constants.GAME_CONTINUE, null, false);
            fieldOfView.setMaze(currentUser);
            fieldOfView.startDrawing(Color.BLACK, currentUser.crawler.getSceneObjects(),
                    currentUser.crawler.getLights(), currentUser.crawler.getVisionLight());
            fieldOfView.drawScene();
            fieldOfView.endDrawing();
            pager.sendPic(currentUser);
        }
        UserDataHandler.saveSession(currentUser);
    }

    public void expungeGame(MessageContext ctx) {
        ChatSession currentUser = UserDataHandler.readSession(ctx.update());
        currentUser.pauseGame = true;
        currentUser.gameInSession = false;
        currentUser.crawler = null;

        walkieTalkie.patternExecute(currentUser, Constants.GAME_EXPUNGE, null, false);

    }
}
