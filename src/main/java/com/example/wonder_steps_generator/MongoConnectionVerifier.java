package com.example.wonder_steps_generator;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "wonder-steps-generator.mongodb", name = "verify-connection", havingValue = "true")
public class MongoConnectionVerifier implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(MongoConnectionVerifier.class);

	private final MongoTemplate mongoTemplate;

	public MongoConnectionVerifier(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void run(ApplicationArguments args) {
		mongoTemplate.executeCommand(new Document("ping", 1));
		log.info("Connected to MongoDB database '{}'", mongoTemplate.getDb().getName());
	}
}
