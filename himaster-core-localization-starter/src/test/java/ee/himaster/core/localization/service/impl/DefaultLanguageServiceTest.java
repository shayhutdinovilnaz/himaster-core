package ee.himaster.core.localization.service.impl;

import ee.himaster.core.localization.model.LanguageModel;
import ee.himaster.core.localization.repository.LanguageRepository;
import ee.himaster.core.service.exception.ModelNotFoundException;
import ee.himaster.core.service.service.impl.AbstractModelService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class DefaultLanguageServiceTest extends AbstractModelServiceTest<LanguageModel> {

    @InjectMocks
    private DefaultLanguageService underTest;

    @Mock
    private LanguageRepository languageRepository;

    @Test
    public void getByIsoCode() {
        final String isoCode = "ru";
        final Optional<LanguageModel> language = Optional.of(new LanguageModel());

        when(languageRepository.findByIsoCodeAndActive(isoCode, true)).thenReturn(language);

        Assert.assertNotNull(underTest.getByIsoCode(isoCode));
        verify(languageRepository).findByIsoCodeAndActive(isoCode, true);
        verifyNoMoreInteractions(languageRepository);
    }

    @Test(expected = ModelNotFoundException.class)
    public void getByIsoCodeNotFound() {
        final String isoCode = "ru";
        final Optional<LanguageModel> language = Optional.empty();

        when(languageRepository.findByIsoCodeAndActive(isoCode, true)).thenReturn(language);

        underTest.getByIsoCode(isoCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByIsoCodeNullableArgument() {
        underTest.getByIsoCode(null);
    }

    @Override
    protected Class<LanguageModel> getGenericClassOfService() {
        return LanguageModel.class;
    }

    @Override
    protected AbstractModelService<LanguageModel> getGenericModelService() {
        return underTest;
    }

    @Override
    protected JpaRepository<LanguageModel, Long> getModelRepository() {
        return languageRepository;
    }
}