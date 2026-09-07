package com.example.wonder_steps_generator.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "story")
public class WonderStepDocument {

	@Id
	private String id;

	private Integer order;
	private Integer displayOrder;
	private Date date;
	private String visitDate;
	private Date createdAt;
	private String tripId;

	@Field("trip_id")
	private String tripIdSnakeCase;

	private String trip;
	private String storyKey;
	private String heroAssetKey;
	private String lang;
	private String status;
	private String primaryPlaceId;
	private Article article;
	private Seo seo;
	private String title;
	private String name;
	private String headline;
	private String slug;
	private String urlSlug;
	private String path;
	private String summary;
	private String excerpt;
	private String description;
	private String shortDescription;
	private String intro;
	private String subtitle;
	private String subTitle;
	private String dek;
	private String city;
	private String country;
	private String location;
	private String placeLocation;
	private String dateLabel;
	private String season;
	private String visitedAtLabel;
	private String placeName;
	private String place;
	private String venue;
	private String attraction;
	private String restaurant;
	private String category;
	private String type;
	private String kind;
	private String imageUrl;
	private String imageURL;
	private String photoUrl;
	private String photoURL;
	private String image;
	private String photo;
	private String heroImage;
	private String imageAlt;
	private String photoAlt;
	private String alt;
	private String caption;
	private String imageCaption;
	private String photoCaption;
	private List<?> paragraphs;
	private Object content;
	private Object body;
	private Object story;
	private String metaDescription;
	private String crumb;
	private String highlightLabel;
	private String momentLabel;
	private String highlightTitle;
	private String momentTitle;
	private String memoryTitle;
	private String highlightText;
	private String momentText;
	private String memoryText;
	private String closing;
	private String closingLine;

	public String getId() {
		return id;
	}

	public Integer getOrder() {
		return order;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public Date getDate() {
		return date;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getTripId() {
		return tripId;
	}

	public String getTripIdSnakeCase() {
		return tripIdSnakeCase;
	}

	public String getTrip() {
		return trip;
	}

	public String getStoryKey() {
		return storyKey;
	}

	public String getHeroAssetKey() {
		return heroAssetKey;
	}

	public String getLang() {
		return lang;
	}

	public String getStatus() {
		return status;
	}

	public String getPrimaryPlaceId() {
		return primaryPlaceId;
	}

	public Article getArticle() {
		return article;
	}

	public Seo getSeo() {
		return seo;
	}

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public String getHeadline() {
		return headline;
	}

	public String getSlug() {
		return slug;
	}

	public String getUrlSlug() {
		return urlSlug;
	}

	public String getPath() {
		return path;
	}

	public String getSummary() {
		return summary;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getIntro() {
		return intro;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getDek() {
		return dek;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getLocation() {
		return location;
	}

	public String getPlaceLocation() {
		return placeLocation;
	}

	public String getDateLabel() {
		return dateLabel;
	}

	public String getSeason() {
		return season;
	}

	public String getVisitedAtLabel() {
		return visitedAtLabel;
	}

	public String getPlaceName() {
		return placeName;
	}

	public String getPlace() {
		return place;
	}

	public String getVenue() {
		return venue;
	}

	public String getAttraction() {
		return attraction;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public String getCategory() {
		return category;
	}

	public String getType() {
		return type;
	}

	public String getKind() {
		return kind;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getImage() {
		return image;
	}

	public String getPhoto() {
		return photo;
	}

	public String getHeroImage() {
		return heroImage;
	}

	public String getImageAlt() {
		return imageAlt;
	}

	public String getPhotoAlt() {
		return photoAlt;
	}

	public String getAlt() {
		return alt;
	}

	public String getCaption() {
		return caption;
	}

	public String getImageCaption() {
		return imageCaption;
	}

	public String getPhotoCaption() {
		return photoCaption;
	}

	public List<?> getParagraphs() {
		return paragraphs;
	}

	public Object getContent() {
		return content;
	}

	public Object getBody() {
		return body;
	}

	public Object getStory() {
		return story;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public String getCrumb() {
		return crumb;
	}

	public String getHighlightLabel() {
		return highlightLabel;
	}

	public String getMomentLabel() {
		return momentLabel;
	}

	public String getHighlightTitle() {
		return highlightTitle;
	}

	public String getMomentTitle() {
		return momentTitle;
	}

	public String getMemoryTitle() {
		return memoryTitle;
	}

	public String getHighlightText() {
		return highlightText;
	}

	public String getMomentText() {
		return momentText;
	}

	public String getMemoryText() {
		return memoryText;
	}

	public String getClosing() {
		return closing;
	}

	public String getClosingLine() {
		return closingLine;
	}

	public static class Article {

		private String title;
		private String subtitle;
		private String summary;
		private List<Section> sections;
		private String closing;

		public String getTitle() {
			return title;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public String getSummary() {
			return summary;
		}

		public List<Section> getSections() {
			return sections;
		}

		public String getClosing() {
			return closing;
		}
	}

	public static class Section {

		private String type;
		private String text;
		private String label;
		private String title;

		public String getType() {
			return type;
		}

		public String getText() {
			return text;
		}

		public String getLabel() {
			return label;
		}

		public String getTitle() {
			return title;
		}
	}

	public static class Seo {

		private String title;
		private String description;
		private String canonicalPath;
		private Boolean indexable;

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public String getCanonicalPath() {
			return canonicalPath;
		}

		public Boolean getIndexable() {
			return indexable;
		}
	}
}
