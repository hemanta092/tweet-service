package com.api.tweet.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.api.tweet.entity.Tweet;

@Configuration
public class TweetConsumerConfig {
	@Value("${kafka.server}")
	private String serverUrl;
	
	@Bean
	public ConsumerFactory<String, Tweet> consumerTweetFactory(){
		Map<String,Object> config=buildMap();
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "tweetGroup");
		config.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumeTweet");
		config.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 60*30*1000);
		return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),new JsonDeserializer<>(Tweet.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Tweet> kafkaTweetListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, Tweet> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerTweetFactory());
		return factory;
	}
	
	
	
	private Map<String,Object> buildMap(){
		Map<String,Object> config=new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class );
		config.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 60*30*1000);
		return config;
	}

}
