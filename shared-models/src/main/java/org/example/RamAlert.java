package org.example;

import java.time.Instant;

public record RamAlert (

    String message,       // Treść alertu
    double ramUsagePercentage,   // Aktualne zużycie RAM (%)
    Instant time)
{}