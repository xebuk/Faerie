package game.items.armors;

import game.items.Armor;
import game.items.artefacts.CommonItem;

public class Robe extends Armor {

    public Robe() {
        this.armorValue = 6;

        this.specialBonuses = new CommonItem();
    }
}
