package com.example.alertsdispatcherms4;

import lombok.RequiredArgsConstructor;
import org.example.RamAlert;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertConsumer {

    private final List<RamAlert> ramAlerts = Collections.synchronizedList(new ArrayList<>());

    @KafkaListener(topics = "ram-alerts")
    public void analyzeMetrics(RamAlert ramAlert) {
        ramAlerts.add(ramAlert);
    }

    public List<RamAlert> snapshot() {
        synchronized (ramAlerts) {
            return List.copyOf(ramAlerts);
        }
    }
}
