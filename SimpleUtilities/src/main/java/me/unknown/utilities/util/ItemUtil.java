package me.unknown.utilities.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.unknown.utilities.Main;
import me.unknown.utilities.exception.AlreadyACommandItemException;
import me.unknown.utilities.exception.NoCommandItemException;

public class ItemUtil {
	
	public static boolean isCommandItem(ItemStack itm) {
		if(itm == null || itm.getType().equals(Material.AIR))
			return false;
		if(!itm.hasItemMeta())
			return false;
		if(!itm.getItemMeta().getPersistentDataContainer().has(Main.get.cmdVerifyKey, PersistentDataType.BOOLEAN))
			return false;
		if(!itm.getItemMeta().getPersistentDataContainer().has(Main.get.cmdKey, PersistentDataType.STRING))
			return false;
		return true;
	}
	
	public static String getCommandFromCommandItem(ItemStack itm) {
		if(!isCommandItem(itm))
			return null;
		return itm.getItemMeta().getPersistentDataContainer().get(Main.get.cmdKey, PersistentDataType.STRING);
	}
	
	public static ItemStack createCommandItem(ItemStack itm, String command, boolean override) throws AlreadyACommandItemException {
		if(isCommandItem(itm) && !override) {
			throw new AlreadyACommandItemException();
		}
		ItemStack cmdItm = itm;
		ItemMeta meta = cmdItm.getItemMeta();
		meta.getPersistentDataContainer().set(Main.get.cmdKey, PersistentDataType.STRING, command);
		meta.getPersistentDataContainer().set(Main.get.cmdVerifyKey, PersistentDataType.BOOLEAN, true);
		List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
		lore.add("§7Imbetted command: /" + command);
		meta.setLore(lore);
		cmdItm.setItemMeta(meta);
		return cmdItm;
	}
	
	public ItemStack removeCommandFromItem(ItemStack itm) throws NoCommandItemException {
		if(!isCommandItem(itm)) {
			throw new NoCommandItemException();
		}
		ItemStack cmdItm = itm;
		ItemMeta meta = cmdItm.getItemMeta();
		meta.getPersistentDataContainer().remove(Main.get.cmdKey);
		meta.getPersistentDataContainer().remove(Main.get.cmdVerifyKey);
		if(meta.hasLore() && !meta.getLore().isEmpty()) {
			List<String> lore = meta.getLore();
			for(int i = 0; i < lore.size(); i++) {
				if(lore.get(i).startsWith("§7Imbetted command: /"))
					lore.remove(i);
			}
			meta.setLore(lore);
		}
		cmdItm.setItemMeta(meta);
		return cmdItm;
	}
	
}
