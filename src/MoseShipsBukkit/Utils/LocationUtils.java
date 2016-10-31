package MoseShipsBukkit.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.material.Attachable;

public class LocationUtils {

	public static boolean blocksEqual(Location loc1, Location loc2) {
		if ((loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY())
				&& (loc1.getBlockZ() == loc2.getBlockZ())) {
			return true;
		}
		return false;
	}

	public static boolean blockContains(Collection<Location> list, Location loc) {
		for (Location loc2 : list) {
			if (blocksEqual(loc, loc2)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Sign> getAttachedSigns(Block block){
		List<Sign> list = new ArrayList<Sign>();
		BlockFace[] faces = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
		for(BlockFace face : faces){
			Block block2 = block.getRelative(face);
			if(block2.getState() instanceof Sign){
				Sign sign = (Sign)block2.getState();
				Attachable attach = (Attachable)sign.getData();
				if(attach.getAttachedFace().getOppositeFace().equals(face)){
					list.add(sign);
				}
			}
		}
		return list;
	}

}
