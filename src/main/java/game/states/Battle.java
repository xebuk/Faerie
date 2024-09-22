package game.states;

import game.entities.NonPlayerCharacter;
import game.entities.PlayerCharacter;

public class Battle {
    public PlayerCharacter MC;
    public NonPlayerCharacter Enemy;
    public boolean PlayerTurn;

    Battle(PlayerCharacter MC, NonPlayerCharacter Enemy) {
        this.MC = MC;
        this.Enemy = Enemy;
    }
}
