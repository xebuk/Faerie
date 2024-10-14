package game.items.weapons;

import game.items.Ammunition;
import game.items.artefacts.CommonItem;

public class Air extends Ammunition {

    public Air() {
        this.damage = 0;

        this.specialBonuses = new CommonItem();
    }
}
