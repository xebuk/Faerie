package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;

import java.util.*;

public class InstrumentsDnD extends ItemDnD {
    public HashMap<String, Integer> possibleChecks;
    public List<String> possibleActivities;

    public InstrumentsDnD() {
        this.name = "Свои инструменты";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.possibleChecks = new HashMap<>();
        this.possibleActivities = new ArrayList<>();

        this.effects = "Нет.";
    }

    //инструменты ремесленников
    public static class AlchemistSuppliesDnD extends InstrumentsDnD {

        public AlchemistSuppliesDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>(Map.of("Создать клубы густого дыма", 10,
                    "Опознать яд", 10,
                    "Опознать вещество", 10,
                    "Устроить поджог", 15,
                    "Нейтрализовать кислоту", 20));

            this.effects = "Нет.";
        }
    }

    public static class BrewerSuppliesDnD extends InstrumentsDnD {

        public BrewerSuppliesDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class CalligrapherSuppliesDnD extends InstrumentsDnD {

        public CalligrapherSuppliesDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class CarpenterToolsDnD extends InstrumentsDnD {

        public CarpenterToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class CartographerToolsDnD extends InstrumentsDnD {

        public CartographerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class CobblerToolsDnD extends InstrumentsDnD {
        public CobblerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class CookUtensilsDnD extends InstrumentsDnD {
        public CookUtensilsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class GlassblowerToolsDnD extends InstrumentsDnD {
        public GlassblowerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class JewelerToolsDnD extends InstrumentsDnD {
        public JewelerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class LeatherworkerToolsDnD extends InstrumentsDnD {
        public LeatherworkerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class MasonToolsDnD extends InstrumentsDnD {
        public MasonToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class PainterToolsDnD extends InstrumentsDnD {
        public PainterToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class PotterToolsDnD extends InstrumentsDnD {
        public PotterToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class SmithToolsDnD extends InstrumentsDnD {
        public SmithToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class TinkerToolsDnD extends InstrumentsDnD {
        public TinkerToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class WeaverToolsDnD extends InstrumentsDnD {
        public WeaverToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class WoodcarverToolsDnD extends InstrumentsDnD {
        public WoodcarverToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class DisguiseKitDnD extends InstrumentsDnD {
        public DisguiseKitDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class ForgeryKitDnD extends InstrumentsDnD {
        public ForgeryKitDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    //игровые наборы
    public static class DiceSetDnD extends InstrumentsDnD {
        public DiceSetDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class DragonchessSetDnD extends InstrumentsDnD {
        public DragonchessSetDnD() {
        }
    }

    public static class PlayingCardSetDnD extends InstrumentsDnD {
        public PlayingCardSetDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class ThreeDragonAnteSetDnD extends InstrumentsDnD {
        public ThreeDragonAnteSetDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class HerbalismKitDnD extends InstrumentsDnD {
        public HerbalismKitDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    //музыкальные инструменты
    public static class BagpipesDnD extends InstrumentsDnD {
        public BagpipesDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class DrumDnD extends InstrumentsDnD {
        public DrumDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class DulcimerDnD extends InstrumentsDnD {
        public DulcimerDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class FluteDnD extends InstrumentsDnD {
        public FluteDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class LuteDnD extends InstrumentsDnD {
        public LuteDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class LyreDnD extends InstrumentsDnD {
        public LyreDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class HornDnD extends InstrumentsDnD {
        public HornDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class PanFluteDnD extends InstrumentsDnD {
        public PanFluteDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class ShawmDnD extends InstrumentsDnD {
        public ShawmDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class ViolDnD extends InstrumentsDnD {
        public ViolDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class NavigatorToolsDnD extends InstrumentsDnD {
        public NavigatorToolsDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class PoisonerKitDnD extends InstrumentsDnD {
        public PoisonerKitDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }

    public static class ThievesToolsDnD extends InstrumentsDnD {

        public ThievesToolsDnD() {
            this.name = "Воровские инструменты";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.value = 25;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;
        }
    }

    public static class VehicleDnD extends InstrumentsDnD {
        public VehicleDnD() {
            this.name = "Инструменты алхимика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 1;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.possibleChecks = new HashMap<>();

            this.effects = "Нет.";
        }
    }
}
