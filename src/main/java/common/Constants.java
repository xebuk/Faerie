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
}
