package com.beanmeat.security.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author tchstart
 * @data 2025-04-23
 */
@Data
@Builder
public class R {

    private int code; // 结果码

    private String msg; // 结果信息

    private Object info; // 结果数据

    private static R OK() {
        return R.builder().code(200).msg("成功").info(null).build();
    }
}
