package com.example.wonder_steps_generator.render;

final class HtmlEscaper {

	private HtmlEscaper() {
	}

	static String escape(String value) {
		return value == null ? "" : value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	static String attribute(String value) {
		return escape(value).replace("\"", "&quot;");
	}
}
