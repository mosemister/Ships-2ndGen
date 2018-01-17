package MoseShipsBukkit.ShipsTypes;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.ships.block.blockhandler.BlockHandler;

import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;

public class VesselTypeUtils {

	// checks if the new location is touching a list of materials
	public boolean isMaterialTouchingMovingTo(Collection<MovingBlock> blocks, Collection<Material> material,
			boolean includeHeight) {
		if (material != null) {
			for (MovingBlock block : blocks) {
				for (int X = -1; X < 2; X++) {
					for (int Z = -1; Z < 2; Z++) {
						if (includeHeight) {
							for (int Y = -1; Y < 2; Y++) {
								Block block2 = block.getMovingTo().getBlock().getRelative(X, Y, Z);
								if (material.contains(block2.getType())) {
									Bukkit.getConsoleSender().sendMessage("Block: " + block2.getType());
									return true;
								}
							}
						} else {
							Block block2 = block.getMovingTo().getBlock().getRelative(X, 0, Z);
							if (material.contains(block2.getType())) {
								Bukkit.getConsoleSender().sendMessage("Block: " + block2.getType());
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	// checks if the vessel is going to be moving into a select few blocks,
	// returns true if vessel is moving into at least 1 material value
	public boolean isMaterialInMovingTo(Collection<MovingBlock> blocks, Collection<Material> material) {
		return blocks.stream().anyMatch(b -> material.contains(b.getMovingTo().getBlock().getType()));
	}

	// checks if the vessel is moving into anything but the ignorelist
	public boolean isMovingInto(Collection<MovingBlock> blocks, Collection<Material> ignoreList) {
		if(ignoreList == null) {
			return false;
		}
		return blocks.stream().anyMatch(b -> ignoreList.contains(b.getMovingTo().getBlock().getType()));
	}

	// checks if the vessel has enough fuel to be taken
	public boolean checkFuel(BaseVessel vessel, int take, Collection<Material> fuels) {
		if(fuels.isEmpty()) {
			return true;
		}
		int count = 0;
		for (BlockHandler block : vessel.getStructure().getBlocks(Material.FURNACE)) {
			Furnace bBlock = (Furnace) block.getBlock().getState();
			FurnaceInventory inv = bBlock.getInventory();
			ItemStack item = inv.getFuel();
			boolean check = false;
			if (item == null) {
				continue;
			}
			for(Material fuel : fuels) {
			if(item.getType().equals(fuel)) {
				check = true;
				break;
			}
			}
			if(!check) {
				continue;
			}
			if(item.getAmount() - take >= 0) {
				count = (count + take);
			} else {
				count = count + item.getAmount();
			}
			if (count >= take) {
				return true;
			}
		}
		return false;
	}

	public int getTotalAmountOfFuel(BaseVessel vessel, Collection<Material> fuels) {
		if(fuels.isEmpty()) {
			return 0;
		}
		int count = 0;
		for (BlockHandler block : vessel.getStructure().getBlocks(Material.FURNACE)) {
			Furnace bBlock = (Furnace) block.getBlock().getState();
			FurnaceInventory inv = bBlock.getInventory();
			ItemStack item = inv.getFuel();
			boolean check = false;
			for(Material fuel : fuels) {
			if(item.getType().equals(fuel)) {
				check = true;
				break;
			}
			}
			if(!check) {
				continue;
			}
			count = (count + item.getAmount());
		}
		return count;
	}

	// does the same as above only takes the fuel away instead of checking it
	public boolean takeFuel(BaseVessel vessel, int take, Collection<Material> fuels) {
		if(fuels.isEmpty()) {
			return false;
		}
		int count = 0;
		while(count != take) {
			for (BlockHandler block : vessel.getStructure().getBlocks(Material.FURNACE)) {
				Furnace bBlock = (Furnace)block.getBlock().getState();
				FurnaceInventory inv = bBlock.getInventory();
				ItemStack item = inv.getFuel();
				boolean check = false;
				for(Material fuel : fuels) {
				if(item.getType().equals(fuel)) {
					check = true;
					break;
				}
				}
				if(!check) {
					continue;
				}
				if (item.getAmount() - take > 0) {
					count = (count + take);
					item.setAmount(item.getAmount() - take);
				} else {
					count = count + item.getAmount();
					inv.remove(item);
				}
			}
			if (count == take) {
				return true;
			}
		}
		return false;
	}

	// checks vessels current state and checks if the selected material is in
	// that state
	public boolean isMaterialInMovingFrom(Collection<MovingBlock> blocks, Material material) {
		return blocks.stream().anyMatch(e -> e.getBlock().getType().equals(material));
	}

	// checks vessels current state, then out of that state it grabs the
	// selected materials, then finds the percentage of those selected blocks
	// compared to the full list of blocks. returns true if the result is over
	// the minPercent
	public boolean isPercentInMovingFrom(Collection<MovingBlock> blocks, Collection<Material> checkForBlocks, float minPercent) {
		if (checkForBlocks != null) {
			float count = 0;
			for (MovingBlock block : blocks) {
				if (checkForBlocks.contains(block.getBlock().getType())) {
					count++;
				}
			}
			float percentAmount = (blocks.size() * (minPercent / 100f));
			if (count >= percentAmount) {
				return true;
			}
			return false;
		}
		return true;
	}

	// returns the percent you are off by.
	public float getOffByPercent(Collection<MovingBlock> blocks, Collection<Material> material, float minPercent) {
		if (material != null) {
			float count = 0;
			for (MovingBlock block : blocks) {
				if (material.contains(block.getBlock().getType())) {
					count++;
				}
			}
			float percentAmount = (blocks.size() * (count / 100f));
			if (percentAmount >= minPercent) {
				return 0;
			}
			return 100 - percentAmount;
		}
		return 0;
	}

}
