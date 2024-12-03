package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.equipmentids.ArmorsDnD;

public class ArmorDnD extends ItemDnD {
    public ArmorsDnD id;
    public ArmorsDnD type;

    public int armorClass;
    public int dexterityModMax;

    public int strengthRequirement;

    public boolean hasStealthDisadvantage;

    public String effects;

    public void setType(ArmorsDnD type) {
        this.type = type;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public void setDexterityModMax(int dexterityModMax) {
        this.dexterityModMax = dexterityModMax;
    }

    public void setStrengthRequirement(int strengthRequirement) {
        this.strengthRequirement = strengthRequirement;
    }

    public void changeHasStealthDisadvantage() {
        this.hasStealthDisadvantage = !hasStealthDisadvantage;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    @Override
    public String toString() {
        StringBuilder armor = new StringBuilder();
        armor.append(name).append("\n").append(summary).append("\n");
        armor.append("Количество в одном наборе: ").append(amountInInstance).append("\n");
        armor.append("Цена: ").append(value).append(" ").append(currencyGrade).append("\n");
        armor.append("Масса: ").append(weight).append("\n");
        armor.append("Эффекты: ").append(effects).append("\n");
        armor.append("Тип: ").append(type).append("\n");
        armor.append("Класс брони: ").append(armorClass).append("\n");
        armor.append("Модификатор ловкости: ").append(dexterityModMax).append("\n");
        armor.append("Требование к силе: ").append(strengthRequirement).append("\n");
        armor.append("Есть ли помеха к скрытности: ").append(hasStealthDisadvantage).append("\n");
        return armor.toString();
    }

    public ArmorDnD() {
        this.name = "Своя броня";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.id = ArmorsDnD.CUSTOM;
        this.type = ArmorsDnD.LIGHT;

        this.armorClass = 10;
        this.dexterityModMax = 0;

        this.strengthRequirement = 0;

        this.hasStealthDisadvantage = false;

        this.effects = "Нет.";
    }

    public static class BreastplateDnD extends ArmorDnD {

        public BreastplateDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class ChainMailDnD extends ArmorDnD {

        public ChainMailDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class ChainShirtDnD extends ArmorDnD {

        public ChainShirtDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class HalfPlateDnD extends ArmorDnD {

        public HalfPlateDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class HideArmorDnD extends ArmorDnD {

        public HideArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class LeatherArmorDnD extends ArmorDnD {

        public LeatherArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class PaddedArmorDnD extends ArmorDnD {

        public PaddedArmorDnD() {
            this.name = "Стеганный";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 8;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 11;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = true;

            this.effects = "Нет.";
        }
    }

    public static class PlateArmorDnD extends ArmorDnD {
        public PlateArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class RingMailDnD extends ArmorDnD {

        public RingMailDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class ScaleMailDnD extends ArmorDnD {

        public ScaleMailDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class ShieldDnD extends ArmorDnD {

        public ShieldDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class SpikedArmorDnD extends ArmorDnD {

        public SpikedArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class SplintArmorDnD extends ArmorDnD {

        public SplintArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }

    public static class StuddedLeatherArmorDnD extends ArmorDnD {

        public StuddedLeatherArmorDnD() {
            this.name = "Своя броня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ArmorsDnD.CUSTOM;
            this.type = ArmorsDnD.LIGHT;

            this.armorClass = 10;
            this.dexterityModMax = 0;

            this.strengthRequirement = 0;

            this.hasStealthDisadvantage = false;

            this.effects = "Нет.";
        }
    }
}
