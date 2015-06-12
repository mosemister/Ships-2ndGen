package MoseShipsBukkit.StillShip;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;

public class ShipsStructure {
	
	List<Block> PRI_BLOCKS = new ArrayList<Block>();
	List<Block> STA_BLOCKS = new ArrayList<Block>();
	List<SpecialBlock> SPE_BLOCKS = new ArrayList<SpecialBlock>();
	
	@SuppressWarnings("deprecation")
	public ShipsStructure(List<Block> blocks){
		for(Block block : blocks){
			int id = block.getTypeId();
			if ((id == 50) || 
					(id == 55) || 
					(id == 51) || 
					(id == 64) || 
					(id == 65) || 
					(id == 68) || 
					(id == 69) || 
					(id == 70) || 
					(id == 71) || 
					(id == 72) || 
					(id == 75) || 
					(id == 76) || 
					(id == 77) || 
					(id == 93) || 
					(id == 94) || 
					(id == 96) || 
					(id == 131) || 
					(id == 132) || 
					(id == 143) || 
					(id == 147) || 
					(id == 148) || 
					(id == 149) || 
					(id == 150) || 
					(id == 167) || 
					(id == 177) || 
					(id == 193) || 
					(id == 194) || 
					(id == 195) || 
					(id == 196) || 
					(id == 197)){
				if (block.getState() instanceof Sign){
					SpecialBlock block2 = new SpecialBlock((Sign)block.getState());
					SPE_BLOCKS.add(block2);
				}
				PRI_BLOCKS.add(block);
			}
			else if ((id == 23) ||
					(id == 54) || 
					(id == 61) || 
					(id == 62) || 
					(id == 146) || 
					(id == 154) || 
					(id == 158)){ 
				if (block.getState() instanceof Chest){
					Chest chest = (Chest)block.getState();
					if (chest.getInventory().getHolder() instanceof DoubleChest){
						DoubleChest dChest = (DoubleChest)chest.getInventory().getHolder();
						Chest lChest = (Chest)dChest.getLeftSide();
						if (lChest.equals(chest)){
							SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), false);
							SPE_BLOCKS.add(block2);
						}else{
							SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), true);
							SPE_BLOCKS.add(block2);
						}
					}else{
						SpecialBlock block2 = new SpecialBlock((Chest)block.getState(), false);
						SPE_BLOCKS.add(block2);
					}
				}else if (block.getState() instanceof Furnace){
					SpecialBlock block2 = new SpecialBlock((Furnace)block.getState());
					SPE_BLOCKS.add(block2);
				}else if (block.getState() instanceof Sign){
					SpecialBlock block2 = new SpecialBlock((Sign)block.getState());
					SPE_BLOCKS.add(block2);
				}else if (block.getState() instanceof Dropper){
					SpecialBlock block2 = new SpecialBlock((Dropper)block.getState());
					SPE_BLOCKS.add(block2);
				}else if (block.getState() instanceof Hopper){
					SpecialBlock block2 = new SpecialBlock((Hopper)block.getState());
					SPE_BLOCKS.add(block2);
				}else if (block.getState() instanceof Dispenser){
					SpecialBlock block2 = new SpecialBlock((Dispenser)block.getState());
					SPE_BLOCKS.add(block2);
				}
			}else{
				STA_BLOCKS.add(block);
			}
		}
	}

	public List<Block> getPriorityBlocks(){
		return PRI_BLOCKS;
	}
	
	public List<Block> getStandardBlocks(){
		return STA_BLOCKS;
	}
	
	public List<SpecialBlock> getSpecialBlocks(){
		return SPE_BLOCKS;
	}
	
	public List<Block> getAllBlocks(){
		List<Block> blocks = new ArrayList<Block>();
		blocks.addAll(STA_BLOCKS);
		blocks.addAll(PRI_BLOCKS);
		for(SpecialBlock block : SPE_BLOCKS){
			blocks.add(block.getBlock());
		}
		return blocks;
	}
}
