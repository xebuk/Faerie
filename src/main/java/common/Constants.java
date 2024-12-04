package common;

public interface Constants {
    String URL = "https://dnd.su/";
    String CREDITS = "Сделано на коленке @LartsAL и @esb_bbdl за 3 недели";

    String CHANGE_TO_COMMON_KEYBOARD = "Вы перешли на стандартную клавиатуру!";
    String CHANGE_TO_GAME_KEYBOARD = "Вы перешли на клавиатуру игры!";
    String CHANGE_TO_DND_KEYBOARD = "Вы перешли на клавиатуру DnD!";
    String CHANGE_TO_PLAYER_KEYBOARD = "Вы перешли на клавиатуру Игрока!";
    String CHANGE_TO_DM_KEYBOARD = "Вы перешли на клавиатуру DM-a!";
    String CHANGE_TO_ITEMS_KEYBOARD = "Вы перешли на клавиатуру предметов!";
    String CHANGE_TO_CAMPAIGN_KEYBOARD = "Вы перешли на клавиатуру компании!";
    String CHANGE_TO_STATS_KEYBOARD = "Вы перешли на клавиатуру изменения статов!";
    String CHANGE_TO_QUEST_KEYBOARD = "Вы перешли на клавиатуру заданий!";

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

    int MAX_CUSTOM_DICE_PRESETS = 5;

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

    String CREATION_MENU_CHOOSE_NAME = "Введите своё имя.";
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

    int gameMazeWidth = 50;
    int gameMazeHeight = 50;
    int gameRoomMinSize = 3;
    int gameRoomMaxSize = 7;
    int gameRoomCount = 30;

    String GAME_START = """
            Игра запущена!
            Для паузы введите /pauseagame
            """;
    String GAME_RESTRICTED = """
            Вы не создали персонажа.
            """;
    String GAME_PAUSE = """
            Сеанс приостановлен.
            """;
    String GAME_CONTINUE = """
            Сеанс возобновлен.
            """;
    String GAME_EXPUNGE = """
            Сеанс удален.
            """;

    String CAMPAIGN_CREATION_START = """
            Поздравляю! Вам присуждена роль Мастера Подземелья (ДМ-а) в этом чате!
            Выберите название для компании (его можно сменить позже).
            Внимание! Кампания, как и ДМ, может быть только одна на группу.
            """;
    String CAMPAIGN_CREATION_NOTIFICATION = """
            Вы создали компанию. Вы можете просмотреть свои компании через /showcampaigns
            """;
    String CAMPAIGN_CREATION_CONGRATULATION = """
            Поздравляю с началом кампании! Теперь игроки могут добавляться к вам в компанию.
            """;
    String CAMPAIGN_CREATION_EXISTS = """
            В этой группе уже существует компания.
            Для начала новой компании ткните старого DM-а в бок и попросите его распустить компанию.
            """;

    String PLAYER_CREATION_START = """
            Пройдите в личные сообщения для начала создания персонажа.
            Вы можете пользоваться функцией поиска для создания персонажа в случае сомнений.
            """;
    String PLAYER_CREATION_PAUSE = "Процесс создания персонажа ДнД был поставлен на паузу.";
    String PLAYER_CREATION_CONTINUE = "Процесс создания персонажа ДнД был возобновлен.";
    String PLAYER_CREATION_WARNING = """
            Внимание!
            Перед тем, как начать создание персонажа компании,
            введите команду /start в личных сообщениях у бота,
            иначе процесс создания не будет начат.
            Также, если одновременно создаются несколько персонажей,
            то рекомендуем при окончании создания (последний этап создания на этот момент: описание волос)
            держать период в 1 минуту для избежания риска потери данных о персонаже.
            """;
    String PLAYER_CREATION_END = """
            Создание персонажа было успешно произведено.
            Если вам не нравится персонаж, то вы можете его либо пересоздать, заново введя команду создания персонажа,
            либо изменив параметры текущего персонажа с помощью DM-а.
            """;

    String PLAYER_CREATION_NAME = "Введите имя персонажа.";

    String SHOW_CAMPAIGN_GROUP_NOTHING = "В данной группе нет компании.";

    String SET_CAMPAIGN_NOT_FOUND = "Такая компания не найдена. Устанавливаю: Нет компании.";
    String SET_CAMPAIGN_NULL = "Текущая компания не была указана. Для начала укажите текущую компанию.";
    String SET_CAMPAIGN_SUCCESS = "Название компании было изменено.";

    String SET_PASSWORD_SUCCESS = "Пароль для удаления компании был указан. При удалении компании он отправится к вам в личные сообщения.";

    String SET_MULTICLASS_LIMIT_ZERO = "При установке лимита классов на персонажа на 0, вы снимаете ограничение на количество классов.";

    String CAMPAIGN_END_RESTRICTION = "Вы не DM данной компании.";
    String CAMPAIGN_END_VERIFICATION = "Введите пароль для роспуска компании.";
    String CAMPAIGN_END_AFFIRMATION = "Вы точно уверены, что хотите закончить данную компанию?";
    String CAMPAIGN_END = "Компания была распущена.";
    String CAMPAIGN_END_FALSE_ALARM = "Действия отменены.";

    String STANDARD_INVENTORY_SUMMARY = "Определите с ДМ-ом описание предмета.";

    String IMAGE_OUTPUT_PATH = "../token_dir/userData/";        // TODO: Finally rename/split token_dir
    String ROOM_PRESETS_PATH = "../token_dir/roomPresets/";     //       and make logical folders names?
    String LOGS_PATH = "../token_dir/logs/";
}
