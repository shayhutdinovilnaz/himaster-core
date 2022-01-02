package ee.himaster.localizationcore.impl;


import ee.himaster.localizationcore.model.LanguageModel;
import ee.himaster.localizationcore.model.LocalizedStringModel;
import ee.himaster.localizationcore.model.LocalizedStringValueModel;
import ee.himaster.localizationcore.service.impl.DefaultLocalizationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DefaultLocalizationServiceTest {

    @InjectMocks
    private DefaultLocalizationService underTest;

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
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final Set<LocalizedStringValueModel> localizedStringValues = mock(HashSet.class);
        final String ruValue = "Русское значение";

        when(localizedString.getLocalizedValues()).thenReturn(localizedStringValues);
        when(genericInstanceFactory.getInstance(LocalizedStringValueModel.class)).thenReturn(ruLocalizedString);
        when(localizedStringValues.stream()).thenReturn(Stream.empty());

        Assert.assertNotNull(underTest.addLocalizedStringValue(ruLang, ruValue, localizedString));
        verify(genericInstanceFactory).getInstance(LocalizedStringValueModel.class);
        verifyNoMoreInteractions(genericInstanceFactory);
        verify(ruLocalizedString).setValue(ruValue);
        verify(ruLocalizedString).setLanguage(ruLang);
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
        verifyZeroInteractions(genericInstanceFactory);
        verify(localizedString).getLocalizedValues();
        verifyNoMoreInteractions(localizedString);
    }

    @Test
    public void testAddLocalizedStringValueToNullableLocalizedString() {
        final LanguageModel ruLang = mock(LanguageModel.class);
        final LocalizedStringModel localizedString = mock(LocalizedStringModel.class);
        final LocalizedStringValueModel ruLocalizedString = mock(LocalizedStringValueModel.class);
        final Set<LocalizedStringValueModel> localizedStringValues = mock(HashSet.class);
        final String ruValue = "Русское значение";

        when(localizedString.getLocalizedValues()).thenReturn(localizedStringValues);
        when(genericInstanceFactory.getInstance(LocalizedStringModel.class)).thenReturn(localizedString);
        when(genericInstanceFactory.getInstance(LocalizedStringValueModel.class)).thenReturn(ruLocalizedString);
        when(localizedStringValues.stream()).thenReturn(Stream.empty());

        Assert.assertNotNull(underTest.addLocalizedStringValue(ruLang, ruValue, null));
        verify(genericInstanceFactory).getInstance(LocalizedStringValueModel.class);
        verify(genericInstanceFactory).getInstance(LocalizedStringModel.class);
        verifyNoMoreInteractions(genericInstanceFactory);
        verify(ruLocalizedString).setValue(ruValue);
        verify(ruLocalizedString).setLanguage(ruLang);
        verifyNoMoreInteractions(ruLocalizedString);
        verify(localizedStringValues).add(ruLocalizedString);
        verify(localizedStringValues).stream();
        verifyNoMoreInteractions(localizedStringValues);
        verify(localizedString, times(2)).getLocalizedValues();
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