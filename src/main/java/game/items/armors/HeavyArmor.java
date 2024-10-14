package game.items.armors;

import game.items.Armor;
import game.items.artefacts.CommonItem;

public class HeavyArmor extends Armor {

    public HeavyArmor() {
        this.armorValue = 14;

        this.specialBonuses = new CommonItem();
    }
}
