package botexecution.handlers.dndhandlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import dnd.values.RoleParameters;
import dnd.values.masteryvalues.AdvantageTypeDnD;
import dnd.values.masteryvalues.MasteryTypeDnD;
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
                check = switch (parameters) {
                    case "int<1" -> Integer.parseInt(ctx.firstArg()) - 1;
                    case "int<2" -> Integer.parseInt(ctx.secondArg()) - 1;
                    case "int<3" -> Integer.parseInt(ctx.thirdArg()) - 1;
                    default -> check;
                };

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

        if (parameters.contains("int")) {
            int check;

            try {
                switch (parameters) {
                    case "int1" -> check = Integer.parseInt(ctx.firstArg());
                    case "int2" -> check = Integer.parseInt(ctx.secondArg());
                    case "int3" -> check = Integer.parseInt(ctx.thirdArg());
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
            boolean check = switch (parameters) {
                case "prof1" -> currentCampaign.activeDm.campaignParty.containsKey(ctx.firstArg());
                case "prof2" -> currentCampaign.activeDm.campaignParty.containsKey(ctx.secondArg());
                case "prof3" -> currentCampaign.activeDm.campaignParty.containsKey(ctx.thirdArg());
                default -> false;
            };

            if (!check) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого игрока не существует в данной компании.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        if (parameters.contains("ct")) {
            ChatSession requestedUser = switch (parameters) {
                case "ct1" -> knowledge.getSession(knowledge.findChatId(ctx.firstArg()));
                case "ct2" -> knowledge.getSession(knowledge.findChatId(ctx.secondArg()));
                case "ct3" -> knowledge.getSession(knowledge.findChatId(ctx.thirdArg()));
                default -> null;
            };

            if (requestedUser == null) {
                walkieTalkie.patternExecute(ctx,
                        "Игрок в компании не имеет тега или у бота нет доступа к его личным сообщениям.\n" +
                                "Попросите его установить тег или начать диалог с ботом.\n" +
                                "Если ничего не получается, обратитесь к создателю данного бота.");
                return true;
            }
        }

        if (parameters.contains("par_item")) {
            boolean param = switch (parameters) {
                case "par_item1" -> itemParameterTokens.contains(ctx.firstArg().toLowerCase());
                case "par_item2" -> itemParameterTokens.contains(ctx.secondArg().toLowerCase());
                case "par_item3" -> itemParameterTokens.contains(ctx.thirdArg().toLowerCase());
                default -> false;
            };

            if (!param) {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого параметра у команды нет.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                return true;
            }
        }

        if (parameters.contains("par_line")) {
            String[] data = switch (parameters) {
                case "par_line1" -> ctx.firstArg().split("-");
                case "par_line2" -> ctx.secondArg().split("-");
                case "par_line3" -> ctx.thirdArg().split("-");
                default -> null;
            };

            for (String token : data) {
                if (!MasteryTypeDnD.getRollParameters().containsKey(token)
                        && !MasteryTypeDnD.getSpecialCases().containsKey(token)) {
                    walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введены некорректные параметры.\n" +
                                    "Попробуйте ещё раз.");
                }
            }
        }

        if (parameters.contains("par_stat")) {
            boolean isStat = switch (parameters) {
                case "par_stat1" -> playerParameterTokens.contains(ctx.firstArg());
                case "par_stat2" -> playerParameterTokens.contains(ctx.secondArg());
                case "par_stat3" -> playerParameterTokens.contains(ctx.thirdArg());
                default -> false;
            };

            if (!isStat) {
                walkieTalkie.patternExecute(ctx,
                            "Произошла ошибка - введен некорректный параметр.\n" +
                                    "Попробуйте ещё раз.");
                return true;
            }
        }

        if (parameters.contains("par_adv")) {
            boolean isAdv = switch (parameters) {
                case "par_adv1" -> MasteryTypeDnD.getEditAdv().containsKey(ctx.firstArg());
                case "par_adv2" -> MasteryTypeDnD.getEditAdv().containsKey(ctx.secondArg());
                case "par_adv3" -> MasteryTypeDnD.getEditAdv().containsKey(ctx.thirdArg());
                default -> false;
            };

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
