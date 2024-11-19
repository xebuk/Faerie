package dnd.characteristics;

import dnd.values.ArmorTypeDnD;
import dnd.values.InstrumentsDnD;
import dnd.values.MasteryTypeDnD;
import dnd.values.WeaponTraitsDnD;

import java.io.Serializable;
import java.util.*;

public class JobDnD implements Serializable {
    public String title;
    public String advancedTitle;
    public boolean usesMagic;

    public int level = 1;
    public int startingHealth;
    public String healthDice;

    public Set<ArmorTypeDnD> armorMastery;
    public Set<WeaponTraitsDnD> weaponMastery;
    public Set<InstrumentsDnD> instrumentsMastery;
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
            this.instrumentsMastery = Set.of(InstrumentsDnD.THIEF_TOOLS, InstrumentsDnD.TINKER_TOOLS);
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

            this.startingHealth = 12;
            this.healthDice = "1d12";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.LONGSWORD,
                    WeaponTraitsDnD.SHORTSWORD, WeaponTraitsDnD.RAPIER, WeaponTraitsDnD.HAND_CROSSBOW);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.MACE,
                    WeaponTraitsDnD.DART, WeaponTraitsDnD.CLUB, WeaponTraitsDnD.DAGGER,
                    WeaponTraitsDnD.SPEAR, WeaponTraitsDnD.JAVELIN, WeaponTraitsDnD.SLING,
                    WeaponTraitsDnD.SICKLE, WeaponTraitsDnD.SCIMITAR);
            this.instrumentsMastery = Set.of(InstrumentsDnD.HERBALISM_KIT);
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

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.HEAVY, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.SHORTSWORD);
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

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.HEAVY, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
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

            this.startingHealth = 10;
            this.healthDice = "1d10";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT, ArmorTypeDnD.MEDIUM, ArmorTypeDnD.SHIELD);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.MARTIAL);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE, WeaponTraitsDnD.HAND_CROSSBOW,
                    WeaponTraitsDnD.LONGSWORD, WeaponTraitsDnD.RAPIER, WeaponTraitsDnD.SHORTSWORD);
            this.instrumentsMastery = Set.of(InstrumentsDnD.THIEF_TOOLS);
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

            this.startingHealth = 6;
            this.healthDice = "1d6";

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.DART,
                    WeaponTraitsDnD.DAGGER, WeaponTraitsDnD.LIGHT_CROSSBOW, WeaponTraitsDnD.SLING);
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

            this.startingHealth = 8;
            this.healthDice = "1d8";

            this.armorMastery = Set.of(ArmorTypeDnD.LIGHT);
            this.weaponMastery = Set.of(WeaponTraitsDnD.SIMPLE);
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

            this.armorMastery = Set.of();
            this.weaponMastery = Set.of(WeaponTraitsDnD.DAGGER, WeaponTraitsDnD.SLING,
                    WeaponTraitsDnD.QUARTERSTAFF, WeaponTraitsDnD.LIGHT_CROSSBOW);
            this.instrumentsMastery = Set.of();
            this.saveMastery = Set.of(MasteryTypeDnD.INTELLIGENCE, MasteryTypeDnD.WISDOM);

            this.startingSkillAmount = 2;
            this.skillRoster = Arrays.asList("Анализ", "История", "Магия", "Медицина",
                    "Проницательность", "Религия");

            this.inventoryRefusalMoney = "4d4";
        }
    }
}
