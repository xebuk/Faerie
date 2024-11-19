package dnd.equipment;

import common.Constants;
import dnd.values.CurrencyDnD;

import java.util.List;

public class InstrumentsDnD extends ItemDnD {
    public String name;
    public String summary;

    public int value;
    public CurrencyDnD currencyGrade;

    public int weight;

    public List<String> contents;
    public List<String> advantages;

    //инструменты ремесленников
    public static class AlchemistSuppliesDnD extends InstrumentsDnD {

    }
    public static class BrewerSuppliesDnD extends InstrumentsDnD {

    }
    public static class CalligrapherSuppliesDnD extends InstrumentsDnD {

    }
    public static class CarpenterToolsDnD extends InstrumentsDnD {

    }
    public static class CartographerToolsDnD extends InstrumentsDnD {

    }
    public static class CobblerToolsDnD extends InstrumentsDnD {

    }
    public static class CookUtensilsDnD extends InstrumentsDnD {

    }
    public static class GlassblowerToolsDnD extends InstrumentsDnD {

    }
    public static class JewelerToolsDnD extends InstrumentsDnD {

    }
    public static class LeatherworkerToolsDnD extends InstrumentsDnD {

    }
    public static class MasonToolsDnD extends InstrumentsDnD {

    }
    public static class PainterToolsDnD extends InstrumentsDnD {

    }
    public static class PotterToolsDnD extends InstrumentsDnD {

    }
    public static class SmithToolsDnD extends InstrumentsDnD {

    }
    public static class TinkerToolsDnD extends InstrumentsDnD {

    }
    public static class WeaverToolsDnD extends InstrumentsDnD {

    }
    public static class WoodcarverToolsDnD extends InstrumentsDnD {

    }

    public static class DisguiseKitDnD extends InstrumentsDnD {

    }
    public static class ForgeryKitDnD extends InstrumentsDnD {

    }

    //игровые наборы
    public static class DiceSetDnD extends InstrumentsDnD {

    }
    public static class DragonchessSetDnD extends InstrumentsDnD {

    }
    public static class PlayingCardSetDnD extends InstrumentsDnD {

    }
    public static class ThreeDragonAnteSetDnD extends InstrumentsDnD {

    }

    public static class HerbalismKitDnD extends InstrumentsDnD {

    }

    //музыкальные инструменты
    public static class BagpipesDnD extends InstrumentsDnD {

    }
    public static class DrumDnD extends InstrumentsDnD {

    }
    public static class DulcimerDnD extends InstrumentsDnD {

    }
    public static class FluteDnD extends InstrumentsDnD {

    }
    public static class LuteDnD extends InstrumentsDnD {

    }
    public static class LyreDnD extends InstrumentsDnD {

    }
    public static class HornDnD extends InstrumentsDnD {

    }
    public static class PanFluteDnD extends InstrumentsDnD {

    }
    public static class ShawmDnD extends InstrumentsDnD {

    }
    public static class ViolDnD extends InstrumentsDnD {

    }

    public static class NavigatorToolsDnD extends InstrumentsDnD {

    }
    public static class PoisonerKitDnD extends InstrumentsDnD {

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

    }
}
