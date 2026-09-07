package com.example.wonder_steps_generator.recommendation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.wonder_steps_generator.domain.DatabaseImprovement;
import com.example.wonder_steps_generator.domain.WonderStep;

@Component
public class DatabaseImprovementAdvisor {

	public List<DatabaseImprovement> inspect(List<WonderStep> steps) {
		List<DatabaseImprovement> improvements = new ArrayList<>();
		if (steps.isEmpty()) {
			improvements.add(new DatabaseImprovement("Collection",
					"No usable MongoDB documents were found. Add documents with a supported title field, or verify the configured collection name."));
			return improvements;
		}
		for (WonderStep step : steps) {
			addIfMissing(improvements, step.title(), step.imageUrl(),
					"Add imageUrl and imageAlt to support the prototype's image-first card and detail layouts.");
			if (step.paragraphs().size() < 2) {
				improvements.add(new DatabaseImprovement(step.title(),
						"Add at least two narrative paragraphs in paragraphs, content, body, or story."));
			}
			if (step.highlightTitle().isBlank() || step.highlightText().isBlank()) {
				improvements.add(new DatabaseImprovement(step.title(),
						"Add highlightTitle and highlightText for the prototype's moment panel."));
			}
			addIfMissing(improvements, step.title(), step.location(),
					"Add location, or city and country, so the card metadata and detail metadata are complete.");
			addIfMissing(improvements, step.title(), step.dateLabel(),
					"Add date or dateLabel so the detail page can show when the memory happened.");
			addIfMissing(improvements, step.title(), step.caption(),
					"Add caption or imageCaption so the hero image has the same editorial treatment as the prototype.");
		}
		return improvements;
	}

	private static void addIfMissing(List<DatabaseImprovement> improvements, String stepTitle, String value,
			String suggestion) {
		if (value == null || value.isBlank()) {
			improvements.add(new DatabaseImprovement(stepTitle, suggestion));
		}
	}
}
