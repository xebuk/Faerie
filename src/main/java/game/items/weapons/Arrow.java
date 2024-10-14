package game.items.weapons;

import game.items.Ammunition;
import game.items.artefacts.CommonItem;

public class Arrow extends Ammunition {

    public Arrow() {
        this.damage = 2;

        this.specialBonuses = new CommonItem();
    }
}
