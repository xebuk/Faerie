package game.states;

import java.util.ArrayList;
import java.util.Random;

public class Event {
    private ArrayList<String> listOfIncidents = new ArrayList<>();
    private Random random = new Random();
    private int occurence = random.nextInt(5);

    public Event() {
        listOfIncidents.add("Mimic!"); // 1
        listOfIncidents.add("Trap!"); // 2
        listOfIncidents.add("Treasure chest!"); // 3
        listOfIncidents.add("Ambush!"); // 4
        listOfIncidents.add("Secret!"); // 5
    }

    public void generateEvent() {
        System.out.println(listOfIncidents.get(occurence));
    }
}
