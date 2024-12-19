package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import dnd.values.RoleParameters;
import dnd.values.masteryvalues.MasteryTypeDnD;
import logger.BotLogger;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.Set;

public class DnDNotificationHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;

    public DnDNotificationHandler(DataHandler knowledge, TextHandler walkieTalkie) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
    }

    public void eventLogging(ChatSession currentCampaign, ChatSession affectedUser, String messageFromFunction) {
        ChatSession dungeonMaster = knowledge.getSession(String.valueOf(currentCampaign.activeDm.dungeonMasterChatId));
        StringBuilder log = new StringBuilder();

        log.append("Компания: ").append(currentCampaign.activeDm.campaignName).append("\n");
        log.append("Игрок: ").append(affectedUser.username).append("\n");
        log.append(messageFromFunction);

        walkieTalkie.patternExecute(dungeonMaster, log.toString());
    }

    private final Set<String> itemParameterTokens = Set.of("-i", "-w", "-a", "-in", "-k", "-f", "-ab", "-sp");
    private final Set<String> playerParameterTokens = Set.of("-str", "-dex", "-con", "-int", "-wis", "-cha");
    public boolean isNotLegal(MessageContext ctx, String parameters) {
        if ((parameters.contains("3") && ctx.arguments().length < 3)
                || (parameters.contains("2") && ctx.arguments().length < 2)
                || (parameters.contains("1") && ctx.arguments().length == 0)) {
            walkieTalkie.patternExecute(ctx,
                    "Произошла ошибка - введено недостаточное количество аргументов в функции");
            return true;
        }


        if (parameters.contains("dm")) {
            ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
            if (currentUser.role != RoleParameters.DUNGEON_MASTER) {
                walkieTalkie.patternExecute(ctx, "Доступ запрещен - вы не ДМ данной компании.");
                return true;
            }
        }

        if (parameters.contains("int<")) {
            int check = -2;

            try {
                if (parameters.contains("int<1")) {
                    check = Integer.parseInt(ctx.firstArg()) - 1;
                }
                else if (parameters.contains("int<2")) {
                    check = Integer.parseInt(ctx.secondArg()) - 1;
                }
                else if (parameters.contains("int<3")) {
                    check = Integer.parseInt(ctx.thirdArg()) - 1;
                }

                if (check < 0) {
                    walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n"
                                    + "Попробуйте заново.");
                    return true;
                }
            } catch (NumberFormatException e) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - введено не число.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        else if (parameters.contains("int")) {
            int check;

            try {
                if (parameters.contains("int1")) {
                    check = Integer.parseInt(ctx.firstArg());
                }
                else if (parameters.contains("int2")) {
                    check = Integer.parseInt(ctx.secondArg());
                }
                else if (parameters.contains("int3")) {
                    check = Integer.parseInt(ctx.thirdArg());
                }
            } catch (NumberFormatException e) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - введено не число.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        if (parameters.contains("prof")) {
            ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
            ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());

            boolean check = false;
            if (parameters.contains("prof1")) {
                check = currentCampaign.activeDm.campaignParty.containsKey(ctx.firstArg());
            }
            else if (parameters.contains("prof2")) {
                check = currentCampaign.activeDm.campaignParty.containsKey(ctx.secondArg());
            }
            else if (parameters.contains("prof3")) {
                check = currentCampaign.activeDm.campaignParty.containsKey(ctx.thirdArg());
            }

            if (!check) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого игрока не существует в данной компании.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        if (parameters.contains("ct")) {
            ChatSession requestedUser = null;
            if (parameters.contains("ct1")) {
                requestedUser = knowledge.getSession(knowledge.findChatId(ctx.firstArg()));
            }
            else if (parameters.contains("ct2")) {
                requestedUser = knowledge.getSession(knowledge.findChatId(ctx.secondArg()));
            }
            else if (parameters.contains("ct3")) {
                requestedUser = knowledge.getSession(knowledge.findChatId(ctx.thirdArg()));
            }

            if (requestedUser == null) {
                walkieTalkie.patternExecute(ctx,
                        "Игрок в компании не имеет тега или у бота нет доступа к его личным сообщениям.\n" +
                                "Попросите его установить тег или начать диалог с ботом.\n" +
                                "Если ничего не получается, обратитесь к создателю данного бота.");
                return true;
            }
        }

        if (parameters.contains("par_item")) {
            boolean param = false;
            if (parameters.contains("par_item1")) {
                param = itemParameterTokens.contains(ctx.firstArg().toLowerCase());
            }
            else if (parameters.contains("par_item2")) {
                param = itemParameterTokens.contains(ctx.secondArg().toLowerCase());
            }
            else if (parameters.contains("par_item3")) {
                param = itemParameterTokens.contains(ctx.thirdArg().toLowerCase());
            }

            if (!param) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого параметра у команды нет.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        if (parameters.contains("par_line")) {
            String[] data = null;
            if (parameters.contains("par_line1")) {
                data = ctx.firstArg().split("-");
            }
            else if (parameters.contains("par_line2")) {
                data = ctx.secondArg().split("-");
            }
            else if (parameters.contains("par_line3")) {
                data = ctx.thirdArg().split("-");
            }

            try {
                for (String token : data) {
                    if (!MasteryTypeDnD.getRollParameters().containsKey(token)
                            && !MasteryTypeDnD.getSpecialCases().containsKey(token)) {
                        walkieTalkie.patternExecute(ctx,
                                "Произошла ошибка - введены некорректные параметры.\n" +
                                        "Попробуйте ещё раз.");
                    }
                }
            } catch (NullPointerException e) {
                walkieTalkie.patternExecute(ctx,
                                "Произошла критическая ошибка - утечка данных.\n" +
                                        "Сообщите о этом авторам бота.");
                BotLogger.severe(e.getMessage());
            }
        }

        if (parameters.contains("par_stat")) {
            boolean isStat = false;
            if (parameters.contains("par_stat1")) {
                isStat = playerParameterTokens.contains(ctx.firstArg());
            }
            else if (parameters.contains("par_stat2")) {
                isStat = playerParameterTokens.contains(ctx.secondArg());
            }
            else if (parameters.contains("par_stat3")) {
                isStat = playerParameterTokens.contains(ctx.thirdArg());
            }

            if (!isStat) {
                walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введен некорректный параметр.\n" +
                                    "Попробуйте ещё раз.");
                return true;
            }
        }

        if (parameters.contains("par_adv")) {
            boolean isAdv = false;
            if (parameters.contains("par_adv1")) {
                isAdv = MasteryTypeDnD.getEditAdv().containsKey(ctx.firstArg());
            }
            else if (parameters.contains("par_adv2")) {
                isAdv = MasteryTypeDnD.getEditAdv().containsKey(ctx.secondArg());
            }
            else if (parameters.contains("par_adv3")) {
                isAdv = MasteryTypeDnD.getEditAdv().containsKey(ctx.thirdArg());
            }

            if (!isAdv) {
                walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введен некорректный параметр.\n" +
                                    "Попробуйте ещё раз.");
                return true;
            }
        }

        return false;
    }
}
