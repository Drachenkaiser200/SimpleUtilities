package me.unknown.utilities.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.unknown.utilities.Main;
import me.unknown.utilities.util.ItemUtil;

public class InteractListener implements Listener {
	
	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action act = e.getAction();
		ItemStack itm = e.getItem();
		
		if(act.equals(Action.RIGHT_CLICK_AIR) || act.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(ItemUtil.isCommandItem(itm)) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ItemUtil.getCommandFromCommandItem(itm));
				itm.setAmount(itm.getAmount()-1);
				p.sendMessage(Main.get.prefix + "§aSuccessfully executed the command!");
				e.setCancelled(true);
			}
		}
	}
	
}
