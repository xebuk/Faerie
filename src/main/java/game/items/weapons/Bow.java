package game.items.weapons;

import game.items.Weapon;
import game.items.artefacts.CommonItem;

public class Bow extends Weapon {

    public Bow() {
        this.usesAmmunition = true;
        this.ammunitionType = new Arrow();

        this.attackDice = "1d6";
        this.attackPower = 4;

        this.specialBonuses = new CommonItem();
    }
}
