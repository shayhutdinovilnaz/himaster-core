package ee.himaster.core.service.populator;

import ee.himaster.core.service.model.ItemModel;

public interface Populator<T, S extends ItemModel> {

    /**
     * Populate target object with values of source
     *
     * @param source - source object
     * @param target - target object
     * @return populated target object
     */
    T populate(S source, T target);

    /**
     * Populate target object with values of source
     *
     * @param source - source object
     * @param target - target object
     * @return populated target object
     */
    S reversePopulate(T source, S target);
}
