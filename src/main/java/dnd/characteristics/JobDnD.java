package dnd.characteristics;

import dnd.values.aspectvalues.ItemsIdsDnD;
import dnd.values.characteristicsvalues.JobsDnD;
import dnd.values.masteryvalues.MasteryTypeDnD;

import java.io.Serializable;
import java.util.*;

public class JobDnD implements Serializable {
    public String title;
    public String advancedTitle;
    public boolean usesMagic;

    public JobsDnD jobId;

    public int level = 1;
    public int startingHealth;
    public String healthDice;

    public Set<ItemsIdsDnD> armorMastery;
    public Set<ItemsIdsDnD> weaponMastery;
    public Set<ItemsIdsDnD> instrumentsMastery;
    public Set<MasteryTypeDnD> saveMastery;

    public int startingSkillAmount;
    public List<String> skillRoster;

    public String inventoryRefusalMoney;

    public List<AbilityDnD> abilityRoster;

    public static class ArtificerDnD extends JobDnD {

        public ArtificerDnD() {
            this.title = "Изобретатель";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.ARTIFICER;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE);
            this.instrumentsMastery = Set.of(ItemsIdsDnD.THIEF_TOOLS, ItemsIdsDnD.TINKER_TOOLS);
            // одни инструменты ремесленника на выбор
            this.saveMastery = Set.of(MasteryTypeDnD.CONSTITUTION, MasteryTypeDnD.INTELLIGENCE);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Анализ", "Восприятие", "История", "Ловкость рук",
                    "Магия", "Медицина", "Природа");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class BarbarianDnD extends JobDnD {

        public BarbarianDnD() {
            title = "Варвар";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.BARBARIAN;

            this.startingHealth = 12;
            this.healthDice = "1d12";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.MARTIAL);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.STRENGTH, MasteryTypeDnD.CONSTITUTION);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Атлетика", "Восприятие", "Выживание",
                    "Запугивание", "Природа", "Уход за животными");

            this.inventoryRefusalMoney = "2d4";
        }
    }

    public static class BardDnD extends JobDnD {

        public BardDnD() {
            this.title = "Бард";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.BARD;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.LONGSWORD,
                    ItemsIdsDnD.SHORTSWORD, ItemsIdsDnD.RAPIER, ItemsIdsDnD.HAND_CROSSBOW);
            this.instrumentsMastery = Set.of(); // три музыкальных инструмента на выбор
            this.saveMastery = Set.of(MasteryTypeDnD.DEXTERITY, MasteryTypeDnD.CHARISMA);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                    "Восприятие", "Выживание", "Выступление",
                    "Запугивание", "История", "Ловкость рук",
                    "Магия", "Медицина", "Обман",
                    "Природа", "Проницательность", "Религия",
                    "Скрытность", "Убеждение", "Уход за животными");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class ClericDnD extends JobDnD {

        public ClericDnD() {
            this.title = "Жрец";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.CLERIC;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.WISDOM, MasteryTypeDnD.CHARISMA);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("История", "Медицина",
                    "Проницательность", "Религия", "Убеждение");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class DruidDnD extends JobDnD {

        public DruidDnD() {
            this.title = "Друид";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.DRUID;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.QUARTERSTAFF, ItemsIdsDnD.MACE,
                    ItemsIdsDnD.DART, ItemsIdsDnD.CLUB, ItemsIdsDnD.DAGGER,
                    ItemsIdsDnD.SPEAR, ItemsIdsDnD.JAVELIN, ItemsIdsDnD.SLING,
                    ItemsIdsDnD.SICKLE, ItemsIdsDnD.SCIMITAR);
            this.instrumentsMastery = Set.of(ItemsIdsDnD.HERBALISM_KIT);
            this.saveMastery = Set.of(MasteryTypeDnD.INTELLIGENCE, MasteryTypeDnD.WISDOM);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Восприятие", "Выживание",
                    "Магия", "Медицина", "Природа",
                    "Проницательность", "Религия", "Уход за животными");

            this.inventoryRefusalMoney = "2d4";
        }
    }

    public static class FighterDnD extends JobDnD {

        public FighterDnD() {
            this.title = "Воин";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.FIGHTER;

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.HEAVY, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.MARTIAL);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.STRENGTH, MasteryTypeDnD.CONSTITUTION);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Акробатика", "Атлетика",
                    "Восприятие", "Выживание",
                    "Запугивание", "История",
                    "Проницательность", "Уход за животными");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class HomebrewJobDnD extends JobDnD {

        public HomebrewJobDnD() {
            this.title = "Свой класс";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.HOMEBREW;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = new HashSet<>();
            this.weaponMastery = new HashSet<>();
            this.instrumentsMastery = new HashSet<>();
            this.saveMastery = new HashSet<>();

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                    "Восприятие", "Выживание", "Выступление",
                    "Запугивание", "История", "Ловкость рук",
                    "Магия", "Медицина", "Обман",
                    "Природа", "Проницательность", "Религия",
                    "Скрытность", "Убеждение", "Уход за животными");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class MonkDnD extends JobDnD {

        public MonkDnD() {
            this.title = "Монах";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.MONK;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.SHORTSWORD);
            this.instrumentsMastery = Set.of(); // либо инструмент ремесленника, либо музыкальный инструмент
            this.saveMastery = Set.of(MasteryTypeDnD.STRENGTH, MasteryTypeDnD.DEXTERITY);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Акробатика", "Атлетика",
                    "История", "Проницательность", "Религия",
                    "Скрытность");

            this.inventoryRefusalMoney = "5d4"; // на 10 не умножать
        }
    }

    public static class PaladinDnD extends JobDnD {

        public PaladinDnD() {
            this.title = "Паладин";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.PALADIN;

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.HEAVY, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.MARTIAL);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.WISDOM, MasteryTypeDnD.CHARISMA);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Атлетика", "Запугивание", "Медицина",
                    "Проницательность", "Религия", "Убеждение");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class RangerDnD extends JobDnD {

        public RangerDnD() {
            this.title = "Следопыт";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.RANGER;

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT, ItemsIdsDnD.MEDIUM, ItemsIdsDnD.SHIELD);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.MARTIAL);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.STRENGTH, MasteryTypeDnD.DEXTERITY);

            this.startingSkillAmount = 3;
            this.skillRoster = Arrays.asList("Анализ", "Атлетика",
                    "Восприятие", "Выживание",
                    "Природа", "Проницательность",
                    "Скрытность", "Уход за животными");

            this.inventoryRefusalMoney = "5d4";
        }
    }

    public static class RogueDnD extends JobDnD {

        public RogueDnD() {
            this.title = "Плут";
            this.advancedTitle = "Не выбран";
            this.usesMagic = false;

            this.jobId = JobsDnD.ROGUE;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE, ItemsIdsDnD.HAND_CROSSBOW,
                    ItemsIdsDnD.LONGSWORD, ItemsIdsDnD.RAPIER, ItemsIdsDnD.SHORTSWORD);
            this.instrumentsMastery = Set.of(ItemsIdsDnD.THIEF_TOOLS);
            this.saveMastery = Set.of(MasteryTypeDnD.DEXTERITY, MasteryTypeDnD.INTELLIGENCE);

            this.startingSkillAmount = 4;
            this.skillRoster = Arrays.asList("Акробатика", "Анализ", "Атлетика",
                    "Восприятие", "Выступление",
                    "Запугивание", "Ловкость рук", "Обман",
                    "Проницательность", "Скрытность", "Убеждение");

            this.inventoryRefusalMoney = "4d4";
        }
    }

    public static class SorcererDnD extends JobDnD {

        public SorcererDnD() {
            this.title = "Чародей";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.SORCERER;

            this.startingHealth = 6;
            this.healthDice = "1d6";

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(ItemsIdsDnD.QUARTERSTAFF, ItemsIdsDnD.DART,
                    ItemsIdsDnD.DAGGER, ItemsIdsDnD.LIGHT_CROSSBOW, ItemsIdsDnD.SLING);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.CONSTITUTION, MasteryTypeDnD.CHARISMA);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Запугивание", "Магия", "Обман",
                    "Проницательность", "Религия", "Убеждение");

            this.inventoryRefusalMoney = "3d4";
        }
    }

    public static class WarlockDnD extends JobDnD {

        public WarlockDnD() {
            this.title = "Колдун";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.jobId = JobsDnD.WARLOCK;

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ItemsIdsDnD.LIGHT);
            this.weaponMastery = Set.of(ItemsIdsDnD.SIMPLE);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.WISDOM, MasteryTypeDnD.CHARISMA);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Анализ", "Запугивание", "История",
                    "Магия", "Обман",
                    "Природа", "Религия");

            this.inventoryRefusalMoney = "4d4";
        }
    }

    public static class WizardDnD extends JobDnD {

        public WizardDnD() {
            this.title = "Волшебник";
            this.advancedTitle = "Не выбран";
            this.usesMagic = true;

            this.startingHealth = 6;
            this.healthDice = "1d6";

            this.jobId = JobsDnD.WIZARD;

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(ItemsIdsDnD.DAGGER, ItemsIdsDnD.SLING,
                    ItemsIdsDnD.QUARTERSTAFF, ItemsIdsDnD.LIGHT_CROSSBOW);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.INTELLIGENCE, MasteryTypeDnD.WISDOM);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Анализ", "История", "Магия", "Медицина",
                    "Проницательность", "Религия");

            this.inventoryRefusalMoney = "4d4";
        }
    }
}
