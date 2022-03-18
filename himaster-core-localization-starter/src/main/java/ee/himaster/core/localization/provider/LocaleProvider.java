package ee.himaster.core.localization.provider;

import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.localization.model.Region;

public interface LocaleProvider {

    LocaleModel getByCode(String localizationCode);

    LocaleModel getByRegionAndLanguage(Region region, Language language);
}
