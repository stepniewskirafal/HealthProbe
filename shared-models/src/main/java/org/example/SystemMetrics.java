package org.example;

import java.time.Instant;


public record SystemMetrics(
        String hostId,
        Instant timestamp,
        double cpuPower,            // Moc procesora (GHz)
        double cpuUsagePercentage,   // Aktualne zużycie CPU (%)
        double totalRam,             // Całkowita ilość RAM (GB)
        double ramUsagePercentage   // Aktualne zużycie RAM (%)
) {}
