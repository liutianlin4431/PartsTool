package org.parts.mp.wrapper.auxiliary;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface FFunction<T, F> extends Function<T, F>, Serializable {

}