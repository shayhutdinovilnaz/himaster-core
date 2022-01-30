package ee.himaster.core.localization.service;


import ee.himaster.core.localization.model.LanguageModel;
import ee.himaster.core.service.service.ModelService;

public interface LanguageService extends ModelService<LanguageModel> {
    /**
     * Retrieve the language model by ISO-code.
     *
     * @param isoCode - ISO-code of language
     * @return language model
     */
    LanguageModel getByIsoCode(String isoCode);
}
