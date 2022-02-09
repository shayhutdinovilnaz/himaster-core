package ee.himaster.core.localization.provider.impl;

import ee.himaster.core.localization.model.Currency;
import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocaleConfiguration;
import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.localization.provider.LocaleProvider;
import ee.himaster.core.service.exception.ModelNotFoundException;
import ee.himaster.core.service.util.JsonConfigurationReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class DefaultLocaleProvider implements LocaleProvider {
    private static final String LOCALE_CONFIGURATION_FILE_PATH = "locale-configuration.json";
    private static final Map<String, LocaleModel> LOCALE_CONFIGURATION;

    static {
        try {
            LOCALE_CONFIGURATION = readLocaleConfiguration().stream().collect(Collectors.toMap(LocaleModel::getCode, localeModel -> localeModel));
        } catch (Exception e) {
            throw new IllegalStateException("Error during read locale configuration file. " +
                    "Configuration file path=" + LOCALE_CONFIGURATION_FILE_PATH, e);
        }
    }

    private static List<LocaleModel> readLocaleConfiguration() {
        return JsonConfigurationReader.readFromFile(LOCALE_CONFIGURATION_FILE_PATH, LocaleConfiguration.class)
                .stream()
                .map(DefaultLocaleProvider::convert)
                .collect(Collectors.toList());
    }

    private static LocaleModel convert(LocaleConfiguration localeConfiguration) {
        validateConfiguration(localeConfiguration);
        return LocaleModel
                .builder()
                .code(localeConfiguration.getCode().toUpperCase())
                .currency(Currency.get(localeConfiguration.getCurrency().toUpperCase()))
                .language(Language.get(localeConfiguration.getLanguageIsoCode().toUpperCase()))
                .timeZone(TimeZone.getTimeZone(localeConfiguration.getTimeZoneId().toUpperCase()))
                .build();
    }

    private static void validateConfiguration(final LocaleConfiguration configuration) {
        Objects.requireNonNull(configuration.getCode(),
                "Locale's code could not be nullable in the configuration file");
        Objects.requireNonNull(configuration.getTimeZoneId(),
                "Time zone id could not be nullable in the configuration file. The locale code =" + configuration.getCode());
        Objects.requireNonNull(configuration.getLanguageIsoCode(),
                "Language iso-code could not be nullable in the configuration file. The locale code =" + configuration.getCode());
        Objects.requireNonNull(configuration.getCurrency(),
                "Language currency could not be nullable in the configuration file. The locale code =" + configuration.getCode());
    }

    @Override
    public LocaleModel getByCode(final String localeCode) {
        Objects.requireNonNull(localeCode, "Locale code could not be nullable");
        LocaleModel localeModel = LOCALE_CONFIGURATION.get(localeCode.toUpperCase());
        if (localeModel == null) {
            throw new ModelNotFoundException("Local is not found by code. Code: " + localeCode);
        }

        return localeModel;
    }
}
