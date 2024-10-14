package game.items.weapons;

import game.items.Weapon;
import game.items.artefacts.CommonItem;

public class HeavySword extends Weapon {

    public HeavySword() {
        this.usesAmmunition = false;
        this.ammunitionType = new Air();

        this.attackDice = "1d12";
        this.attackPower = 7;

        this.specialBonuses = new CommonItem();
    }
}
