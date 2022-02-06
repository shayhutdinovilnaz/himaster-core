package ee.himaster.core.localization.model;

import java.util.TimeZone;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocaleModel {
    private final String code;
    private final Language language;
    private final TimeZone timeZone;
    private final Currency currency;
}
