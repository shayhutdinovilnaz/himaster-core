package ee.himaster.core.localization.service.impl;


import ee.himaster.core.localization.model.LanguageModel;
import ee.himaster.core.localization.model.LocalizedStringModel;
import ee.himaster.core.localization.model.LocalizedStringValueModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultLocalizationServiceTest {

    private final DefaultLocalizationService underTest = new DefaultLocalizationService();

    @Test
    public void testGetLocalizedStringValueRu() {
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LanguageModel enLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final LocalizedStringValueModel enLocalizedString = mock(LocalizedStringValueModel.class);
        final String ruValue = "Русское значение";
        final String enValue = "English value";

        when(localizedString.getLocalizedValues()).thenReturn(new HashSet<>(Arrays.asList(enLocalizedString, ruLocalizedString)));
        when(ruLocalizedString.getLanguage()).thenReturn(ruLang);
        when(enLocalizedString.getLanguage()).thenReturn(enLang);
        when(ruLocalizedString.getValue()).thenReturn(ruValue);
        when(enLocalizedString.getValue()).thenReturn(enValue);

        Assert.assertEquals(ruValue, underTest.getLocalizedStringValue(ruLang, localizedString));
        verify(localizedString).getLocalizedValues();
        verifyNoMoreInteractions(localizedString);
        verify(ruLocalizedString).getLanguage();
        verify(ruLocalizedString).getValue();
        verifyNoMoreInteractions(ruLocalizedString);
        Assert.assertEquals(enValue, underTest.getLocalizedStringValue(enLang, localizedString));
    }

    @Test
    public void testGetLocalizedStringValueWithNullableLanguage() {
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LanguageModel enLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final LocalizedStringValueModel enLocalizedString = mock(LocalizedStringValueModel.class);
        final String defaultValue = "Default value";

        when(localizedString.getLocalizedValues()).thenReturn(new HashSet<>(Arrays.asList(enLocalizedString, ruLocalizedString)));
        when(ruLocalizedString.getLanguage()).thenReturn(ruLang);
        when(enLocalizedString.getLanguage()).thenReturn(enLang);
        when(localizedString.getDefaultValue()).thenReturn(defaultValue);

        Assert.assertEquals(defaultValue, underTest.getLocalizedStringValue(null, localizedString));
        verify(localizedString).getLocalizedValues();
        verify(localizedString).getDefaultValue();
        verifyNoMoreInteractions(localizedString);
        verify(ruLocalizedString).getLanguage();
        verifyNoMoreInteractions(ruLocalizedString);
        verify(enLocalizedString).getLanguage();
        verifyNoMoreInteractions(enLocalizedString);
        verifyZeroInteractions(enLocalizedString);
    }

    @Test
    public void testGetLocalizedStringValueWithNullableLocalizedString() {
        final LanguageModel ruLang = mock(LanguageModel.class);

        Assert.assertNull(underTest.getLocalizedStringValue(ruLang, null));
    }

    @Test
    public void testGetLocalizedStringValueForNotLocalizedValue() {
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LanguageModel enLang = mock(LanguageModel.class);
        final LanguageModel deLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final LocalizedStringValueModel enLocalizedString = mock(LocalizedStringValueModel.class);
        final String defaultValue = "Default value";

        when(localizedString.getLocalizedValues()).thenReturn(new HashSet<>(Arrays.asList(enLocalizedString, ruLocalizedString)));
        when(ruLocalizedString.getLanguage()).thenReturn(ruLang);
        when(enLocalizedString.getLanguage()).thenReturn(enLang);
        when(localizedString.getDefaultValue()).thenReturn(defaultValue);

        Assert.assertEquals(defaultValue, underTest.getLocalizedStringValue(deLang, localizedString));
        verify(localizedString).getLocalizedValues();
        verify(localizedString).getDefaultValue();
        verifyNoMoreInteractions(localizedString);
        verify(ruLocalizedString).getLanguage();
        verifyNoMoreInteractions(ruLocalizedString);
        verify(enLocalizedString).getLanguage();
        verifyNoMoreInteractions(ruLocalizedString);
    }

    @Test
    public void testCreateLocalizedStringValue() {
        final String isoCode = "RU";
        final LanguageModel ruLang = new LanguageModel();
        ruLang.setIsoCode(isoCode);
        final LocalizedStringModel localizedString = new LocalizedStringModel();
        final String ruValue = "Русское значение";

        LocalizedStringModel result = underTest.addLocalizedStringValue(ruLang, ruValue, localizedString);
        Optional<LocalizedStringValueModel> expected = result.getLocalizedValues().stream().filter(l -> l.getLanguage().equals(ruLang)).findFirst();

        Assert.assertTrue(expected.isPresent());
        Assert.assertEquals(expected.get().getValue(), ruValue);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateLocalizedStringValue() {
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LanguageModel enLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final LocalizedStringValueModel enLocalizedString = mock(LocalizedStringValueModel.class);
        final Set<LocalizedStringValueModel> localizedStringValues = mock(HashSet.class);
        final String ruValue = "Русское значение";

        when(localizedString.getLocalizedValues()).thenReturn(localizedStringValues);
        when(localizedStringValues.stream()).thenReturn(Stream.of(enLocalizedString, ruLocalizedString));
        when(ruLocalizedString.getLanguage()).thenReturn(ruLang);
        when(enLocalizedString.getLanguage()).thenReturn(enLang);

        Assert.assertNotNull(underTest.addLocalizedStringValue(ruLang, ruValue, localizedString));
        verify(ruLocalizedString).getLanguage();
        verify(ruLocalizedString).setValue(ruValue);
        verifyNoMoreInteractions(ruLocalizedString);
        verify(enLocalizedString).getLanguage();
        verifyNoMoreInteractions(enLocalizedString);
        verify(localizedStringValues).stream();
        verifyNoMoreInteractions(localizedStringValues);
        verify(localizedString).getLocalizedValues();
        verifyNoMoreInteractions(localizedString);
    }

    @Test
    public void testAddLocalizedStringValueLikeDefaultValue() {
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final String defaultValue = "Default value";

        Assert.assertNotNull(underTest.addLocalizedStringValue(null, defaultValue, localizedString));
        verify(localizedString).setDefaultValue(defaultValue);
        verifyNoMoreInteractions(localizedString);
    }
}