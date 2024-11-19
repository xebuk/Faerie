package dnd.equipment;

import dnd.values.ArmorTypeDnD;

public class ArmorDnD extends ItemDnD {
    public ArmorTypeDnD type;

    public int armorClass;
    public int dexterityModMax;

    public int strengthRequirement;

    public boolean hasStealthDisadvantage;

    public static class BreastplateDnD {
    }

    public static class ChainMailDnD {
    }

    public static class ChainShirtDnD {
    }

    public static class HalfPlateDnD {
    }

    public static class HideArmorDnD {
    }

    public static class LeatherArmorDnD {
    }

    public static class PaddedArmorDnD extends ArmorDnD {

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

    public static class PlateArmorDnD {
    }

    public static class RingMailDnD {
    }

    public static class ScaleMailDnD {
    }

    public static class ShieldDnD {
    }

    public static class SpikedArmorDnD {
    }

    public static class SplintArmorDnD {
    }

    public static class StuddedLeatherArmorDnD {
    }
}
