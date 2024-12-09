package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.aspectvalues.ItemsIdsDnD;

public class InstrumentDnD extends ItemDnD {

    public InstrumentDnD() {
        this.name = "Свои инструменты";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.id = ItemsIdsDnD.CUSTOM_INSTRUMENT;

        this.effects = "Нет.";
    }

    @Override
    public String toString() {
        StringBuilder instrument = new StringBuilder();
        instrument.append(name).append("\n").append(summary).append("\n");
        instrument.append("Цена: ").append(value).append(" ").append(currencyGrade).append("\n");
        instrument.append("Масса: ").append(weight).append("\n");
        return instrument.toString();
    }

    //инструменты ремесленников
    public static class AlchemistSuppliesDnD extends InstrumentDnD {

        public AlchemistSuppliesDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 8;

            this.id = ItemsIdsDnD.ALCHEMIST_SUPPLIES;

            this.effects = "Нет.";
        }
    }

    public static class BrewerSuppliesDnD extends InstrumentDnD {

        public BrewerSuppliesDnD() {
            this.name = "Инструменты пивовара";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 20;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 9;

            this.id = ItemsIdsDnD.BREWER_SUPPLIES;

            this.effects = "Нет.";
        }
    }

    public static class CalligrapherSuppliesDnD extends InstrumentDnD {

        public CalligrapherSuppliesDnD() {
            this.name = "Инструменты каллиграфа";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.CALLIGRAPHER_SUPPLIES;

            this.effects = "Нет.";
        }
    }

    public static class CarpenterToolsDnD extends InstrumentDnD {

        public CarpenterToolsDnD() {
            this.name = "Инструменты плотника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 8;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = ItemsIdsDnD.CARPENTER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class CartographerToolsDnD extends InstrumentDnD {

        public CartographerToolsDnD() {
            this.name = "Инструменты картографа";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = ItemsIdsDnD.CARTOGRAPHER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class CobblerToolsDnD extends InstrumentDnD {

        public CobblerToolsDnD() {
            this.name = "Инструменты сапожника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.COBBLER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class CookUtensilsDnD extends InstrumentDnD {

        public CookUtensilsDnD() {
            this.name = "Инструменты повара";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 8;

            this.id = ItemsIdsDnD.COOK_UTENSILS;

            this.effects = "Нет.";
        }
    }

    public static class GlassblowerToolsDnD extends InstrumentDnD {

        public GlassblowerToolsDnD() {
            this.name = "Инструменты стеклодува";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 30;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.GLASSBLOWER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class JewelerToolsDnD extends InstrumentDnD {

        public JewelerToolsDnD() {
            this.name = "Инструменты ювелира";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.JEWELER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class LeatherworkerToolsDnD extends InstrumentDnD {

        public LeatherworkerToolsDnD() {
            this.name = "Инструменты кожевника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.LEATHERWORKER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class MasonToolsDnD extends InstrumentDnD {

        public MasonToolsDnD() {
            this.name = "Инструменты каменщика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 8;

            this.id = ItemsIdsDnD.MASON_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class PainterToolsDnD extends InstrumentDnD {

        public PainterToolsDnD() {
            this.name = "Инструменты художника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.PAINTER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class PotterToolsDnD extends InstrumentDnD {

        public PotterToolsDnD() {
            this.name = "Инструменты гончара";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.POTTER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class SmithToolsDnD extends InstrumentDnD {

        public SmithToolsDnD() {
            this.name = "Инструменты кузнеца";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 20;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 8;

            this.id = ItemsIdsDnD.SMITH_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class TinkerToolsDnD extends InstrumentDnD {

        public TinkerToolsDnD() {
            this.name = "Инструменты ремонтника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.TINKER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class WeaverToolsDnD extends InstrumentDnD {

        public WeaverToolsDnD() {
            this.name = "Инструменты ткача";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.WEAVER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class WoodcarverToolsDnD extends InstrumentDnD {

        public WoodcarverToolsDnD() {
            this.name = "Инструменты резчика по дереву";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.WOODCARVER_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class DisguiseKitDnD extends InstrumentDnD {

        public DisguiseKitDnD() {
            this.name = "Набор для грима";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.DISGUISE_KIT;

            this.effects = "Нет.";
        }
    }

    public static class ForgeryKitDnD extends InstrumentDnD {

        public ForgeryKitDnD() {
            this.name = "Набор для фальсификации";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 15;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 5;

            this.id = ItemsIdsDnD.FORGERY_KIT;

            this.effects = "Нет.";
        }
    }

    //игровые наборы
    public static class DiceSetDnD extends InstrumentDnD {

        public DiceSetDnD() {
            this.name = "Кости";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.GAMING_SET;

            this.effects = "Нет.";
        }
    }

    public static class DragonchessSetDnD extends InstrumentDnD {

        public DragonchessSetDnD() {
            this.name = "Драконьи шахматы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0.5;

            this.id = ItemsIdsDnD.GAMING_SET;

            this.effects = "Нет.";
        }
    }

    public static class PlayingCardSetDnD extends InstrumentDnD {

        public PlayingCardSetDnD() {
            this.name = "Карты";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.SILVER_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.GAMING_SET;

            this.effects = "Нет.";
        }
    }

    public static class ThreeDragonAnteSetDnD extends InstrumentDnD {

        public ThreeDragonAnteSetDnD() {
            this.name = "Ставка трех драконов";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.GAMING_SET;

            this.effects = "Нет.";
        }
    }

    public static class HerbalismKitDnD extends InstrumentDnD {

        public HerbalismKitDnD() {
            this.name = "Набор травника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 5;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.HERBALISM_KIT;

            this.effects = "Нет.";
        }
    }

    //музыкальные инструменты
    public static class BagpipesDnD extends InstrumentDnD {

        public BagpipesDnD() {
            this.name = "Волынка";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 30;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 6;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class DrumDnD extends InstrumentDnD {

        public DrumDnD() {
            this.name = "Барабан";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 6;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 3;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class DulcimerDnD extends InstrumentDnD {

        public DulcimerDnD() {
            this.name = "Цимбалы";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 10;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class FluteDnD extends InstrumentDnD {

        public FluteDnD() {
            this.name = "Флейта";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class LuteDnD extends InstrumentDnD {

        public LuteDnD() {
            this.name = "Лютня";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 35;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class LyreDnD extends InstrumentDnD {

        public LyreDnD() {
            this.name = "Лира";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 30;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class HornDnD extends InstrumentDnD {

        public HornDnD() {
            this.name = "Рожок";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 3;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class PanFluteDnD extends InstrumentDnD {

        public PanFluteDnD() {
            this.name = "Свирель";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 12;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class ShawmDnD extends InstrumentDnD {

        public ShawmDnD() {
            this.name = "Шалмей";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 2;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class ViolDnD extends InstrumentDnD {

        public ViolDnD() {
            this.name = "Виола";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 30;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.MUSICAL_INSTRUMENTS;

            this.effects = "Нет.";
        }
    }

    public static class NavigatorToolsDnD extends InstrumentDnD {

        public NavigatorToolsDnD() {
            this.name = "Инструменты навигатора";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.NAVIGATOR_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class PoisonerKitDnD extends InstrumentDnD {

        public PoisonerKitDnD() {
            this.name = "Инструменты отравителя";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 50;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 2;

            this.id = ItemsIdsDnD.POISONER_KIT;

            this.effects = "Нет.";
        }
    }

    public static class ThievesToolsDnD extends InstrumentDnD {

        public ThievesToolsDnD() {
            this.name = "Воровские инструменты";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.THIEF_TOOLS;

            this.effects = "Нет.";
        }
    }

    public static class VehicleDnD extends InstrumentDnD {

        public VehicleDnD() {
            this.name = "Транспорт";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 0;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 0;

            this.id = ItemsIdsDnD.VEHICLE;

            this.effects = "Нет.";
        }
    }
}
