package botexecution;

import common.Constants;
import dnd.mainobjects.PlayerDnD;
import dnd.values.LanguagesDnD;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup searchBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("Заклинания"));
        inlineKeyRow1.get(0).setCallbackData(Constants.SPELLS);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("Предметы"));
        inlineKeyRow2.get(0).setCallbackData(Constants.ITEMS);
        inlineKeyRow2.add(new InlineKeyboardButton("Бестиарий"));
        inlineKeyRow2.get(1).setCallbackData(Constants.BESTIARY);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();

        inlineKeyRow3.add(new InlineKeyboardButton("Расы"));
        inlineKeyRow3.get(0).setCallbackData(Constants.RACES);
        inlineKeyRow3.add(new InlineKeyboardButton("Классы"));
        inlineKeyRow3.get(1).setCallbackData(Constants.CLASSES);

        InlineKeyboardRow inlineKeyRow4 = new InlineKeyboardRow();

        inlineKeyRow4.add(new InlineKeyboardButton("Черты"));
        inlineKeyRow4.get(0).setCallbackData(Constants.FEATS);
        inlineKeyRow4.add(new InlineKeyboardButton("Предыстории"));
        inlineKeyRow4.get(1).setCallbackData(Constants.BACKGROUNDS);

        ArrayList<InlineKeyboardRow> inlineKeyRows = new ArrayList<>();
        inlineKeyRows.add(inlineKeyRow1);
        inlineKeyRows.add(inlineKeyRow2);
        inlineKeyRows.add(inlineKeyRow3);
        inlineKeyRows.add(inlineKeyRow4);

        return new InlineKeyboardMarkup(inlineKeyRows);
    }

    public static InlineKeyboardMarkup rollVariantsBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("d20"));
        inlineKeyRow1.get(0).setCallbackData(Constants.ROLL_D20);
        inlineKeyRow1.add(new InlineKeyboardButton("2d20"));
        inlineKeyRow1.get(1).setCallbackData(Constants.ROLL_2D20);
        inlineKeyRow1.add(new InlineKeyboardButton("d8"));
        inlineKeyRow1.get(2).setCallbackData(Constants.ROLL_D8);
        inlineKeyRow1.add(new InlineKeyboardButton("d4"));
        inlineKeyRow1.get(3).setCallbackData(Constants.ROLL_D4);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("d6"));
        inlineKeyRow2.get(0).setCallbackData(Constants.ROLL_D6);
        inlineKeyRow2.add(new InlineKeyboardButton("Бросок статов (4d6)"));
        inlineKeyRow2.get(1).setCallbackData(Constants.ROLL_4D6);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Кастомные кости"));
        inlineKeyRow3.get(0).setCallbackData(Constants.CUSTOM_DICE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup rollAdvantageBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        inlineKeyRow.add(new InlineKeyboardButton("Да"));
        inlineKeyRow.get(0).setCallbackData(Constants.ADVANTAGE);
        inlineKeyRow.add(new InlineKeyboardButton("Нет"));
        inlineKeyRow.get(1).setCallbackData(Constants.DISADVANTAGE);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup rollCustomBoard(ChatSession cs) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        for (String diceVariant : cs.dicePresets) {
            InlineKeyboardButton dice = new InlineKeyboardButton(diceVariant);
            dice.setCallbackData(diceVariant);
            inlineKeyRow.add(dice);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup YesOrNoBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        inlineKeyRow.add(new InlineKeyboardButton("Да"));
        inlineKeyRow.get(0).setCallbackData("Да");
        inlineKeyRow.add(new InlineKeyboardButton("Нет"));
        inlineKeyRow.get(1).setCallbackData("Нет");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup jobSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();

        inlineKeyRow1.add(new InlineKeyboardButton("Воин"));
        inlineKeyRow1.get(0).setCallbackData(Constants.CREATION_MENU_FIGHTER);
        inlineKeyRow1.add(new InlineKeyboardButton("Клерик"));
        inlineKeyRow1.get(1).setCallbackData(Constants.CREATION_MENU_CLERIC);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();

        inlineKeyRow2.add(new InlineKeyboardButton("Маг"));
        inlineKeyRow2.get(0).setCallbackData(Constants.CREATION_MENU_MAGE);
        inlineKeyRow2.add(new InlineKeyboardButton("Плут"));
        inlineKeyRow2.get(1).setCallbackData(Constants.CREATION_MENU_ROGUE);
        inlineKeyRow2.add(new InlineKeyboardButton("Следопыт"));
        inlineKeyRow2.get(2).setCallbackData(Constants.CREATION_MENU_RANGER);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup assignStatsBoardGame(HashSet<String> bannedButtons) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        if (!bannedButtons.contains(Constants.CREATION_MENU_STRENGTH)) {
            InlineKeyboardButton strength = new InlineKeyboardButton("Сила");
            strength.setCallbackData(Constants.CREATION_MENU_STRENGTH);
            inlineKeyRow.add(strength);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_DEXTERITY)) {
            InlineKeyboardButton dexterity = new InlineKeyboardButton("Ловкость");
            dexterity.setCallbackData(Constants.CREATION_MENU_DEXTERITY);
            inlineKeyRow.add(dexterity);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_CONSTITUTION)) {
            InlineKeyboardButton constitution = new InlineKeyboardButton("Выносливость");
            constitution.setCallbackData(Constants.CREATION_MENU_CONSTITUTION);
            inlineKeyRow.add(constitution);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_INTELLIGENCE)) {
            InlineKeyboardButton intelligence = new InlineKeyboardButton("Интеллект");
            intelligence.setCallbackData(Constants.CREATION_MENU_INTELLIGENCE);
            inlineKeyRow.add(intelligence);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_WISDOM)) {
            InlineKeyboardButton wisdom = new InlineKeyboardButton("Мудрость");
            wisdom.setCallbackData(Constants.CREATION_MENU_WISDOM);
            inlineKeyRow.add(wisdom);
        }

        if (!bannedButtons.contains(Constants.CREATION_MENU_CHARISMA)) {
            InlineKeyboardButton charisma = new InlineKeyboardButton("Харизма");
            charisma.setCallbackData(Constants.CREATION_MENU_CHARISMA);
            inlineKeyRow.add(charisma);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup raceDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();
        inlineKeyRow.add(new InlineKeyboardButton("Гном"));
        inlineKeyRow.get(0).setCallbackData("Гном");
        inlineKeyRow.add(new InlineKeyboardButton("Дварф"));
        inlineKeyRow.get(1).setCallbackData("Дварф");
        inlineKeyRow.add(new InlineKeyboardButton("Драконорожденный"));
        inlineKeyRow.get(2).setCallbackData("Драконорожденный");
        inlineKeyRow.add(new InlineKeyboardButton("Полуорк"));
        inlineKeyRow.get(3).setCallbackData("Полуорк");
        inlineKeyRow.add(new InlineKeyboardButton("Полурослик"));
        inlineKeyRow.get(4).setCallbackData("Полурослик");
        inlineKeyRow.add(new InlineKeyboardButton("Полуэльф"));
        inlineKeyRow.get(5).setCallbackData("Полуэльф");
        inlineKeyRow.add(new InlineKeyboardButton("Тифлинг"));
        inlineKeyRow.get(6).setCallbackData("Тифлинг");
        inlineKeyRow.add(new InlineKeyboardButton("Человек"));
        inlineKeyRow.get(7).setCallbackData("Человек");
        inlineKeyRow.add(new InlineKeyboardButton("Человек (вариант)"));
        inlineKeyRow.get(8).setCallbackData("Человек (вариант)");
        inlineKeyRow.add(new InlineKeyboardButton("Эльф"));
        inlineKeyRow.get(9).setCallbackData("Эльф");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup jobDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();
        inlineKeyRow.add(new InlineKeyboardButton("Бард"));
        inlineKeyRow.get(0).setCallbackData("Бард");
        inlineKeyRow.add(new InlineKeyboardButton("Варвар"));
        inlineKeyRow.get(1).setCallbackData("Варвар");
        inlineKeyRow.add(new InlineKeyboardButton("Воин"));
        inlineKeyRow.get(2).setCallbackData("Воин");
        inlineKeyRow.add(new InlineKeyboardButton("Волшебник"));
        inlineKeyRow.get(3).setCallbackData("Волшебник");
        inlineKeyRow.add(new InlineKeyboardButton("Друид"));
        inlineKeyRow.get(4).setCallbackData("Друид");
        inlineKeyRow.add(new InlineKeyboardButton("Жрец"));
        inlineKeyRow.get(5).setCallbackData("Жрец");
        inlineKeyRow.add(new InlineKeyboardButton("Изобретатель"));
        inlineKeyRow.get(6).setCallbackData("Изобретатель");
        inlineKeyRow.add(new InlineKeyboardButton("Колдун"));
        inlineKeyRow.get(7).setCallbackData("Колдун");
        inlineKeyRow.add(new InlineKeyboardButton("Монах"));
        inlineKeyRow.get(8).setCallbackData("Монах");
        inlineKeyRow.add(new InlineKeyboardButton("Паладин"));
        inlineKeyRow.get(9).setCallbackData("Паладин");
        inlineKeyRow.add(new InlineKeyboardButton("Плут"));
        inlineKeyRow.get(10).setCallbackData("Плут");
        inlineKeyRow.add(new InlineKeyboardButton("Следопыт"));
        inlineKeyRow.get(11).setCallbackData("Следопыт");
        inlineKeyRow.add(new InlineKeyboardButton("Чародей"));
        inlineKeyRow.get(12).setCallbackData("Чародей");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup backgroundDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();
        inlineKeyRow.add(new InlineKeyboardButton("Артист"));
        inlineKeyRow.get(0).setCallbackData("Артист");
        inlineKeyRow.add(new InlineKeyboardButton("Беспризорник"));
        inlineKeyRow.get(1).setCallbackData("Беспризорник");
        inlineKeyRow.add(new InlineKeyboardButton("Благородный"));
        inlineKeyRow.get(2).setCallbackData("Благородный");
        inlineKeyRow.add(new InlineKeyboardButton("Гильдейский ремесленник"));
        inlineKeyRow.get(3).setCallbackData("Гильдейский ремесленник");
        inlineKeyRow.add(new InlineKeyboardButton("Моряк"));
        inlineKeyRow.get(4).setCallbackData("Моряк");
        inlineKeyRow.add(new InlineKeyboardButton("Мудрец"));
        inlineKeyRow.get(5).setCallbackData("Мудрец");
        inlineKeyRow.add(new InlineKeyboardButton("Народный герой"));
        inlineKeyRow.get(6).setCallbackData("Народный герой");
        inlineKeyRow.add(new InlineKeyboardButton("Отшельник"));
        inlineKeyRow.get(7).setCallbackData("Отшельник");
        inlineKeyRow.add(new InlineKeyboardButton("Пират"));
        inlineKeyRow.get(8).setCallbackData("Пират");
        inlineKeyRow.add(new InlineKeyboardButton("Преступник"));
        inlineKeyRow.get(9).setCallbackData("Преступник");
        inlineKeyRow.add(new InlineKeyboardButton("Прислужник"));
        inlineKeyRow.get(10).setCallbackData("Прислужник");
        inlineKeyRow.add(new InlineKeyboardButton("Солдат"));
        inlineKeyRow.get(11).setCallbackData("Солдат");
        inlineKeyRow.add(new InlineKeyboardButton("Чужеземец"));
        inlineKeyRow.get(12).setCallbackData("Чужеземец");
        inlineKeyRow.add(new InlineKeyboardButton("Шарлатан"));
        inlineKeyRow.get(13).setCallbackData("Шарлатан");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup alignmentDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();
        inlineKeyRow1.add(new InlineKeyboardButton("Законопослушный-добрый"));
        inlineKeyRow1.get(0).setCallbackData("Законопослушный-добрый");
        inlineKeyRow1.add(new InlineKeyboardButton("Нейтральный-добрый"));
        inlineKeyRow1.get(1).setCallbackData("Нейтральный-добрый");
        inlineKeyRow1.add(new InlineKeyboardButton("Хаотичный-добрый"));
        inlineKeyRow1.get(2).setCallbackData("Хаотичный-добрый");

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();
        inlineKeyRow2.add(new InlineKeyboardButton("Законопослушный-нейтральный"));
        inlineKeyRow2.get(0).setCallbackData("Законопослушный-нейтральный");
        inlineKeyRow2.add(new InlineKeyboardButton("Нейтральный"));
        inlineKeyRow2.get(1).setCallbackData("Нейтральный");
        inlineKeyRow2.add(new InlineKeyboardButton("Хаотичный-нейтральный"));
        inlineKeyRow2.get(2).setCallbackData("Хаотичный-нейтральный");

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Законопослушный-злой"));
        inlineKeyRow3.get(0).setCallbackData("Законопослушный-злой");
        inlineKeyRow3.add(new InlineKeyboardButton("Нейтральный-злой"));
        inlineKeyRow3.get(1).setCallbackData("Нейтральный-злой");
        inlineKeyRow3.add(new InlineKeyboardButton("Хаотичный-злой"));
        inlineKeyRow3.get(2).setCallbackData("Хаотичный-злой");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup languagesDnDSelectionBoard(HashSet<LanguagesDnD> bannedButtons) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        InlineKeyboardButton language;
        if (!bannedButtons.contains(LanguagesDnD.GIANTS)) {
            language = new InlineKeyboardButton("Великанский");
            language.setCallbackData("Великанский");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.GNOMISH)) {
            language = new InlineKeyboardButton("Гномий");
            language.setCallbackData("Гномий");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.GOBLIN)) {
            language = new InlineKeyboardButton("Гоблинский");
            language.setCallbackData("Гоблинский");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.DWARWISH)) {
            language = new InlineKeyboardButton("Дварфский");
            language.setCallbackData("Дварфский");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.COMMON)) {
            language = new InlineKeyboardButton("Общий");
            language.setCallbackData("Общий");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.ORCISH)) {
            language = new InlineKeyboardButton("Орочий");
            language.setCallbackData("Орочий");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.HALFLING)) {
            language = new InlineKeyboardButton("Язык Полуросликов");
            language.setCallbackData("Язык Полуросликов");
            inlineKeyRow.add(language);
            ;
        }
        if (!bannedButtons.contains(LanguagesDnD.ELVISH)) {
            language = new InlineKeyboardButton("Эльфийский");
            language.setCallbackData("Эльфийский");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.ABYSSAL)) {
            language = new InlineKeyboardButton("Язык Бездны");
            language.setCallbackData("Язык Бездны");
            inlineKeyRow.add(language);
            ;
        }
        if (!bannedButtons.contains(LanguagesDnD.CELESTIAL)) {
            language = new InlineKeyboardButton("Небесный");
            language.setCallbackData("Небесный");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.DRACONIC)) {
            language = new InlineKeyboardButton("Драконий");
            language.setCallbackData("Драконий");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.DEEP_SPEECH)) {
            language = new InlineKeyboardButton("Глубинная речь");
            language.setCallbackData("Глубинная речь");
            inlineKeyRow.add(language);
            ;
        }
        if (!bannedButtons.contains(LanguagesDnD.INFERNAL)) {
            language = new InlineKeyboardButton("Инфернальный");
            language.setCallbackData("Инфернальный");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.PRIMORDIAL)) {
            language = new InlineKeyboardButton("Первичный");
            language.setCallbackData("Первичный");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.SYLVAN)) {
            language = new InlineKeyboardButton("Сильван");
            language.setCallbackData("Сильван");
            inlineKeyRow.add(language);

        }
        if (!bannedButtons.contains(LanguagesDnD.UNDERCOMMON)) {
            language = new InlineKeyboardButton("Подземный");
            language.setCallbackData("Подземный");
            inlineKeyRow.add(language);

        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup assignStatsBoardDnD(HashSet<String> bannedButtons) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        if (!bannedButtons.contains("Сила")) {
            InlineKeyboardButton strength = new InlineKeyboardButton("Сила");
            strength.setCallbackData("Сила");
            inlineKeyRow.add(strength);
        }

        if (!bannedButtons.contains("Ловкость")) {
            InlineKeyboardButton dexterity = new InlineKeyboardButton("Ловкость");
            dexterity.setCallbackData("Ловкость");
            inlineKeyRow.add(dexterity);
        }

        if (!bannedButtons.contains("Выносливость")) {
            InlineKeyboardButton constitution = new InlineKeyboardButton("Выносливость");
            constitution.setCallbackData("Выносливость");
            inlineKeyRow.add(constitution);
        }

        if (!bannedButtons.contains("Интеллект")) {
            InlineKeyboardButton intelligence = new InlineKeyboardButton("Интеллект");
            intelligence.setCallbackData("Интеллект");
            inlineKeyRow.add(intelligence);
        }

        if (!bannedButtons.contains("Мудрость")) {
            InlineKeyboardButton wisdom = new InlineKeyboardButton("Мудрость");
            wisdom.setCallbackData("Мудрость");
            inlineKeyRow.add(wisdom);
        }

        if (!bannedButtons.contains("Харизма")) {
            InlineKeyboardButton charisma = new InlineKeyboardButton("Харизма");
            charisma.setCallbackData("Харизма");
            inlineKeyRow.add(charisma);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup assignSkillsBoardDnD(List<String> buttons, HashSet<String> bannedButtons) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        for (String button: buttons) {
            if (bannedButtons.contains(button)) {
                continue;
            }
            InlineKeyboardButton skill = new InlineKeyboardButton(button);
            skill.setCallbackData(button);
            inlineKeyRow.add(skill);
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup variantsBoard(List<String> variants) {
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        for (int i = 1; i < variants.size() + 1; i++) {
            inlineKeyRow.add(new InlineKeyboardButton(String.valueOf(i)));
            inlineKeyRow.get(i - 1).setCallbackData(variants.get(i - 1));
        }

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static ReplyKeyboardMarkup commonSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/search"));
        keyRow1.add(new KeyboardButton("/roll"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/mofu"));
        keyRow2.add(new KeyboardButton("/help"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/credits"));

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/game"));
        keyRow4.add(new KeyboardButton("/dnd"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);
        keyRowList.add(keyRow4);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }

    public static ReplyKeyboardMarkup dndSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add("/createaplayer");
        keyRow1.add("/haltcreation");

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add("/search");
        keyRow2.add("/roll");

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add("/createacampaign");
        keyRow3.add("/endacampaign");

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add("/dmboard");

        KeyboardRow keyRow5 = new KeyboardRow();
        keyRow5.add("/common");
        keyRow5.add("/game");

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);
        keyRowList.add(keyRow4);
        keyRowList.add(keyRow5);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }

    public static ReplyKeyboardMarkup dmSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/search"));
        keyRow1.add(new KeyboardButton("/roll"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/showcampaigns"));
        keyRow2.add(new KeyboardButton("/setcampaign"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/campaignsettings"));

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/common"));
        keyRow4.add(new KeyboardButton("/game"));
        keyRow4.add(new KeyboardButton("/dnd"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow4);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }

    public static ReplyKeyboardMarkup campaignSettingsBoard() {
        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/setcampaignsname"));
        keyRow4.add(new KeyboardButton("/setpassword"));

        KeyboardRow keyRow5 = new KeyboardRow();
        keyRow5.add(new KeyboardButton("/dmboard"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow4);
        keyRowList.add(keyRow5);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }

    public static ReplyKeyboardMarkup gameSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/startagame"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/createacharacter"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/dnd"));
        keyRow3.add(new KeyboardButton("/common"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }
}
