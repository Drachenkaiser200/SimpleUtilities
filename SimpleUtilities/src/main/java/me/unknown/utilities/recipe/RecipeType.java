package me.unknown.utilities.recipe;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class RecipeType {
	
	public static final String BLASTING = "BLASTING";
	public static final String CAMPFIRE = "CAMPFIRE";
	public static final String FURNACE = "FURNACE";
	public static final String MERCHANT = "MERCHANT";
	public static final String SHAPED = "SHAPED";
	public static final String SHAPELESS = "SHAPELESS";
	public static final String SMOKING = "SMOKING";
	public static final String SMITHING_TRIM = "SMITHING_TRIM";
	public static final String SMITHING_TRANSFORM = "SMITHING_TRANSFORM";
	public static final String STONECUTTER = "STONECUTTER";
	
	public static Map<String, Material> RECIPE_TYPES = new HashMap<>();
	
	public static void load() {
		RECIPE_TYPES.clear();
		RECIPE_TYPES.put(BLASTING, Material.BLAST_FURNACE);
		RECIPE_TYPES.put(CAMPFIRE, Material.CAMPFIRE);
		RECIPE_TYPES.put(FURNACE, Material.FURNACE);
		RECIPE_TYPES.put(MERCHANT, Material.VILLAGER_SPAWN_EGG);
		RECIPE_TYPES.put(SHAPED, Material.DIAMOND_PICKAXE);
		RECIPE_TYPES.put(SHAPELESS, Material.WOODEN_PICKAXE);
		RECIPE_TYPES.put(SMOKING, Material.SMOKER);
		RECIPE_TYPES.put(SMITHING_TRIM, Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
		RECIPE_TYPES.put(SMITHING_TRANSFORM, Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
		RECIPE_TYPES.put(STONECUTTER, Material.STONECUTTER);
	}
	
	public static String getNextRecipeType(String prevRecipeType) {
		switch (prevRecipeType) {
		case BLASTING: {
			return CAMPFIRE;
		}
		case CAMPFIRE: {
			return FURNACE;
		}
		case FURNACE: {
			return MERCHANT;
		}
		case MERCHANT: {
			return SHAPED;
		}
		case SHAPED: {
			return SHAPELESS;
		}
		case SHAPELESS: {
			return SMOKING;
		}
		case SMOKING: {
			return SMITHING_TRIM;
		}
		case SMITHING_TRIM: {
			return SMITHING_TRANSFORM;
		}
		case SMITHING_TRANSFORM: {
			return STONECUTTER;
		}
		case STONECUTTER: {
			return BLASTING;
		}
		default:
			return SHAPED;
		}
	}
	
}
