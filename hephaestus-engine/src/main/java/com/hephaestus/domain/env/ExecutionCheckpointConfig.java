package com.hephaestus.domain.env;

import lombok.Data;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;

/**
 * @fileName: ExecutionCheckpointConfig.java
 * @description: execution checkpoint config
 * @author: huangshimin
 * @date: 2024/6/27 20:08
 */
@Data
public class ExecutionCheckpointConfig {
    /**
     * checkpoint mode，support EXACTLY_ONCE、AT_LEAST_ONCE;
     */
    private String checkpointMode = CheckpointingMode.EXACTLY_ONCE.name();
    /**
     * checkpoint clean strategy，default RETAIN_ON_CANCELLATION，support DELETE_ON_CANCELLATION、RETAIN_ON_CANCELLATION
     * 、NO_EXTERNALIZED_CHECKPOINTS
     */
    private String checkpointCleanupMode = CheckpointConfig.ExternalizedCheckpointCleanup
            .RETAIN_ON_CANCELLATION.name();
    /**
     * The default checkpoint interval is 5 minutes.
     */
    private Long checkpointInterval = 300000L;
    /**
     * The default checkpoint timeout is 5 minutes.
     */
    private Long checkpointTimeout = 300000L;
    /**
     * By default, a maximum of 2 checkpoints are allowed to run simultaneously. However, when unaligned checkpoints
     * are enabled, the value of maxConcurrentCheckpoints cannot be greater than 1.
     */
    private Integer maxConcurrentCheckpoints = 1;
    /**
     * The default time interval between two checkpoints is 5 seconds.
     */
    private Long minPauseBetweenCheckpoints = 5000L;

    /**
     * The default timeout for aligned checkpoints is 0.
     * This is only relevant when unaligned checkpoints are enabled.
     * If the timeout is set to 0, checkpoints will always be unaligned.
     * If the timeout value is positive, checkpoints will initially attempt to align. However, if during a
     * checkpoint, the delay in initiating the checkpoint exceeds this timeout,
     * alignment will time out, and the checkpoint barriers will start working as for unaligned checkpoints.
     */
    private Long alignedCheckpointTimeout = 0L;

    /**
     * Default is false.
     * Determines whether checkpoints can still proceed normally when a task is finished.
     */
    private Boolean enableCheckpointsAfterTaskFinish = false;

    /**
     * Whether to enable unaligned checkpoints. Default is false.
     * false: Use aligned checkpoint barrier.
     * true:  Use unaligned checkpoint, which corresponds to at least once semantics.
     */
    private Boolean enableUnaligned = false;

    /**
     * Allowed number of checkpoint failures. If exceeded, the task will report an error.
     */
    private Integer tolerableFailedCheckpoints;
}
