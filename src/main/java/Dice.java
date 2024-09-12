import java.util.ArrayList;
import java.util.Random;

public class Dice {
    private final int number_of_die;
    private final int number_of_sides;
    private final String color = "purple";

    public Dice(int number_of_die, int number_of_sides) {
        this.number_of_die = number_of_die;
        this.number_of_sides = number_of_sides;
    }

    public int get_number_of_die() {
        return number_of_die;
    }

    public int get_number_of_sides() {
        return number_of_sides;
    }

    public ArrayList<Integer> diceRoller() {
        Random dice = new Random();
        ArrayList<Integer> diceTable = new ArrayList<>();

        for (int i = 0; i < number_of_die; i++) {
            diceTable.add(dice.nextInt(number_of_sides) + 1);
        }

        return diceTable;
    }
}
