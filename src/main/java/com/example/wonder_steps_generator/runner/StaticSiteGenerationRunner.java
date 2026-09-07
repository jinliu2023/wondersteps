package com.example.wonder_steps_generator.runner;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.example.wonder_steps_generator.config.WonderStepsProperties;
import com.example.wonder_steps_generator.domain.GeneratedSite;
import com.example.wonder_steps_generator.service.StaticSiteGenerationService;
import com.example.wonder_steps_generator.service.StaticSiteWriter;

@Component
@ConditionalOnProperty(prefix = "wonder-steps-generator.static-site", name = "enabled", havingValue = "true")
public class StaticSiteGenerationRunner implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(StaticSiteGenerationRunner.class);

	private final WonderStepsProperties properties;
	private final StaticSiteGenerationService generationService;
	private final StaticSiteWriter writer;

	public StaticSiteGenerationRunner(WonderStepsProperties properties, StaticSiteGenerationService generationService,
			StaticSiteWriter writer) {
		this.properties = properties;
		this.generationService = generationService;
		this.writer = writer;
	}

	@Override
	public void run(ApplicationArguments args) throws IOException {
		GeneratedSite site = generationService.generate();
		writer.write(site);
		log.info("Generated {} MyWonderSteps file(s) into {}", site.pages().size(),
				properties.getStaticSite().getOutputDir().toAbsolutePath());
		if (!site.improvements().isEmpty()) {
			log.info("Wrote {} database improvement suggestion(s)", site.improvements().size());
		}
	}
}
