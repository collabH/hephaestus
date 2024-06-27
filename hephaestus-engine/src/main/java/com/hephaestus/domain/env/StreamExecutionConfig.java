package com.hephaestus.domain.env;

import lombok.Data;
import org.apache.flink.api.common.RuntimeExecutionMode;

import java.util.Map;

/**
 * @fileName: StreamExecutionConfig.java
 * @description: stream execution config
 * @author: huangshimin
 * @date: 2024/6/27 19:50
 */
@Data
public class StreamExecutionConfig {
    /**
     * job name
     */
    private String jobName;
    /**
     * checkpoint config
     */
    private ExecutionCheckpointConfig executionCheckpointConfig = new ExecutionCheckpointConfig();
    /**
     * The default value is -1, which means that the operator throughput will be increased only when the buffer is
     * full before outputting, but this will cause data delay.
     */
    private Long bufferTimeout = -1L;
    /**
     * Overall parallelism of the project
     */
    private Integer parallelism;
    /**
     * The default maximum parallelism is 2000
     */
    private Integer maxParallelism = 2000;
    /**
     * The default execution environment is streaming.
     */
    private String runtimeMode = RuntimeExecutionMode.STREAMING.name();

    /**
     * Whether to disable the chaining optimization of operators, default is false
     */
    private Boolean disableOperatorChaining = false;

    /**
     * Extended Flink parameters
     */
    private Map<String, String> options;

}
