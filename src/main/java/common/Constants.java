package common;

public interface Constants {
    String URL = "https://dnd.su/";
    String HELP_MESSAGE = """
            /help - выводит список команд
            /search [раздел (spells/items/bestiary)] [название] - выводит искомую статью
            /roll [кол-во костей] [кол-во сторон] - выводит значение костей
            """;
    String SPELLS = "Fireball!";
    String ITEMS = "Deсk of many things!";
    String BESTIARY = "Tarrasque!";
    String SEARCH_MESSAGE = "What would you like to search?";
}
