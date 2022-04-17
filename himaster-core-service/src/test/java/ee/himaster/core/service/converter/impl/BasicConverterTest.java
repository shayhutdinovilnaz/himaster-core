package ee.himaster.core.service.converter.impl;


import ee.himaster.core.service.model.ItemModel;
import ee.himaster.core.service.populator.Populator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BasicConverterTest {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void convert_nullableSource_nullPointerException() {
        final var underTest = new BasicConverter<>(new ArrayList<>(), TestDto::new, TestModel::new);
        underTest.convert(null);
    }

    @Test
    public void convert_existSource_success() {
        Populator<TestDto, TestModel> populator = mock(Populator.class);
        final var underTest = new BasicConverter<>(Collections.singletonList(populator), TestDto::new, TestModel::new);
        final var source = new TestModel();
        final var target = underTest.convert(source);

        Assert.assertNotNull(target);
        verify(populator).populate(source, target);
    }

    @Test
    public void convert_emptyPopulatorList_success() {
        final var underTest = new BasicConverter<>(Collections.emptyList(), TestDto::new, TestModel::new);
        final var source = new TestModel();
        final var target = underTest.convert(source);

        Assert.assertNotNull(target);
    }

    @Test(expected = NullPointerException.class)
    public void reverseConvert_nullableSource_nullPointerException() {
        final var underTest = new BasicConverter<>(new ArrayList<>(), TestDto::new, TestModel::new);
        underTest.convert(null);
    }

    @Test
    public void reverseConvert_existSource_success() {
        Populator<TestDto, TestModel> populator = mock(Populator.class);
        final var underTest = new BasicConverter<>(Collections.singletonList(populator), TestDto::new, TestModel::new);
        final var source = new TestDto();
        final var target = underTest.reverseConvert(source);

        Assert.assertNotNull(target);
        verify(populator).reversePopulate(source, target);
    }

    @Test
    public void reverseConvert_emptyPopulatorList_success() {
        final var underTest = new BasicConverter<>(Collections.emptyList(), TestDto::new, TestModel::new);
        final var source = new TestDto();
        final var target = underTest.reverseConvert(source);

        Assert.assertNotNull(target);
    }

    static class TestModel extends ItemModel {

    }

    static class TestDto {

    }


}