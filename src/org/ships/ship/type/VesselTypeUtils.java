package org.ships.ship.type;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.ships.block.MovingBlock;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.ship.Ship;

public class VesselTypeUtils {
	public boolean isMaterialTouchingMovingTo(Collection<MovingBlock> blocks, Collection<Material> material, boolean includeHeight) {
		if (material != null) {
			for (MovingBlock block : blocks) {
				for (int X = -1; X < 2; ++X) {
					for (int Z = -1; Z < 2; ++Z) {
						if (includeHeight) {
							for (int Y = -1; Y < 2; ++Y) {
								Block block2 = block.getMovingTo().getBlock().getRelative(X, Y, Z);
								if (!material.contains(block2.getType()))
									continue;
								Bukkit.getConsoleSender().sendMessage("Block: " + block2.getType());
								return true;
							}
							continue;
						}
						Block block2 = block.getMovingTo().getBlock().getRelative(X, 0, Z);
						if (!material.contains(block2.getType()))
							continue;
						Bukkit.getConsoleSender().sendMessage("Block: " + block2.getType());
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isMaterialInMovingTo(Collection<MovingBlock> blocks, Collection<Material> material) {
		return blocks.stream().anyMatch(b -> material.contains(b.getMovingTo().getBlock().getType()));
	}

	public boolean isMovingInto(Collection<MovingBlock> blocks, Collection<Material> ignoreList) {
		if (ignoreList == null) {
			return false;
		}
		return blocks.stream().anyMatch(b -> ignoreList.contains(b.getMovingTo().getBlock().getType()));
	}

	public boolean checkFuel(Ship vessel, int take, Collection<Material> fuels) {
		if (fuels.isEmpty()) {
			return true;
		}
		int count = 0;
		for (BlockHandler<? extends BlockState> block : vessel.getStructure().getBlocks(Material.FURNACE)) {
			Furnace bBlock = (Furnace) block.getBlock().getState();
			FurnaceInventory inv = bBlock.getInventory();
			ItemStack item = inv.getFuel();
			boolean check = false;
			if (item == null)
				continue;
			for (Material fuel : fuels) {
				if (!item.getType().equals(fuel))
					continue;
				check = true;
				break;
			}
			if (!check)
				continue;
			count = item.getAmount() - take >= 0 ? (count += take) : (count += item.getAmount());
			if (count < take)
				continue;
			return true;
		}
		return false;
	}

	public int getTotalAmountOfFuel(Ship vessel, Collection<Material> fuels) {
		if (fuels.isEmpty()) {
			return 0;
		}
		int count = 0;
		for (BlockHandler<? extends BlockState> block : vessel.getStructure().getBlocks(Material.FURNACE)) {
			Furnace bBlock = (Furnace) block.getBlock().getState();
			FurnaceInventory inv = bBlock.getInventory();
			ItemStack item = inv.getFuel();
			boolean check = false;
			if (item != null) {
				for (Material fuel : fuels) {
					if (!item.getType().equals(fuel))
						continue;
					check = true;
					break;
				}
			}
			if (!check)
				continue;
			count += item.getAmount();
		}
		return count;
	}

	public boolean takeFuel(Ship vessel, int take, Collection<Material> fuels) {
		if (fuels.isEmpty()) {
			return false;
		}
		int count = 0;
		while (count != take) {
			for (BlockHandler<? extends BlockState> block : vessel.getStructure().getBlocks(Material.FURNACE)) {
				Furnace bBlock = (Furnace) block.getBlock().getState();
				FurnaceInventory inv = bBlock.getInventory();
				ItemStack item = inv.getFuel();
				boolean check = false;
				for (Material fuel : fuels) {
					if (!item.getType().equals(fuel))
						continue;
					check = true;
					break;
				}
				if (!check)
					continue;
				if (item.getAmount() - take > 0) {
					count += take;
					item.setAmount(item.getAmount() - take);
					continue;
				}
				count += item.getAmount();
				inv.remove(item);
			}
			if (count != take)
				continue;
			return true;
		}
		return false;
	}

	public boolean isMaterialInMovingFrom(Collection<MovingBlock> blocks, Material material) {
		return blocks.stream().anyMatch(e -> e.getBlock().getType().equals(material));
	}

	public boolean isPercentInMovingFrom(Collection<MovingBlock> blocks, Collection<Material> checkForBlocks, float minPercent) {
		if (checkForBlocks != null) {
			float count = 0.0f;
			for (MovingBlock block : blocks) {
				if (!checkForBlocks.contains(block.getBlock().getType()))
					continue;
				count += 1.0f;
			}
			float percentAmount = blocks.size() * (minPercent / 100.0f);
			if (count >= percentAmount) {
				return true;
			}
			return false;
		}
		return true;
	}

	public float getOffByPercent(Collection<MovingBlock> blocks, Collection<Material> material, float minPercent) {
		if (material != null) {
			float count = 0.0f;
			for (MovingBlock block : blocks) {
				if (!material.contains(block.getBlock().getType()))
					continue;
				count += 1.0f;
			}
			float percentAmount = blocks.size() * (count / 100.0f);
			if (percentAmount >= minPercent) {
				return 0.0f;
			}
			return 100.0f - percentAmount;
		}
		return 0.0f;
	}
}
