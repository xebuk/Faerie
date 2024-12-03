package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;

import java.util.HashMap;
import java.util.Map;

public class KitDnD extends ItemDnD {
    public Map<ItemDnD, Integer> contents;

    public KitDnD() {
        this.name = "Свой набор";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.effects = "Нет.";

        this.contents = new HashMap<>();
    }

    public static class BurglarPackDnD extends KitDnD {

//        public BurglarPackDnD() {
//            this.name = "Набор взломщика";
//            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;
//
//            this.contents = Map.of(new ItemDnD.BackpackDnD(), 1,
//                    new ItemDnD.BallBearingDnD(), 1000,
//                    new ItemDnD.SilkRopeDnD(), 1,
//                    new ItemDnD.BellDnD(), 1,
//                    new ItemDnD.CandleDnD(), 5,
//                    new ItemDnD.CrowbarDnD(), 1,
//                    new ItemDnD.HammerDnD(), 1,
//                    new ItemDnD.PitonDnD(), 10,
//                    new ItemDnD.HoodedLanternDnD(), 1,
//                    new ItemDnD.FlaskOfOilDnD(), 2,
//                    new ItemDnD.RationsDnD(), 5,
//                    new ItemDnD.TinderboxDnD(), 1,
//                    new ItemDnD.WaterskinDnD(), 1,
//                    new ItemDnD.HempenRopeDnD(), 5);
//        }
    }
    public static class DiplomatPackDnD extends KitDnD {

    }
    public static class DungeoneerPackDnD extends KitDnD {

    }
    public static class EntertainerKitDnD extends KitDnD {

        public EntertainerKitDnD() {
            this.name = "Набор артиста";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.contents = Map.of(new ItemDnD.BackpackDnD(), 1,
                    new ItemDnD.BedrollDnD(), 1,
                    new ItemDnD.CostumeClothesDnD(), 1,
                    new ItemDnD.CostumeClothesDnD(), 1,
                    new ItemDnD.CandleDnD(), 5,
                    new ItemDnD.RationsDnD(), 5,
                    new ItemDnD.WaterskinDnD(), 1,
                    new InstrumentsDnD.DisguiseKitDnD(), 1);

            this.value = 40;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 38;
        }
    }
    public static class ExplorerPackDnD extends KitDnD {

    }
    public static class MonsterHunterPackDnD extends KitDnD {

    }
    public static class PriestPackDnD extends KitDnD {

    }
    public static class ScholarPackDnD extends KitDnD {

    }
}
