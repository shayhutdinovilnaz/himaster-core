package ee.himaster.core.localization.service;

import ee.himaster.core.localization.model.Language;
import java.util.TimeZone;

public interface LocaleService {


    void initialize(String localeCode);

    /**
     * The retrieving the current language of the request.
     *
     * @return the current request language.
     */
    Language getCurrentLanguage();

    /**
     * The retrieving the current timezone of the request.
     *
     * @return the current request timezone.
     */
    TimeZone getCurrentTimeZone();


    /**
     * The retrieving the current currency of the request.
     *
     * @return the current request currency.
     */
    String getCurrentCurrency();
}
