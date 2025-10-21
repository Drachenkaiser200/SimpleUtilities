package me.unknown.utilities.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSignOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.unknown.utilities.Main;
import me.unknown.utilities.chestshop.CSType;
import me.unknown.utilities.chestshop.ChestShop;
import me.unknown.utilities.util.BaseUtil;
import me.unknown.utilities.util.BlockUtil;
import me.unknown.utilities.util.InventoryUtil;
import me.unknown.utilities.util.ShopUtil;

public class ChestShopListener implements Listener {
	
	//TODO fix SIGN-COLOR bug - not fixed
	@EventHandler
	public void on(SignChangeEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		double prize = 0;
		int amount = 0;
		Material material = null;
		if (e.getLine(0).equalsIgnoreCase("[sell]") || e.getLine(0).equalsIgnoreCase("[buy]")) {
			if (!p.hasPermission("simpleutilities.chestshop.create")) {
				p.sendMessage(
						Main.get.prefix + "§cYou're missing the permission §esimpleutilities.chestshop.create§c.");
				e.setCancelled(true);
				return;
			}

			if (!(BlockUtil.getBlockBehindSign(b).getState() instanceof Container)) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! There has to be a container block behind the sign (a chest for example)!");
				return;
			}

			if (!BaseUtil.isDouble(e.getLine(1))) {
				p.sendMessage(
						Main.get.prefix + "§cShop creation failed! The prize has to be a double (0.0 for example)!");
				return;
			}

			prize = BaseUtil.getDouble(e.getLine(1));

			if (!BaseUtil.isInt(e.getLine(2))) {
				p.sendMessage(
						Main.get.prefix + "§cShop creation failed! The amount has to be an integer (0 for example)!");
				return;
			}

			amount = BaseUtil.getInt(e.getLine(2));

			if (Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_")) == null) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! The material was not found (GRASS_BLOCK for example)!");
				return;
			}

			if (!Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_")).isItem()) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! The material has to be a holdeable item (GRASS_BLOCK for example)!");
				return;
			}

			material = Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_"));

			ChestShop cs = new ChestShop(p, e.getLine(0).substring(1, e.getLine(0).length() - 1).toUpperCase(), prize,
					amount, material, b);
//
//			Sign s = (Sign) b.getState();
//			s.getSide(Side.FRONT).setColor(cs.isAdminShop() ? DyeColor.YELLOW : DyeColor.ORANGE);
//			s.getSide(Side.BACK).setColor(cs.isAdminShop() ? DyeColor.YELLOW : DyeColor.ORANGE);
//			s.getSide(Side.FRONT).setGlowingText(cs.isAdminShop() ? true : false);
//			s.getSide(Side.BACK).setGlowingText(cs.isAdminShop() ? true : false);
//			s.update();

			Main.get.csManager.registerOrUpdateShop(cs);
			if (!Main.get.csManager.isChestShop(b)) {
				p.sendMessage(Main.get.prefix + "§aSuccessfully created the shop at (X" + b.getX() + " Y" + b.getY()
						+ " Z" + b.getZ() + ")!");
				p.sendMessage(Main.get.prefix + "§aPlace the item you want to sell/buy in the chest!");
			} else {
				p.sendMessage(Main.get.prefix + "§aSuccessfully updated the shop at (X" + b.getX() + " Y" + b.getY()
						+ " Z" + b.getZ() + ")!");
			}
		} else if (e.getLine(0).equalsIgnoreCase("[adminsell]") || e.getLine(0).equalsIgnoreCase("[adminbuy]")) {
			if (!p.hasPermission("simpleutilities.admin.chestshop.create")) {
				p.sendMessage(
						Main.get.prefix + "§cYou're missing the permission §esimpleutilities.admin.chestshop.create§c.");
				e.setCancelled(true);
				return;
			}

			if (!(BlockUtil.getBlockBehindSign(b).getState() instanceof Container)) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! There has to be a container block behind the sign (a chest for example)!");
				return;
			}

			if (!BaseUtil.isDouble(e.getLine(1))) {
				p.sendMessage(
						Main.get.prefix + "§cShop creation failed! The prize has to be a double (0.0 for example)!");
				return;
			}

			prize = BaseUtil.getDouble(e.getLine(1));

			if (!BaseUtil.isInt(e.getLine(2))) {
				p.sendMessage(
						Main.get.prefix + "§cShop creation failed! The amount has to be an integer (0 for example)!");
				return;
			}

			amount = BaseUtil.getInt(e.getLine(2));

			if (Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_")) == null) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! The material was not found (GRASS_BLOCK for example)!");
				return;
			}

			if (!Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_")).isItem()) {
				p.sendMessage(Main.get.prefix
						+ "§cShop creation failed! The material has to be a holdeable item (GRASS_BLOCK for example)!");
				return;
			}

			material = Material.getMaterial(e.getLine(3).toUpperCase().replace(" ", "_"));

			ChestShop cs = new ChestShop(null, e.getLine(0).substring(1, e.getLine(0).length() - 1).toUpperCase(), prize,
					amount, material, b);
