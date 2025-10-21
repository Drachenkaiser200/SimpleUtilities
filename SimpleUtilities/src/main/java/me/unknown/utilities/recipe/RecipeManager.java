package me.unknown.utilities.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;
import org.bukkit.inventory.SmithingTrimRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.meta.trim.TrimPattern;

import me.unknown.utilities.Main;
import me.unknown.utilities.util.Config;
import me.unknown.utilities.util.GenericUtil;

public class RecipeManager {

	public Config recipesYML;

	public RecipeManager() {
		recipesYML = new Config("recipes.yml");
	}

	public boolean isRecipeLoaded(String key) {
		return recipesYML.getBoolean(key + ".loaded");
	}

	public void loadRecipe(String key) {
		recipesYML.set(key + ".loaded", true);
		if (Bukkit.getRecipe(getKey(key)) != null)
			Bukkit.removeRecipe(getKey(key));
		Bukkit.addRecipe(getRecipe(key));
	}

	public void unloadRecipe(String key) {
		recipesYML.set(key + ".loaded", false);
		if (Bukkit.getRecipe(getKey(key)) != null)
			Bukkit.removeRecipe(getKey(key));
	}

	public void deleteRecipe(String key) {
		unloadRecipe(key);
		recipesYML.set(key, null);
	}

	public void registerRecipes() {
		for (String s : recipesYML.getKeys(false)) {
			if (recipesYML.getBoolean(s + ".loaded")) {
				if (Bukkit.getRecipe(getKey(s)) != null)
					Bukkit.removeRecipe(getKey(s));
				Bukkit.addRecipe(getRecipe(s));
			}
		}
	}

