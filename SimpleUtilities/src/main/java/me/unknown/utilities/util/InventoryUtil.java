package me.unknown.utilities.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.unknown.utilities.recipe.RecipeType;

public class InventoryUtil {
	
	public static ItemStack[] INVENTORY_ITEMS = new ItemStack[] { getArrowItem(), getCancelItem(), getFuelItem(), getPlaceholderItem() };

	public static void openRecipeInventory(Player p, String key) {
		Inventory inv = Bukkit.createInventory(null, 9*6, "§0§lCustom-Recipe-Editor" + '\n' + p.getUniqueId().toString() + '\n' + key);
		updateRecipeInventory(inv, key, RecipeType.SHAPED);
		p.openInventory(inv);
	}

	public static ItemStack getPlaceholderItem() {
		ItemStack itm = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemMeta meta = itm.getItemMeta();
		meta.setEnchantmentGlintOverride(true);
		meta.setDisplayName("§0");
		itm.setItemMeta(meta);
		return itm;
	}
	
	public static ItemStack getRecipeTypeItem(String recipeType) {
		ItemStack itm = new ItemStack(RecipeType.RECIPE_TYPES.get(recipeType));
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§3§lRecipeType: " + recipeType);
		meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ARMOR_TRIM, ItemFlag.HIDE_ATTRIBUTES);
		itm.setItemMeta(meta);
		return itm;
	}

	public static ItemStack getCreateItem() {
		ItemStack itm = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§a§lCreate Recipe");
		itm.setItemMeta(meta);
		return itm;
	}

	public static ItemStack getCancelItem() {
		ItemStack itm = new ItemStack(Material.BARRIER);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§c§lCancel Recipe");
		itm.setItemMeta(meta);
		return itm;
	}

	public static ItemStack getArrowItem() {
		ItemStack itm = new ItemStack(Material.ARROW);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§0");
		itm.setItemMeta(meta);
		return itm;
	}

	public static ItemStack getFuelItem() {
		ItemStack itm = new ItemStack(Material.COAL);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§0");
		itm.setItemMeta(meta);
		return itm;
	}
	
	public static ItemStack getChestShopRemovalItem() {
		ItemStack itm = new ItemStack(Material.BARRIER);
		ItemMeta meta = itm.getItemMeta();
		meta.setDisplayName("§4ChestShop-Removal Item");
		meta.setLore(Arrays.asList("Right click chest shop to", "remove it."));
		meta.setEnchantmentGlintOverride(true);
		itm.setItemMeta(meta);
		return itm;
	}

	public static void updateRecipeInventory(Inventory inv, String key, String recipeType) {
		for(int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, getPlaceholderItem());
		}
		if(recipeType.equalsIgnoreCase(RecipeType.BLASTING) || recipeType.equalsIgnoreCase(RecipeType.CAMPFIRE) ||
				recipeType.equalsIgnoreCase(RecipeType.FURNACE) || recipeType.equalsIgnoreCase(RecipeType.SMOKING)) {
			inv.setItem(12, new ItemStack(Material.AIR));
			inv.setItem(30, getFuelItem());
			inv.setItem(25, new ItemStack(Material.AIR));
		}
		if(recipeType.equalsIgnoreCase(RecipeType.MERCHANT)) {
			inv.setItem(19, new ItemStack(Material.AIR));
			inv.setItem(21, new ItemStack(Material.AIR));
			inv.setItem(25, new ItemStack(Material.AIR));
		}
		if(recipeType.equalsIgnoreCase(RecipeType.SHAPED) || recipeType.equalsIgnoreCase(RecipeType.SHAPELESS)) {
			for(int i = 0; i < inv.getSize(); i++) {
				if((i > 9 && i < 13) || (i > 18 && i < 22) || (i > 27 && i < 31) || i == 25) {
					inv.setItem(i, new ItemStack(Material.AIR));
				}
			}
		}
		if(recipeType.equalsIgnoreCase(RecipeType.SMITHING_TRIM) || recipeType.equalsIgnoreCase(RecipeType.SMITHING_TRANSFORM)) {
			inv.setItem(19, new ItemStack(Material.AIR));
			inv.setItem(20, new ItemStack(Material.AIR));
			inv.setItem(21, new ItemStack(Material.AIR));
			inv.setItem(25, new ItemStack(Material.AIR));
		}
		if(recipeType.equalsIgnoreCase(RecipeType.STONECUTTER)) {
			inv.setItem(20, new ItemStack(Material.AIR));
			inv.setItem(25, new ItemStack(Material.AIR));
		}
		inv.setItem(23, getArrowItem());
		inv.setItem(41, getRecipeTypeItem(recipeType));
//		inv.setItem(42, getCancelItem(recipeType));
		inv.setItem(43, getCreateItem());
	}

	public static int getAmountOfItemsMatching(Inventory inv, ItemStack itm) {
		int amnt = 0;
		for(int i = 0; i < inv.getSize(); i++)
			if(inv.getItem(i) != null && inv.getItem(i).isSimilar(itm))
				amnt += inv.getItem(i).getAmount();
		return amnt;
	}
	
}
