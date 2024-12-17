package logger;

import java.util.Map;
import java.util.Objects;
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
    WHITE,

    BLACK_BOLD,
    RED_BOLD,
    GREEN_BOLD,
    YELLOW_BOLD,
    BLUE_BOLD,
    PURPLE_BOLD,
    CYAN_BOLD,
    WHITE_BOLD,

    BLACK_HIGH_INTENSITY,
    RED_HIGH_INTENSITY,
    GREEN_HIGH_INTENSITY,
    YELLOW_HIGH_INTENSITY,
    BLUE_HIGH_INTENSITY,
    PURPLE_HIGH_INTENSITY,
    CYAN_HIGH_INTENSITY,
    WHITE_HIGH_INTENSITY;

    private static Map<AnsiColorCodes, String> colors = Map.ofEntries(
            Map.entry(NONE, ""),
            Map.entry(BLACK, "\033[0;30m"),
            Map.entry(RED, "\033[0;31m"),
            Map.entry(GREEN, "\033[0;32m"),
            Map.entry(YELLOW, "\033[0;33m"),
            Map.entry(BLUE, "\033[0;34m"),
            Map.entry(PURPLE, "\033[0;35m"),
            Map.entry(CYAN, "\033[0;36m"),
            Map.entry(WHITE, "\033[0;37m"),

            Map.entry(BLACK_BOLD, "\033[1;30m"),
            Map.entry(RED_BOLD, "\033[1;31m"),
            Map.entry(GREEN_BOLD, "\033[1;32m"),
            Map.entry(YELLOW_BOLD, "\033[1;33m"),
            Map.entry(BLUE_BOLD, "\033[1;34m"),
            Map.entry(PURPLE_BOLD, "\033[1;35m"),
            Map.entry(CYAN_BOLD, "\033[1;36m"),
            Map.entry(WHITE_BOLD, "\033[1;37m"),

            Map.entry(BLACK_HIGH_INTENSITY, "\033[0;90m"),
            Map.entry(RED_HIGH_INTENSITY, "\033[0;91m"),
            Map.entry(GREEN_HIGH_INTENSITY, "\033[0;92m"),
            Map.entry(YELLOW_HIGH_INTENSITY, "\033[0;93m"),
            Map.entry(BLUE_HIGH_INTENSITY, "\033[0;94m"),
            Map.entry(PURPLE_HIGH_INTENSITY, "\033[0;95m"),
            Map.entry(CYAN_HIGH_INTENSITY, "\033[0;96m"),
            Map.entry(WHITE_HIGH_INTENSITY, "\033[0;97m")
    );

    private static Map<Level, AnsiColorCodes> consoleColor = Map.of(
            Level.INFO, CYAN_HIGH_INTENSITY,
            Level.WARNING, PURPLE_HIGH_INTENSITY,
            Level.SEVERE, RED_HIGH_INTENSITY
    );

    @Override
    public String toString() {
        return colors.get(this);
    }

    public static AnsiColorCodes getColorForLevel(Level level) {
        AnsiColorCodes returned = consoleColor.get(level);
        return Objects.requireNonNullElse(returned, NONE);
    }
}
