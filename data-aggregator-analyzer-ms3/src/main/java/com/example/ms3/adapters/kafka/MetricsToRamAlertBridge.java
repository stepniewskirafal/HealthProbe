package com.example.ms3.adapters.in.kafka;

import com.example.ms3.config.AppProps;
import com.example.ms3.domain.RamAlert;
import com.example.ms3.domain.SystemMetrics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MetricsToRamAlertBridge {

    private final KafkaTemplate<String, RamAlert> kafka;
    private final String outTopic;

    public MetricsToRamAlertBridge(KafkaTemplate<String, RamAlert> kafka, AppProps props) {
        this.kafka = kafka;
        this.outTopic = props.getKafkaOutTopic();
    }

    @KafkaListener(
            topics = "${app.kafka-in-topic}",                 // system-metrics
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onMetrics(@Payload SystemMetrics m) {
        RamAlert alert = new RamAlert(
                "RamAlert",
                m.ramUsagePercentage(),
                Instant.now()
        );
        kafka.send(outTopic, key(m), alert);
    }

    private String key(SystemMetrics m) {
        return "host-" + m.hostId();
    }
}
