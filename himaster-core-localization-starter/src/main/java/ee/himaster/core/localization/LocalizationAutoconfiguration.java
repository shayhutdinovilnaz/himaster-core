package ee.himaster.core.localization;

import ee.himaster.core.localization.filter.LocalizationRequestFilter;
import ee.himaster.core.localization.provider.LocaleProvider;
import ee.himaster.core.localization.provider.impl.DefaultLocaleProvider;
import ee.himaster.core.localization.service.LocaleService;
import ee.himaster.core.localization.service.impl.DefaultLocaleService;
import ee.himaster.core.localization.service.impl.DefaultLocalizedStringService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@Slf4j
public class LocalizationAutoconfiguration {
    private static final Log LOG = LogFactory.getLog(LocalizationAutoconfiguration.class);

    @Bean
    public LocaleProvider localeProvider() {
        return new DefaultLocaleProvider();
    }

    @Bean
    @RequestScope
    public LocaleService localeService(LocaleProvider localeProvider) {
        return new DefaultLocaleService(localeProvider);
    }

    @Bean
    public DefaultLocalizedStringService localizedStringService(LocaleService localeService) {
        return new DefaultLocalizedStringService(localeService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "himaster.localization.request.filter", name = "enabled", havingValue = "true")
    public LocalizationRequestFilter localizationRequestFilter(LocaleService localeService) {
        LOG.info("The LocalizationRequestFilter is successfully initialized.");
        return new LocalizationRequestFilter(localeService);
    }
}
