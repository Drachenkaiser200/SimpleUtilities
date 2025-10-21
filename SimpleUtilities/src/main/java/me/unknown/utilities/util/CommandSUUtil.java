package me.unknown.utilities.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.unknown.utilities.Main;
import me.unknown.utilities.exception.AlreadyACommandItemException;

public class CommandSUUtil {

	public static final Map<Integer, List<String>> TAB_COMPLETES = new HashMap<>();

	public static void executeItemSetCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("item") || !args[1].equalsIgnoreCase("setcommand"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities item setcommand [override] [command]");
		}
		if (args.length == 3) {

			if (!BaseUtil.isBoolean(args[2])) {
				sender.sendMessage(Main.get.prefix + "§e\"Override\" §ccan only be §e\"true\"§c or §e\"false\"§c.");
				return;
			}

			sender.sendMessage(Main.get.prefix + "§c/simpleutilities item setCommand " + args[2] + " [command]");
		}
		if (args.length >= 4) {
			if (!BaseUtil.isBoolean(args[2])) {
				sender.sendMessage(Main.get.prefix + "§e\"Override\" §ccan only be set to §e\"true\"§c or §e\"false\"§c.");
				return;
			}

			Player p = (Player) sender;
			if (p.getInventory().getItemInMainHand() == null
					|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				sender.sendMessage(Main.get.prefix + "§cYou need to hold an §eitem§c.");
				return;
			}

			String command = "";

			for (int i = 3; i < args.length; i++)
				command += args[i] + " ";

			try {
				p.getInventory().setItemInMainHand(ItemUtil.createCommandItem(p.getInventory().getItemInMainHand(),
						command, Boolean.valueOf(args[1])));
				p.sendMessage(Main.get.prefix + "§aSuccessfully imbetted the following command in the item:");
				p.sendMessage("§8Command: /" + command);
			} catch (AlreadyACommandItemException e) {
				p.sendMessage(e.getMessage());
			}
		}
	}

	public static void executeItemSetName(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("item") || !args[1].equalsIgnoreCase("setname"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities item setname [name]");
		}
		if (args.length >= 3) {

			Player p = (Player) sender;

			String name = "";

			for (int i = 2; i < args.length; i++)
				name += args[i] + " ";

			if (p.getInventory().getItemInMainHand() == null
					|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				sender.sendMessage(Main.get.prefix + "§cYou need to hold an §eitem§c.");
				return;
			}
			ItemStack itm = p.getInventory().getItemInMainHand();
			ItemMeta meta = itm.getItemMeta();
			meta.setDisplayName(name.replace("&", "§"));
			itm.setItemMeta(meta);
			p.getInventory().setItemInMainHand(itm);
			p.sendMessage(Main.get.prefix + "§aSuccessfully renamed the item!");
		}
	}

	public static void executeItemSetLore(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("item") || !args[1].equalsIgnoreCase("setlore"))
			return;
		if (args.length == 2) {
			sender.sendMessage(
					Main.get.prefix + "§c/simpleutilities item setlore [lore]");
		}
		if (args.length >= 3) {

			Player p = (Player) sender;

			String lore = "";

			for (int i = 2; i < args.length; i++)
				lore += args[i] + " ";

			if (p.getInventory().getItemInMainHand() == null
					|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				sender.sendMessage(Main.get.prefix + "§cYou need to hold an §eitem§c.");
				return;
			}
			ItemStack itm = p.getInventory().getItemInMainHand();
			ItemMeta meta = itm.getItemMeta();
			meta.setLore(Arrays.asList(lore.replace("&", "§")));
			itm.setItemMeta(meta);
			p.getInventory().setItemInMainHand(itm);
			p.sendMessage(Main.get.prefix + "§aSuccessfully set the lore of the item!");
		}
	}

	public static void executeItemAddLore(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("item") || !args[1].equalsIgnoreCase("addlore"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities item addlore [lore]");
		}
		if (args.length >= 3) {

			Player p = (Player) sender;

			String loreline = "";

			for (int i = 2; i < args.length; i++)
				loreline += args[i] + " ";

			if (p.getInventory().getItemInMainHand() == null
					|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				sender.sendMessage(Main.get.prefix + "§cYou need to hold an §eitem§c.");
				return;
			}
			ItemStack itm = p.getInventory().getItemInMainHand();
			ItemMeta meta = itm.getItemMeta();
			List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
			lore.add(loreline.replace("&", "§"));
			meta.setLore(lore);
			itm.setItemMeta(meta);
			p.getInventory().setItemInMainHand(itm);
			p.sendMessage(Main.get.prefix + "§aSuccessfully added lore to the item!");
		}
	}

	public static void executeItemSetGlow(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("item") || !args[1].equalsIgnoreCase("setglow"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities item setglow [true/false]");
		}
		if (args.length >= 3) {

			if (!BaseUtil.isBoolean(args[2])) {
				sender.sendMessage(Main.get.prefix + "§e\"Glow\" §ccan only be set to §e\"true\"§c or §e\"false\"§c.");
				return;
			}
			
			Bukkit.broadcastMessage("" + BaseUtil.isBoolean(args[2]));
			
			Player p = (Player) sender;

			if (p.getInventory().getItemInMainHand() == null
					|| p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				sender.sendMessage(Main.get.prefix + "§cYou need to hold an §eitem§c.");
				return;
			}
			ItemStack itm = p.getInventory().getItemInMainHand();
			ItemMeta meta = itm.getItemMeta();
			meta.setEnchantmentGlintOverride(BaseUtil.getBoolean(args[2]));
			itm.setItemMeta(meta);
			p.getInventory().setItemInMainHand(itm);
			p.sendMessage(Main.get.prefix + "§aSuccessfully set the enchantment glint of the item!");
		}

	}

	public static void executeRecipeCreateCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("recipe") || !args[1].equalsIgnoreCase("create"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe create [name]");
		}
		if (args.length >= 3) {
			if (Main.get.recipeManager.getRecipe(args[2].toLowerCase()) != null) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe already exists! Use /simpleutilities recipe edit "
						+ args[2].toLowerCase() + " to edit the existing recipe!");
				return;
			}
			Player p = (Player) sender;
			InventoryUtil.openRecipeInventory(p, args[2].toLowerCase());
		}
	}

	public static void executeRecipeEditCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Main.get.prefix + "§cYou have to be a §eplayer§c.");
			return;
		}
		if (!args[0].equalsIgnoreCase("recipe") || !args[1].equalsIgnoreCase("edit"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe edit [name]");
		}
		if (args.length >= 3) {
			if (Main.get.recipeManager.getRecipe(args[2].toLowerCase()) == null) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe doesn't exists! Use /simpleutilities recipe create "
						+ args[2].toLowerCase() + " to create a new recipe!");
				return;
			}
			Player p = (Player) sender;
			InventoryUtil.openRecipeInventory(p, args[2].toLowerCase());
		}
	}

	public static void executeRecipeDeleteCommand(CommandSender sender, String[] args) {
		if (!args[0].equalsIgnoreCase("recipe") || !args[1].equalsIgnoreCase("delete"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe delete [name]");
		}
		if (args.length >= 3) {
			if (Main.get.recipeManager.getRecipe(args[2].toLowerCase()) == null) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe doesn't exists! Use /simpleutilities recipe create "
						+ args[2].toLowerCase() + " to create a new recipe!");
				return;
			}
			Main.get.recipeManager.deleteRecipe(args[2].toLowerCase());
			sender.sendMessage(
					Main.get.prefix + "§aSuccessfully deleted the recipe§e " + args[2].toLowerCase() + "§a!");
		}
	}

	public static void executeRecipeLoadCommand(CommandSender sender, String[] args) {
		if (!args[0].equalsIgnoreCase("recipe") || !args[1].equalsIgnoreCase("load"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe load [name]");
		}
		if (args.length >= 3) {
			if (Main.get.recipeManager.getRecipe(args[2].toLowerCase()) == null) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe doesn't exists! Use /simpleutilities recipe create "
						+ args[2].toLowerCase() + " to create a new recipe!");
				return;
			}
			if (Main.get.recipeManager.isRecipeLoaded(args[2].toLowerCase())) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe is already loaded!");
				return;
			}
			Main.get.recipeManager.loadRecipe(args[2].toLowerCase());
			sender.sendMessage(Main.get.prefix + "§aSuccessfully loaded the recipe§e " + args[2].toLowerCase() + "§a!");
		}
	}

	public static void executeRecipeUnloadCommand(CommandSender sender, String[] args) {
		if (!args[0].equalsIgnoreCase("recipe") || !args[1].equalsIgnoreCase("unload"))
			return;
		if (args.length == 2) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe unload [name]");
		}
		if (args.length >= 3) {
			if (Main.get.recipeManager.getRecipe(args[2].toLowerCase()) == null) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe doesn't exists! Use /simpleutilities recipe create "
						+ args[2].toLowerCase() + " to create a new recipe!");
				return;
			}
			if (!Main.get.recipeManager.isRecipeLoaded(args[2].toLowerCase())) {
				sender.sendMessage(Main.get.prefix + "§cThis recipe is already unloaded!");
				return;
			}
			Main.get.recipeManager.unloadRecipe(args[2].toLowerCase());
			sender.sendMessage(
					Main.get.prefix + "§aSuccessfully unloaded the recipe§e " + args[2].toLowerCase() + "§a!");
		}
	}

	public static void setTabCompletes(CommandSender sender, String[] args) {
		TAB_COMPLETES.clear();
		if (args.length == 1)
			addTabCompletesForArg(1, "item", "recipe");
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("item")) {
				addTabCompletesForArg(2, "setcommand", "setname", "setlore", "addlore", "setglow");
				addTabCompletesForArg(3, "true", "false");
			}
			if (args[0].equalsIgnoreCase("recipe")) {
				addTabCompletesForArg(2, "create", "edit", "delete", "load", "unload");
				if (args[1].equalsIgnoreCase("edit") || args[1].equalsIgnoreCase("delete")) {
					addTabCompletesForArg(3, Main.get.recipeManager.recipesYML.getKeys(false));
				}
				if (args[1].equalsIgnoreCase("load")) {
					List<String> s = new ArrayList<>();
					for (String str : Main.get.recipeManager.recipesYML.getKeys(false))
						if (!Main.get.recipeManager.recipesYML.getBoolean(str + ".loaded"))
							s.add(str);
					TAB_COMPLETES.put(3, s);
				}
				if (args[1].equalsIgnoreCase("unload")) {
					List<String> s = new ArrayList<>();
					for (String str : Main.get.recipeManager.recipesYML.getKeys(false))
						if (Main.get.recipeManager.recipesYML.getBoolean(str + ".loaded"))
							s.add(str);
					TAB_COMPLETES.put(3, s);
				}
			}
		}
	}

	private static void addTabCompletesForArg(int arg, String... s) {
		List<String> l = new ArrayList<>();
		for (int i = 0; i < s.length; i++)
			l.add(s[i]);
		TAB_COMPLETES.put(arg, l);
	}

	private static void addTabCompletesForArg(int arg, Set<String> s) {
		List<String> l = new ArrayList<>();
		for (int i = 0; i < s.stream().toList().size(); i++)
			l.add(s.stream().toList().get(i));
		TAB_COMPLETES.put(arg, l);
	}

}
