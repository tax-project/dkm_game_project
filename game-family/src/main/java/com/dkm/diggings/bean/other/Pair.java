package com.dkm.diggings.bean.other;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class Pair<T, K> {
    private T first;
    private K second;
}
