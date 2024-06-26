# 概览
## 背景
* 基于Flink建设一个可扩展通用的实时数据处理引擎，包含对于setting、source、sink、dag等模块的配置，支持通过各个模块的自定义配置&Flink SQL配置快速构建实时ETL任务的能力。
## 为什么我们要使用Tiangong
* Hephaestus目前已在多个场景广泛应用,并支持数百万亿的大流量场景。
  * 多种数据集成解决方案, 覆盖离线、实时、增量场景
  * 分布式架构, 支持水平扩展，动态扩缩容；
  * 在准确性、稳定性、性能上，成熟度更好
  * 丰富的基础功能，例如配置化任务接入、异构数据源接入、自定义SQL拓扑等；
  * 完善的任务运行状态监控，例如流量、QPS、延迟等
## 愿景
* 希望人人都可以快速、便捷、低代码、配置化方式的接入Flink，快速构建实时数据处理任务；
## 适用场景
* 异构数据源海量数据同步
* 流批一体数据处理能力
* 湖仓一体数据处理能力
* 高性能、高可靠的数据同步
* 分布式架构数据集成引擎
## 支持能力
* 支持开源版本Flink所有功能
* 支持Datastream API+Table API+Flink SQL灵活组合，屏蔽底层Flink接入细节，支持快速接入Flink
* 支持Flink SQL自定义算子拓扑能力，例如自定义算子并行度、uid、算子名、算子描述、上下游选择分区策略等；
* 支持时间宏函数、UDF、灵活任务配置能力等；
