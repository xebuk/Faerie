package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.aspectvalues.ItemsIdsDnD;

import java.io.Serializable;

public class ItemDnD implements Serializable {
    public String name;
    public String summary;

    public int amountInInstance;

    public int value;
    public CurrencyDnD currencyGrade;

    public double weight;

    public ItemsIdsDnD id;

    public String effects;

    public ItemDnD() {
        this.name = "Свой предмет";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.id = ItemsIdsDnD.CUSTOM_ITEM;

        this.effects = "Нет.";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAmountInInstance(int amountInInstance) {
        this.amountInInstance = amountInInstance;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCurrencyGrade(CurrencyDnD currencyGrade) {
        this.currencyGrade = currencyGrade;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    @Override
    public String toString() {
        StringBuilder item = new StringBuilder();
        item.append(name).append("\n").append(summary).append("\n");
        item.append("Количество в одном наборе: ").append(amountInInstance).append("\n");
        item.append("Цена на единицу: ").append(value).append(" ").append(currencyGrade.toString()).append("\n");
        item.append("Масса на единицу: ").append(weight).append("\n");
        item.append("Эффекты: ").append(effects).append("\n");
        return item.toString();
    }

    //предметы из магазина
    public static class AbacusDnD extends ItemDnD {

        public AbacusDnD() {
            this.name = "Абак";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.ABACUS;

            this.effects = "Нет.";
        }
    }
    public static class VialOfAcidDnD extends ItemDnD {

        public VialOfAcidDnD() {
            this.name = "Флакон кислоты";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.effects = "Нет.";
        }
    }
    public static class FlaskOfAlchemistFireDnD extends ItemDnD {

        public FlaskOfAlchemistFireDnD() {
            this.name = "Фляга алхимического огня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.FLASK_OF_ALCHEMIST_FIRE;

            this.effects = "Нет.";
        }
    }

    //амуниция
    public static class ArrowDnD extends ItemDnD {

        public ArrowDnD() {
            this.name = "Стрела";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 20;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.ARROW;

            this.effects = "Нет.";
        }
    }
    public static class BlowgunNeedleDnD extends ItemDnD {

        public BlowgunNeedleDnD() {
            this.name = "Игла для духовой трубки";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 50;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.BLOWGUN_NEEDLE;

            this.effects = "Нет.";
        }
    }
    public static class CrossbowBoltDnD extends ItemDnD {

        public CrossbowBoltDnD() {
            this.name = "Арбалетный болт";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 20;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1.5;

            this.id = ItemsIdsDnD.CROSSBOW_BOLT;

            this.effects = "Нет.";
        }
    }
    public static class SlingBulletDnD extends ItemDnD {

        public SlingBulletDnD() {
            this.name = "Снаряд для пращи";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 20;

            this.value = 4;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 1.5;

            this.id = ItemsIdsDnD.SLING_BULLET;

            this.effects = "Нет.";
        }
    }

    public static class VialOfAntitoxinDnD extends ItemDnD {

        public VialOfAntitoxinDnD() {
            this.name = "Флакон противоядия";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.VIAL_OF_ANTITOXIN;

            this.effects = "Нет.";
        }
    }

    //магические фокусы
    public static class CrystalDnD extends ItemDnD {

        public CrystalDnD() {
            this.name = "Кристалл";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.CRYSTAL;

            this.effects = "Нет.";
        }
    }
    public static class OrbDnD extends ItemDnD {

        public OrbDnD() {
            this.name = "Сфера";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 20;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.ORB;

            this.effects = "Нет.";
        }
    }
    public static class RodDnD extends ItemDnD {

        public RodDnD() {
            this.name = "Жезл";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.ROD;

            this.effects = "Нет.";
        }
    }
    public static class StaffDnD extends ItemDnD {

        public StaffDnD() {
            this.name = "Посох";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.STAFF;

            this.effects = "Нет.";
        }
    }
    public static class WandDnD extends ItemDnD {

        public WandDnD() {
            this.name = "Волшебная палочка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.WAND;

            this.effects = "Нет.";
        }
    }

    public static class BackpackDnD extends ItemDnD {

        public BackpackDnD() {
            this.name = "Рюкзак";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.BACKPACK;

            this.effects = "Нет.";
        }
    }
    public static class BallBearingDnD extends ItemDnD {

        public BallBearingDnD() {
            this.name = "Металлический шарик";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1000;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.BALL_BEARING;

            this.effects = "Нет.";
        }
    }
    public static class BarrelDnD extends ItemDnD {

        public BarrelDnD() {
            this.name = "Бочка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 70;

            this.id = ItemsIdsDnD.BARREL;

            this.effects = "Нет.";
        }
    }
    public static class BasketDnD extends ItemDnD {

        public BasketDnD() {
            this.name = "Корзина";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 4;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.BASKET;

            this.effects = "Нет.";
        }
    }
    public static class BedrollDnD extends ItemDnD {

        public BedrollDnD() {
            this.name = "Спальник";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 7;

            this.id = ItemsIdsDnD.BEDROLL;

            this.effects = "Нет.";
        }
    }
    public static class BellDnD extends ItemDnD {

        public BellDnD() {
            this.name = "Колокольчик";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.BELL;

            this.effects = "Нет.";
        }
    }
    public static class BlanketDnD extends ItemDnD {

        public BlanketDnD() {
            this.name = "Одеяло";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.BLANKET;

            this.effects = "Нет.";
        }
    }
    public static class BlockAndTackleDnD extends ItemDnD {

        public BlockAndTackleDnD() {
            this.name = "Блок и лебедка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.BLOCK_AND_TACKLE;

            this.effects = "Нет.";
        }
    }
    public static class BookDnD extends ItemDnD {

        public BookDnD() {
            this.name = "Книга";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.BOOK;

            this.effects = "Нет.";
        }
    }
    public static class GlassBottleDnD extends ItemDnD {

        public GlassBottleDnD() {
            this.name = "Стеклянная бутылка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.GLASS_BOTTLE;

            this.effects = "Нет.";
        }
    }
    public static class BucketDnD extends ItemDnD {

        public BucketDnD() {
            this.name = "Ведро";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.BUCKET;

            this.effects = "Нет.";
        }
    }
    public static class CaltropDnD extends ItemDnD {

        public CaltropDnD() {
            this.name = "Калтроп";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 20;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.CALTROP;

            this.effects = "Нет.";
        }
    }
    public static class CandleDnD extends ItemDnD {

        public CandleDnD() {
            this.name = "Свеча";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.CANDLE;

            this.effects = "Нет.";
        }
    }
    public static class CaseForCrossbowBoltsDnD extends ItemDnD {

        public CaseForCrossbowBoltsDnD() {
            this.name = "Контейнер для арбалетных болтов";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.CASE_FOR_CROSSBOW_BOLTS;

            this.effects = "Нет.";
        }
    }
    public static class CaseForMapsAndScrollsDnD extends ItemDnD {

        public CaseForMapsAndScrollsDnD() {
            this.name = "Контейнер для карт и свитков";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.CASE_FOR_CROSSBOW_BOLTS;

            this.effects = "Нет.";
        }
    }
    public static class ChainDnD extends ItemDnD {

        public ChainDnD() {
            this.name = "Цепь (10 футов)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.CHAIN;

            this.effects = "Нет.";
        }
    }
    public static class ChalkDnD extends ItemDnD {

        public ChalkDnD() {
            this.name = "Мел";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.CHALK;

            this.effects = "Нет.";
        }
    }
    public static class ChestDnD extends ItemDnD {

        public ChestDnD() {
            this.name = "Сундук";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 25;

            this.id = ItemsIdsDnD.CHEST;

            this.effects = "Нет.";
        }
    }
    public static class ClimberKitDnD extends ItemDnD {

        public ClimberKitDnD() {
            this.name = "Комплект для лазания";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 12;

            this.id = ItemsIdsDnD.CLIMBER_KIT;

            this.effects = "Нет.";
        }
    }
    public static class CommonClothesDnD extends ItemDnD {

        public CommonClothesDnD() {
            this.name = "Обычная одежда";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.COMMON_CLOTHES;

            this.effects = "Нет.";
        }
    }
    public static class CostumeClothesDnD extends ItemDnD {

        public CostumeClothesDnD() {
            this.name = "Костюм";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.COSTUME_CLOTHES;

            this.effects = "Нет.";
        }
    }
    public static class FineClothesDnD extends ItemDnD {

        public FineClothesDnD() {
            this.name = "Отличная одежда";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = ItemsIdsDnD.FINE_CLOTHES;

            this.effects = "Нет.";
        }
    }
    public static class TravelerClothesDnD extends ItemDnD {

        public TravelerClothesDnD() {
            this.name = "Дорожная одежда";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.TRAVELER_CLOTHES;

            this.effects = "Нет.";
        }
    }
    public static class ComponentPouchDnD extends ItemDnD {

        public ComponentPouchDnD() {
            this.name = "Мешочек с компонентами";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.COMPONENT_POUCH;

            this.effects = "Нет.";
        }
    }
    public static class CrowbarDnD extends ItemDnD {

        public CrowbarDnD() {
            this.name = "Ломик";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.CROWBAR;

            this.effects = "Нет.";
        }
    }

    //Фокусы друида
    public static class SprigOfMistletoeDnD extends ItemDnD {

        public SprigOfMistletoeDnD() {
            this.name = "Веточка омелы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.SPRIG_OF_MISTLETOE;

            this.effects = "Нет.";
        }
    }
    public static class TotemDnD extends ItemDnD {

        public TotemDnD() {
            this.name = "Тотем";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.TOTEM;

            this.effects = "Нет.";
        }
    }
    public static class WoodenStaffDnD extends ItemDnD {

        public WoodenStaffDnD() {
            this.name = "Деревянный посох";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.WOODEN_STAFF;

            this.effects = "Нет.";
        }
    }
    public static class YewWandDnD extends ItemDnD {

        public YewWandDnD() {
            this.name = "Тисовая палочка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.YEW_WAND;

            this.effects = "Нет.";
        }
    }

    public static class FishingTackleDnD extends ItemDnD {

        public FishingTackleDnD() {
            this.name = "Комплект для рыбалки";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.FISHING_TACKLE;

            this.effects = "Нет.";
        }
    }
    public static class FlaskOrTankardDnD extends ItemDnD {

        public FlaskOrTankardDnD() {
            this.name = "Фляга или большая кружка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.FLASK_OR_TANKARD;

            this.effects = "Нет.";
        }
    }
    public static class GrapplingHookDnD extends ItemDnD {

        public GrapplingHookDnD() {
            this.name = "Крюк-кошка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.GRAPPLING_HOOK;

            this.effects = "Нет.";
        }
    }
    public static class HammerDnD extends ItemDnD {

        public HammerDnD() {
            this.name = "Молоток";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.HAMMER;

            this.effects = "Нет.";
        }
    }
    public static class SledgehammerDnD extends ItemDnD {

        public SledgehammerDnD() {
            this.name = "Кузнечный молот";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.SLEDGEHAMMER;

            this.effects = "Нет.";
        }
    }
    public static class HealerKitDnD extends ItemDnD {

        public HealerKitDnD() {
            this.name = "Комплект целителя";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.HEALER_KIT;

            this.effects = "Нет.";
        }
    }

    //священные символы
    public static class AmuletDnD extends ItemDnD {

        public AmuletDnD() {
            this.name = "Амулет";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.AMULET;

            this.effects = "Нет.";
        }
    }
    public static class EmblemDnD extends ItemDnD {

        public EmblemDnD() {
            this.name = "Эмблема";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.EMBLEM;

            this.effects = "Нет.";
        }
    }
    public static class ReliquaryDnD extends ItemDnD {

        public ReliquaryDnD() {
            this.name = "Реликварий";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.RELIQUARY;

            this.effects = "Нет.";
        }
    }

    public static class FlaskOfHolyWaterDnD extends ItemDnD {

        public FlaskOfHolyWaterDnD() {
            this.name = "Фляга святой воды";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.FLASK_OF_HOLY_WATER;

            this.effects = "Нет.";
        }
    }
    public static class HourglassDnD extends ItemDnD {

        public HourglassDnD() {
            this.name = "Песочные часы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.HOURGLASS;

            this.effects = "Нет.";
        }
    }
    public static class HuntingTrapDnD extends ItemDnD {

        public HuntingTrapDnD() {
            this.name = "Охотничий капкан";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 25;

            this.id = ItemsIdsDnD.HUNTING_TRAP;

            this.effects = "Нет.";
        }
    }
    public static class InkDnD extends ItemDnD {

        public InkDnD() {
            this.name = "Чернила (бутылочка на 30 грамм)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.INK;

            this.effects = "Нет.";
        }
    }
    public static class InkPenDnD extends ItemDnD {

        public InkPenDnD() {
            this.name = "Писчее перо";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.INK_PEN;

            this.effects = "Нет.";
        }
    }
    public static class JugOrPitcherDnD extends ItemDnD {

        public JugOrPitcherDnD() {
            this.name = "Кувшин или графин";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.JUG_OR_PITCHER;

            this.effects = "Нет.";
        }
    }
    public static class LadderDnD extends ItemDnD {

        public LadderDnD() {
            this.name = "Лестница (10 футов)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 25;

            this.id = ItemsIdsDnD.LADDER;

            this.effects = "Нет.";
        }
    }
    public static class LampDnD extends ItemDnD {

        public LampDnD() {
            this.name = "Лампа";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.LAMP;

            this.effects = "Нет.";
        }
    }
    public static class BullseyeLanternDnD extends ItemDnD {

        public BullseyeLanternDnD() {
            this.name = "Направленный фонарь";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.BULLSEYE_LANTERN;

            this.effects = "Нет.";
        }
    }
    public static class HoodedLanternDnD extends ItemDnD {

        public HoodedLanternDnD() {
            this.name = "Закрытый фонарь";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.HOODED_LANTERN;

            this.effects = "Нет.";
        }
    }
    public static class LockDnD extends ItemDnD {

        public LockDnD() {
            this.name = "Замок";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.LOCK;

            this.effects = "Нет.";
        }
    }
    public static class MagnifyingGlassDnD extends ItemDnD {

        public MagnifyingGlassDnD() {
            this.name = "Увеличительное стекло";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 100;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.MAGNIFYING_GLASS;

            this.effects = "Нет.";
        }
    }
    public static class ManaclesDnD extends ItemDnD {

        public ManaclesDnD() {
            this.name = "Кандалы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = ItemsIdsDnD.MANACLES;

            this.effects = "Нет.";
        }
    }
    public static class MessKitDnD extends ItemDnD {

        public MessKitDnD() {
            this.name = "Столовый набор";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.MESS_KIT;

            this.effects = "Нет.";
        }
    }
    public static class SteelMirrorDnD extends ItemDnD {

        public SteelMirrorDnD() {
            this.name = "Стальное зеркало";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0.5;

            this.id = ItemsIdsDnD.STEEL_MIRROR;

            this.effects = "Нет.";
        }
    }
    public static class FlaskOfOilDnD extends ItemDnD {

        public FlaskOfOilDnD() {
            this.name = "Фляга масла";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.FLASK_OF_OIL;

            this.effects = "Нет.";
        }
    }
    public static class PaperDnD extends ItemDnD {

        public PaperDnD() {
            this.name = "Бумага";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.PAPER;

            this.effects = "Нет.";
        }
    }
    public static class ParchmentDnD extends ItemDnD {

        public ParchmentDnD() {
            this.name = "Пергамент";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.PARCHMENT;

            this.effects = "Нет.";
        }
    }
    public static class VialOfPerfumeDnD extends ItemDnD {

        public VialOfPerfumeDnD() {
            this.name = "Флакон духов";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.VIAL_OF_PERFUME;

            this.effects = "Нет.";
        }
    }
    public static class MinersPickDnD extends ItemDnD {

        public MinersPickDnD() {
            this.name = "Горняцкая кирка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.MINERS_PICK;

            this.effects = "Нет.";
        }
    }
    public static class PitonDnD extends ItemDnD {

        public PitonDnD() {
            this.name = "Шлямбур";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0.25;

            this.id = ItemsIdsDnD.PITON;

            this.effects = "Нет.";
        }
    }
    public static class VialOfBasicPoisonDnD extends ItemDnD {

        public VialOfBasicPoisonDnD() {
            this.name = "Флакон простого яда";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 100;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.VIAL_OF_BASIC_POISON;

            this.effects = "Нет.";
        }
    }
    public static class PoleDnD extends ItemDnD {

        public PoleDnD() {
            this.name = "Шест (10 футов)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.POLE;

            this.effects = "Нет.";
        }
    }
    public static class IronPotDnD extends ItemDnD {

        public IronPotDnD() {
            this.name = "Железный горшок";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.IRON_POT;

            this.effects = "Нет.";
        }
    }
    public static class PotionOfHealingDnD extends ItemDnD {

        public PotionOfHealingDnD() {
            this.name = "Зелье лечения";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0.5;

            this.id = ItemsIdsDnD.POTION_OF_HEALING;

            this.effects = "Нет.";
        }
    }
    public static class PouchDnD extends ItemDnD {

        public PouchDnD() {
            this.name = "Кошель";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.POUCH;

            this.effects = "Нет.";
        }
    }
    public static class QuiverDnD extends ItemDnD {

        public QuiverDnD() {
            this.name = "Колчан";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.QUIVER;

            this.effects = "Нет.";
        }
    }
    public static class PortableRamDnD extends ItemDnD {

        public PortableRamDnD() {
            this.name = "Портативный таран";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 4;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 35;

            this.id = ItemsIdsDnD.PORTABLE_RAM;

            this.effects = "Нет.";
        }
    }
    public static class RationsDnD extends ItemDnD {

        public RationsDnD() {
            this.name = "Рационы (1 день)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.RATIONS;

            this.effects = "Нет.";
        }
    }
    public static class RobesDnD extends ItemDnD {

        public RobesDnD() {
            this.name = "Ряса";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 4;

            this.id = ItemsIdsDnD.ROBES;

            this.effects = "Нет.";
        }
    }
    public static class HempenRopeDnD extends ItemDnD {

        public HempenRopeDnD() {
            this.name = "Пеньковая веревка (50 футов)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.HEMPEN_ROPE;

            this.effects = "Нет.";
        }
    }
    public static class SilkRopeDnD extends ItemDnD {

        public SilkRopeDnD() {
            this.name = "Шелковая веревка (50 футов)";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.SILK_ROPE;

            this.effects = "Нет.";
        }
    }
    public static class SackDnD extends ItemDnD {

        public SackDnD() {
            this.name = "Мешок";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0.5;

            this.id = ItemsIdsDnD.SACK;

            this.effects = "Нет.";
        }
    }
    public static class MerchantScaleDnD extends ItemDnD {

        public MerchantScaleDnD() {
            this.name = "Торговые весы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.MERCHANT_SCALE;

            this.effects = "Нет.";
        }
    }
    public static class SealingWaxDnD extends ItemDnD {

        public SealingWaxDnD() {
            this.name = "Воск";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.SEALING_WAX;

            this.effects = "Нет.";
        }
    }
    public static class ShovelDnD extends ItemDnD {

        public ShovelDnD() {
            this.name = "Лопата";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.SHOVEL;

            this.effects = "Нет.";
        }
    }
    public static class SignalWhistleDnD extends ItemDnD {

        public SignalWhistleDnD() {
            this.name = "Сигнальный свисток";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.SIGNAL_WHISTLE;

            this.effects = "Нет.";
        }
    }
    public static class SignetRingDnD extends ItemDnD {

        public SignetRingDnD() {
            this.name = "Кольцо-печатка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.SIGNET_RING;

            this.effects = "Нет.";
        }
    }
    public static class SoapDnD extends ItemDnD {

        public SoapDnD() {
            this.name = "Мыло";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.SOAP;

            this.effects = "Нет.";
        }
    }
    public static class SpellbookDnD extends ItemDnD {

        public SpellbookDnD() {
            this.name = "Книга заклинаний";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.SPELL_BOOK;

            this.effects = "Нет.";
        }
    }
    public static class IronSpikeDnD extends ItemDnD {

        public IronSpikeDnD() {
            this.name = "Железный шип";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 10;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.IRON_SPIKE;

            this.effects = "Нет.";
        }
    }
    public static class SpyglassDnD extends ItemDnD {

        public SpyglassDnD() {
            this.name = "Подзорная труба";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1000;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.SPYGLASS;

            this.effects = "Нет.";
        }
    }
    public static class TwoPersonTentDnD extends ItemDnD {

        public TwoPersonTentDnD() {
            this.name = "Двухместная палатка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 20;

            this.id = ItemsIdsDnD.TWO_PERSON_TENT;

            this.effects = "Нет.";
        }
    }
    public static class TinderboxDnD extends ItemDnD {

        public TinderboxDnD() {
            this.name = "Трутница";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.TINDERBOX;

            this.effects = "Нет.";
        }
    }
    public static class TorchDnD extends ItemDnD {

        public TorchDnD() {
            this.name = "Факел";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.TORCH;

            this.effects = "Нет.";
        }
    }
    public static class VialDnD extends ItemDnD {

        public VialDnD() {
            this.name = "Флакон";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.VIAL;

            this.effects = "Нет.";
        }
    }
    public static class WaterskinDnD extends ItemDnD {

        public WaterskinDnD() {
            this.name = "Бурдюк";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.WATERSKIN;

            this.effects = "Нет.";
        }
    }
    public static class WhetstoneDnD extends ItemDnD {

        public WhetstoneDnD() {
            this.name = "Точильный камень";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.COPPER_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.WHETSTONE;

            this.effects = "Нет.";
        }
    }
}
