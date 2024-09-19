package common;

public interface Constants {
    String helpMessage = """
            /help - выводит список команд
            /search [раздел (spells/items/bestiary)] [название] - выводит искомую статью
            /roll [кол-во костей] [кол-во сторон] - выводит значение костей
            """;
    String spells = "Fireball!";
    String items = "Deсk of many things!";
    String bestiary = "Tarrasque!";
    String searchMessage = "What would you like to search?";
}
