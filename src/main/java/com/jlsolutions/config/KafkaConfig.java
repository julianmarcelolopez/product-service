package com.jlsolutions.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.jlsolutions.commons.ProductDTO;
import com.jlsolutions.commons.ProductEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

//	@Value("${spring.kafka.consumer.group-id}")
//	private String groupIdConfig;

	@Bean
	public ProducerFactory<String, ProductEvent> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, ProductEvent> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

//	@Bean
//	public ConsumerFactory<String, ProductEvent> consumerFactory() {
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "your_kafka_broker");
//		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-group");
//		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//		return new DefaultKafkaConsumerFactory<>(configProps, (Deserializer) new StringDeserializer(),
//				new JsonDeserializer<>(ProductEvent.class));
//	}

//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, ProductEvent> kafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, ProductEvent> factory =
//				new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		return factory;
//	}
}
