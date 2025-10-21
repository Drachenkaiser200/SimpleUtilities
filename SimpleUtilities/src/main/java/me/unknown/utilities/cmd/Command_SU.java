package me.unknown.utilities.cmd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.unknown.utilities.Main;
import me.unknown.utilities.util.CommandSUUtil;

public class Command_SU implements CommandExecutor, TabCompleter {
	
	// /su item setcommand [override] [command]
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("simpleutilities.command")) {
			sender.sendMessage(Main.get.prefix + "§cYou're missing the permission §esimpleutilities.command§c.");
			return true;
		}
		if(args.length == 0) {
			sender.sendMessage(Main.get.prefix + "§c/simpleutilities [item/recipe] [...]");
			return true;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("item")) {
				sender.sendMessage(Main.get.prefix + "§c/simpleutilities item [setcommand/setname/setlore/addlore/setglow] [...]");
			}else
			if(args[0].equalsIgnoreCase("recipe")) {
				sender.sendMessage(Main.get.prefix + "§c/simpleutilities recipe [create/edit/delete/load/unload] [...]");
			}else
				sender.sendMessage(Main.get.prefix + "§cInvalid argument! Use §e\"item\" §cor §e\"recipe\"§c!");
		}
		if(args.length >= 2) {
			if(args[0].equalsIgnoreCase("item")) {
				if(args[1].equalsIgnoreCase("setcommand")) {
					CommandSUUtil.executeItemSetCommand(sender, args);
				}
				if(args[1].equalsIgnoreCase("setname")) {
					CommandSUUtil.executeItemSetName(sender, args);
				}
				if(args[1].equalsIgnoreCase("setlore")) {
					CommandSUUtil.executeItemSetLore(sender, args);
				}
				if(args[1].equalsIgnoreCase("addlore")) {
					CommandSUUtil.executeItemAddLore(sender, args);
				}
				if(args[1].equalsIgnoreCase("setglow")) {
					CommandSUUtil.executeItemSetGlow(sender, args);
				}
			}else
			if(args[0].equalsIgnoreCase("recipe")) {
				if(args[1].equalsIgnoreCase("create")) {
					CommandSUUtil.executeRecipeCreateCommand(sender, args);
				}
				if(args[1].equalsIgnoreCase("edit")) {
					CommandSUUtil.executeRecipeEditCommand(sender, args);
				}
				if(args[1].equalsIgnoreCase("delete")) {
					CommandSUUtil.executeRecipeDeleteCommand(sender, args);
				}
				if(args[1].equalsIgnoreCase("load")) {
					CommandSUUtil.executeRecipeLoadCommand(sender, args);
				}
				if(args[1].equalsIgnoreCase("unload")) {
					CommandSUUtil.executeRecipeUnloadCommand(sender, args);
				}
			}else
				sender.sendMessage(Main.get.prefix + "§cInvalid argument! Use §e\"item\" §cor §e\"recipe\"§c!");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		CommandSUUtil.setTabCompletes(sender, args);
		if(CommandSUUtil.TAB_COMPLETES.get(args.length) == null)
			return null;
		if(args.length > 0) {
			if(args[args.length-1] != null) {
				List<String> f = new ArrayList<>();
				for(int i = 0; i < CommandSUUtil.TAB_COMPLETES.get(args.length).size(); i++)
					if(CommandSUUtil.TAB_COMPLETES.get(args.length).get(i).toLowerCase().startsWith(args[args.length-1].toLowerCase()))
						f.add(CommandSUUtil.TAB_COMPLETES.get(args.length).get(i));
				return f;
			}
		}
		return CommandSUUtil.TAB_COMPLETES.get(args.length);
	}

}
