package com.example.wonder_steps_generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.mongodb.uri=mongodb://localhost:27017/wondersteps",
		"wonder-steps-generator.mongodb.verify-connection=false"
})
class WonderStepsGeneratorApplicationTests {

	@Test
	void contextLoads() {
	}

}
