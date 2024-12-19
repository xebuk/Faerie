package dnd.characteristics;

import dnd.values.masteryvalues.LanguagesDnD;
import dnd.values.characteristicsvalues.RacesDnD;
import dnd.values.characteristicsvalues.RacesSizeDnD;
import dnd.values.masteryvalues.ScriptsDnD;

import java.io.Serializable;
import java.util.*;

public class RaceDnD implements Serializable {
    public String name;
    public String subspeciesName;
    public RacesSizeDnD size;

    public RacesDnD raceId;

    public int strengthBonus = 0;
    public int dexterityBonus = 0;
    public int constitutionBonus = 0;
    public int intelligenceBonus = 0;
    public int wisdomBonus = 0;
    public int charismaBonus = 0;

    public int statBonus1;
    public String statBonusTowards1;
    public int statBonus2;
    public String statBonusTowards2;

    public int walkingSpeed;

    public int bonusLanguages = 0;
    public Set<LanguagesDnD> languages;
    public Set<ScriptsDnD> scripts;

    public HashMap<String, String> features;

    public List<String> personality;
    public List<String> ideal;
    public List<String> bond;
    public List<String> flaw;

    public static class DragonbornDnD extends RaceDnD {

        public DragonbornDnD() {
            this.name = "Драконорожденный";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.DRAGONBORN;

            this.strengthBonus = 2;
            this.charismaBonus = 1;

            this.walkingSpeed = 30;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.DRACONIC);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DRACONIC);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class DwarfDnD extends RaceDnD {

        public DwarfDnD() {
            this.name = "Дварф";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.DWARF;

            this.constitutionBonus = 2;

            this.statBonus1 = 1;
            this.statBonusTowards1 = "Не выбран";

            this.walkingSpeed = 25;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.DWARWISH);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class ElfDnD extends RaceDnD {

        public ElfDnD() {
            this.name = "Эльф";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.ELF;

            this.dexterityBonus = 2;

            this.walkingSpeed = 30;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.ELVISH);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.ELVISH);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class GnomeDnD extends RaceDnD {

        public GnomeDnD() {
            this.name = "Гном";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.SMALL;

            this.raceId = RacesDnD.GNOME;

            this.intelligenceBonus = 2;

            this.walkingSpeed = 25;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.GNOMISH);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

            this.personality = Arrays.asList("Как только вам начинает нравится что-то, вы становитесь одержимы этим.",
                    "Вы живёте подобно листу на ветру, следуя туда, куда вас понесёт ветер.",
                    "Мир – удивительное место, и вы очарованы всем, что находится в нём.",
                    "Вы изучаете своих друзей и делаете заметки о том, как они себя ведут, записывая то, как они говорят и что они делают.",
                    "Ваше любопытство настолько велико, что у вас проблемы с концентрацией на чём-то одном.",
                    "Вам нравится делать маленькие вещи из веточек или кусочков металла и дарить их друзьям.");
            this.ideal = Arrays.asList("Любовь. Вы любите маленьких (и больших) зверушек, и изо всех сил стараетесь помочь им.",
                    "Любопытство. Вы не выдерживаете, если рядом есть неразгаданная загадка или неоткрытая дверь.",
                    "Знания. Вы заинтересованы во всём. Кто знает, когда вам пригодится то, что вы изучили.",
                    "Сострадание. Вы никогда не пропускаете мимо ушей просьбы о помощи.",
                    "Полезность. Неважно, видите ли вы сломанное устройство или разбитое сердце, вам нужно попытаться исправить это.",
                    "Совершенство. Вы стремитесь делать всё, что в ваших силах.");
            this.bond = Arrays.asList("Вы пообещали вернуть нечто, представляющее особую ценность для вашего дома.",
                    "Все произведения высокого качества и исключительного ремесленного мастерства нужно защитить, выказать им уважение и ухаживать за ними.",
                    "Кобольды причинили вам и вашему народу множество проблем. Вы отомстите им за такую несправедливость.",
                    "Вы разыскиваете утраченную любовь.",
                    "Вы должны вернуть реликвию, украденную у вашего клана.",
                    "Вы готовы рисковать, чтобы глубже познать мир.");
            this.flaw = Arrays.asList("Вы воплощаете в себе типичного рассеянного профессора. Если бы вы могли забыть где-нибудь свою голову, то вы бы её забыли.",
                    "Вы предпочитаете прятаться во время боя.",
                    "Нет разницы между тем, что вы думаете и тем, что вы говорите.",
                    "Вы не умеете хранить секреты.");
        }
    }

    public static class HalfElfDnD extends RaceDnD {

        public HalfElfDnD() {
            this.name = "Полуэльф";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.HALF_ELF;

            this.charismaBonus = 2;

            this.statBonus1 = 1;
            this.statBonusTowards1 = "Не выбран";
            this.statBonus2 = 1;
            this.statBonusTowards2 = "Не выбран";

            this.walkingSpeed = 30;

            this.bonusLanguages = 1;
            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.ELVISH);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.ELVISH);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class HalflingDnD extends RaceDnD {

        public HalflingDnD() {
            this.name = "Полурослик";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.SMALL;

            this.raceId = RacesDnD.HALFLING;

            this.dexterityBonus = 2;

            this.walkingSpeed = 25;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.HALFLING);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class HalfOrcDnD extends RaceDnD {

        public HalfOrcDnD() {
            this.name = "Полуорк";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.HALF_ORC;

            this.strengthBonus = 2;
            this.constitutionBonus = 1;

            this.walkingSpeed = 30;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.ORCISH);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.DWARVISH);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class HumanDnD extends RaceDnD {

        public HumanDnD() {
            this.name = "Человек";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.HUMAN;

            this.strengthBonus = 1;
            this.dexterityBonus = 1;
            this.constitutionBonus = 1;
            this.intelligenceBonus = 1;
            this.wisdomBonus = 1;
            this.charismaBonus = 1;

            this.walkingSpeed = 30;

            this.bonusLanguages = 1;
            this.languages = Set.of(LanguagesDnD.COMMON);
            this.scripts = Set.of(ScriptsDnD.COMMON);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class HumanVariantDnD extends RaceDnD {

        public HumanVariantDnD() {
            this.name = "Человек (Вариант)";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.HUMAN_VARIANT;

            this.statBonus1 = 1;
            this.statBonusTowards1 = "Не выбран";
            this.statBonus2 = 1;
            this.statBonusTowards2 = "Не выбран";

            this.walkingSpeed = 30;

            this.bonusLanguages = 1;
            this.languages = Set.of(LanguagesDnD.COMMON);
            this.scripts = Set.of(ScriptsDnD.COMMON);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }

    public static class TieflingDnD extends RaceDnD {

        public TieflingDnD() {
            this.name = "Тифлинг";
            this.subspeciesName = "Не выбран";
            this.size = RacesSizeDnD.MEDIUM;

            this.raceId = RacesDnD.TIEFLING;

            this.intelligenceBonus = 1;
            this.charismaBonus = 2;

            this.walkingSpeed = 30;

            this.languages = Set.of(LanguagesDnD.COMMON, LanguagesDnD.INFERNAL);
            this.scripts = Set.of(ScriptsDnD.COMMON, ScriptsDnD.INFERNAL);

            this.personality = new ArrayList<>();
            this.ideal = new ArrayList<>();
            this.bond = new ArrayList<>();
            this.flaw = new ArrayList<>();
        }
    }
}
