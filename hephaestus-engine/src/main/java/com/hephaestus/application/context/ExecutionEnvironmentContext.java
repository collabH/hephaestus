package com.hephaestus.application.context;

import com.hephaestus.domain.env.ExecutionCheckpointConfig;
import com.hephaestus.domain.env.StreamExecutionConfig;
import com.hephaestus.domain.env.TableExecutionConfig;
import org.apache.commons.collections4.MapUtils;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableConfig;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ALIGNED_CHECKPOINT_TIMEOUT;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.CHECKPOINTING_MODE;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.CHECKPOINTING_TIMEOUT;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.ENABLE_UNALIGNED;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.EXTERNALIZED_CHECKPOINT;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.MAX_CONCURRENT_CHECKPOINTS;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.MIN_PAUSE_BETWEEN_CHECKPOINTS;
import static org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions.TOLERABLE_FAILURE_NUMBER;

/**
 * @fileName: ExecutionEnvironmentContext.java
 * @description: flink run environment context
 * @author: huangshimin
 * @date: 2024/6/27 19:44
 */
public class ExecutionEnvironmentContext {
    /**
     * obtain stream execution environment
     *
     * @param config stream execution config
     * @return {@link StreamExecutionEnvironment}
     */
    public StreamExecutionEnvironment obtainStreamExecutionEnvironment(StreamExecutionConfig config) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        buildStreamEnvironment(env, config);
        return env;
    }

    /**
     * obtain table execution environment
     *
     * @param jobName                   job name
     * @param tableExecutionConfig      table/Flink SQL Execution config
     * @param executionCheckpointConfig checkpoint config
     * @param environment               dataStream execution environment
     * @return {@link TableEnvironment}
     */
    public StreamTableEnvironment obtainTableEnvironment(String jobName, TableExecutionConfig tableExecutionConfig,
                                                         ExecutionCheckpointConfig executionCheckpointConfig,
                                                         StreamExecutionEnvironment environment) {
        EnvironmentSettings environmentSettings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(environment, environmentSettings);
        TableConfig tableConfig = tableEnvironment.getConfig();
        tableConfig.setLocalTimeZone(ZoneId.of(tableExecutionConfig.getLocalTimeZone()));
        tableConfig.setIdleStateRetention(Duration.ofMillis(tableExecutionConfig.getStateTtlMs()));
        Configuration configuration = buildCheckpointConfig(executionCheckpointConfig);
        configuration.setString("pipeline.name", jobName);
        if (tableExecutionConfig.getEnableDefaultOptimize()) {
            configuration.setString("table.exec.mini-batch.enabled", "true");
            configuration.setString("table.exec.mini-batch.allow-latency", "5 s");
            configuration.setString("table.exec.mini-batch.size", "5000");
            configuration.setString("table.optimizer.agg-phase-strategy", "TWO_PHASE");
            configuration.setString("table.optimizer.distinct-agg.split.enabled", "true");
        }
        // 可以覆盖掉上述配置
        Map<String, String> sqlOptions = tableExecutionConfig.getOptions();
        if (MapUtils.isNotEmpty(sqlOptions)) {
            for (Map.Entry<String, String> entry : sqlOptions.entrySet()) {
                configuration.setString(entry.getKey(), entry.getValue());
            }
        }
        tableConfig.addConfiguration(configuration);
        return tableEnvironment;
    }

    /**
     * build checkpoint config
     *
     * @param executionCheckpointConfig checkpoint config
     * @return {@link Configuration}
     */
    private Configuration buildCheckpointConfig(ExecutionCheckpointConfig executionCheckpointConfig) {
        Configuration configuration = new Configuration();
        configuration.set(CHECKPOINTING_MODE,
                CheckpointingMode.valueOf(executionCheckpointConfig.getCheckpointMode()));
        configuration.set(CHECKPOINTING_INTERVAL,
                Duration.ofMillis(executionCheckpointConfig.getCheckpointInterval()));
        configuration.set(CHECKPOINTING_TIMEOUT,
                Duration.ofMillis(executionCheckpointConfig.getCheckpointTimeout()));
        configuration.set(ENABLE_CHECKPOINTS_AFTER_TASKS_FINISH,
                executionCheckpointConfig.getEnableCheckpointsAfterTaskFinish());
        Boolean enableUnaligned = executionCheckpointConfig.getEnableUnaligned();
        Integer maxConcurrentCheckpoints = executionCheckpointConfig.getMaxConcurrentCheckpoints();
        if (enableUnaligned &&
                maxConcurrentCheckpoints == 1) {
            configuration.set(MAX_CONCURRENT_CHECKPOINTS,
                    maxConcurrentCheckpoints);
        }
        configuration.set(MIN_PAUSE_BETWEEN_CHECKPOINTS,
                Duration.ofMillis(executionCheckpointConfig.getMinPauseBetweenCheckpoints()));
        configuration.set(EXTERNALIZED_CHECKPOINT, CheckpointConfig.
                ExternalizedCheckpointCleanup.valueOf(executionCheckpointConfig.getCheckpointCleanupMode()));
        configuration.set(ALIGNED_CHECKPOINT_TIMEOUT,
                Duration.ofMillis(executionCheckpointConfig.getAlignedCheckpointTimeout()));
        configuration.set(ENABLE_UNALIGNED, enableUnaligned);
        Integer tolerableFailedCheckpoints = executionCheckpointConfig.getTolerableFailedCheckpoints();
        if (Objects.nonNull(tolerableFailedCheckpoints)) {
            configuration.set(TOLERABLE_FAILURE_NUMBER, tolerableFailedCheckpoints);
        }
        return configuration;
    }

    /**
     * build stream environment
     *
     * @param env    stream execution environment
     * @param config stream execution config
     */
    private void buildStreamEnvironment(StreamExecutionEnvironment env, StreamExecutionConfig config) {
        Integer parallelism = config.getParallelism();
        if (Objects.nonNull(parallelism) && parallelism > 1) {
            env.setParallelism(parallelism);
        }
        env.setMaxParallelism(config.getMaxParallelism());
        env.setBufferTimeout(config.getBufferTimeout());
        env.setRuntimeMode(RuntimeExecutionMode.valueOf(config.getRuntimeMode()));
        // config checkpoints
        Configuration configuration = buildCheckpointConfig(config.getExecutionCheckpointConfig());
        env.getCheckpointConfig().configure(configuration);
        Map<String, String> options = config.getOptions();
        if (MapUtils.isNotEmpty(options)) {
            buildCustomConfig(configuration, options);
            env.configure(configuration);
        }
        if (config.getDisableOperatorChaining()) {
            env.disableOperatorChaining();
        }
    }

    /**
     * build custom config
     *
     * @param options custom config
     */
    private void buildCustomConfig(Configuration configuration, Map<String, String> options) {
        for (Map.Entry<String, String> entry : options.entrySet()) {
            configuration.setString(entry.getKey(), entry.getValue());
        }
    }
}
