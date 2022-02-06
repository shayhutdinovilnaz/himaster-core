package ee.himaster.core.localization.service.impl;

import ee.himaster.core.localization.model.Currency;
import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.localization.provider.LocaleProvider;
import ee.himaster.core.localization.service.LocaleService;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
@RequiredArgsConstructor
@Slf4j
public class DefaultLocaleService implements LocaleService {
    private final LocaleProvider localeProvider;
    private LocaleModel localeModel;
    private boolean initialized;

    @Override
    public void initialize(String localizationCode) {
        localeModel = localeProvider.getByCode(localizationCode);
        if (localeModel != null) {
            initialized = true;
        } else {
            throw new IllegalArgumentException("The localization by mode is not found. Code: " + localizationCode);
        }
    }

    @Override
    public Language getCurrentLanguage() {
        validateInitializing();
        return localeModel.getLanguage();
    }

    @Override
    public TimeZone getCurrentTimeZone() {
        validateInitializing();
        return localeModel.getTimeZone();
    }

    @Override
    public Currency getCurrentCurrency() {
        validateInitializing();
        return localeModel.getCurrency();
    }

    private void validateInitializing() {
        if (!initialized) {
            log.error("Localization was not installed.");
            throw new IllegalStateException("Localization was not installed.");
        }
    }

}
