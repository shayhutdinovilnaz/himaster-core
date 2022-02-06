package ee.himaster.core.localization.service.impl;

import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocalizedStringModel;
import ee.himaster.core.localization.model.LocalizedStringValueModel;
import ee.himaster.core.localization.service.LocaleService;
import ee.himaster.core.localization.service.LocalizedStringService;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLocalizedStringService implements LocalizedStringService {
    private final LocaleService localeService;

    @Override
    public String getLocalizedStringValue(final Language language, final LocalizedStringModel localizedString) {
        return Optional.ofNullable(localizedString)
                .map(LocalizedStringModel::getLocalizedValues).stream().flatMap(Collection::stream)
                .filter(value -> value.getLanguage().equals(language))
                .findAny()
                .map(LocalizedStringValueModel::getValue)
                .orElseGet(() -> getFallbackValue(localizedString));
    }

    private String getFallbackValue(final LocalizedStringModel localizedString) {
        return Optional.ofNullable(localizedString).map(LocalizedStringModel::getDefaultValue).orElse(null);
    }

    @Override
    public String getLocalizedStringValue(LocalizedStringModel localizedString) {
        return getLocalizedStringValue(localeService.getCurrentLanguage(), localizedString);
    }

    @Override
    public LocalizedStringModel addLocalizedStringValue(final Language language, final String value, final LocalizedStringModel localizedString) {
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

    @Override
    public LocalizedStringModel addLocalizedStringValue(String value, LocalizedStringModel localizedString) {
        return addLocalizedStringValue(localeService.getCurrentLanguage(), value, localizedString);
    }

    private LocalizedStringValueModel addLocalizedStringValueInstance(final Language language, final LocalizedStringModel localizedString) {
        final LocalizedStringValueModel localizedStringValue = new LocalizedStringValueModel();
        localizedStringValue.setLanguage(language);
        localizedString.getLocalizedValues().add(localizedStringValue);
        return localizedStringValue;
    }
}
