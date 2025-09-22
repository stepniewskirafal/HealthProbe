package com.example.metrics.app;

import com.example.metrics.domain.MetricsProvider;
import com.example.metrics.infrastructure.kafka.MetricsPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.SystemMetrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MetricsScheduler {

  private final MetricsProvider provider;
  private final MetricsPublisher publisher;

  // co sekundę
  @Scheduled(fixedRateString = "${app.collect.period-ms:1000}")
  public void tick() {
    try {
      SystemMetrics metrics = provider.getMetrics();
      publisher.publish(metrics);
      if (log.isDebugEnabled()) {
        log.debug("Published metrics for {}", metrics.hostId());
      }
    } catch (Exception ex) {
      log.warn("Failed to collect/publish metrics: {}", ex.getMessage(), ex);
    }
  }
}
