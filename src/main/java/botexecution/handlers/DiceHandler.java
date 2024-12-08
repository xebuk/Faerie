package botexecution.handlers;

import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.mainobjects.KeyboardFactory;
import dice.Dice;
import dnd.mainobjects.PlayerDnD;
import dnd.values.masteryvalues.MasteryTypeDnD;
import dnd.values.masteryvalues.AdvantageTypeDnD;
import dnd.values.aspectvalues.WeaponTraitsDnD;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DiceHandler {
    private final TextHandler walkieTalkie;
    private final DataHandler knowledge;

    private final Dice die;
    private final String color = "purple";

    public DiceHandler(DataHandler knowledge, TextHandler walkieTalkie) {
        this.walkieTalkie = walkieTalkie;
        this.knowledge = knowledge;
        this.die = new Dice();
    }

    public void D20(ChatSession cs) {
        StringBuilder luck = new StringBuilder();

        int dice = die.throwDice(20);
        luck.append(dice).append("\n");

        if (dice == 20) {
            luck.append("Критический успех!");
//            luck.append("Nat 20!");
        }
        else if (dice == 1) {
            luck.append("Критический провал...");
//            luck.append("Nat 1...");
        }

        walkieTalkie.patternExecute(cs, luck.toString(), null, false);
    }

    public void D20TwoTimes(ChatSession cs, boolean adv) {
        StringBuilder luck = new StringBuilder();

        int dice1 = die.throwDice(20);
        int dice2 = die.throwDice(20);

        luck.append(dice1).append(" / ").append(dice2).append("\n");

        if (adv) {
            if (dice1 == 20 && dice2 == 20) {
                luck.append("Двойная критический успех!!!");
//                luck.append("Double Nat 20!!!");
            }
            else if (dice1 == 20 || dice2 == 20) {
                luck.append("Критический успех!");
//                luck.append("Nat 20!");
            }
            else if (dice1 == 1 && dice2 == 1) {
                luck.append("Двойной критический провал...");
//                luck.append("Double Nat 1...");
            }
            else {
                luck.append(Integer.max(dice1, dice2)).append(" - большее из двух");
            }
        }
        else {
            if (dice1 == 20 && dice2 == 20) {
                luck.append("Двойная критический успех!!!");
//                luck.append("Double Nat 20!!!");
            }
            else if (dice1 == 1 && dice2 == 1) {
                luck.append("Двойной критический провал...");
//                luck.append("Double Nat 1...");
            }
            else if (dice1 == 1 || dice2 == 1) {
                luck.append("Критический провал...");
//                luck.append("Nat 1...");
            }
            else {
                luck.append(Integer.min(dice1, dice2)).append(" - меньшее из двух");
            }
        }

        walkieTalkie.patternExecute(cs, luck.toString(), null, false);
    }

    public String D6FourTimes() {
        StringBuilder luck = new StringBuilder();

        int dice1 = die.throwDice(6);
        int dice2 = die.throwDice(6);
        int dice3 = die.throwDice(6);
        int dice4 = die.throwDice(6);

        luck.append(dice1).append(" / ").append(dice2).append(" / ").append(dice3).append(" / ").append(dice4).append("\n");
        luck.append("Итоговый стат по костям: ").append(dice1 + dice2 + dice3 + dice4 - Integer.min(Integer.min(dice1, dice2), Integer.min(dice3, dice4)));

        return luck.toString();
    }

    public String D6FourTimes(ArrayList<Integer> dices) {
        StringBuilder luck = new StringBuilder();

        int dice1 = dices.get(0);
        int dice2 = dices.get(1);
        int dice3 = dices.get(2);
        int dice4 = dices.get(3);

        int stat = dices.get(4);

        luck.append(dice1).append(" / ").append(dice2).append(" / ").append(dice3).append(" / ").append(dice4).append("\n");
        luck.append("Итоговый стат по костям: ").append(stat).append("\n");
        luck.append("Итоговый модификатор стата: ").append(-5 + (stat / 2));

        return luck.toString();
    }

    public void D8(ChatSession cs) {
        int dice = die.throwDice(8);
        walkieTalkie.patternExecute(cs, String.valueOf(dice), null, false);
    }

    public void D6(ChatSession cs) {
        int dice = die.throwDice(8);
        walkieTalkie.patternExecute(cs, String.valueOf(dice), null, false);
    }

    public void D4(ChatSession cs) {
        int dice = die.throwDice(8);
        walkieTalkie.patternExecute(cs, String.valueOf(dice), null, false);
    }

    public String customDice(int numberOfDice, int numberOfSides) {
        StringBuilder diceTable = new StringBuilder();
        int sum = 0;
        int critSuccess = 0;
        int critFailures = 0;

        for (int i = 0; i < numberOfDice; i++) {
            int dice = die.throwDice(numberOfSides);;
            diceTable.append(dice).append(" / ");
            sum = sum + dice;
            if (dice == numberOfSides) {
                critSuccess++;
            }
            else if (dice == 1) {
                critFailures++;
            }
        }
        diceTable.setLength(diceTable.length() - 3);

        diceTable.append("\n");
        diceTable.append("Сумма: ").append(sum).append("\n");
        diceTable.append("Критических успехов: ").append(critSuccess).append("\n");
        diceTable.append("Критических провалов: ").append(critFailures).append("\n");

        return diceTable.toString();
    }

    public int customDiceResult(String dice) {
        String[] diceInfo = dice.split("d");
        int numberOfDice = Integer.parseInt(diceInfo[0]);
        int numberOfSides = Integer.parseInt(diceInfo[1]);

        int luck = 0;
        for (int i = 0; i < numberOfDice; i++) {
            luck = luck + die.throwDice(numberOfSides);
        }

        return luck;
    }

    public ArrayList<Integer> D6FourTimesCreation() {
        ArrayList<Integer> luck = new ArrayList<>();

        luck.add(die.throwDice(6));
        luck.add(die.throwDice(6));
        luck.add(die.throwDice(6));
        luck.add(die.throwDice(6));
        luck.add(luck.get(0) + luck.get(1) + luck.get(2) + luck.get(3)
                - Integer.min(Integer.min(luck.get(0), luck.get(1)), Integer.min(luck.get(2), luck.get(3))));

        return luck;
    }

    public String rollDiceLine(String diceComb) {
        StringBuilder res = new StringBuilder();

        int result = 0;
        int diceRes;

        String[] diceInfo = diceComb.split("\\+");
        int plus;
        for (String dice : diceInfo) {
            try {
                plus = Integer.parseInt(dice);
                res.append(plus).append(" (число) | ");
                result += plus;
            } catch (NumberFormatException e) {
                diceRes = customDiceResult(dice);
                res.append(diceRes).append(" / ");
                result += customDiceResult(dice);
            }
        }

        res.setLength(res.length() - 3);
        res.append("\n").append("Итого: ").append(result);

        return res.toString();
    }

    public void rollWithParameters(PlayerDnD affectedPlayer, String response) {
        ChatSession currentCampaign = knowledge.getSession(affectedPlayer.campaignChatId.toString());
        ChatSession affectedUser = knowledge.getSession(knowledge.findChatId(affectedPlayer.playerName));
        ChatSession dungeonMaster = knowledge.getSession(String.valueOf(currentCampaign.activeDm.dungeonMasterChatId));

        if (Objects.equals(response, "Нет")) {
            if (affectedPlayer.inPublic) {
                walkieTalkie.patternExecute(currentCampaign, affectedUser.username,
                        "Вы отказались от броска костей.",
                        null, false);
                walkieTalkie.patternExecute(dungeonMaster,
                        "Игрок " + affectedUser.username + " отказался от вашего предложения броска костей.",
                        null, false);
                return;
            } else {
                walkieTalkie.patternExecute(affectedUser,
                        "Вы отказались от броска костей.", null, false);
                walkieTalkie.patternExecute(dungeonMaster,
                        "Игрок " + affectedUser.username + " отказался от вашего предложения броска костей.",
                        null, false);
                return;
            }
        }
        else if (Objects.equals(response, "Да")
                && affectedPlayer.specialCase == MasteryTypeDnD.PRECISION
                && affectedPlayer.equippedWeapon.traits.contains(WeaponTraitsDnD.FENCING)) {
            if (affectedPlayer.inPublic) {
                walkieTalkie.patternExecute(currentCampaign, affectedUser.username,
                        "У вас экипировано фехтовальное оружие. Вы можете использовать Ловкость ("
                                + affectedPlayer.dexterityModifier + ") для точности.\nЧто вы хотите использовать?",
                        KeyboardFactory.dexOrStrPrecisionBoard(), false);
            } else {
                walkieTalkie.patternExecute(affectedUser,
                        "У вас экипировано фехтовальное оружие. Вы можете использовать Ловкость ("
                                + affectedPlayer.dexterityModifier + ") для точности.\nЧто вы хотите использовать?",
                        KeyboardFactory.dexOrStrPrecisionBoard(), false);
            }
            walkieTalkie.patternExecute(dungeonMaster,
                    "У игрока " + affectedUser.username + " есть фехтовальное оружие.",
                    null, false);
            return;
        }

        StringBuilder result = new StringBuilder();
        int bonus = 0;
        switch (affectedPlayer.specialCase) {
            case LUCK -> {
                bonus = 0;
                affectedPlayer.statCheck = MasteryTypeDnD.LUCK;
            }
            case PRECISION -> {
                if (Objects.equals(response, "Ловкость")) {
                    bonus = affectedPlayer.dexterityModifier;
                    affectedPlayer.statCheck = MasteryTypeDnD.DEXTERITY;
                } else if (Objects.equals(response, "Сила")) {
                    bonus = affectedPlayer.strengthModifier;
                    affectedPlayer.statCheck = MasteryTypeDnD.STRENGTH;
                }
            }
            case DAMAGE -> {
                bonus = 0;
                affectedPlayer.statCheck = MasteryTypeDnD.DAMAGE;
            }
            case DEATH_SAVE -> {
                bonus = 0;
                affectedPlayer.statCheck = MasteryTypeDnD.DEATH_SAVE;
            }
            default -> {
                switch (affectedPlayer.statCheck) {
                    case STRENGTH -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.strengthSaveThrow;
                        } else {
                            bonus = affectedPlayer.strengthModifier;
                        }
                    }
                    case DEXTERITY -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.dexteritySaveThrow;
                        } else {
                            bonus = affectedPlayer.dexterityModifier;
                        }
                    }
                    case CONSTITUTION -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.constitutionSaveThrow;
                        } else {
                            bonus = affectedPlayer.constitutionModifier;
                        }
                    }
                    case INTELLIGENCE -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.intelligenceSaveThrow;
                        } else {
                            bonus = affectedPlayer.intelligenceModifier;
                        }
                    }
                    case WISDOM -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.wisdomSaveThrow;
                        } else {
                            bonus = affectedPlayer.wisdomModifier;
                        }
                    }
                    case CHARISMA -> {
                        if (affectedPlayer.specialCase == MasteryTypeDnD.SAVE) {
                            bonus = affectedPlayer.charismaSaveThrow;
                        } else {
                            bonus = affectedPlayer.charismaModifier;
                        }
                    }
                    case ACROBATICS -> bonus = affectedPlayer.acrobatics;
                    case ANALYSIS -> bonus = affectedPlayer.analysis;
                    case ATHLETICS -> bonus = affectedPlayer.athletics;
                    case PERCEPTION -> bonus = affectedPlayer.perception;
                    case SURVIVAL -> bonus = affectedPlayer.survival;
                    case PERFORMANCE -> bonus = affectedPlayer.performance;
                    case INTIMIDATION -> bonus = affectedPlayer.intimidation;
                    case HISTORY -> bonus = affectedPlayer.history;
                    case SLEIGHT_OF_HAND -> bonus = affectedPlayer.sleightOfHand;
                    case ARCANE -> bonus = affectedPlayer.arcane;
                    case MEDICINE -> bonus = affectedPlayer.medicine;
                    case DECEPTION -> bonus = affectedPlayer.deception;
                    case NATURE -> bonus = affectedPlayer.nature;
                    case INSIGHT -> bonus = affectedPlayer.insight;
                    case RELIGION -> bonus = affectedPlayer.religion;
                    case STEALTH -> bonus = affectedPlayer.stealth;
                    case PERSUASION -> bonus = affectedPlayer.persuasion;
                    case ANIMAL_HANDLING -> bonus = affectedPlayer.animalHandling;
                    default -> bonus = 0;
                }
            }
        }

        result.append("Бросающий: ").append(affectedPlayer.name)
                .append("(").append(affectedPlayer.playerName).append(")").append("\n");

        if (Objects.equals(affectedPlayer.diceComb, "")) {
            result.append("Параметры: ").append(affectedPlayer.statCheck.toString()).append(" - ")
                .append(affectedPlayer.advantage).append("\n");
        } else {
            result.append("Параметры: ").append(affectedPlayer.diceComb).append(" - ")
                    .append(affectedPlayer.statCheck.toString()).append(" - ")
                    .append(affectedPlayer.advantage).append("\n");
        }

        if (affectedPlayer.inPublic) {
            walkieTalkie.patternExecute(currentCampaign, affectedPlayer.playerName,
                    result.toString(), null, false);
        } else {
            walkieTalkie.patternExecute(affectedUser, result.toString(), null, false);
        }
        walkieTalkie.patternExecute(dungeonMaster, result.toString(), null, false);

        result.setLength(0);

        if (affectedPlayer.advantage != AdvantageTypeDnD.CLEAR_THROW) {
            result.append("2D20:\n");
            int dice1 = die.throwDice(20);;
            int dice2 = die.throwDice(20);;
            result.append(dice1).append(" / ").append(dice2).append("\n");
            int res = 0;
            switch (affectedPlayer.advantage) {
                case ADVANTAGE -> res = Integer.max(dice1, dice2);
                case DISADVANTAGE -> res = Integer.min(dice1, dice2);
            }

            result.append("Результат: ").append(res)
                    .append(" (").append(affectedPlayer.advantage.toString()).append(")");
            result.append(" + ").append(bonus).append(" (").append(affectedPlayer.statCheck.toString()).append(") = ")
                    .append(res + bonus);

            if (affectedPlayer.inPublic) {
                walkieTalkie.patternExecute(currentCampaign, affectedPlayer.playerName,
                        result.toString(), null, false);
            } else {
                walkieTalkie.patternExecute(affectedUser, result.toString(), null, false);
            }
            walkieTalkie.patternExecute(dungeonMaster, result.toString(), null, false);
        }
        else {
            int sum;
            int critSuccess;
            int critFailures;
            int numberOfDice;
            int numberOfSides;

            int throwRes;
            int plus;

            int sumRes = 0;

            String[] dices = affectedPlayer.diceComb.split("\\+");

            for (String dice : dices) {
                sum = 0;
                critSuccess = 0;
                critFailures = 0;

                String diceInfo;
                if (dice.startsWith("d")) {
                    diceInfo = "1" + dice;
                } else {
                    diceInfo = dice;
                }

                try {
                    plus = Integer.parseInt(diceInfo);
                    result.append(plus).append(" (число)");

                    if (affectedPlayer.inPublic) {
                        walkieTalkie.patternExecute(currentCampaign, affectedPlayer.playerName,
                                result.toString(), null, false);
                    } else {
                        walkieTalkie.patternExecute(affectedUser, result.toString(), null, false);
                    }
                    walkieTalkie.patternExecute(dungeonMaster, result.toString(), null, false);

                    result.setLength(0);
                    sumRes = sumRes + plus;
                    continue;
                } catch (NumberFormatException ignored) {}

                result.append(diceInfo.toUpperCase()).append(":").append("\n");

                String[] diceData = diceInfo.split("d");
                numberOfDice = Integer.parseInt(diceData[0]);
                numberOfSides = Integer.parseInt(diceData[1]);

                for (int i = 0; i < numberOfDice; i++) {
                    throwRes = die.throwDice(numberOfSides);;
                    result.append(throwRes).append(" / ");
                    sum = sum + throwRes;
                    if (throwRes == numberOfSides) {
                        critSuccess++;
                    } else if (throwRes == 1) {
                        critFailures++;
                    }
                }
                sumRes = sumRes + sum;

                result.setLength(result.length() - 3);

                result.append("\n");
                result.append("Сумма: ").append(sum).append("\n");
                result.append("Критических успехов: ").append(critSuccess).append("\n");
                result.append("Критических провалов: ").append(critFailures).append("\n");

                if (affectedPlayer.inPublic) {
                    walkieTalkie.patternExecute(currentCampaign, affectedPlayer.playerName,
                            result.toString(), null, false);
                } else {
                    walkieTalkie.patternExecute(affectedUser, result.toString(), null, false);
                }
                walkieTalkie.patternExecute(dungeonMaster, result.toString(), null, false);

                result.setLength(0);
            }

            if (affectedPlayer.inPublic) {
                walkieTalkie.patternExecute(currentCampaign, affectedPlayer.playerName,
                        "Итого: " + sumRes, null, false);
            } else {
                walkieTalkie.patternExecute(affectedUser,
                        "Итого: " + sumRes, null, false);
            }
            walkieTalkie.patternExecute(dungeonMaster,
                    "Итого: " + sumRes, null, false);
        }

        affectedUser.whoIsRolling = "";
        currentCampaign.whoIsRolling = "";
        knowledge.renewListChat(affectedUser);
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(dungeonMaster);
    }
}
