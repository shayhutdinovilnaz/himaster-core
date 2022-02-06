package ee.himaster.core.localization.provider;

import ee.himaster.core.localization.model.LocaleModel;

public interface LocaleProvider {

    LocaleModel getByCode(String localizationCode);

}
