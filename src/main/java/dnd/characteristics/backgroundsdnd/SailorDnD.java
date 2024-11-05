package dnd.characteristics.backgroundsdnd;

import dnd.characteristics.BackgroundDnD;
import dnd.values.InstrumentsDnD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SailorDnD extends BackgroundDnD {

    public SailorDnD() {
        this.name = "Моряк";

        this.specialAbility = "Поездка на корабле";
        this.specialAbilitySummary = """
                Если понадобится, вы можете получить бесплатную поездку на паруснике для себя и своих спутников.
                Это может быть ваш старый корабль, или другой корабль, с которым вы находитесь в хороших отношениях (возможно, им командует ваш бывший напарник).
                Вам оказывают услугу, поэтому вы не можете устанавливать распорядок и прокладывать маршрут.
                Мастер сообщит, сколько времени уйдёт на плавание.
                В обмен на бесплатную поездку от вас и ваших спутников ожидают посильную помощь экипажу во время плавания.
                """;

        this.bonusSkills = 0;
        this.learnedSkills = Arrays.asList("Атлетика", "Восприятие");

        this.instrumentMastery = Set.of(InstrumentsDnD.NAVIGATOR_TOOLS); // водный транспорт

        this.bonusLanguages = 0;
        this.languages = Set.of();
        this.scripts = Set.of();

        this.specialInfo = List.of();

        this.personality = Arrays.asList("Друзья знают, что всегда могут на меня положиться.",
                "Я усердно тружусь, чтобы потом можно было хорошо отдохнуть.",
                "Мне нравится заходить в новые порты и находить друзей за кружечкой эля.",
                "Я готов приврать, чтобы получился хороший рассказ.",
                "Для меня драка в таверне — отличный способ познакомиться с новым городом.",
                "Я никогда не откажусь от пари.",
                "Я ругаюсь как банда орков.",
                "Мне нравится, когда работа выполнена, особенно если её можно поручить другому.");
        this.ideal = Arrays.asList("Уважение. Корабль держится целым благодаря взаимному уважению капитана и экипажа. (Добрый)",
                "Справедливость. Все мы делаем одно дело, и награду должны делить поровну. (Законный)",
                "Свобода. Море это свобода — свобода идти куда угодно и делать что угодно. (Хаотичный)",
                "Власть. Я хищник, а другие корабли в море — моя добыча. (Злой)",
                "Команда. Я предан экипажу, а не идеалам. (Нейтральный)",
                "Стремление. Когда-нибудь у меня будет свой корабль, и я проложу курс куда захочу. (Любой)");
        this.bond = Arrays.asList("В первую очередь я верен своему капитану, всё остальное вторично.",
                "Самое важное — это корабль, а экипаж и капитаны постоянно меняются.",
                "Я всегда буду помнить свой первый корабль.",
                "В гавани у меня есть возлюбленная, глаза которой стоят того, чтобы оставить ради неё море.",
                "Как-то раз мне выплатили не полную долю с дохода, и я хочу вернуть себе причитающееся.",
                "Безжалостные пираты убили моего капитана и почти всю команду, разграбили корабль, и оставили меня умирать. Месть будет страшной.");
        this.flaw = Arrays.asList("Я выполняю приказы, даже если считаю, что они несправедливые.",
                "Я наплету с три короба, лишь бы не делать лишнюю работу.",
                "Когда кто-то бросает мне вызов, я никогда не отступлюсь, какой бы опасной не была ситуация.",
                "Начав пить, я не могу остановиться.",
                "Не могу ничего поделать, но деньги у меня не задерживаются.",
                "Моя гордость меня когда-нибудь погубит.");
    }
}
