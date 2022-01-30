package ee.himaster.core.localization;

import ee.himaster.core.localization.repository.LanguageRepository;
import ee.himaster.core.localization.service.impl.DefaultLanguageService;
import ee.himaster.core.localization.service.impl.DefaultLocalizationService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan
public class LocalizationAutoconfiguration {

    @Bean
    public DefaultLanguageService languageService(LanguageRepository languageRepository) {
        return new DefaultLanguageService(languageRepository);
    }

    @Bean
    public DefaultLocalizationService localizationService() {
        return new DefaultLocalizationService();
    }
}
