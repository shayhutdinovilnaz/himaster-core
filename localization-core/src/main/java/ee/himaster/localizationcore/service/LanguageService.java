package ee.himaster.localizationcore.service;


import ee.himaster.localizationcore.model.LanguageModel;
import ee.himaster.servicecore.service.ModelService;

public interface LanguageService extends ModelService<LanguageModel> {
    /**
     * Retrieve the language model by ISO-code.
     *
     * @param isoCode - ISO-code of language
     * @return language model
     */
    LanguageModel getByIsoCode(String isoCode);
}
