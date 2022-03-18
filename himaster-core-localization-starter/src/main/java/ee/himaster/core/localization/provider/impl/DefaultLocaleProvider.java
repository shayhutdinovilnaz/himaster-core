package ee.himaster.core.localization.provider.impl;

import ee.himaster.core.localization.model.*;
import ee.himaster.core.localization.model.Currency;
import ee.himaster.core.localization.provider.LocaleProvider;
import ee.himaster.core.service.exception.ModelNotFoundException;
import ee.himaster.core.service.util.JsonConfigurationReader;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultLocaleProvider implements LocaleProvider {
    private static final String LOCALE_CONFIGURATION_FILE_PATH = "locale-configuration.json";
    private static final Map<String, LocaleModel> LOCALE_CONFIGURATION_BY_LOCALE_CODE;
    private static final Map<Region, List<LocaleModel>> LOCALE_CONFIGURATION_BY_REGION;

    static {
        try {
            List<LocaleModel> locales = readLocaleConfiguration();
            LOCALE_CONFIGURATION_BY_LOCALE_CODE = locales.stream()
                    .collect(Collectors.toMap(locale -> locale.getCode().toUpperCase(), locale -> locale));
            LOCALE_CONFIGURATION_BY_REGION = locales.stream()
                    .collect(Collectors.groupingBy(LocaleModel::getRegion));
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
                .region(Region.get(localeConfiguration.getRegion().toUpperCase()))
                .defaultForRegion(localeConfiguration.isDefaultValueForRegion())
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
                "Currency could not be nullable in the configuration file. The locale code =" + configuration.getCode());
        Objects.requireNonNull(configuration.getRegion(),
                "Region could not be nullable in the configuration file. The locale code =" + configuration.getCode());
    }

    @Override
    public LocaleModel getByCode(final String localeCode) {
        Objects.requireNonNull(localeCode, "Locale code could not be nullable");
        LocaleModel localeModel = LOCALE_CONFIGURATION_BY_LOCALE_CODE.get(localeCode.toUpperCase());
        if (localeModel == null) {
            throw new ModelNotFoundException("Local is not found by code. Code: " + localeCode);
        }

        return localeModel;
    }

    @Override
    public LocaleModel getByRegionAndLanguage(final Region region, final Language language) {
        Objects.requireNonNull(region, "Region could not be nullable");

        if (language == null) {
            return LOCALE_CONFIGURATION_BY_REGION.getOrDefault(region, Collections.emptyList())
                    .stream()
                    .filter(LocaleModel::isDefaultForRegion)
                    .findAny()
                    .orElseThrow(() -> new ModelNotFoundException("The locale model for region is not found. Region: " + region));
        }

        var localeCode = region.getCode() + "_" + language.getIsoCode();
        return Optional.ofNullable(LOCALE_CONFIGURATION_BY_LOCALE_CODE.get(localeCode.toUpperCase()))
                .orElseThrow(() -> new ModelNotFoundException("The locale model by a locale code  is not found. Locale code: " + localeCode));
    }
}
