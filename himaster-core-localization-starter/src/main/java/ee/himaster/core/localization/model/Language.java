package ee.himaster.core.localization.model;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;

@Getter
public enum Language {
    RUSSIAN("RU"), ESTONIAN("EE"), ENGLISH("EN"), DEUTSCHE("DE");

    private final String isoCode;

    private static final Map<String, Language> LANGUAGE_MAP;

    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    static {
        Map<String, Language> map = new ConcurrentHashMap<>();
        for (Language instance : Language.values()) {
            map.put(instance.getIsoCode().toUpperCase(), instance);
        }
        LANGUAGE_MAP = Collections.unmodifiableMap(map);
    }

    public static Language get(String name) {
        return Optional.ofNullable(LANGUAGE_MAP.get(name.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Illegal argument exception. Language iso-code: " + name));
    }

}
