package me.unknown.utilities.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.unknown.utilities.Main;
import me.unknown.utilities.recipe.RecipeType;
import me.unknown.utilities.util.InventoryUtil;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		InventoryView view = e.getView();
		Player p = (Player) e.getWhoClicked();
		if(view.getTitle().startsWith(("§0§lCustom-Recipe-Editor" + '\n' + p.getUniqueId().toString()) + '\n')) {
			
			ItemStack itm = e.getCurrentItem();
			
			if(itm == null)
				return;
			
			String type = view.getTopInventory().getItem(41).getItemMeta().getDisplayName().replace("§3§lRecipeType: ", "");
			String key = view.getTitle().replace(("§0§lCustom-Recipe-Editor" + '\n' + p.getUniqueId().toString()) + '\n', "");
			ItemStack result = view.getTopInventory().getItem(25);
			
			
			if(view.getTopInventory().getItem(41).isSimilar(itm)) {
				e.setCancelled(true);
				InventoryUtil.updateRecipeInventory(e.getInventory(), key, RecipeType.getNextRecipeType(type));
			}
			
			if(itm.isSimilar(InventoryUtil.getCreateItem())) {
				e.setCancelled(true);
				if(Main.get.recipeManager.getRecipe(key) != null) {
					Main.get.recipeManager.deleteRecipe(key);
				}
				if(type.equalsIgnoreCase(RecipeType.BLASTING) || type.equalsIgnoreCase(RecipeType.CAMPFIRE) || type.equalsIgnoreCase(RecipeType.FURNACE) ||
						type.equalsIgnoreCase(RecipeType.SMOKING)) {
					Main.get.recipeManager.createCookingRecipe(type, key, result, view.getTopInventory().getItem(12), 0.35F, 200);
				}
				if(type.equalsIgnoreCase(RecipeType.MERCHANT)) {
					p.sendMessage(Main.get.prefix + "§cThis type of recipe is not supported yet!");
//					Main.get.recipeManager.createMerchantRecipe(key, result, 10, view.getTopInventory().getItem(19), view.getTopInventory().getItem(21));
					return;
				}
				if(type.equalsIgnoreCase(RecipeType.SHAPED) || type.equalsIgnoreCase(RecipeType.SHAPELESS)) {

					ItemStack[] ingredients = new ItemStack[9];
					ingredients[0] = view.getTopInventory().getItem(10);
					ingredients[1] = view.getTopInventory().getItem(11);
					ingredients[2] = view.getTopInventory().getItem(12);
					ingredients[3] = view.getTopInventory().getItem(19);
					ingredients[4] = view.getTopInventory().getItem(20);
					ingredients[5] = view.getTopInventory().getItem(21);
					ingredients[6] = view.getTopInventory().getItem(28);
					ingredients[7] = view.getTopInventory().getItem(29);
					ingredients[8] = view.getTopInventory().getItem(30);
					Main.get.recipeManager.createCraftingRecipe(type, key, result, ingredients);
				}
				if(type.equalsIgnoreCase(RecipeType.SMITHING_TRIM) || type.equalsIgnoreCase(RecipeType.SMITHING_TRANSFORM)) {
					Main.get.recipeManager.createSmithinRecipe(type, key, result, view.getTopInventory().getItem(19), view.getTopInventory().getItem(20), view.getTopInventory().getItem(21));
				}
				if(type.equalsIgnoreCase(RecipeType.STONECUTTER)) {
					Main.get.recipeManager.createStoneCutterRecipe(key, result, view.getTopInventory().getItem(20));
				}
				Main.get.recipeManager.recipesYML.set(key + ".loaded", true);
				Main.get.recipeManager.recipesYML.set(key + ".type", type);
				p.closeInventory();
				p.sendMessage(Main.get.prefix + "§aSuccessfully created/edited the recipe §e" + key + "§a!");
			}
			
			for(int i = 0; i < InventoryUtil.INVENTORY_ITEMS.length; i++)
				if(itm.isSimilar(InventoryUtil.INVENTORY_ITEMS[i]))
					e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		InventoryView view = e.getView();
		Player p = (Player) e.getPlayer();
		if(view.getTitle().startsWith("§0§lCustom-Recipe-Editor" + '\n' + p.getUniqueId().toString())) {
			for(int i = 0; i < inv.getContents().length; i++) {
				ItemStack itm = inv.getContents()[i];
				
				if(itm == null)
					continue;
				
				boolean bool = false;
				
				for(String str : RecipeType.RECIPE_TYPES.keySet())
					if(InventoryUtil.getRecipeTypeItem(str).isSimilar(itm))
						bool = true;
				
				for(int j = 0; j < InventoryUtil.INVENTORY_ITEMS.length; j++)
					if(itm.isSimilar(InventoryUtil.INVENTORY_ITEMS[j]))
						bool = true;
				
				if(!itm.isSimilar(InventoryUtil.getCreateItem()) && !itm.isSimilar(view.getTopInventory().getItem(41)) && !bool)
					p.getWorld().dropItem(p.getLocation(), itm);
			}
		}
	}
	
}
