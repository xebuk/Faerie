package common;

import java.util.ArrayList;
import java.util.Random;

public class DiceNew {
    private static final Random random = new Random();
    private final String color = "purple";

    public static String D20() {
        int dice = random.nextInt(20) + 1;
        if (dice == 20) {
            return dice + " - Nat 20!";
        }
        else if (dice == 1) {
            return dice + " - Nat 1...";
        }
        else {
            return String.valueOf(dice);
        }
    }

    public static String D20_two_times(boolean adv) {
        int dice1 = random.nextInt(20) + 1;
        int dice2 = random.nextInt(20) + 1;

        if (adv) {
            if (dice1 == 20 || dice2 == 20) {
                return dice1 + " " + dice2 + " - Nat 20!";
            }
            else if (dice1 == 1 && dice2 == 1) {
                return dice1 + " " + dice2 + " - Double Nat 1...";
            }
            else {
                return dice1 + " " + dice2 + " - " + Integer.max(dice1, dice2);
            }
        }
        else {
            if (dice1 == 20 && dice2 == 20) {
                return dice1 + " " + dice2 + " - Double Nat 20!";
            }
            else if (dice1 == 1 || dice2 == 1) {
                return dice1 + " " + dice2 + " - Nat 1...";
            }
            else {
                return dice1 + " " + dice2 + " - " + Integer.min(dice1, dice2);
            }
        }
    }

    public static String D6_four_times() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;
        int dice4 = random.nextInt(6) + 1;

        return dice1 + " " + dice2 + " " + dice3 + " " + dice4 + " - Итоговый стат: "
                + (dice1 + dice2 + dice3 + dice4 - Integer.min(Integer.min(dice1, dice2), Integer.min(dice3, dice4)));
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

    public static ArrayList<Integer> customDice(int numberOfDice, int numberOfSides) {
        ArrayList<Integer> diceTable = new ArrayList<>();

        for (int i = 0; i < numberOfDice; i++) {
            diceTable.add(random.nextInt(numberOfSides) + 1);
        }

        return diceTable;
    }
}
