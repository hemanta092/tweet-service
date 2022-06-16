package com.api.tweet.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.api.tweet.entity.Tweet;


@Configuration
public class TweetProducerConfig {
	/*
	@Value("${kafka.server}")
	private String serverUrl;
	

	@Bean
	public ProducerFactory<String, Tweet> producerTweetFactory() {
	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    return props;
	}

	@Bean
	public KafkaTemplate<String, Tweet> kafkaTweetTemplate() {
	    return new KafkaTemplate<>(producerTweetFactory());
	}
*/
}
