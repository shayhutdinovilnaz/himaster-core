package ee.himaster.core.localization.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocaleConfiguration implements Serializable {
    @JsonProperty("code")
    private String code;
    @JsonProperty("timeZoneId")
    private String timeZoneId;
    @JsonProperty("languageIsoCode")
    private String languageIsoCode;
    @JsonProperty("ccy")
    private String currency;
}
