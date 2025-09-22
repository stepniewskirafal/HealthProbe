package com.example.ms3.domain;


import java.time.Instant;


/**
 * System-level single observation (CPU and RAM).
 * If you already have this class in another module, reuse it instead of duplicating.
 */
public record SystemMetrics(
        String hostId,
        Instant timestamp,
        double cpuPower,            // Moc procesora (GHz)
        double cpuUsagePercentage,   // Aktualne zużycie CPU (%)
        double totalRam,             // Całkowita ilość RAM (GB)
        double ramUsagePercentage   // Aktualne zużycie RAM (%)
) {
}