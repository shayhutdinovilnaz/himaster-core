package ee.himaster.core.localization.provider.impl;

import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.service.exception.ModelNotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class DefaultLocaleProviderTest {

    private final DefaultLocaleProvider localeProvider = new DefaultLocaleProvider();

    @Test
    public void getByCode_localExist_success() {
        String localeCode = "EE_EST";
        LocaleModel localeModel = localeProvider.getByCode(localeCode);
        Assert.assertEquals(localeModel.getCode(), localeCode);
        Assert.assertNotNull(localeModel.getCurrency());
        Assert.assertNotNull(localeModel.getLanguage());
        Assert.assertNotNull(localeModel.getTimeZone());
    }

    @Test(expected = ModelNotFoundException.class)
    public void getByCode_localNotExist_modelNotFoundException() {
        localeProvider.getByCode("NOT_EXIST");
    }

    @Test(expected = NullPointerException.class)
    public void getByCode_isoCodeNull_exception() {
        localeProvider.getByCode(null);
    }

}