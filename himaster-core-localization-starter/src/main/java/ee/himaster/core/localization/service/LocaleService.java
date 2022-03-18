package ee.himaster.core.localization.service;

import ee.himaster.core.localization.model.Currency;
import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.localization.model.Region;

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
    Currency getCurrentCurrency();


    /**
     * The retrieving the locale model by a region and a language. The Language could be nullable.
     *
     * @return the locale model
     */
    LocaleModel getLocale(Region region, Language language);
}
