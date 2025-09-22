package com.example.ms3.domain;

import java.time.Instant;

// proste „powiadomienie RAM” – tylko to, co potrzebne
public record RamAlert (
        String message,       // Treść alertu
        double ramUsagePercentage,   // Aktualne zużycie RAM (%)
        Instant time)
{}