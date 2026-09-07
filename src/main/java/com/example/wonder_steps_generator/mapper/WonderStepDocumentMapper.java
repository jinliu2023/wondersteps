package com.example.wonder_steps_generator.mapper;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.example.wonder_steps_generator.domain.WonderStep;
import com.example.wonder_steps_generator.repository.WonderStepDocument;

@Component
public class WonderStepDocumentMapper {

	private static final Pattern NON_SLUG_CHARACTER = Pattern.compile("[^a-z0-9]+");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

	public WonderStep toWonderStep(Document document) {
		String id = stringValue(document, "_id");
		String title = firstString(document, "title", "name", "headline");
		String slug = firstString(document, "slug", "urlSlug", "path");
		if (slug.isBlank()) {
			slug = slugify(title.isBlank() ? id : title);
		}

		List<String> paragraphs = collectParagraphs(document);
		String summary = firstString(document, "summary", "excerpt", "description", "shortDescription", "intro");
		if (summary.isBlank() && !paragraphs.isEmpty()) {
			summary = paragraphs.getFirst();
		}
		String subtitle = firstString(document, "subtitle", "subTitle", "dek", "intro");
		if (subtitle.isBlank()) {
			subtitle = summary;
		}

		String city = firstString(document, "city");
		String country = firstString(document, "country");
		String location = firstString(document, "location", "placeLocation");
		if (location.isBlank()) {
			location = joinNonBlank(", ", city, country);
		}

		String dateLabel = firstString(document, "dateLabel", "season", "visitedAtLabel");
		if (dateLabel.isBlank()) {
			dateLabel = formatDate(document.get("date"));
		}

		String imageUrl = firstString(document, "imageUrl", "imageURL", "photoUrl", "photoURL", "image", "photo",
				"heroImage");
		String imageAlt = firstString(document, "imageAlt", "photoAlt", "alt");
		if (imageAlt.isBlank()) {
			imageAlt = title;
		}

		return new WonderStep(
				id,
				slug,
				intValue(document, "order"),
				title,
				summary,
				subtitle,
				firstString(document, "metaDescription", "description", "summary"),
				firstNonBlank(firstString(document, "crumb"), "One step" + (city.isBlank() ? "" : " in " + city)),
				location,
				dateLabel,
				firstString(document, "placeName", "place", "venue", "attraction", "restaurant"),
				firstString(document, "category", "type", "kind"),
				imageUrl,
				imageAlt,
				firstString(document, "caption", "imageCaption", "photoCaption"),
				paragraphs,
				firstNonBlank(firstString(document, "highlightLabel", "momentLabel"), "What stayed with me"),
				firstString(document, "highlightTitle", "momentTitle", "memoryTitle"),
				firstString(document, "highlightText", "momentText", "memoryText"),
				firstNonBlank(firstString(document, "closing", "closingLine"),
						"A small step, saved before it disappears into the rest of the journey."));
	}

	public WonderStep toWonderStep(WonderStepDocument document) {
		return toWonderStep(toBsonDocument(document));
	}

