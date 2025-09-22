package com.example.alertsdispatcherms4;

import lombok.RequiredArgsConstructor;
import org.example.RamAlert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertConsumer alertConsumer;

    @GetMapping("/get-alerts")
    public List<RamAlert> getAlerts(@RequestParam(required = false) String level) {
        List<RamAlert> all = alertConsumer.snapshot();
        if (level == null || level.isBlank()) return all;

        return all.stream()
                .filter(a -> a.message() != null && a.message().equalsIgnoreCase(level))
                .toList();
    }
}
