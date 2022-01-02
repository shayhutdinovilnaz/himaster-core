package ee.himaster.localizationcore.service.impl;

import ee.himaster.localizationcore.model.LanguageModel;
import ee.himaster.localizationcore.repository.LanguageRepository;
import ee.himaster.localizationcore.service.LanguageService;
import ee.himaster.servicecore.exception.ModelNotFoundException;
import ee.himaster.servicecore.service.impl.AbstractModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class DefaultLanguageService extends AbstractModelService<LanguageModel> implements LanguageService {
    private final LanguageRepository languageRepository;

    @Override
    public LanguageModel getByIsoCode(final String isoCode) {
        Assert.notNull(isoCode, "Iso code of language can't be nullable.");
        return languageRepository.findByIsoCodeAndActive(isoCode, true).orElseThrow(() -> new ModelNotFoundException("Language isn't found. Required isoCode = " + isoCode));
    }

    @Override
    protected JpaRepository<LanguageModel, Long> getItemRepository() {
        return languageRepository;
    }
}