//
//			Sign s = (Sign) b.getState();
//			s.getSide(Side.FRONT).setColor(cs.isAdminShop() ? DyeColor.YELLOW : DyeColor.ORANGE);
//			s.getSide(Side.BACK).setColor(cs.isAdminShop() ? DyeColor.YELLOW : DyeColor.ORANGE);
//			s.getSide(Side.FRONT).setGlowingText(cs.isAdminShop() ? true : false);
//			s.getSide(Side.BACK).setGlowingText(cs.isAdminShop() ? true : false);
//			s.update();

			Main.get.csManager.registerOrUpdateShop(cs);
			if (!Main.get.csManager.isChestShop(b)) {
				p.sendMessage(Main.get.prefix + "§aSuccessfully created the admin shop at (X" + b.getX() + " Y" + b.getY()
						+ " Z" + b.getZ() + ")!");
				p.sendMessage(Main.get.prefix + "§aPlace the item you want to be sold/bought in the chest!");
			} else {
				p.sendMessage(Main.get.prefix + "§aSuccessfully updated the admin shop at (X" + b.getX() + " Y" + b.getY()
						+ " Z" + b.getZ() + ")!");
			}
		} else {
			Main.get.csManager.deleteShop(Main.get.csManager.getChestShop(b));
			p.sendMessage(Main.get.prefix + "§aSuccessfully removed your shop!");
		}

	}

	//TODO fix adminshop not opening bug
	//FIXED
	@EventHandler
	public void on(PlayerSignOpenEvent e) {
		Player p = e.getPlayer();
		Block b = e.getSign().getBlock();

		if (Main.get.csManager.isChestShop(b)) {
			ChestShop cs = Main.get.csManager.getChestShop(b);

			if (!cs.isAdminShop() && p.getUniqueId().toString().equals(cs.getCreator().getUniqueId().toString())) {
				return;
			}
			e.setCancelled(true);
			if (cs.getType().replace("ADMIN", "").equals(CSType.BUY)) {
				// Buying from shop

				// Check if shop is out of stock
				if (!cs.isAdminShop() && !cs.getContainer().getInventory().contains(cs.getMaterial(), cs.getAmount())) {
					p.sendMessage(Main.get.prefix + "§cThis shop is out of stock!");
					return;
				}
				// Check if player has the money to buy stuff
				if (ShopUtil.getMoney(p) < cs.getPrize()) {
					p.sendMessage(Main.get.prefix + "§cYou cannot afford this!");
					return;
				}

				// Check if player's inventory is full
				if (p.getInventory().firstEmpty() == -1) {
					p.sendMessage(Main.get.prefix + "§cYour inventory is full!");
					return;
				}
				ShopUtil.decreaseBalance(p, cs.getPrize());
				if (!cs.isAdminShop())
					ShopUtil.increaseBalance(cs.getCreator(), cs.getPrize());
				ShopUtil.transferItemFromShopToPlayer(cs, p);
				p.sendMessage(
						Main.get.prefix + "§aSuccessfully bought " + cs.getMaterial() + " x" + cs.getAmount() + ".");
			}
			if (cs.getType().replace("ADMIN", "").equals(CSType.SELL)) {
				// Selling to shop

				// Check if shop's inventory is full
				if (cs.getContainer().getInventory().firstEmpty() == -1) {
					p.sendMessage(Main.get.prefix + "§cThis shop cannot buy your item right now!");
					return;
				}
				// Check if the shop has the money to buy stuff
				if (!cs.isAdminShop() && ShopUtil.getMoney(cs.getCreator()) < cs.getPrize()) {
					p.sendMessage(Main.get.prefix + "§cThe shop cannot afford this!");
					return;
				}
				ItemStack itm = null;
				if (cs.getContainer().getInventory().first(cs.getMaterial()) == -1) {
					itm = new ItemStack(cs.getMaterial(), cs.getAmount());
				} else {
					itm = cs.getContainer().getInventory()
							.getItem(cs.getContainer().getInventory().first(cs.getMaterial()));
				}
				// Check if player has the item
				if (InventoryUtil.getAmountOfItemsMatching(p.getInventory(), itm) < cs.getAmount()) {
					p.sendMessage(Main.get.prefix + "§cYou don't have the requested item(s)! (" + itm.getType() + " x"
							+ itm.getAmount() + ")");
					return;
				}
				ShopUtil.increaseBalance(p, cs.getPrize());
				if (!cs.isAdminShop())
					ShopUtil.decreaseBalance(cs.getCreator(), cs.getPrize());
				ShopUtil.transferItemFromPlayerToShop(itm, cs, p);
				p.sendMessage(
						Main.get.prefix + "§aSuccessfully sold " + cs.getMaterial() + " x" + cs.getAmount() + ".");
			}

		}
	}

	//TODO -
	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

			if (b == null || p.isSneaking())
				return;

			if (Main.get.csManager.isChestShop(b) && !Main.get.csManager.getChestShop(b).isAdminShop() && !Main.get.csManager.getChestShop(b).getCreator().getUniqueId()
					.toString().equals(p.getUniqueId().toString())) {
				e.setCancelled(true);

				ChestShop cs = Main.get.csManager.getChestShop(b);
				Inventory inv = Bukkit.createInventory(null, cs.getContainer().getInventory().getSize(),
						!cs.isAdminShop() ? ("§0[Shop] of " + cs.getCreator().getName()) : "§0[AdminShop]");
				p.openInventory(inv);
			}
		}

		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			if (b == null)
				return;
			
			if(!Main.get.csManager.isContainerofShop(b)) {
				return;
			}
			
			if(p.hasPermission("simpleutilities.admin.chestshop.open")) {
				return;
			}
			
			if (!Main.get.csManager.getShopofContainer(b).isAdminShop() && Main.get.csManager.getShopofContainer(b).getCreator().getUniqueId().toString().equals(p.getUniqueId().toString())) {
				return;
			}
			e.setCancelled(true);
			p.sendMessage(Main.get.prefix + "§cYou cannot open the shop of another player (Missing permission §esimpleutilities.admin.chestshop.open§c)!");
			return;
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		InventoryView view = e.getView();
		if (view.getTitle().equals("§0[AdminShop]") || view.getTitle().startsWith("§0[Shop] of ")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		if ((Main.get.csManager.isChestShop(b))) {
			if (!p.isSneaking()) {
				p.sendMessage(Main.get.prefix + "§cYou have to shift to destroy a shop!");
				e.setCancelled(true);
				return;
			}
			if (!Main.get.csManager.getChestShop(b).isAdminShop() && !Main.get.csManager.getChestShop(b).getCreator().getUniqueId().toString()
					.equals(p.getUniqueId().toString())) {
				if(p.hasPermission("simpleutilities.admin.chestshop.destroy")) {
					Main.get.csManager.deleteShop(Main.get.csManager.getChestShop(b));
					p.sendMessage(Main.get.prefix + "§aSuccessfully removed the shop!");
					return;
				}
				e.setCancelled(true);
				p.sendMessage(Main.get.prefix + "§cYou need the permission §esimpleutilities.admin.chestshop.destroy§c to destroy another players shop!");
				return;
			} else {
				Main.get.csManager.deleteShop(Main.get.csManager.getChestShop(b));
				p.sendMessage(Main.get.prefix + "§aSuccessfully removed your shop!");
			}
		}
		if (Main.get.csManager.isContainerofShop(b)) {
			if (!p.isSneaking()) {
				p.sendMessage(Main.get.prefix + "§cYou have to shift to destroy a shop!");
				e.setCancelled(true);
				return;
			}
			if (!Main.get.csManager.getShopofContainer(b).isAdminShop() && !Main.get.csManager.getShopofContainer(b).getCreator().getUniqueId().toString()
					.equals(p.getUniqueId().toString())) {
				if(p.hasPermission("simpleutilities.admin.chestshop.destroy")) {
					Main.get.csManager.deleteShop(Main.get.csManager.getShopofContainer(b));
					p.sendMessage(Main.get.prefix + "§aSuccessfully removed the shop!");
					return;
				}
				e.setCancelled(true);
				p.sendMessage(Main.get.prefix + "§cYou need the permission §esimpleutilities.admin.chestshop.destroy§c to destroy another players shop!");
				return;
			} else {
				Main.get.csManager.deleteShop(Main.get.csManager.getShopofContainer(b));
				p.sendMessage(Main.get.prefix + "§aSuccessfully removed your shop!");
			}
		}
	}

	@EventHandler
	public void on(BlockExplodeEvent e) {
		for (int i = 0; i < e.blockList().size(); i++) {
			Block b = e.blockList().get(i);
			if (Main.get.csManager.isChestShop(b) || Main.get.csManager.isContainerofShop(b)) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void on(BlockFromToEvent e) {
		Block b = e.getBlock();
		if (Main.get.csManager.isChestShop(b) || Main.get.csManager.isContainerofShop(b)) {
			e.setCancelled(true);
			return;
		}
	}

}
