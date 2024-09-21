package common;

public interface Constants {
    String URL = "https://dnd.su/";

    String HELP_MESSAGE = """
            /help - выводит список команд
            /hello - "Привет, мир!"
            /search - выводит меню поиска статьи
            /roll - выводит меню броска костей
            """;
    String START_MESSAGE = """
            Привет! Меня зовут Faerie!
            Для вывода описания команд нажмите или наберите /help
            """;
    String SEARCH_MESSAGE = "What would you like to search?";
    String SEARCH_MESSAGE_SPELLS = "Type the name of a spell.";
    String SEARCH_MESSAGE_ITEMS = "Type the name of a item.";
    String SEARCH_MESSAGE_BESTIARY = "Type the name of a beast.";
    String SEARCH_MESSAGE_RACES = "Type the name of a race.";
    String SEARCH_MESSAGE_CLASSES = "Type the name of a class.";
    String SEARCH_MESSAGE_FEATS = "Type the name of a feat.";
    String SEARCH_MESSAGE_BACKGROUNDS = "Type the name of a background.";

    String ROLL_MESSAGE = "What kind of a dice do you want to roll?";
    String ROLL_MESSAGE_QUANTITY = "How many?";
    String ROLL_MESSAGE_ADVANTAGE = "Do you have advantage?";

    String ADVANTAGE = "Yes)";
    String DISADVANTAGE = "No(";

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

    String SEARCH_COMMAND = "User requested an article search.";
    String DICE_COMMAND = "User requested a dice roll.";

    String SPELLS = "Fireball!";
    String ITEMS = "Deck of many things!";
    String BESTIARY = "Tarrasque!";
    String RACES = "Elf!";
    String CLASSES = "Artificer!";
    String FEATS = "Sentinel!";
    String BACKGROUNDS = "Criminal!";

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
}
