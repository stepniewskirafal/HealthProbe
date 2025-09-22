package com.example.ms3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProps {
    private String kafkaInTopic;
    private String kafkaOutTopic;

    public String getKafkaInTopic() { return kafkaInTopic; }
    public void setKafkaInTopic(String kafkaInTopic) { this.kafkaInTopic = kafkaInTopic; }

    public String getKafkaOutTopic() { return kafkaOutTopic; }
    public void setKafkaOutTopic(String kafkaOutTopic) { this.kafkaOutTopic = kafkaOutTopic; }
}
