package botexecution.mainobjects;

import controlpanel.ControlPanel;
import logger.BotLogger;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BotLogger.info("Admin panel is opened");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            ControlPanel.parseInput(command);
        }
    }
}
