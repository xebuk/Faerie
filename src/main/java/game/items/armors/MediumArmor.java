package game.items.armors;

import game.items.Armor;
import game.items.artefacts.CommonItem;

public class MediumArmor extends Armor {

    public MediumArmor() {
        this.armorValue = 10;

        this.specialBonuses = new CommonItem();
    }
}
