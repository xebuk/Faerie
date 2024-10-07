package game.items.armors;

import game.items.Armor;
import game.items.artefacts.CommonItem;

public class LightArmor extends Armor {

    public LightArmor() {
        this.armorValue = 8;

        this.specialBonuses = new CommonItem();
    }
}
