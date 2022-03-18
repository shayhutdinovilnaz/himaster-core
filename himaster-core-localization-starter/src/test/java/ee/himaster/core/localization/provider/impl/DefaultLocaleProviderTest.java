package ee.himaster.core.localization.provider.impl;

import ee.himaster.core.localization.model.Language;
import ee.himaster.core.localization.model.LocaleModel;
import ee.himaster.core.localization.model.Region;
import ee.himaster.core.service.exception.ModelNotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class DefaultLocaleProviderTest {

    private final DefaultLocaleProvider localeProvider = new DefaultLocaleProvider();

    @Test
    public void getByCode_localExist_success() {
        String localeCode = "EST_EE";
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

    @Test
    public void getByRegionAndLanguage_RegionAndLanguageExist_success() {
        LocaleModel localeModel = localeProvider.getByRegionAndLanguage(Region.ESTONIA_GENERAL, Language.RUSSIAN);
        Assert.assertNotNull(localeModel);
        Assert.assertNotNull(localeModel.getCode());
        Assert.assertNotNull(localeModel.getCurrency());
        Assert.assertNotNull(localeModel.getLanguage());
        Assert.assertNotNull(localeModel.getTimeZone());
        Assert.assertNotNull(localeModel.getRegion());
        Assert.assertFalse(localeModel.isDefaultForRegion());
    }

    @Test(expected = ModelNotFoundException.class)
    public void getByRegionAndLanguage_RegionAndLanguageNotExist_modelNotFoundException() {
        localeProvider.getByRegionAndLanguage(Region.ESTONIA_GENERAL, Language.DEUTSCHE);
    }

    @Test
    public void getByRegionAndLanguage_RegionOnly_returnDefaultLocaleForRegion() {
        LocaleModel localeModel = localeProvider.getByRegionAndLanguage(Region.ESTONIA_GENERAL, null);
        Assert.assertNotNull(localeModel);
        Assert.assertNotNull(localeModel.getCode());
        Assert.assertNotNull(localeModel.getCurrency());
        Assert.assertNotNull(localeModel.getLanguage());
        Assert.assertNotNull(localeModel.getTimeZone());
        Assert.assertNotNull(localeModel.getRegion());
        Assert.assertTrue(localeModel.isDefaultForRegion());
    }


}