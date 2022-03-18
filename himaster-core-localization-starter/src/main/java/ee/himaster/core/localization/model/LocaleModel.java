package ee.himaster.core.localization.model;

import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocaleModel {
    private String code;
    private Language language;
    private TimeZone timeZone;
    private Currency currency;
    private Region region;
    private boolean defaultForRegion;
}
