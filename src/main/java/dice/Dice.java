package dice;

import java.util.List;
import java.util.Random;

public class Dice {
    private final Random pointer;

    public Dice() {
        this.pointer = new Random();
    }

    private final List<List<Integer>> d20 = List.of(
            List.of(1, 19, 13, 7),
            List.of(2, 12, 18, 20),
            List.of(3, 16, 19, 17),
            List.of(4, 18, 14, 11),
            List.of(5, 13, 15, 18),
            List.of(6, 14, 16, 9),
            List.of(7, 1, 17, 15),
            List.of(8, 10, 20, 16),
            List.of(9, 6, 11, 19),
            List.of(10, 17, 12, 8),
            List.of(11, 4, 9, 13),
            List.of(12, 15, 10, 2),
            List.of(13, 11, 1, 5),
            List.of(14, 20, 4, 6),
            List.of(15, 7, 5, 12),
            List.of(16, 8, 6, 3),
            List.of(17, 3, 7, 10),
            List.of(18, 5, 2, 4),
            List.of(19, 9, 3, 1),
            List.of(20, 2, 8, 14)
    );

    private final List<List<Integer>> d12 = List.of(
            List.of(1, 5, 3, 2, 4, 6),
            List.of(2, 8, 4, 1, 3, 7),
            List.of(3, 2, 1, 5, 9, 7),
            List.of(4, 2, 8, 10, 6, 1),
            List.of(5, 11, 9, 3, 1, 6),
            List.of(6, 4, 10, 11, 5, 1),
            List.of(7, 8, 2, 3, 9, 12),
            List.of(8, 4, 2, 7, 12, 10),
            List.of(9, 12, 7, 3, 5, 11),
            List.of(10, 4, 8, 12, 11, 6),
            List.of(11, 10, 12, 9, 5, 6),
            List.of(12, 9, 11, 10, 8, 7)
    );

    private final List<List<Integer>> d10 = List.of(
            List.of(1, 9, 6, 4, 7),
            List.of(2, 8, 5, 9, 6),
            List.of(3, 7, 10, 8, 5),
            List.of(4, 6, 1, 7, 10),
            List.of(5, 3, 8, 2, 9),
            List.of(6, 2, 9, 1, 4),
            List.of(7, 1, 4, 10, 3),
            List.of(8, 10, 3, 5, 2),
            List.of(9, 5, 2, 6, 1),
            List.of(10, 4, 7, 3, 8)
    );

    private final List<List<Integer>> d10_tens = List.of(
            List.of(10, 50, 0, 60, 70),
            List.of(20, 80, 30, 50, 0),
            List.of(30, 90, 80, 20, 50),
            List.of(40, 60, 70, 90, 80),
            List.of(50, 30, 20, 0, 10),
            List.of(60, 0, 10, 70, 40),
            List.of(70, 10, 60, 40, 90),
            List.of(80, 40, 90, 30, 20),
            List.of(90, 70, 40, 80, 30),
            List.of(0, 20, 50, 10, 60)
    );

    private final List<List<Integer>> d8 = List.of(
            List.of(1, 4, 6, 8),
            List.of(2, 3, 5, 7),
            List.of(3, 6, 8, 2),
            List.of(4, 5, 7, 1),
            List.of(5, 8, 2, 4),
            List.of(6, 7, 1, 3),
            List.of(7, 2, 4, 6),
            List.of(8, 1, 3, 5)
    );

    private final List<List<Integer>> d6 = List.of(
            List.of(1, 2, 3, 4, 5),
            List.of(2, 1, 3, 4, 6),
            List.of(3, 1, 2, 5, 6),
            List.of(4, 1, 2, 5, 6),
            List.of(5, 1, 3, 4, 6),
            List.of(6, 2, 3, 4, 5)
    );

    public int generateGaussian(double min, double max) {
        int res;
        while (true) {
            res = (int) Math.round(pointer.nextGaussian(
                    (min + max) / 2,
                    (max - min) / (2 * 1.41421356237)));

            if (res <= max && res >= min) {
                return res;
            }
        }
    }

    public int generateGaussian(double bound) {
        int res;
        while (true) {
            res = (int) Math.round(pointer.nextGaussian(
                    bound / 2,
                    bound / (2 * 1.41421356237)));

            if (res <= bound && res >= 0) {
                return res;
            }
        }
    }

    public int throwDice(int diceSides) {
        int index = generateGaussian(diceSides - 1);
        int result = 0;
        List<List<Integer>> activeList;

        switch (diceSides) {
            case 20 -> activeList = d20;
            case 12 -> activeList = d12;
            case 10 -> activeList = d10;
            case 1010 -> activeList = d10_tens;
            case 8 -> activeList = d8;
            case 6 -> activeList = d6;
            case 4 -> {
                int dice1 = 0;
                int dice2 = 0;
                for (int i = 0; i < 10000; i++) {
                    dice1 = generateGaussian(1, 4);
                    dice2 = generateGaussian(1, 4);
                    if (dice1 == dice2) {
                        break;
                    }
                }
                return dice1;
            }
            default -> {
                return generateGaussian(1, diceSides);
            }
        }


        int neighbours = activeList.getFirst().size();
        for (int i = 0; i < 10000; i++) {
            result = activeList.get(index).get(generateGaussian(neighbours - 1));
            if (result != index + 1) {
                index = result - 1;
            } else {
                break;
            }
        }

        return result;
    }
}
