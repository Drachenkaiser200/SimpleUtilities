package me.unknown.utilities.chestshop;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Container;

import me.unknown.utilities.util.BlockUtil;

public class ChestShop {

	private String type;
	private double prize;
	private int amount;
	private Material material;
	
	private OfflinePlayer creator;
	
	private Block sign;
	
	/**Type can either be "Buy", "Sell"*/
	public ChestShop(OfflinePlayer creator, String type, double prize, int amount, Material material, Block sign) {
		this.creator = creator;
		this.type = type;
		this.prize = prize;
		this.amount = amount;
		this.material = material;
		this.sign = sign;
	}
	
	public boolean isAdminShop() {
		return creator == null;
	}
	
	public OfflinePlayer getCreator() {
		return creator;
	}
	
	public String getType() {
		return type;
	}

	public double getPrize() {
		return prize;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public Block getSign() {
		return sign;
	}
	
	public Container getContainer() {
		return (Container) BlockUtil.getBlockBehindSign(getSign()).getState();
	}
}
