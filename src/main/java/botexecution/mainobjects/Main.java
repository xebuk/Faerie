package botexecution.mainobjects;

import controlpanel.ControlPanel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            ControlPanel.parseInput(command);
        }
    }
}
