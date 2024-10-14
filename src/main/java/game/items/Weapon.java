package game.items;

import java.io.Serializable;

public class Weapon implements Serializable {
    public boolean usesAmmunition;
    public Ammunition ammunitionType;

    public String attackDice;
    public int attackPower;

    public Artefact specialBonuses;
}
