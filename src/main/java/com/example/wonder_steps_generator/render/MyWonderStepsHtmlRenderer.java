package com.example.wonder_steps_generator.render;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.wonder_steps_generator.config.WonderStepsProperties;
import com.example.wonder_steps_generator.domain.GeneratedPage;
import com.example.wonder_steps_generator.domain.WonderStep;

@Component
public class MyWonderStepsHtmlRenderer {

	public List<GeneratedPage> render(List<WonderStep> steps, WonderStepsProperties.StaticSite staticSite) {
		List<GeneratedPage> pages = new java.util.ArrayList<>();
		pages.add(new GeneratedPage("index.html", renderIndex(steps, staticSite)));
		for (WonderStep step : steps) {
			pages.add(new GeneratedPage(step.slug() + ".html", renderDetail(step, staticSite)));
		}
		return List.copyOf(pages);
	}

	private String renderIndex(List<WonderStep> steps, WonderStepsProperties.StaticSite staticSite) {
		String cards = steps.stream().map(step -> renderCard(step, staticSite)).reduce("", String::concat);
		return """
				<!doctype html>
				<html lang="%s">
				<head>
				  <meta charset="utf-8" />
				  <meta name="viewport" content="width=device-width, initial-scale=1" />
				  <meta name="description" content="MyWonderSteps is a personal record of places visited, small moments, and tastes remembered." />
				  <title>MyWonderSteps - Places, tastes, memories</title>
				  <style>%s</style>
				</head>
				<body>
				<header>
				  <a class="brand" href="index.html"><span class="footmark" aria-hidden="true"></span><span>MyWonderSteps<small>漫步记 · places, tastes, memories</small></span></a>
				  <nav><a href="#steps">Steps</a><a href="#">Places</a><a href="#">About</a></nav>
				</header>
				<main>
				  <section class="hero">
				    <div class="eyebrow">A personal trail</div>
				    <h1>Places pass by.<br>Some steps stay.</h1>
				    <p>A quiet record of where I went, what caught my attention, and the small moments I did not want to lose.</p>
				  </section>
				  <div class="trail" aria-hidden="true"><span></span><span></span><span></span><span></span><span></span><span></span></div>
				  <section class="steps" id="steps">
				%s
				  </section>
				</main>
				<footer>MyWonderSteps · A personal record of where I went and what I tasted.</footer>
				</body>
				</html>
				""".formatted(HtmlEscaper.attribute(staticSite.getLang()), PrototypeStyles.index(), cards);
	}

	private String renderCard(WonderStep step, WonderStepsProperties.StaticSite staticSite) {
		return """
				    <a class="step-card" href="%s.html">
				      <div class="card-image">%s</div>
				      <div class="card-copy">
				        <div class="meta">%s</div>
				        <h2>%s</h2>
				        <p>%s</p>
				        <span class="read">Follow this step</span>
				      </div>
				    </a>

				""".formatted(HtmlEscaper.attribute(step.slug()), renderImage(step, "card", staticSite),
				HtmlEscaper.escape(step.metaLine()), HtmlEscaper.escape(step.title()), HtmlEscaper.escape(step.summary()));
	}

	private String renderDetail(WonderStep step, WonderStepsProperties.StaticSite staticSite) {
		String paragraphs = step.paragraphs().stream()
				.map(paragraph -> "<p>" + HtmlEscaper.escape(paragraph) + "</p>")
				.reduce("", String::concat);
		String moment = step.highlightTitle().isBlank() && step.highlightText().isBlank() ? "" : """
				<aside class="moment"><div class="label">%s</div><h2>%s</h2><p>%s</p></aside>
				""".formatted(HtmlEscaper.escape(step.highlightLabel()), HtmlEscaper.escape(step.highlightTitle()),
				HtmlEscaper.escape(step.highlightText()));

		return """
				<!doctype html>
				<html lang="%s">
				<head>
				  <meta charset="utf-8" />
				  <meta name="viewport" content="width=device-width,initial-scale=1" />
				  <meta name="description" content="%s" />
				  <title>%s - MyWonderSteps</title>
				  <style>%s</style>
				</head>
				<body>
				<header><a class="brand" href="index.html"><span class="footmark" aria-hidden="true"></span><span>MyWonderSteps<small>漫步记 · places, tastes, memories</small></span></a><nav><a href="index.html#steps">Steps</a><a href="#">Places</a><a href="#">About</a></nav></header>
				<main class="page"><a class="back" href="index.html">&larr; Back to my steps</a><section class="intro"><div class="crumb">%s</div><h1>%s</h1><p class="subtitle">%s</p><figure class="photo-wrap">%s<figcaption class="caption">%s</figcaption></figure></section><div class="trail" aria-hidden="true"><span></span><span></span><span></span><span></span><span></span><span></span></div><article><div class="memory-meta">%s</div>%s%s<div class="closing">%s</div></article></main>
				<footer>MyWonderSteps · A personal record of where I went and what I tasted.</footer>
				</body>
				</html>
				""".formatted(HtmlEscaper.attribute(staticSite.getLang()), HtmlEscaper.attribute(step.description()),
				HtmlEscaper.escape(step.title()), PrototypeStyles.detail(), HtmlEscaper.escape(step.crumb()),
				HtmlEscaper.escape(step.title()), HtmlEscaper.escape(step.subtitle()), renderImage(step, "hero-photo", staticSite),
				HtmlEscaper.escape(step.caption()), renderMetaItems(step), paragraphs, moment, HtmlEscaper.escape(step.closing()));
	}

	private String renderImage(WonderStep step, String cssClass, WonderStepsProperties.StaticSite staticSite) {
		if (step.imageUrl().isBlank()) {
			return "<div class=\"" + cssClass + " image-placeholder\">No image in database</div>";
		}
		String imageUrl = resolveUrl(step.imageUrl(), staticSite);
		return "<img class=\"" + cssClass + "\" src=\"" + HtmlEscaper.attribute(imageUrl) + "\" alt=\""
				+ HtmlEscaper.attribute(step.imageAlt()) + "\">";
	}

	private String renderMetaItems(WonderStep step) {
		return step.metaItems().stream()
				.filter(item -> !item.isBlank())
				.map(item -> "<span><i></i>" + HtmlEscaper.escape(item) + "</span>")
				.reduce("", String::concat);
	}

	private static String resolveUrl(String url, WonderStepsProperties.StaticSite staticSite) {
		String baseUrl = staticSite.getBaseUrl();
		if (baseUrl == null || baseUrl.isBlank() || url.startsWith("http://") || url.startsWith("https://")
				|| url.startsWith("/") || url.startsWith("data:")) {
			return url;
		}
		return baseUrl.replaceAll("/+$", "") + "/" + url.replaceAll("^/+", "");
	}
}
