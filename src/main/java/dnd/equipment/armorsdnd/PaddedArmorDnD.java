package dnd.equipment.armorsdnd;

import dnd.equipment.ArmorDnD;
import dnd.values.ArmorTypeDnD;

public class PaddedArmorDnD extends ArmorDnD {

    public PaddedArmorDnD() {
        this.name = "Стеганный";
        this.type = ArmorTypeDnD.LIGHT;

        this.value = 5;

        this.armorClass = 11;
        this.dexterityModMax = 0;

        this.strengthRequirement = 0;

        this.hasStealthDisadvantage = true;

        this.weight = 8;
    }
}
