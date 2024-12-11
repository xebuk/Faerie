package logger;

import java.util.Map;
import java.util.logging.Level;

public enum AnsiColorCodes {
    NONE,

    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    CYAN,
    WHITE;

    private static Map<AnsiColorCodes, String> colors = Map.ofEntries(
            Map.entry(NONE, ""),
            Map.entry(BLACK, "\u001B[30m"),
            Map.entry(RED, "\u001B[31m"),
            Map.entry(GREEN, "\u001B[32m"),
            Map.entry(YELLOW, "\u001B[33m"),
            Map.entry(BLUE, "\u001B[34m"),
            Map.entry(PURPLE, "\u001B[35m"),
            Map.entry(CYAN, "\u001B[36m"),
            Map.entry(WHITE, "\u001B[37m")
    );

    private static Map<Level, AnsiColorCodes> consoleColor = Map.of(
            Level.INFO, CYAN,
            Level.WARNING, PURPLE,
            Level.SEVERE, BLACK
    );

    @Override
    public String toString() {
        return colors.get(this);
    }

    public static AnsiColorCodes getColorForLevel(Level level) {
        AnsiColorCodes returned = consoleColor.get(level);
        if (returned == null) {
            return NONE;
        }
        else {
            return returned;
        }
    }
}
