package com.example.wonder_steps_generator.domain;

import java.util.ArrayList;
import java.util.List;

public record WonderStep(
		String id,
		String slug,
		int order,
		String title,
		String summary,
		String subtitle,
		String description,
		String crumb,
		String location,
		String dateLabel,
		String placeName,
		String category,
		String imageUrl,
		String imageAlt,
		String caption,
		List<String> paragraphs,
		String highlightLabel,
		String highlightTitle,
		String highlightText,
		String closing) {

	public String metaLine() {
		return joinNonBlank(" · ", location, category);
	}

	public List<String> metaItems() {
		List<String> items = new ArrayList<>();
		items.add(location);
		items.add(dateLabel);
		items.add(placeName);
		return items;
	}

	private static String joinNonBlank(String delimiter, String... values) {
		return java.util.Arrays.stream(values)
				.filter(value -> value != null && !value.isBlank())
				.reduce((left, right) -> left + delimiter + right)
				.orElse("");
	}
}
