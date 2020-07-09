package com.dkm.utils;

/**
 * 关于对象的工具方法
 *
 * @author fkl
 */
public interface ObjectUtils {
    /**
     * 判断类是否为 NULL 或者为空字段
     *
     * @param any 任何类型
     * @return 是否为空
     */
    static boolean isNullOrEmpty(Object any) {
        return any == null || (any instanceof String && ((String) any).isEmpty());
    }
}
