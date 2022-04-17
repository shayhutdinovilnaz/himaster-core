package ee.himaster.core.service.converter;

import ee.himaster.core.service.model.ItemModel;

/**
 * Converting instance of object from source class to target class.
 * Some of field of target class could be none converted.
 * Converter can invoke another converter for converting field.
 * Also necessary implement reverse converting.
 */
public interface Converter<T, S extends ItemModel> {
    /**
     * Converting instance of object from source class to target class.
     *
     * @param source -  instance of source class.
     *
     * @return converted instance of target class.
     */
    T convert(S source);

    /**
     * Converting instance of object from target class to source class.
     *
     * @param source -  instance of target class.
     *
     * @return converted instance of source class.
     */
    S reverseConvert(T source);
}