	public Recipe getRecipe(String key) {
		for (String s : recipesYML.getKeys(false)) {
			if (s.equalsIgnoreCase(key)) {
				Recipe recipe = null;
				switch (recipesYML.getString(key + ".type")) {
				case RecipeType.BLASTING: {
					recipe = new BlastingRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".input")),
							recipesYML.getFloat(key + ".experience"), recipesYML.getInt(key + ".cookingTime"));
					break;
				}
				case RecipeType.CAMPFIRE: {
					recipe = new CampfireRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".input")),
							recipesYML.getFloat(key + ".experience"), recipesYML.getInt(key + ".cookingTime"));
					break;
				}
				case RecipeType.FURNACE: {
					recipe = new FurnaceRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".input")),
							recipesYML.getFloat(key + ".experience"), recipesYML.getInt(key + ".cookingTime"));
					break;
				}
				case RecipeType.MERCHANT: {
					recipe = new MerchantRecipe(recipesYML.getItemStack(key + ".result"),
							recipesYML.getInt(key + ".maxUses"));
					for (int i = 0; i < recipesYML.getItemStackList(key + ".ingredients").size(); i++)
						if(recipesYML.getItemStackList(key + ".ingredients").get(i) != null)
							((MerchantRecipe) recipe).addIngredient(recipesYML.getItemStackList(key + ".ingredients").get(i));
					break;
				}
				case RecipeType.SHAPED:
					recipe = new ShapedRecipe(getKey(key), recipesYML.getItemStack(key + ".result"));
					((ShapedRecipe) recipe).shape(new String[] { "123", "456", "789" });
					for (int i = 1; i <= 9; i++) {
						if (recipesYML.getItemStack(key + ".ingredient" + i) != null
								&& recipesYML.getItemStack(key + ".ingredient" + i).getType() != Material.AIR)
							((ShapedRecipe) recipe).setIngredient(GenericUtil.toChar(i),
									new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".ingredient" + i)));
					}
					break;
				case RecipeType.SHAPELESS:
					recipe = new ShapelessRecipe(getKey(key), recipesYML.getItemStack(key + ".result"));
					for (int i = 1; i <= 9; i++) {
						if (recipesYML.getItemStack(key + ".ingredient" + i) != null
								&& recipesYML.getItemStack(key + ".ingredient" + i).getType() != Material.AIR)
							((ShapelessRecipe) recipe).addIngredient(
									new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".ingredient" + i)));
					}
					break;
				case RecipeType.SMITHING_TRANSFORM:
					recipe = new SmithingTransformRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".template")),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".base")),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".addition")));
					break;
				case RecipeType.SMITHING_TRIM:
					recipe = new SmithingTrimRecipe(getKey(key),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".template")),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".base")),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".addition")), TrimPattern.SILENCE); //TODO impl Trim types
					break;
				case RecipeType.SMOKING:
					recipe = new SmokingRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".input")),
							recipesYML.getFloat(key + ".experience"), recipesYML.getInt(key + ".cookingTime"));
					break;
				case RecipeType.STONECUTTER:
					recipe = new StonecuttingRecipe(getKey(key), recipesYML.getItemStack(key + ".result"),
							new RecipeChoice.ExactChoice(recipesYML.getItemStack(key + ".input")));
					break;
				default:
					break;
				}
				return recipe;
			}
		}
		return null;
	}

	public NamespacedKey getKey(String key) {
		return NamespacedKey.fromString("recipe." + key.toLowerCase(), Main.get);
	}

	public void createCookingRecipe(String type, String key, ItemStack result, ItemStack input, float experience,
			int cookingTime) {
		Recipe recipe = null;
		if (type.equalsIgnoreCase(RecipeType.BLASTING))
			recipe = new BlastingRecipe(getKey(key), result, new RecipeChoice.ExactChoice(input), experience,
					cookingTime);
		if (type.equalsIgnoreCase(RecipeType.CAMPFIRE))
			recipe = new CampfireRecipe(getKey(key), result, new RecipeChoice.ExactChoice(input), experience,
					cookingTime);
		if (type.equalsIgnoreCase(RecipeType.FURNACE))
			recipe = new FurnaceRecipe(getKey(key), result, new RecipeChoice.ExactChoice(input), experience,
					cookingTime);
		if (type.equalsIgnoreCase(RecipeType.SMOKING))
			recipe = new SmokingRecipe(getKey(key), result, new RecipeChoice.ExactChoice(input), experience,
					cookingTime);
		recipesYML.getConfig().set(key + ".result", result);
		recipesYML.getConfig().set(key + ".input", input);
		recipesYML.getConfig().set(key + ".experience", experience);
		recipesYML.getConfig().set(key + ".cookingTime", cookingTime);
		recipesYML.saveConfig();
		Bukkit.addRecipe(recipe);
	}

	public void createMerchantRecipe(String key, ItemStack result, int maxUses, ItemStack... ingredients) {
		MerchantRecipe recipe = new MerchantRecipe(result, maxUses);
		for (int i = 0; i < ingredients.length; i++)
			if(ingredients[i] != null)
				((MerchantRecipe) recipe).addIngredient(ingredients[i]);
		recipesYML.getConfig().set(key + ".result", result);
		recipesYML.getConfig().set(key + ".maxUses", maxUses);
		recipesYML.getConfig().set(key + ".ingredients", GenericUtil.asList(ingredients));
		recipesYML.saveConfig();
	}

	public void createCraftingRecipe(String type, String key, ItemStack result, ItemStack... ingredients) {
		Recipe recipe = null;
		if (type.equalsIgnoreCase(RecipeType.SHAPED)) {
			recipe = new ShapedRecipe(getKey(key), result);
			((ShapedRecipe) recipe).shape("012", "345", "678");
		}
		if (type.equalsIgnoreCase(RecipeType.SHAPELESS)) {
			recipe = new ShapelessRecipe(getKey(key), result);
		}
		recipesYML.getConfig().set(key + ".result", result);
		for (int i = 0; i < ingredients.length; i++) {
			if (recipe instanceof ShapedRecipe) {
				if (ingredients[i] != null) {
					((ShapedRecipe) recipe).setIngredient(GenericUtil.toChar(i),
							new RecipeChoice.ExactChoice(ingredients[i]));
					recipesYML.set(key + ".ingredient" + i, ingredients[i]);
				}
			}
			if (recipe instanceof ShapelessRecipe) {
				if (ingredients[i] != null) {
					((ShapelessRecipe) recipe).addIngredient(new RecipeChoice.ExactChoice(ingredients[i]));
					recipesYML.set(key + ".ingredient" + i, ingredients[i]);
				}
			}
		}
		recipesYML.saveConfig();
		Bukkit.addRecipe(recipe);
	}

	/**template -> Smithing Template
	 * base -> Item that should be decorated
	 * addition -> Ingot / Crystal (color of the template)
	 * @param key2 */
	public void createSmithinRecipe(String type, String key, ItemStack result, ItemStack template, ItemStack base, ItemStack addition) {
		Recipe recipe = null;
		if(type.equalsIgnoreCase(RecipeType.SMITHING_TRIM)) {
			recipe = new SmithingTrimRecipe(getKey(key), new RecipeChoice.ExactChoice(template),
					new RecipeChoice.ExactChoice(base),
					new RecipeChoice.ExactChoice(addition), TrimPattern.SILENCE); //TODO impl Trim types
		}
		if(type.equalsIgnoreCase(RecipeType.SMITHING_TRANSFORM)) {
			recipe = new SmithingTransformRecipe(getKey(key), result, new RecipeChoice.ExactChoice(template),
					new RecipeChoice.ExactChoice(base),
					new RecipeChoice.ExactChoice(addition));
		}
		recipesYML.getConfig().set(key + ".result", result);
		recipesYML.getConfig().set(key + ".template", template);
		recipesYML.getConfig().set(key + ".base", base);
		recipesYML.getConfig().set(key + ".addition", addition);
		recipesYML.saveConfig();
		Bukkit.addRecipe(recipe);
	}

	public void createStoneCutterRecipe(String key, ItemStack result, ItemStack input) {
		StonecuttingRecipe recipe = new StonecuttingRecipe(getKey(key), result, new RecipeChoice.ExactChoice(input));
		recipesYML.getConfig().set(key + ".result", result);
		recipesYML.getConfig().set(key + ".input", input);
		recipesYML.saveConfig();
		Bukkit.addRecipe(recipe);
	}

}
