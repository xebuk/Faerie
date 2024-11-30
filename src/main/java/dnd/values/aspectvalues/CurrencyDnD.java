package dnd.values.aspectvalues;

import java.io.Serializable;
import java.util.Map;

public enum CurrencyDnD implements Serializable {
    COPPER_COINS,
    SILVER_COINS,
    GOLD_COINS;

    private static final Map<String, CurrencyDnD> currencies = Map.of("Медь", COPPER_COINS,
            "Серебро", SILVER_COINS, "Золото", GOLD_COINS);

    public static Map<String, CurrencyDnD> getCurrencies() {
        return currencies;
    }

    public static CurrencyDnD getCurrency(String currency) {
        return currencies.get(currency);
    }
}