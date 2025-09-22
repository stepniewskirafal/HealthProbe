package com.example.ms3.domain;


import java.time.Instant;


/** Windowed aggregation per host. */
public record AggregatedMetrics(
        String hostId,
        Instant windowStart,
        Instant windowEnd,
        long count,
        double cpuAvg,
        double cpuMax,
        double ramUsedPctAvg,
        double ramUsedPctMax,
        long ramUsedAvgBytes
) {}