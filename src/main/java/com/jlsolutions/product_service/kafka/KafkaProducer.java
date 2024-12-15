package com.jlsolutions.product_service.kafka;

import com.jlsolutions.commons.ProductDTO;
import com.jlsolutions.commons.ProductEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendProductEvent(ProductEvent productEvent) {
		kafkaTemplate.send("product-events", productEvent);
	}
}