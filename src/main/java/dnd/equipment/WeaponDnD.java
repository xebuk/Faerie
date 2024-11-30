package dnd.equipment;

import common.Constants;
import dnd.values.equipmentids.WeaponsDnD;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.aspectvalues.WeaponTraitsDnD;

import java.util.HashSet;

public class WeaponDnD extends ItemDnD {
    public WeaponsDnD id;

    public WeaponsDnD type;
    public WeaponsDnD range;

    public String attackDice;
    public String damageType;

    public HashSet<WeaponTraitsDnD> traits;
    public int minDistance;
    public int maxDistance;

    public int hitBonus;

    public void setType(WeaponsDnD type) {
        this.type = type;
    }

    public void setRange(WeaponsDnD range) {
        this.range = range;
    }

    public void setAttackDice(String attackDice) {
        this.attackDice = attackDice;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void addTraits(WeaponTraitsDnD trait) {
        this.traits.add(trait);
    }

    public void delTraits(WeaponTraitsDnD trait) {
        this.traits.remove(trait);
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public void setHitBonus(int hitBonus) {
        this.hitBonus = hitBonus;
    }

    @Override
    public String toString() {
        StringBuilder weapon = new StringBuilder();
        weapon.append(name).append("\n").append(summary).append("\n");
        weapon.append("Количество в одном наборе: ").append(amountInInstance).append("\n");
        weapon.append("Цена: ").append(value).append(" ").append(currencyGrade).append("\n");
        weapon.append("Масса: ").append(weight).append("\n");
        weapon.append("Эффекты: ").append(effects).append("\n");
        weapon.append("Тип: ").append(type).append("\n");
        weapon.append("Специальность: ").append(range).append("\n");
        weapon.append("Кость атаки: ").append(attackDice).append("\n");
        weapon.append("Тип урона: ").append(damageType).append("\n");
        weapon.append("Черты оружия: ").append(traits).append("\n");
        weapon.append("Минимальное расстояние: ").append(minDistance).append("\n");
        weapon.append("Максимальное расстояние: ").append(maxDistance).append("\n");
        weapon.append("Бонус к точности: ").append(hitBonus).append("\n");
        return weapon.toString();
    }

    public WeaponDnD() {
        this.name = "Новое оружие";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.id = WeaponsDnD.CUSTOM;

        this.type = WeaponsDnD.SIMPLE;
        this.range = WeaponsDnD.MELEE;

        this.attackDice = "1d4";
        this.damageType = "Дробящий";

        this.traits = new HashSet<>();

        this.minDistance = 0;
        this.maxDistance = 5;

        this.hitBonus = 0;
        this.effects = "Нет.";
    }

    public static class BattleaxeDnD extends WeaponDnD {

        public BattleaxeDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class BlowgunDnD extends WeaponDnD {

        public BlowgunDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ClubDnD extends WeaponDnD {

        public ClubDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class DaggerDnD extends WeaponDnD {

        public DaggerDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class DartDnD extends WeaponDnD {

        public DartDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class DoubleBladedScimitarDnD extends WeaponDnD {

        public DoubleBladedScimitarDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class FlailDnD extends WeaponDnD {

        public FlailDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GlaiveDnD extends WeaponDnD {

        public GlaiveDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreataxeDnD extends WeaponDnD {

        public GreataxeDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreatclubDnD extends WeaponDnD {

        public GreatclubDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreatswordDnD extends WeaponDnD {

        public GreatswordDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HalbergDnD extends WeaponDnD {

        public HalbergDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HandaxeDnD extends WeaponDnD {

        public HandaxeDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HandCrossbowDnD extends WeaponDnD {

        public HandCrossbowDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HeavyCrossbowDnD extends WeaponDnD {

        public HeavyCrossbowDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class JavelinDnD extends WeaponDnD {

        public JavelinDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LanceDnD extends WeaponDnD {

        public LanceDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LightCrossbowDnD extends WeaponDnD {

        public LightCrossbowDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LightHammerDnD extends WeaponDnD {

        public LightHammerDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LongbowDnD extends WeaponDnD {

        public LongbowDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LongswordDnD extends WeaponDnD {

        public LongswordDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MaceDnD extends WeaponDnD {

        public MaceDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MaulDnD extends WeaponDnD {

        public MaulDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MorningstarDnD extends WeaponDnD {

        public MorningstarDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class NetDnD extends WeaponDnD {

        public NetDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class PikeDnD extends WeaponDnD {

        public PikeDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class QuarterstaffDnD extends WeaponDnD {

        public QuarterstaffDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class RapierDnD extends WeaponDnD {

        public RapierDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ScimitarDnD extends WeaponDnD {

        public ScimitarDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ShortbowDnD extends WeaponDnD {

        public ShortbowDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ShortswordDnD extends WeaponDnD {

        public ShortswordDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SickleDnD extends WeaponDnD {

        public SickleDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SlingDnD extends WeaponDnD {

        public SlingDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SpearDnD extends WeaponDnD {

        public SpearDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class TridentDnD extends WeaponDnD {

        public TridentDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WarhammerDnD extends WeaponDnD {

        public WarhammerDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WarPickDnD extends WeaponDnD {

        public WarPickDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WhipDnD extends WeaponDnD {

        public WhipDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class YklwaDnD extends WeaponDnD {

        public YklwaDnD() {
            this.name = "Новое оружие";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.CUSTOM;

            this.type = WeaponsDnD.SIMPLE;
            this.range = WeaponsDnD.MELEE;

            this.attackDice = "1d4";
            this.damageType = "Дробящий";

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }
}
