package com.example.metrics.domain;

import org.example.SystemMetrics;

public interface MetricsProvider {
  SystemMetrics getMetrics();
}
