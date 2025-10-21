package me.unknown.utilities.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.unknown.utilities.Main;
import me.unknown.utilities.chestshop.ChestShop;

public class ShopUtil {

	public static double getMoney(OfflinePlayer p) {
		return Main.get.econ.getBalance(p);
	}

	public static void increaseBalance(OfflinePlayer p, double amount) {
		Main.get.econ.depositPlayer(p, amount);
	}
	
	public static void decreaseBalance(OfflinePlayer p, double amount) {
		Main.get.econ.withdrawPlayer(p, amount);
	}

	public static void transferItemFromShopToPlayer(ChestShop cs, Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get, new Runnable() {
			
			@Override
			public void run() {
				List<ItemStack> itms = new ArrayList<>();
				Inventory inv = cs.getContainer().getInventory();
				int totalAmount = cs.getAmount();
				if(cs.isAdminShop() && inv.first(cs.getMaterial()) == -1) {
					itms.add(new ItemStack(cs.getMaterial(), cs.getAmount()));
				}else {
					while(inv.first(cs.getMaterial()) != -1 && totalAmount > 0) {
						int index = inv.first(cs.getMaterial());
						ItemStack itm = inv.getItem(index);
						if(itm.getAmount() < totalAmount) {
							itms.add(new ItemStack(itm));
							totalAmount -= itm.getAmount();
							if(!cs.isAdminShop())
								itm.setAmount(0);
						}else {
							ItemStack itm2 = new ItemStack(itm);
							itm2.setAmount(totalAmount);
							itms.add(itm2);
							if(!cs.isAdminShop())
								itm.setAmount(itm.getAmount() - totalAmount);
							totalAmount = 0;
						}
						if(!cs.isAdminShop()) {
							inv.setItem(index, itm);
						}
					}
				}
				for(int i = 0; i < itms.size(); i++)
					p.getInventory().addItem(itms.get(i));
			}
		});
	}

	public static void transferItemFromPlayerToShop(ItemStack itm, ChestShop cs, Player p) {
		if(!cs.isAdminShop()) {
			ItemStack itm2 = new ItemStack(itm);
			itm2.setAmount(cs.getAmount());
			cs.getContainer().getInventory().addItem(itm2);
		}
		int totalAmount = cs.getAmount();
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			if(p.getInventory().getItem(i) != null && p.getInventory().getItem(i).isSimilar(itm)) {
				if(p.getInventory().getItem(i).getAmount() < totalAmount) {
					totalAmount -= p.getInventory().getItem(i).getAmount();
					p.getInventory().setItem(i, new ItemStack(Material.AIR));
				}else {
					ItemStack itm3 = new ItemStack(p.getInventory().getItem(i));
					itm3.setAmount(p.getInventory().getItem(i).getAmount() - totalAmount);
					p.getInventory().setItem(i, itm3);
					totalAmount = 0;
				}
			}
		}
	}
	
}
