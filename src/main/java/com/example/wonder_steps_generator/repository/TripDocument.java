package com.example.wonder_steps_generator.repository;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trip")
public class TripDocument {

	@Id
	private String id;

	private Map<String, String> names;
	private Map<String, String> summaries;
	private String startDate;
	private String endDate;
	private String heroAssetKey;
	private String status;
	private boolean featured;

	public String getId() {
		return id;
	}

	public Map<String, String> getNames() {
		return names;
	}

	public Map<String, String> getSummaries() {
		return summaries;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getHeroAssetKey() {
		return heroAssetKey;
	}

	public String getStatus() {
		return status;
	}

	public boolean isFeatured() {
		return featured;
	}
}
