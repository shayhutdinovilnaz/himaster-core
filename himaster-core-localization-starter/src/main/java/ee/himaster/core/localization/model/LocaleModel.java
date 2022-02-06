package ee.himaster.core.localization.model;

import java.util.TimeZone;
import lombok.Data;

@Data
public class LocaleModel {
    private final String code;
    private final Language language;
    private final TimeZone timeZone;
    private final String currency;
}
