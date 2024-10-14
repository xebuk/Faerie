package game.items.weapons;

import game.items.Weapon;
import game.items.artefacts.CommonItem;

public class Dagger extends Weapon {

    public Dagger() {
        this.usesAmmunition = false;
        this.ammunitionType = new Air();

        this.attackDice = "1d6";
        this.attackPower = 5;

        this.specialBonuses = new CommonItem();
    }
}
