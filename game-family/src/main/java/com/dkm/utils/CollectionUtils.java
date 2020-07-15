package com.dkm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OpenE
 */
public interface CollectionUtils {

    static <T, K> Map<T, K> mapOf(Pair<T, K>... arr) {
        final val map = new HashMap<T, K>(arr.length);
        for (Pair<T, K> tkPair : arr) {
            map.put(tkPair.getFirst(), tkPair.getSecond());
        }
        return map;
    }

    @AllArgsConstructor
    @Data
    public final class Pair<T, K> {
        private T first;
        private K second;
    }

}