	private static Document toBsonDocument(WonderStepDocument source) {
		Document document = new Document();
		put(document, "_id", source.getId());
		put(document, "order", firstNonNull(source.getOrder(), source.getDisplayOrder()));
		put(document, "date", firstNonNull(source.getDate(), source.getVisitDate()));
		put(document, "createdAt", source.getCreatedAt());
		put(document, "tripId", source.getTripId());
		put(document, "trip_id", source.getTripIdSnakeCase());
		put(document, "trip", source.getTrip());
		put(document, "title", firstNonBlank(articleTitle(source), source.getTitle()));
		put(document, "name", source.getName());
		put(document, "headline", source.getHeadline());
		put(document, "slug", firstNonBlank(canonicalSlug(source), source.getSlug()));
		put(document, "urlSlug", source.getUrlSlug());
		put(document, "path", source.getPath());
		put(document, "summary", firstNonBlank(articleSummary(source), source.getSummary()));
		put(document, "excerpt", source.getExcerpt());
		put(document, "description", firstNonBlank(seoDescription(source), source.getDescription()));
		put(document, "shortDescription", source.getShortDescription());
		put(document, "intro", source.getIntro());
		put(document, "subtitle", firstNonBlank(articleSubtitle(source), source.getSubtitle()));
		put(document, "subTitle", source.getSubTitle());
		put(document, "dek", source.getDek());
		put(document, "city", source.getCity());
		put(document, "country", source.getCountry());
		put(document, "location", source.getLocation());
		put(document, "placeLocation", source.getPlaceLocation());
		put(document, "dateLabel", source.getDateLabel());
		put(document, "season", source.getSeason());
		put(document, "visitedAtLabel", source.getVisitedAtLabel());
		put(document, "placeName", firstNonBlank(source.getPlaceName(), source.getPrimaryPlaceId()));
		put(document, "place", source.getPlace());
		put(document, "venue", source.getVenue());
		put(document, "attraction", source.getAttraction());
		put(document, "restaurant", source.getRestaurant());
		put(document, "category", source.getCategory());
		put(document, "type", source.getType());
		put(document, "kind", source.getKind());
		put(document, "imageUrl", firstNonBlank(assetUrl(source), source.getImageUrl()));
		put(document, "imageURL", source.getImageURL());
		put(document, "photoUrl", source.getPhotoUrl());
		put(document, "photoURL", source.getPhotoURL());
		put(document, "image", source.getImage());
		put(document, "photo", source.getPhoto());
		put(document, "heroImage", source.getHeroImage());
		put(document, "imageAlt", source.getImageAlt());
		put(document, "photoAlt", source.getPhotoAlt());
		put(document, "alt", source.getAlt());
		put(document, "caption", source.getCaption());
		put(document, "imageCaption", source.getImageCaption());
		put(document, "photoCaption", source.getPhotoCaption());
		put(document, "paragraphs", firstNonNull(articleParagraphs(source), source.getParagraphs()));
		put(document, "content", source.getContent());
		put(document, "body", source.getBody());
		put(document, "story", source.getStory());
		put(document, "metaDescription", source.getMetaDescription());
		put(document, "crumb", source.getCrumb());
		WonderStepDocument.Section highlight = firstHighlight(source);
		put(document, "highlightLabel", firstNonBlank(highlightLabel(highlight), source.getHighlightLabel()));
		put(document, "momentLabel", source.getMomentLabel());
		put(document, "highlightTitle", firstNonBlank(highlightTitle(highlight), source.getHighlightTitle()));
		put(document, "momentTitle", source.getMomentTitle());
		put(document, "memoryTitle", source.getMemoryTitle());
		put(document, "highlightText", firstNonBlank(highlightText(highlight), source.getHighlightText()));
		put(document, "momentText", source.getMomentText());
		put(document, "memoryText", source.getMemoryText());
		put(document, "closing", firstNonBlank(articleClosing(source), source.getClosing()));
		put(document, "closingLine", source.getClosingLine());
		return document;
	}

	private static Object firstNonNull(Object first, Object second) {
		return first == null ? second : first;
	}

	private static String articleTitle(WonderStepDocument source) {
		return source.getArticle() == null ? "" : source.getArticle().getTitle();
	}

	private static String articleSubtitle(WonderStepDocument source) {
		return source.getArticle() == null ? "" : source.getArticle().getSubtitle();
	}

	private static String articleSummary(WonderStepDocument source) {
		return source.getArticle() == null ? "" : source.getArticle().getSummary();
	}

	private static String articleClosing(WonderStepDocument source) {
		return source.getArticle() == null ? "" : source.getArticle().getClosing();
	}

	private static String seoDescription(WonderStepDocument source) {
		return source.getSeo() == null ? "" : source.getSeo().getDescription();
	}

	private static String canonicalSlug(WonderStepDocument source) {
		if (source.getSeo() == null || source.getSeo().getCanonicalPath() == null) {
			return "";
		}
		String path = source.getSeo().getCanonicalPath().replaceAll("/+$", "");
		int lastSlash = path.lastIndexOf('/');
		return lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
	}

