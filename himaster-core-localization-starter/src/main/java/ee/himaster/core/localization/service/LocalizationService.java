package ee.himaster.core.localization.service;

import ee.himaster.core.localization.model.LocalizedStringModel;
import ee.himaster.core.localization.model.LanguageModel;

/**
 * The service uses for working with a localization string attributes of items.
 * The service allow to get a localized string value of attribute for certain language and to add the localized attribute value.
 */
public interface LocalizationService {

    /**
     * The method create/update localized string value. If value is exist for language, the value is update.
     * If not exist - create a new value in required language.
     * If language is nullable - then value is set as default value for localized attribute.
     *
     * @param language        - the values language
     * @param value           - the value of language
     * @param localizedString - the item contents a string value in different language.
     * @return updated localized string model
     */
    LocalizedStringModel addLocalizedStringValue(final LanguageModel language, final String value, final LocalizedStringModel localizedString);

    /**
     * The method define and return localized string value of attribute content.
     *
     * @param language        - the required language of value
     * @param localizedString - the item contents a string value in different language.
     * @return return localized string value, if it doesn't exist - return default value. If default value isn't exist - return null;
     */
    String getLocalizedStringValue(final LanguageModel language, final LocalizedStringModel localizedString);

}
