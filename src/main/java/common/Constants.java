package common;

public interface Constants {
    String URL = "https://dnd.su/";
    String CREDITS = "Сделано на коленке @LartsAL и @esb_bbdl за 3 недели";

    String HELP_MESSAGE = """
            /help - выводит список команд
            /mofu - "Mofu Mofu!"
            /search - выводит меню поиска статьи
            /roll - выводит меню броска костей
            /credits - выводит авторов сия бота
            """;
    String START_MESSAGE = """
            Привет! Меня зовут Faerie!
            Для вывода описания команд нажмите или наберите /help
            """;

    String SEARCH_MESSAGE = "Что бы вы хотели найти?";
    String SEARCH_MESSAGE_SPELLS = "Введите название заклинания.";
    String SEARCH_MESSAGE_ITEMS = "Введите название предмета.";
    String SEARCH_MESSAGE_BESTIARY = "Введите название существа.";
    String SEARCH_MESSAGE_RACES = "Введите название расы.";
    String SEARCH_MESSAGE_CLASSES = "Введите название класса.";
    String SEARCH_MESSAGE_FEATS = "Введите название черты.";
    String SEARCH_MESSAGE_BACKGROUNDS = "Введите название предыстории.";

    String SEARCH_MESSAGE_FAIL = "Такой статьи не было найдено. Поиск отменен.";
    String SEARCH_MESSAGE_FAIL_SECOND_STAGE = "В запросе есть ошибка. Пожалуйста, попробуйте ввести его ещё раз.";
    String SEARCH_MESSAGE_INCORRECT = "Название введено неверно. Поиск отменен.";
    String SEARCH_MESSAGE_IMPOSSIBLE = "Там, где ошибка невозможна, случилась ошибка. Сообщите об этом авторам бота.";

//    String SEARCH_MESSAGE = "What would you like to search?";
//    String SEARCH_MESSAGE_SPELLS = "Type the name of a spell.";
//    String SEARCH_MESSAGE_ITEMS = "Type the name of a item.";
//    String SEARCH_MESSAGE_BESTIARY = "Type the name of a beast.";
//    String SEARCH_MESSAGE_RACES = "Type the name of a race.";
//    String SEARCH_MESSAGE_CLASSES = "Type the name of a class.";
//    String SEARCH_MESSAGE_FEATS = "Type the name of a feat.";
//    String SEARCH_MESSAGE_BACKGROUNDS = "Type the name of a background.";

    String ROLL_MESSAGE = "Какие кости вы хотите бросить?";
    String ROLL_MESSAGE_ADVANTAGE = "У вас есть преимущество?";

//    String ROLL_MESSAGE = "What kind of a dice do you want to roll?";
//    String ROLL_MESSAGE_ADVANTAGE = "Do you have advantage?";

    String ADVANTAGE = "Да)";
    String DISADVANTAGE = "Нет(";

//    String ADVANTAGE = "Yes)";
//    String DISADVANTAGE = "No(";

    String ROLL_D100 = "Heck, a 100...";
    String ROLL_D20 = "Nat 20!";
    String ROLL_2D20 = "Hm...";
    String ROLL_D12 = "12!";
    String ROLL_D10 = "10!";
    String ROLL_D8 = "8!";
    String ROLL_D6 = "6!";
    String ROLL_4D6 = "More stats!";
    String ROLL_D5 = "5!";
    String ROLL_D4 = "4!";
    String ROLL_D3 = "3!";
    String ROLL_D2 = "2!";

    String CUSTOM_DICE = "Uh-oh, big number incoming!";
    String CUSTOM_DICE_MESSAGE = "Введите количество и номер кости в виде: 10d40";
    String CUSTOM_DICE_MESSAGE_WITH_PRESETS = "Выберите вариант из предложенных или введите количество и номер кости в виде: 10d40";
    String CUSTOM_DICE_ERROR = "Во время вашего броска возникла ошибка. Попробуйте ещё раз.";
//    String CUSTOM_DICE_MESSAGE = "Write a number and a dice in pattern: 10d40";
//    String CUSTOM_DICE_MESSAGE_WITH_PRESETS = "Choose a variant below or write a number and a dice in pattern: 10d40";

    String SEARCH_COMMAND = "User requested an article search.";
    String DICE_COMMAND = "User requested a dice roll.";

    String SPELLS = "Fireball!";
    String ITEMS = "Deck of many things!";
    String BESTIARY = "Tarrasque!";
    String RACES = "Elf!";
    String CLASSES = "Artificer!";
    String FEATS = "Sentinel!";
    String BACKGROUNDS = "Criminal!";

    String CREATION_MENU_CHOOSE_JOB = "Выберите свой класс: ";

    String CREATION_MENU_FIGHTER = "Action Surge!";
    String CREATION_MENU_CLERIC = "Divine Intervention!";
    String CREATION_MENU_MAGE = "Wish!";
    String CREATION_MENU_ROGUE = "Stealth!";
    String CREATION_MENU_RANGER = "Arrow!";

    String CREATION_MENU_SET_STATS = "Установите характеристики персонажа: ";

    String CREATION_MENU_STRENGTH = "SMASH!!!";
    String CREATION_MENU_DEXTERITY = "Initiative!";
    String CREATION_MENU_CONSTITUTION = "Illness!";
    String CREATION_MENU_INTELLIGENCE = "Analysis!";
    String CREATION_MENU_WISDOM = "Perception!";
    String CREATION_MENU_CHARISMA = "Intimidation!";

    String CREATION_MENU_HEALTH = "Ваше здоровье равно: ";
    String CREATION_MENU_ARMOR = "Ваш класс брони равен: ";
    String CREATION_MENU_ATTACK = "Ваш кубик атаки: ";

    String CLASSES_LIST = """
            Официальные классы:
            <a href="https://dnd.su/class/88">Бард [Bard]</a>
            <a href="https://dnd.su/class/87">Варвар [Barbarian]</a>
            <a href="https://dnd.su/class/91">Воин [Fighter]</a>
            <a href="https://dnd.su/class/105">Волшебник [Wizard]</a>
            <a href="https://dnd.su/class/90">Друид [Druid]</a>
            <a href="https://dnd.su/class/89">Жрец [Cleric]</a>
            <a href="https://dnd.su/class/137">Изобретатель [Artificer]</a>
            <a href="https://dnd.su/class/104">Колдун [Warlock]</a>
            <a href="https://dnd.su/class/93">Монах [Monk]</a>
            <a href="https://dnd.su/class/94">Паладин [Paladin]</a>
            <a href="https://dnd.su/class/99">Плут [Rogue]</a>
            <a href="https://dnd.su/class/97">Следопыт [Ranger]</a>
            <a href="https://dnd.su/class/101">Чародей [Sorcerer]</a>
            """;

    String IMAGE_OUTPUT_PATH = "../token_dir/firstPersonView/";
}
