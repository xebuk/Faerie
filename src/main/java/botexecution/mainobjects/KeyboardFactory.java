package botexecution.mainobjects;

import common.Constants;
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

    public static InlineKeyboardMarkup movementBoardGame() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();
        InlineKeyboardButton forward = new InlineKeyboardButton("Вперед");
        forward.setCallbackData("Вперед");
        inlineKeyRow1.add(forward);

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();
        InlineKeyboardButton left = new InlineKeyboardButton("Влево");
        left.setCallbackData("Влево");
        inlineKeyRow2.add(left);
        InlineKeyboardButton right = new InlineKeyboardButton("Вправо");
        right.setCallbackData("Вправо");
        inlineKeyRow2.add(right);

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        InlineKeyboardButton backward = new InlineKeyboardButton("Назад");
        backward.setCallbackData("Назад");
        inlineKeyRow3.add(backward);

        InlineKeyboardRow inlineKeyRow4 = new InlineKeyboardRow();
        InlineKeyboardButton rotateLeft = new InlineKeyboardButton("Повернуться налево");
        rotateLeft.setCallbackData("Повернуться налево");
        inlineKeyRow4.add(rotateLeft);
        InlineKeyboardButton rotateRight = new InlineKeyboardButton("Повернуться направо");
        rotateRight.setCallbackData("Повернуться направо");
        inlineKeyRow4.add(rotateRight);

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);
        inlineKeyboardRows.add(inlineKeyRow4);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup raceDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();
        inlineKeyRow1.add(new InlineKeyboardButton("Гном"));
        inlineKeyRow1.get(0).setCallbackData("Гном");
        inlineKeyRow1.add(new InlineKeyboardButton("Дварф"));
        inlineKeyRow1.get(1).setCallbackData("Дварф");
        inlineKeyRow1.add(new InlineKeyboardButton("Драконорожденный"));
        inlineKeyRow1.get(2).setCallbackData("Драконорожденный");

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();
        inlineKeyRow2.add(new InlineKeyboardButton("Полуорк"));
        inlineKeyRow2.get(0).setCallbackData("Полуорк");
        inlineKeyRow2.add(new InlineKeyboardButton("Полурослик"));
        inlineKeyRow2.get(1).setCallbackData("Полурослик");
        inlineKeyRow2.add(new InlineKeyboardButton("Полуэльф"));
        inlineKeyRow2.get(2).setCallbackData("Полуэльф");

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Тифлинг"));
        inlineKeyRow3.get(0).setCallbackData("Тифлинг");
        inlineKeyRow3.add(new InlineKeyboardButton("Человек"));
        inlineKeyRow3.get(1).setCallbackData("Человек");
        inlineKeyRow3.add(new InlineKeyboardButton("Человек (вариант)"));
        inlineKeyRow3.get(2).setCallbackData("Человек (вариант)");
        inlineKeyRow3.add(new InlineKeyboardButton("Эльф"));
        inlineKeyRow3.get(3).setCallbackData("Эльф");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup jobDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();
        inlineKeyRow1.add(new InlineKeyboardButton("Бард"));
        inlineKeyRow1.get(0).setCallbackData("Бард");
        inlineKeyRow1.add(new InlineKeyboardButton("Варвар"));
        inlineKeyRow1.get(1).setCallbackData("Варвар");
        inlineKeyRow1.add(new InlineKeyboardButton("Воин"));
        inlineKeyRow1.get(2).setCallbackData("Воин");

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();
        inlineKeyRow2.add(new InlineKeyboardButton("Волшебник"));
        inlineKeyRow2.get(0).setCallbackData("Волшебник");
        inlineKeyRow2.add(new InlineKeyboardButton("Друид"));
        inlineKeyRow2.get(1).setCallbackData("Друид");
        inlineKeyRow2.add(new InlineKeyboardButton("Жрец"));
        inlineKeyRow2.get(2).setCallbackData("Жрец");

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Изобретатель"));
        inlineKeyRow3.get(0).setCallbackData("Изобретатель");
        inlineKeyRow3.add(new InlineKeyboardButton("Колдун"));
        inlineKeyRow3.get(1).setCallbackData("Колдун");
        inlineKeyRow3.add(new InlineKeyboardButton("Монах"));
        inlineKeyRow3.get(2).setCallbackData("Монах");

        InlineKeyboardRow inlineKeyRow4 = new InlineKeyboardRow();
        inlineKeyRow4.add(new InlineKeyboardButton("Паладин"));
        inlineKeyRow4.get(0).setCallbackData("Паладин");
        inlineKeyRow4.add(new InlineKeyboardButton("Плут"));
        inlineKeyRow4.get(1).setCallbackData("Плут");

        InlineKeyboardRow inlineKeyRow5 = new InlineKeyboardRow();
        inlineKeyRow5.add(new InlineKeyboardButton("Следопыт"));
        inlineKeyRow5.get(0).setCallbackData("Следопыт");
        inlineKeyRow5.add(new InlineKeyboardButton("Чародей"));
        inlineKeyRow5.get(1).setCallbackData("Чародей");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);
        inlineKeyboardRows.add(inlineKeyRow4);
        inlineKeyboardRows.add(inlineKeyRow5);

        return new InlineKeyboardMarkup(inlineKeyboardRows);
    }

    public static InlineKeyboardMarkup backgroundDnDSelectionBoard() {
        InlineKeyboardRow inlineKeyRow1 = new InlineKeyboardRow();
        inlineKeyRow1.add(new InlineKeyboardButton("Артист"));
        inlineKeyRow1.get(0).setCallbackData("Артист");
        inlineKeyRow1.add(new InlineKeyboardButton("Беспризорник"));
        inlineKeyRow1.get(1).setCallbackData("Беспризорник");
        inlineKeyRow1.add(new InlineKeyboardButton("Благородный"));
        inlineKeyRow1.get(2).setCallbackData("Благородный");

        InlineKeyboardRow inlineKeyRow2 = new InlineKeyboardRow();
        inlineKeyRow2.add(new InlineKeyboardButton("Гильдейский ремесленник"));
        inlineKeyRow2.get(0).setCallbackData("Гильдейский ремесленник");
        inlineKeyRow2.add(new InlineKeyboardButton("Моряк"));
        inlineKeyRow2.get(1).setCallbackData("Моряк");
        inlineKeyRow2.add(new InlineKeyboardButton("Мудрец"));
        inlineKeyRow2.get(2).setCallbackData("Мудрец");

        InlineKeyboardRow inlineKeyRow3 = new InlineKeyboardRow();
        inlineKeyRow3.add(new InlineKeyboardButton("Народный герой"));
        inlineKeyRow3.get(0).setCallbackData("Народный герой");
        inlineKeyRow3.add(new InlineKeyboardButton("Отшельник"));
        inlineKeyRow3.get(1).setCallbackData("Отшельник");
        inlineKeyRow3.add(new InlineKeyboardButton("Пират"));
        inlineKeyRow3.get(2).setCallbackData("Пират");

        InlineKeyboardRow inlineKeyRow4 = new InlineKeyboardRow();
        inlineKeyRow4.add(new InlineKeyboardButton("Преступник"));
        inlineKeyRow4.get(0).setCallbackData("Преступник");
        inlineKeyRow4.add(new InlineKeyboardButton("Прислужник"));
        inlineKeyRow4.get(1).setCallbackData("Прислужник");
        inlineKeyRow4.add(new InlineKeyboardButton("Солдат"));
        inlineKeyRow4.get(2).setCallbackData("Солдат");

        InlineKeyboardRow inlineKeyRow5 = new InlineKeyboardRow();
        inlineKeyRow5.add(new InlineKeyboardButton("Чужеземец"));
        inlineKeyRow5.get(0).setCallbackData("Чужеземец");
        inlineKeyRow5.add(new InlineKeyboardButton("Шарлатан"));
        inlineKeyRow5.get(1).setCallbackData("Шарлатан");

        ArrayList<InlineKeyboardRow> inlineKeyboardRows = new ArrayList<>();
        inlineKeyboardRows.add(inlineKeyRow1);
        inlineKeyboardRows.add(inlineKeyRow2);
        inlineKeyboardRows.add(inlineKeyRow3);
        inlineKeyboardRows.add(inlineKeyRow4);
        inlineKeyboardRows.add(inlineKeyRow5);

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

    public static InlineKeyboardMarkup variantsBoard(int size) {
        ArrayList<InlineKeyboardRow> inlineKeyRows = new ArrayList<>();
        InlineKeyboardRow inlineKeyRow = new InlineKeyboardRow();

        for (int i = 1; i < size + 1; i++) {
            if (inlineKeyRow.size() == 4) {
                inlineKeyRows.add(inlineKeyRow);
                inlineKeyRow = new InlineKeyboardRow();
            }
            InlineKeyboardButton variant = new InlineKeyboardButton(String.valueOf(i));
            variant.setCallbackData(String.valueOf(i - 1));
            inlineKeyRow.add(variant);
        }

        inlineKeyRows.add(inlineKeyRow);

        return new InlineKeyboardMarkup(inlineKeyRows);
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

    public static ReplyKeyboardMarkup gameSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/startagame"));
        keyRow1.add(new KeyboardButton("/pauseagame"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/endagame"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/createacharacter"));

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/dnd"));
        keyRow4.add(new KeyboardButton("/common"));

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
        keyRow1.add(new KeyboardButton("/createaplayer"));
        keyRow1.add(new KeyboardButton("/haltcreation"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/search"));
        keyRow2.add(new KeyboardButton("/roll"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/createacampaign"));
        keyRow3.add(new KeyboardButton("/endacampaign"));

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/help"));

        KeyboardRow keyRow5 = new KeyboardRow();
        keyRow5.add(new KeyboardButton("/dmboard"));

        KeyboardRow keyRow6 = new KeyboardRow();
        keyRow6.add(new KeyboardButton("/common"));
        keyRow6.add(new KeyboardButton("/game"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);
        keyRowList.add(keyRow5);
        keyRowList.add(keyRow6);

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
        keyRow3.add(new KeyboardButton("/help"));

        KeyboardRow keyRow4 = new KeyboardRow();
        keyRow4.add(new KeyboardButton("/campaignboard"));

        KeyboardRow keyRow5 = new KeyboardRow();
        keyRow5.add(new KeyboardButton("/common"));
        keyRow5.add(new KeyboardButton("/game"));
        keyRow5.add(new KeyboardButton("/dnd"));

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

    public static ReplyKeyboardMarkup campaignSetOfCommandsBoard() {
        KeyboardRow keyRow1 = new KeyboardRow();
        keyRow1.add(new KeyboardButton("/setcampaignsname"));
        keyRow1.add(new KeyboardButton("/setpassword"));

        KeyboardRow keyRow2 = new KeyboardRow();
        keyRow2.add(new KeyboardButton("/help"));

        KeyboardRow keyRow3 = new KeyboardRow();
        keyRow3.add(new KeyboardButton("/dmboard"));

        ArrayList<KeyboardRow> keyRowList = new ArrayList<>();
        keyRowList.add(keyRow1);
        keyRowList.add(keyRow2);
        keyRowList.add(keyRow3);

        ReplyKeyboardMarkup keyBoard = new ReplyKeyboardMarkup(keyRowList);
        keyBoard.setResizeKeyboard(true);

        return keyBoard;
    }
}
