package com.hephaestus.domain.env;

import com.google.common.collect.Maps;
import lombok.Data;

import java.time.Duration;
import java.util.Map;

/**
 * @fileName: TableExecutionConfig.java
 * @description: table execution config
 * @author: huangshimin
 * @date: 2024/6/27 20:10
 */
@Data
public class TableExecutionConfig {
    /**
     * flink sql config options
     */
    private Map<String, String> options = Maps.newHashMap();
    /**
     * Whether to enable default optimizations, including mini-batch and pre-aggregation optimizations, which apply
     * to Flink SQL.
     */
    private Boolean enableDefaultOptimize = true;
    /**
     * Timezone setting, with a default of "Asia/Shanghai", applies to Flink SQL.
     */
    private String localTimeZone = "Asia/Shanghai";
    /**
     * State idle storage time, with a default of 1 day, applies to Flink SQL.
     */
    private Long stateTtlMs = Duration.ofDays(1).toMillis();
}
