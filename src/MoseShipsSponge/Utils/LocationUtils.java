package MoseShipsSponge.Utils;

import java.util.Collection;

import org.bukkit.Location;

public class LocationUtils {

	public static boolean blocksEqual(Location loc1, Location loc2) {
		if ((loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY()) && (loc1.getBlockZ() == loc2.getBlockZ())) {
			return true;
		}
		return false;
	}

	public static boolean blockContains(Collection<Location> list, Location loc) {
		for(Location loc2 : list){
			if(blocksEqual(loc, loc2)){
				return true;
			}
		}
		return false;
	}

}
