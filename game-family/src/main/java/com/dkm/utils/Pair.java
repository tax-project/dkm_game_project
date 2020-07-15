package com.dkm.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Pair<T, K> {
    private T first;
    private K second;
}
