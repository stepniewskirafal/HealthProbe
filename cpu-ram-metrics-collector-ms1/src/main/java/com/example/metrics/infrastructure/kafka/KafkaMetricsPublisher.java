package com.example.metrics.infrastructure.kafka;

import org.example.SystemMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMetricsPublisher implements MetricsPublisher {

  private final KafkaTemplate<String, SystemMetrics> kafka;
  private final String topic;

  public KafkaMetricsPublisher(
      KafkaTemplate<String, SystemMetrics> kafka,
      @Value("${app.kafka.topic:system-metrics}") String topic) {
    this.kafka = kafka;
    this.topic = topic;
  }

  @Override
  public void publish(SystemMetrics m) {
    kafka.send(topic, key(m), m);
  }

  private String key(SystemMetrics m) {
    return "host-" + m.hostId();
  }
}
