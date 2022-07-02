package ee.himaster.core.localization.model;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum Region {
    ESTONIA_GENERAL("EST", "EST");

    Region(String code, String countryIsoCode) {
        this.code = code;
        this.countryIsoCode = countryIsoCode;
    }

    private static final Map<String, Region> REGION_MAP;

    private final String code;
    private final String countryIsoCode;

    static {
        Map<String, Region> map = new ConcurrentHashMap<>();
        for (Region instance : Region.values()) {
            map.put(instance.getCode().toUpperCase(), instance);
        }
        REGION_MAP = Collections.unmodifiableMap(map);
    }

    public static Region get(final String code) {
        final Region region = REGION_MAP.get(code);
        if (region == null) {
            throw new IllegalArgumentException("Illegal argument exception. Region code: " + code);
        }

        return region;
    }
}
