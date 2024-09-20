package common;

public interface Constants {
    String URL = "https://dnd.su/";

    String HELP_MESSAGE = """
            /help - выводит список команд
            /search [раздел (spells/items/bestiary)] [название] - выводит искомую статью
            /roll [кол-во костей] [кол-во сторон] - выводит значение костей
            """;
    String SEARCH_MESSAGE = "What would you like to search?";
    String SEARCH_MESSAGE_SPELLS = "Type the name of a spell.";
    String SEARCH_MESSAGE_ITEMS = "Type the name of a item.";
    String SEARCH_MESSAGE_BESTIARY = "Type the name of a beast.";

    String SEARCH_COMMAND = "User requested an article search.";
    String DICE_COMMAND = "User requested a dice roll.";

    String SPELLS = "Fireball!";
    String ITEMS = "Deсk of many things!";
    String BESTIARY = "Tarrasque!";
}
