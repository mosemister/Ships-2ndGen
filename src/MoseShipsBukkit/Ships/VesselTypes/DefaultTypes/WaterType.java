package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class WaterType extends LoadableShip {
	
	public WaterType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public WaterType(ShipsData data) {
		super(data);
	}

	public int getWaterLevel() {
		BlockFace[] faces = {BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST};
		int height = -1;
		for(Block block : getBasicStructure()){
			if(block.getY() > height){
				for(BlockFace face : faces){
					Block block2 = block.getRelative(face);
					if(block2.getType().equals(Material.STATIONARY_WATER) || (block2.getType().equals(Material.WATER))){
						height = block2.getY();
						break;
					}
				}
			}
		}
		return height;
	}

}
