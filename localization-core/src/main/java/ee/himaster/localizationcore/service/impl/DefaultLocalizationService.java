package ee.himaster.localizationcore.service.impl;

import ee.himaster.localizationcore.model.LanguageModel;
import ee.himaster.localizationcore.model.LocalizedStringModel;
import ee.himaster.localizationcore.model.LocalizedStringValueModel;
import ee.himaster.localizationcore.service.LocalizationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DefaultLocalizationService implements LocalizationService {

    public String getLocalizedStringValue(final LanguageModel language, final LocalizedStringModel localizedString) {
        return Optional.ofNullable(localizedString)
                .map(LocalizedStringModel::getLocalizedValues)
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .filter(value -> value.getLanguage().equals(language))
                .findAny()
                .map(LocalizedStringValueModel::getValue)
                .orElseGet(() -> getFallbackValue(localizedString));
    }

    private String getFallbackValue(final LocalizedStringModel localizedString) {
        return Optional.ofNullable(localizedString).map(LocalizedStringModel::getDefaultValue).orElse(null);
    }

    public LocalizedStringModel addLocalizedStringValue(final LanguageModel language, final String value, final LocalizedStringModel localizedString) {
        final LocalizedStringModel updatedLocalizedString = Optional.ofNullable(localizedString).orElseGet(LocalizedStringModel::new);
        if (language != null) {
            final LocalizedStringValueModel updatedLocalizedStringValue = updatedLocalizedString
                    .getLocalizedValues()
                    .stream()
                    .filter(localizedValue -> localizedValue.getLanguage().equals(language))
                    .findAny().orElseGet(() -> addLocalizedStringValueInstance(language, updatedLocalizedString));
            updatedLocalizedStringValue.setValue(value);
        } else {
            updatedLocalizedString.setDefaultValue(value);
        }

        return updatedLocalizedString;
    }

    private LocalizedStringValueModel addLocalizedStringValueInstance(final LanguageModel language, final LocalizedStringModel localizedString) {
        final LocalizedStringValueModel localizedStringValue = new LocalizedStringValueModel();
        localizedStringValue.setLanguage(language);
        localizedString.getLocalizedValues().add(localizedStringValue);
        return localizedStringValue;
    }
}
