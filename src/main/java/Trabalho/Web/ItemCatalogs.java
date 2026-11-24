package Trabalho.Web;

import java.util.List;

/** Tipos e utilitários compartilhados pelos catálogos de itens. */
public final class ItemCatalogs {

	private static final String EQUIPMENT_ROOT = "/images/items/equipamentos/";

	private ItemCatalogs() {
	}

	static CatalogItem food(String name, String image, String effect, String cookedEffect,
			String locations, String weight, String note) {
		return food(name, List.of(image), effect, cookedEffect, locations, weight, note);
	}

	static CatalogItem food(String name, List<String> images, String effect, String cookedEffect,
			String locations, String weight, String note) {
		return new CatalogItem(name, images.stream().map(ImagePaths::canonicalize).toList(),
				effect, cookedEffect, locations, weight, note);
	}

	static CatalogItem gear(String name, String image, String weight, String locations, String description) {
		return new CatalogItem(name, image == null ? List.of()
				: List.of(ImagePaths.canonicalize(EQUIPMENT_ROOT + image)),
				"", "", locations, weight, description);
	}

	static String biomeImage(String biome, String image) {
		return ImagePaths.canonicalize("/images/biome-pages/" + biome + "/" + image);
	}

	public record ItemCatalog(String title, boolean food, List<ItemSection> sections) {
	}

	public record ItemSection(String id, String title, List<CatalogItem> items) {
		/** Nome curto exibido nos botões do sumário (conforme o design do Figma). */
		public String summaryTitle() {
			return switch (id) {
				case "especiais" -> "Especiais";
				case "coletivos" -> "Equipe";
				case "individuais" -> "Solo";
				case "miticos" -> "Míticos";
				default -> title;
			};
		}
	}

	public record CatalogItem(String name, List<String> images, String effect, String cookedEffect,
			String locations, String weight, String description) {
	}
}
