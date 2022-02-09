package ee.himaster.core.localization.model;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public enum Currency {
    USD("USD"), EURO("EURO");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    private static final Map<String, Currency> CURRENCY_MAP;

    static {
        Map<String, Currency> map = new ConcurrentHashMap<>();
        for (Currency instance : Currency.values()) {
            map.put(instance.getCode().toUpperCase(), instance);
        }

        CURRENCY_MAP = Collections.unmodifiableMap(map);
    }

    public static Currency get(String name) {
        Currency currency = CURRENCY_MAP.get(name.toUpperCase());
        if (currency == null) {
            throw new IllegalArgumentException("Illegal argument exception. Currency code: " + name);
        }

        return currency;
    }
}
