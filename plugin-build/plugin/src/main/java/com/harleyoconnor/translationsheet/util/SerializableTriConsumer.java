package com.harleyoconnor.translationsheet.util;

import java.io.Serializable;

/**
 * @author Harley O'Connor
 */
public interface SerializableTriConsumer<T, U, V> extends Serializable {

    void accept(T t, U u, V v);

}
