package dnd.equipment;

import common.Constants;
import dnd.values.aspectvalues.CurrencyDnD;
import dnd.values.aspectvalues.ItemsIdsDnD;

import java.util.ArrayList;
import java.util.List;

public class KitDnD extends ItemDnD {
    public ArrayList<ItemDnD> contents;

    public KitDnD() {
        this.name = "Свой набор";
        this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

        this.amountInInstance = 1;

        this.value = 1;
        this.currencyGrade = CurrencyDnD.GOLD_COINS;

        this.weight = 1;

        this.id = ItemsIdsDnD.CUSTOM_KIT;
        this.contents = new ArrayList<>();

        this.effects = "Нет.";
    }

    public static class BurglarPackDnD extends KitDnD {

        public BurglarPackDnD() {
            this.name = "Набор взломщика";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 16;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 44.5;

            this.id = ItemsIdsDnD.BURGLAR_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.BallBearingDnD(),
                    new ItemDnD.SilkRopeDnD(),
                    new ItemDnD.BellDnD(),
                    new ItemDnD.CandleDnD(),
                    new ItemDnD.CrowbarDnD(),
                    new ItemDnD.HammerDnD(),
                    new ItemDnD.PitonDnD(),
                    new ItemDnD.HoodedLanternDnD(),
                    new ItemDnD.FlaskOfOilDnD(),
                    new ItemDnD.RationsDnD(),
                    new ItemDnD.TinderboxDnD(),
                    new ItemDnD.WaterskinDnD(),
                    new ItemDnD.HempenRopeDnD()
            )
            );

            this.contents.get(2).setName("Шелковая нить (10 футов)");
            this.contents.get(2).setWeight(1);
            this.contents.get(2).setValue(2);
            this.contents.get(4).setAmountInInstance(5);
            this.contents.get(7).setAmountInInstance(10);
            this.contents.get(9).setAmountInInstance(2);
            this.contents.get(10).setAmountInInstance(5);

            this.effects = "Нет.";
        }
    }

    public static class DiplomatPackDnD extends KitDnD {

        public DiplomatPackDnD() {
            this.name = "Набор дипломата";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 39;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.DIPLOMAT_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.ChestDnD(),
                    new ItemDnD.CaseForMapsAndScrollsDnD(),
                    new ItemDnD.FineClothesDnD(),
                    new ItemDnD.InkDnD(),
                    new ItemDnD.InkPenDnD(),
                    new ItemDnD.LampDnD(),
                    new ItemDnD.FlaskOfOilDnD(),
                    new ItemDnD.PaperDnD(),
                    new ItemDnD.VialOfPerfumeDnD(),
                    new ItemDnD.SealingWaxDnD(),
                    new ItemDnD.SoapDnD()
            )
            );

            this.contents.get(1).setAmountInInstance(2);
            this.contents.get(6).setAmountInInstance(2);
            this.contents.get(7).setAmountInInstance(5);

            this.effects = "Нет.";
        }
    }

    public static class DungeoneerPackDnD extends KitDnD {

        public DungeoneerPackDnD() {
            this.name = "Набор исследователя подземелий";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 12;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.DUNGEONEER_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.CrowbarDnD(),
                    new ItemDnD.HammerDnD(),
                    new ItemDnD.PitonDnD(),
                    new ItemDnD.TorchDnD(),
                    new ItemDnD.TinderboxDnD(),
                    new ItemDnD.RationsDnD(),
                    new ItemDnD.WaterskinDnD(),
                    new ItemDnD.HempenRopeDnD()
            )
            );

            this.contents.get(3).setAmountInInstance(10);
            this.contents.get(4).setAmountInInstance(10);
            this.contents.get(6).setAmountInInstance(10);

            this.effects = "Нет.";
        }
    }

    public static class EntertainerKitDnD extends KitDnD {

        public EntertainerKitDnD() {
            this.name = "Набор артиста";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 40;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 38;

            this.id = ItemsIdsDnD.ENTERTAINER_KIT;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.BedrollDnD(),
                    new ItemDnD.CostumeClothesDnD(),
                    new ItemDnD.CandleDnD(),
                    new ItemDnD.RationsDnD(),
                    new ItemDnD.WaterskinDnD(),
                    new InstrumentDnD.DisguiseKitDnD()
            )
            );

            this.contents.get(2).setAmountInInstance(2);
            this.contents.get(3).setAmountInInstance(5);
            this.contents.get(4).setAmountInInstance(5);

            this.effects = "Нет.";
        }
    }

    public static class ExplorerPackDnD extends KitDnD {

        public ExplorerPackDnD() {
            this.name = "Набор путешественника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 10;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.EXPLORER_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.BedrollDnD(),
                    new ItemDnD.MessKitDnD(),
                    new ItemDnD.TinderboxDnD(),
                    new ItemDnD.TorchDnD(),
                    new ItemDnD.RationsDnD(),
                    new ItemDnD.WaterskinDnD(),
                    new ItemDnD.HempenRopeDnD()
            )
            );

            this.contents.get(4).setAmountInInstance(10);
            this.contents.get(5).setAmountInInstance(10);

            this.effects = "Нет.";
        }
    }

    public static class PriestPackDnD extends KitDnD {

        public PriestPackDnD() {
            this.name = "Набор священника";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 19;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.PRIEST_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.BlanketDnD(),
                    new ItemDnD.CandleDnD(),
                    new ItemDnD.TinderboxDnD(),
                    new ItemDnD(),
                    new ItemDnD(),
                    new ItemDnD(),
                    new ItemDnD.TravelerClothesDnD(),
                    new ItemDnD.RationsDnD(),
                    new ItemDnD.WaterskinDnD()
            )
            );

            this.contents.get(2).setAmountInInstance(10);
            this.contents.get(4).setName("Коробка пожертвований");
            this.contents.get(5).setName("Блок благовоний");
            this.contents.get(5).setAmountInInstance(2);
            this.contents.get(6).setName("Кадило");
            this.contents.get(8).setAmountInInstance(2);

            this.effects = "Нет.";
        }
    }

    public static class ScholarPackDnD extends KitDnD {

        public ScholarPackDnD() {
            this.name = "Набор ученого";
            this.summary = Constants.STANDARD_INVENTORY_SUMMARY;

            this.amountInInstance = 1;

            this.value = 40;
            this.currencyGrade = CurrencyDnD.GOLD_COINS;

            this.weight = 1;

            this.id = ItemsIdsDnD.SCHOLAR_PACK;
            this.contents = new ArrayList<>(List.of(
                    new ItemDnD.BackpackDnD(),
                    new ItemDnD.BookDnD(),
                    new ItemDnD.InkDnD(),
                    new ItemDnD.InkPenDnD(),
                    new ItemDnD.ParchmentDnD(),
                    new ItemDnD.SackDnD(),
                    new ItemDnD.IronSpikeDnD()
            )
            );

            this.contents.get(1).setName("Научная книга");
            this.contents.get(4).setAmountInInstance(10);
            this.contents.get(5).setName("Мешочек с песком");
            this.contents.get(6).setName("Небольшой нож");

            this.effects = "Нет.";
        }
    }
}
