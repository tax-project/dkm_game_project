package com.dkm.utils;

import com.dkm.diggings.bean.other.Pair;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OpenE
 */
public interface CollectionUtils {

    static <T, K> Map<T, K> mapof(Pair<T, K>... arr) {
        final val map = new HashMap<T, K>(arr.length);
        for (Pair<T, K> tkPair : arr) {
            map.put(tkPair.getFirst(), tkPair.getSecond());
        }
        return map;
    }
}
