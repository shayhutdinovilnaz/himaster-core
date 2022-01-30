package ee.himaster.core.service.converter.impl;

import ee.himaster.core.service.converter.Converter;
import ee.himaster.core.service.populator.Populator;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicConverter<T, S> implements Converter<T, S> {
    private final List<Populator<T, S>> populators;
    private final Supplier<T> targetSupplier;
    private final Supplier<S> sourceSupplier;

    @Override
    public T convert(S source) {
        final T target = targetSupplier.get();
        for (Populator<T, S> populator : populators) {
            populator.populate(source, target);
        }

        return target;
    }

    @Override
    public S reverseConvert(T source) {
        final S target = sourceSupplier.get();
        for (Populator<T, S> populator : populators) {
            populator.reversePopulate(source, target);
        }

        return target;
    }
}
