package me.unknown.utilities.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

public class BlockUtil {

	
	public static Block getBlockBehindSign(Block signBlock) {
        if (signBlock == null) return null;

        Material type = signBlock.getType();
        
        if (!type.name().endsWith("_SIGN")) {
            return null;
        }

        BlockData data = signBlock.getBlockData();
        BlockFace behindFace;

        if (data instanceof Rotatable rotatable) {
            behindFace = rotatable.getRotation().getOppositeFace();
        } else if (data instanceof Directional directional) {
            behindFace = directional.getFacing().getOppositeFace();
        } else {
            return null;
        }
//        Bukkit.broadcastMessage(signBlock.getRelative(behindFace, 1).getType().name() + " " + signBlock.getRelative(behindFace, 1).getX() + " " + signBlock.getRelative(behindFace, 1).getY() +
//        		" " + signBlock.getRelative(behindFace, 1).getZ());
        return signBlock.getRelative(behindFace, 1);
    }

	public static boolean isSign(Block b) {
		return b.getType().name().endsWith("_SIGN");
	}

	public static boolean isContainer(Block b) {
		return b.getState() instanceof Container;
	}
}
