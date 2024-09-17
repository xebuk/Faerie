import java.util.ArrayList;
import java.util.Random;

public class Dice {
    private final int numberOfDie;
    private final int numberOfSides;
    private final String color = "purple";

    public Dice(int numberOfDie, int numberOfSides) {
        this.numberOfDie = numberOfDie;
        this.numberOfSides = numberOfSides;
    }

    public ArrayList<Integer> diceRoller() {
        Random dice = new Random();
        ArrayList<Integer> diceTable = new ArrayList<>();

        for (int i = 0; i < numberOfDie; i++) {
            diceTable.add(dice.nextInt(numberOfSides) + 1);
        }

        return diceTable;
    }
}
