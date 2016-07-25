package MoseShipsSponge.Utils;

import java.util.Collection;

import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class LocationUtils {
	
	public static boolean blocksEqual(Location<? extends Extent> loc1, Location<? extends Extent> loc2){
		if((loc1.getBlockX() == loc2.getBlockX()) && (loc1.getBlockY() == loc2.getBlockY()) && (loc1.getBlockZ() == loc2.getBlockZ())){
			return true;
		}
		return false;
	}
	
	public static boolean blockWorldContains(Collection<Location<World>> list, Location<? extends Extent> loc){
		return list.stream().anyMatch(l -> blocksEqual(l, loc));
	}
	
	public static boolean blockChunkContains(Collection<Location<Chunk>> list, Location<? extends Extent> loc){
		return list.stream().anyMatch(l -> blocksEqual(l, loc));
	}

}
