package dnd.equipment;

import common.Constants;
import dnd.values.equipmentids.WeaponsDnD;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.aspectvalues.WeaponTraitsDnD;
import dnd.values.masteryvalues.DamageTypeDnD;

import java.util.HashSet;
import java.util.Set;

public class WeaponDnD extends ItemDnD {
    public WeaponsDnD id;

    public WeaponsDnD type;
    public WeaponsDnD range;

    public String attackDice;
    public String attackDiceVersatile;
    public DamageTypeDnD damageType;

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
        this.damageType = DamageTypeDnD.getType(damageType);
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
        weapon.append("Тип: ").append(type.toString()).append("\n");
        weapon.append("Специальность: ").append(range.toString()).append("\n");
        weapon.append("Кость атаки: ").append(attackDice).append("\n");
        weapon.append("Тип урона: ").append(damageType.toString()).append("\n");
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
        this.attackDiceVersatile = "1d4";
        this.damageType = DamageTypeDnD.BLUDGEONING;

        this.traits = new HashSet<>();

        this.minDistance = 0;
        this.maxDistance = 5;

        this.hitBonus = 0;
        this.effects = "Нет.";
    }

    public static class BattleaxeDnD extends WeaponDnD {

        public BattleaxeDnD() {
            this.name = "Боевой топор";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = WeaponsDnD.BATTLEAXE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = "1d10";
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.VERSATILE));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class BlowgunDnD extends WeaponDnD {

        public BlowgunDnD() {
            this.name = "Духовая трубка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = WeaponsDnD.BLOWGUN;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.RELOAD));

            this.minDistance = 25;
            this.maxDistance = 100;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ClubDnD extends WeaponDnD {

        public ClubDnD() {
            this.name = "Дубинка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.CLUB;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class DaggerDnD extends WeaponDnD {

        public DaggerDnD() {
            this.name = "Кинжал";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.DAGGER;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.THROWABLE,
                    WeaponTraitsDnD.FENCING));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class DartDnD extends WeaponDnD {

        public DartDnD() {
            this.name = "Дротик";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0.25;

            this.id = WeaponsDnD.DART;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.THROWABLE,
                    WeaponTraitsDnD.FENCING));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class FlailDnD extends WeaponDnD {

        public FlailDnD() {
            this.name = "Цеп";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.FLAIL;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GlaiveDnD extends WeaponDnD {

        public GlaiveDnD() {
            this.name = "Глефа";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 20;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = WeaponsDnD.GLAIVE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d10";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.REACH,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreataxeDnD extends WeaponDnD {

        public GreataxeDnD() {
            this.name = "";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 30;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 7;

            this.id = WeaponsDnD.GREATAXE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d12";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreatclubDnD extends WeaponDnD {

        public GreatclubDnD() {
            this.name = "Палица";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 10;

            this.id = WeaponsDnD.GREATCLUB;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class GreatswordDnD extends WeaponDnD {

        public GreatswordDnD() {
            this.name = "Двуручный меч";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = WeaponsDnD.GREATSWORD;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "2d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HalbergDnD extends WeaponDnD {

        public HalbergDnD() {
            this.name = "Алебарда";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 20;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = WeaponsDnD.HALBERG;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d10";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.REACH,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HandaxeDnD extends WeaponDnD {

        public HandaxeDnD() {
            this.name = "Ручной топор";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.HANDAXE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.THROWABLE));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HandCrossbowDnD extends WeaponDnD {

        public HandCrossbowDnD() {
            this.name = "Ручной арбалет";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 75;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.HAND_CROSSBOW;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.RELOAD));

            this.minDistance = 30;
            this.maxDistance = 120;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class HeavyCrossbowDnD extends WeaponDnD {

        public HeavyCrossbowDnD() {
            this.name = "Тяжелый арбалет";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 18;

            this.id = WeaponsDnD.HEAVY_CROSSBOW;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d10";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.TWOHANDED,
                    WeaponTraitsDnD.RELOAD,
                    WeaponTraitsDnD.HEAVY));

            this.minDistance = 100;
            this.maxDistance = 400;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class JavelinDnD extends WeaponDnD {

        public JavelinDnD() {
            this.name = "Метательное копье";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.JAVELIN;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.THROWABLE));

            this.minDistance = 30;
            this.maxDistance = 120;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LanceDnD extends WeaponDnD {

        public LanceDnD() {
            this.name = "Длинное копье";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = WeaponsDnD.LANCE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d12";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.REACH,
                    WeaponTraitsDnD.SPECIAL));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LightCrossbowDnD extends WeaponDnD {

        public LightCrossbowDnD() {
            this.name = "Легкий арбалет";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = WeaponsDnD.LIGHT_CROSSBOW;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.TWOHANDED,
                    WeaponTraitsDnD.RELOAD));

            this.minDistance = 80;
            this.maxDistance = 320;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LightHammerDnD extends WeaponDnD {

        public LightHammerDnD() {
            this.name = "Легкий молот";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.LIGHT_HAMMER;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.THROWABLE));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LongbowDnD extends WeaponDnD {

        public LongbowDnD() {
            this.name = "Длинный лук";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.LONGBOW;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.TWOHANDED,
                    WeaponTraitsDnD.HEAVY));

            this.minDistance = 150;
            this.maxDistance = 600;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class LongswordDnD extends WeaponDnD {

        public LongswordDnD() {
            this.name = "Длинный меч";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.LONGSWORD;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = "1d10";
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.VERSATILE));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MaceDnD extends WeaponDnD {

        public MaceDnD() {
            this.name = "Булава";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = WeaponsDnD.MACE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MaulDnD extends WeaponDnD {

        public MaulDnD() {
            this.name = "Молот";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = WeaponsDnD.MAUL;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "2d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class MorningstarDnD extends WeaponDnD {

        public MorningstarDnD() {
            this.name = "Моргенштерн";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = WeaponsDnD.MORNINGSTAR;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class NetDnD extends WeaponDnD {

        public NetDnD() {
            this.name = "Сеть";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.NET;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "0";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.NONE;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.SPECIAL,
                    WeaponTraitsDnD.THROWABLE));

            this.minDistance = 5;
            this.maxDistance = 15;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class PikeDnD extends WeaponDnD {

        public PikeDnD() {
            this.name = "Пика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 18;

            this.id = WeaponsDnD.PIKE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d10";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.HEAVY,
                    WeaponTraitsDnD.REACH,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class QuarterstaffDnD extends WeaponDnD {

        public QuarterstaffDnD() {
            this.name = "Боевой посох";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 4;

            this.id = WeaponsDnD.QUARTERSTAFF;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = "1d8";
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.VERSATILE));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class RapierDnD extends WeaponDnD {

        public RapierDnD() {
            this.name = "Рапира";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.RAPIER;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.FENCING));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ScimitarDnD extends WeaponDnD {

        public ScimitarDnD() {
            this.name = "Скимитар";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.SCIMITAR;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.FENCING));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ShortbowDnD extends WeaponDnD {

        public ShortbowDnD() {
            this.name = "Короткий лук";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.SHORTBOW;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO,
                    WeaponTraitsDnD.TWOHANDED));

            this.minDistance = 80;
            this.maxDistance = 320;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class ShortswordDnD extends WeaponDnD {

        public ShortswordDnD() {
            this.name = "Короткий меч";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.SHORTSWORD;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT,
                    WeaponTraitsDnD.FENCING));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SickleDnD extends WeaponDnD {

        public SickleDnD() {
            this.name = "Серп";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.SICKLE;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.LIGHT));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SlingDnD extends WeaponDnD {

        public SlingDnD() {
            this.name = "Праща";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = WeaponsDnD.SLING;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.AMMO));

            this.minDistance = 30;
            this.maxDistance = 120;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class SpearDnD extends WeaponDnD {

        public SpearDnD() {
            this.name = "Копье";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.SPEAR;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = "1d8";
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.THROWABLE,
                    WeaponTraitsDnD.VERSATILE));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class TridentDnD extends WeaponDnD {

        public TridentDnD() {
            this.name = "Трезубец";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = WeaponsDnD.TRIDENT;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d6";
            this.attackDiceVersatile = "1d8";
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.THROWABLE,
                    WeaponTraitsDnD.VERSATILE));

            this.minDistance = 20;
            this.maxDistance = 60;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WarhammerDnD extends WeaponDnD {

        public WarhammerDnD() {
            this.name = "Боевой молот";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.WARHAMMER;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = "1d10";
            this.damageType = DamageTypeDnD.BLUDGEONING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.VERSATILE));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WarPickDnD extends WeaponDnD {

        public WarPickDnD() {
            this.name = "Боевая кирка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = WeaponsDnD.WAR_PICK;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d8";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.PIERCING;

            this.traits = new HashSet<>();

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }

    public static class WhipDnD extends WeaponDnD {

        public WhipDnD() {
            this.name = "Кнут";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = WeaponsDnD.WHIP;

            this.type = WeaponsDnD.whatType(this.id);
            this.range = WeaponsDnD.whatRange(this.id);

            this.attackDice = "1d4";
            this.attackDiceVersatile = this.attackDice;
            this.damageType = DamageTypeDnD.SLASHING;

            this.traits = new HashSet<>(Set.of(WeaponTraitsDnD.REACH,
                    WeaponTraitsDnD.FENCING));

            this.minDistance = 0;
            this.maxDistance = 5;

            this.hitBonus = 0;
            this.effects = "Нет.";
        }
    }
}
