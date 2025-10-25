package me.unknown.utilities.chestshop;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import me.unknown.utilities.util.BlockUtil;
import me.unknown.utilities.util.Config;

public class CSManager {
	
	public Config chestShopYML;
	
	public CSManager() {
		this.chestShopYML = new Config("chestshop.yml");
	}

	public void registerOrUpdateShop(ChestShop cs) {
		
		String n = (cs.isAdminShop() ? "Admin" : cs.getCreator().getUniqueId().toString())
				+ "_X=" + cs.getSign().getX()
				+ "_Y=" + cs.getSign().getY()
				+ "_Z=" + cs.getSign().getZ()
				+ "_World=" + cs.getSign().getWorld().getName();
		
		this.chestShopYML.set(n + ".type", cs.getType().toUpperCase().replace("admin", ""));
		this.chestShopYML.set(n + ".prize", cs.getPrize());
		this.chestShopYML.set(n + ".amount", cs.getAmount());
		this.chestShopYML.set(n + ".material", cs.getMaterial().name());
		this.chestShopYML.saveConfig();
		
	}
	
	public void deleteShop(ChestShop cs) {
		
		String n = (cs.isAdminShop() ? "Admin" : cs.getCreator().getUniqueId().toString())
				+ "_X=" + cs.getSign().getX()
				+ "_Y=" + cs.getSign().getY()
				+ "_Z=" + cs.getSign().getZ()
				+ "_World=" + cs.getSign().getWorld().getName();
		this.chestShopYML.set(n, null);
		this.chestShopYML.saveConfig();
	}
	
	public ChestShop getChestShopFromYML(String key) {
		Block sign = Bukkit.getWorld(key.split("_")[4].substring(6)).getBlockAt(Integer.parseInt(key.split("_")[1].substring(2)),
				Integer.parseInt(key.split("_")[2].substring(2)), Integer.parseInt(key.split("_")[3].substring(2)));
		return new ChestShop(key.split("_")[0].equalsIgnoreCase("Admin") ? null : Bukkit.getOfflinePlayer(UUID.fromString(key.split("_")[0])),
				chestShopYML.getString(key + ".type"),
				chestShopYML.getDouble(key + ".prize"),
				chestShopYML.getInt(key + ".amount"),
				Material.getMaterial(chestShopYML.getString(key + ".material")), sign);
	}

	public boolean isChestShop(Block b) {
		if(b == null || !BlockUtil.isSign(b) || chestShopYML.getKeys(false).isEmpty())
			return false;
		for(String s : chestShopYML.getKeys(false)) {
			if(b.getWorld().getName().equalsIgnoreCase(s.split("_")[4].substring(6)) && b.getX() == Integer.parseInt(s.split("_")[1].substring(2))
					&& b.getY() == Integer.parseInt(s.split("_")[2].substring(2)) && b.getZ() == Integer.parseInt(s.split("_")[3].substring(2)))
				return true;
		}
		return false;
	}

	public ChestShop getChestShop(Block b) {
		if(!BlockUtil.isSign(b) || chestShopYML.getKeys(false).isEmpty())
			return null;
		for(String s : chestShopYML.getKeys(false)) {
			if(b.getWorld().getName().equalsIgnoreCase(s.split("_")[4].substring(6)) && b.getX() == Integer.parseInt(s.split("_")[1].substring(2))
					&& b.getY() == Integer.parseInt(s.split("_")[2].substring(2)) && b.getZ() == Integer.parseInt(s.split("_")[3].substring(2)))
				return getChestShopFromYML(s);
		}
		return null;
	}

	public boolean isContainerofShop(Block b) {
		if(b == null || !BlockUtil.isContainer(b) || chestShopYML.getKeys(false).isEmpty())
			return false;
		for(String s : chestShopYML.getKeys(false)) {
			Block b2 = BlockUtil.getBlockBehindSign(Bukkit.getWorld(s.split("_")[4].substring(6)).getBlockAt(Integer.parseInt(s.split("_")[1].substring(2)),
					Integer.parseInt(s.split("_")[2].substring(2)), Integer.parseInt(s.split("_")[3].substring(2))));
			if(b2.getWorld().getName().equalsIgnoreCase(b.getWorld().getName()) && b2.getX() == b.getX() & b2.getY() == b.getY() && b2.getZ() == b.getZ())
				return true;
		}
		return false;
	}

	public ChestShop getShopofContainer(Block b) {
		if(!BlockUtil.isContainer(b) || chestShopYML.getKeys(false).isEmpty())
			return null;
		for(String s : chestShopYML.getKeys(false)) {
			Block b2 = BlockUtil.getBlockBehindSign(Bukkit.getWorld(s.split("_")[4].substring(6)).getBlockAt(Integer.parseInt(s.split("_")[1].substring(2)),
					Integer.parseInt(s.split("_")[2].substring(2)), Integer.parseInt(s.split("_")[3].substring(2))));
			if(b2.getWorld().getName().equalsIgnoreCase(b.getWorld().getName()) && b2.getX() == b.getX() & b2.getY() == b.getY() && b2.getZ() == b.getZ())
				return getChestShopFromYML(s);
		}
		return null;
	}
	
}
