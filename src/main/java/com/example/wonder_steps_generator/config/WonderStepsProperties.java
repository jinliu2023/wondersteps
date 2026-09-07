package com.example.wonder_steps_generator.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wonder-steps-generator")
public class WonderStepsProperties {

	private final MongoDb mongodb = new MongoDb();
	private final StaticSite staticSite = new StaticSite();

	public MongoDb getMongodb() {
		return mongodb;
	}

	public StaticSite getStaticSite() {
		return staticSite;
	}

	public static class MongoDb {

		private String collection = "wondersteps";
		private boolean createCollections;
		private boolean verifyConnection;

		public String getCollection() {
			return collection;
		}

		public void setCollection(String collection) {
			this.collection = collection;
		}

		public boolean isCreateCollections() {
			return createCollections;
		}

		public void setCreateCollections(boolean createCollections) {
			this.createCollections = createCollections;
		}

		public boolean isVerifyConnection() {
			return verifyConnection;
		}

		public void setVerifyConnection(boolean verifyConnection) {
			this.verifyConnection = verifyConnection;
		}
	}

	public static class StaticSite {

		private boolean enabled;
		private Path outputDir = Path.of("build/static-site");
		private String tripStatus = "PUBLISHED";
		private boolean featuredOnly = true;
		private String lang = "en";
		private String baseUrl = "";

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public Path getOutputDir() {
			return outputDir;
		}

		public void setOutputDir(Path outputDir) {
			this.outputDir = outputDir;
		}

		public String getTripStatus() {
			return tripStatus;
		}

		public void setTripStatus(String tripStatus) {
			this.tripStatus = tripStatus;
		}

		public boolean isFeaturedOnly() {
			return featuredOnly;
		}

		public void setFeaturedOnly(boolean featuredOnly) {
			this.featuredOnly = featuredOnly;
		}

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}
	}
}
