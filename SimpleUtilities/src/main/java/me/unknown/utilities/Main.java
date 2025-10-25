package me.unknown.utilities;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import me.unknown.utilities.chestshop.CSManager;
import me.unknown.utilities.cmd.Command_SU;
import me.unknown.utilities.listener.ChestShopListener;
import me.unknown.utilities.listener.InteractListener;
import me.unknown.utilities.listener.InventoryListener;
import me.unknown.utilities.recipe.RecipeManager;
import me.unknown.utilities.recipe.RecipeType;
import me.unknown.utilities.util.UpdateChecker;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static Main get;
	public String prefix;
	public NamespacedKey cmdKey;
	public NamespacedKey cmdVerifyKey;
	public RecipeManager recipeManager;
	public CSManager csManager;
	public Economy econ = null;
	private UpdateChecker uc;
	
	@Override
	public void onLoad() {
		
	}
	
	
	@Override
	public void onEnable() {
		
		this.uc = new UpdateChecker(this, "Drachenkaiser200/SimpleUtilities");
		
		uc.checkForUpdates();
		
		get = this;
		prefix = "§a[§2SimpleUtilities§a] §a";
		cmdVerifyKey = new NamespacedKey(Main.get, "UTILITIES_COMMANDITEM_VERIFY");
		cmdKey = new NamespacedKey(Main.get, "UTILITIES_COMMANDITEM_COMMAND");
		RecipeType.load();
		
		if (!setupEconomy().equals("true")) {
            getLogger().severe(setupEconomy());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		recipeManager = new RecipeManager();
		recipeManager.registerRecipes();
		
		csManager = new CSManager();

		getCommand("simpleutilities").setExecutor(new Command_SU());
		
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new InteractListener(), get);
		pm.registerEvents(new InventoryListener(), get);
		pm.registerEvents(new ChestShopListener(), get);
	}

	@Override
	public void onDisable() {
		File pluginFile = uc.getPluginFile();
		PluginManager pm = Bukkit.getPluginManager();
		try {
			pm.loadPlugin(pluginFile);
		} catch (UnknownDependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pm.enablePlugin(pm.getPlugin(getDescription().getName()));
	}
	
	private String setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return "The plugin \"Vault\" was not found, please check your plugins folder";
        
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        
        if (rsp == null)
            return "The registration net.milkbowl.vault.economy.Economy wasn't found despite \"Vault\" being installed, it seems "
            		+ "you're missing a corresponding Economy-Plugin like \"EssentialsX\".";
            
        econ = rsp.getProvider();
        
        return "true";
    }
	
}