	private static String assetUrl(WonderStepDocument source) {
		String assetKey = source.getHeroAssetKey();
		if (assetKey == null || assetKey.isBlank()) {
			return "";
		}
		String assetPath = assetKey.startsWith("assets/") ? assetKey : "assets/" + assetKey;
		if (!assetPath.substring(assetPath.lastIndexOf('/') + 1).contains(".")) {
			return assetPath + ".png";
		}
		return assetPath;
	}

	private static List<String> articleParagraphs(WonderStepDocument source) {
		if (source.getArticle() == null || source.getArticle().getSections() == null) {
			return null;
		}
		return source.getArticle().getSections().stream()
				.filter(section -> "paragraph".equalsIgnoreCase(section.getType()))
				.map(WonderStepDocument.Section::getText)
				.filter(text -> text != null && !text.isBlank())
				.toList();
	}

	private static WonderStepDocument.Section firstHighlight(WonderStepDocument source) {
		if (source.getArticle() == null || source.getArticle().getSections() == null) {
			return null;
		}
		return source.getArticle().getSections().stream()
				.filter(section -> "highlight".equalsIgnoreCase(section.getType()))
				.findFirst()
				.orElse(null);
	}

	private static String highlightLabel(WonderStepDocument.Section section) {
		return section == null ? "" : section.getLabel();
	}

	private static String highlightTitle(WonderStepDocument.Section section) {
		return section == null ? "" : section.getTitle();
	}

	private static String highlightText(WonderStepDocument.Section section) {
		return section == null ? "" : section.getText();
	}

	private static void put(Document document, String key, Object value) {
		if (value != null) {
			document.put(key, value);
		}
	}

	private static String firstString(Document document, String... keys) {
		for (String key : keys) {
			String value = stringValue(document, key);
			if (!value.isBlank()) {
				return value;
			}
		}
		return "";
	}

	private static String firstNonBlank(String first, String second) {
		return first == null || first.isBlank() ? Objects.toString(second, "") : first;
	}

	private static String stringValue(Document document, String key) {
		Object value = document.get(key);
		if (value instanceof String string) {
			return string.trim();
		}
		if (value instanceof ObjectId objectId) {
			return objectId.toHexString();
		}
		return value == null ? "" : value.toString().trim();
	}

	private static int intValue(Document document, String key) {
		Object value = document.get(key);
		if (value instanceof Number number) {
			return number.intValue();
		}
		if (value instanceof String string && !string.isBlank()) {
			try {
				return Integer.parseInt(string);
			}
			catch (NumberFormatException ignored) {
				return Integer.MAX_VALUE;
			}
		}
		return Integer.MAX_VALUE;
	}

	private static List<String> collectParagraphs(Document document) {
		Set<String> values = new LinkedHashSet<>();
		addTextValues(values, document.get("paragraphs"));
		addTextValues(values, document.get("content"));
		addTextValues(values, document.get("body"));
		addTextValues(values, document.get("story"));
		return List.copyOf(values);
	}

	private static void addTextValues(Set<String> values, Object source) {
		if (source instanceof List<?> list) {
			for (Object item : list) {
				addTextValues(values, item);
			}
		}
		else if (source instanceof Document document) {
			addTextValues(values, document.get("text"));
		}
		else if (source instanceof String string) {
			for (String paragraph : string.split("\\R\\s*\\R")) {
				if (!paragraph.isBlank()) {
					values.add(paragraph.trim());
				}
			}
		}
	}

	private static String joinNonBlank(String delimiter, String... values) {
		return java.util.Arrays.stream(values)
				.filter(value -> value != null && !value.isBlank())
				.reduce((left, right) -> left + delimiter + right)
				.orElse("");
	}

	private static String formatDate(Object value) {
		if (value instanceof Date date) {
			return DATE_FORMATTER.format(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()));
		}
		if (value instanceof LocalDate localDate) {
			return DATE_FORMATTER.format(localDate);
		}
		return value == null ? "" : value.toString();
	}

	private static String slugify(String value) {
		String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.toLowerCase(Locale.ENGLISH);
		String slug = NON_SLUG_CHARACTER.matcher(normalized).replaceAll("-").replaceAll("(^-|-$)", "");
		return slug.isBlank() ? "step" : slug;
	}
}
