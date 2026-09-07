package com.example.wonder_steps_generator.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.example.wonder_steps_generator.config.WonderStepsProperties;
import com.example.wonder_steps_generator.domain.DatabaseImprovement;
import com.example.wonder_steps_generator.domain.GeneratedPage;
import com.example.wonder_steps_generator.domain.GeneratedSite;

@Component
public class StaticSiteWriter {

	private final WonderStepsProperties properties;

	public StaticSiteWriter(WonderStepsProperties properties) {
		this.properties = properties;
	}

	public void write(GeneratedSite site) throws IOException {
		Files.createDirectories(properties.getStaticSite().getOutputDir());
		for (GeneratedPage page : site.pages()) {
			Files.writeString(properties.getStaticSite().getOutputDir().resolve(page.fileName()), page.html(),
					StandardCharsets.UTF_8);
		}
		copyStaticAssets();
		Files.writeString(properties.getStaticSite().getOutputDir().resolve("database-improvements.txt"),
				renderImprovements(site.improvements()), StandardCharsets.UTF_8);
	}

	private void copyStaticAssets() throws IOException {
		Path sourceDirectory = Path.of("src/main/resources/static/assets");
		if (!Files.exists(sourceDirectory)) {
			return;
		}
		Path targetDirectory = properties.getStaticSite().getOutputDir().resolve("assets");
		Files.createDirectories(targetDirectory);
		try (Stream<Path> paths = Files.walk(sourceDirectory)) {
			for (Path source : paths.filter(Files::isRegularFile).toList()) {
				Path target = targetDirectory.resolve(sourceDirectory.relativize(source));
				Files.createDirectories(target.getParent());
				Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	private static String renderImprovements(List<DatabaseImprovement> improvements) {
		if (improvements.isEmpty()) {
			return "Database content includes the main fields needed by the prototype style." + System.lineSeparator();
		}
		StringBuilder output = new StringBuilder();
		for (DatabaseImprovement improvement : improvements) {
			output.append(improvement.stepTitle())
					.append(": ")
					.append(improvement.suggestion())
					.append(System.lineSeparator());
		}
		return output.toString();
	}
}
