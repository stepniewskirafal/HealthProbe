package com.example.metrics.infrastructure.kafka;

import org.example.SystemMetrics;

public interface MetricsPublisher {
  void publish(SystemMetrics metrics);
}
