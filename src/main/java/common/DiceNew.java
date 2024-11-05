package common;

import java.util.ArrayList;
import java.util.Random;

public class DiceNew {
    private static final Random random = new Random();
    private final String color = "purple";

    public static String D20() {
        StringBuilder luck = new StringBuilder();

        int dice = random.nextInt(20) + 1;
        luck.append(dice).append("\n");

        if (dice == 20) {
            luck.append("Критический успех!");
//            luck.append("Nat 20!");
        }
        else if (dice == 1) {
            luck.append("Критический провал...");
//            luck.append("Nat 1...");
        }
        return luck.toString();
    }

    public static String D20TwoTimes(boolean adv) {
        StringBuilder luck = new StringBuilder();

        int dice1 = random.nextInt(20) + 1;
        int dice2 = random.nextInt(20) + 1;

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
        return luck.toString();
    }

    public static String D6FourTimes() {
        StringBuilder luck = new StringBuilder();

        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        int dice4 = random.nextInt(6) + 1;

        luck.append(dice1).append(" / ").append(dice2).append(" / ").append(dice3).append(" / ").append(dice4).append("\n");
        luck.append("Итоговый стат по костям: ").append(dice1 + dice2 + dice3 + dice4 - Integer.min(Integer.min(dice1, dice2), Integer.min(dice3, dice4)));

        return luck.toString();
    }

    public static String D6FourTimes(ArrayList<Integer> dices) {
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

    public static String D8() {
        int dice = random.nextInt(8) + 1;
        return String.valueOf(dice);
    }

    public static String D6() {
        int dice = random.nextInt(6) + 1;
        return String.valueOf(dice);
    }

    public static String D4() {
        int dice = random.nextInt(4) + 1;
        return String.valueOf(dice);
    }

    public static String customDice(int numberOfDice, int numberOfSides) {
        StringBuilder diceTable = new StringBuilder();
        int sum = 0;
        int critSuccess = 0;
        int critFailures = 0;

        for (int i = 0; i < numberOfDice; i++) {
            int dice = random.nextInt(numberOfSides) + 1;
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

    public static int customDiceResult(String dice) {
        String[] diceInfo = dice.split("d");
        int numberOfDice = Integer.parseInt(diceInfo[0]);
        int numberOfSides = Integer.parseInt(diceInfo[1]);

        int luck = 0;
        for (int i = 0; i < numberOfDice; i++) {
            luck = luck + random.nextInt(numberOfSides) + 1;
        }

        return luck;
    }

    public static ArrayList<Integer> D6FourTimesCreation() {
        ArrayList<Integer> luck = new ArrayList<>();

        luck.add(random.nextInt(6) + 1);
        luck.add(random.nextInt(6) + 1);
        luck.add(random.nextInt(6) + 1);
        luck.add(random.nextInt(6) + 1);
        luck.add(luck.get(0) + luck.get(1) + luck.get(2) + luck.get(3)
                - Integer.min(Integer.min(luck.get(0), luck.get(1)), Integer.min(luck.get(2), luck.get(3))));

        return luck;
    }
}
