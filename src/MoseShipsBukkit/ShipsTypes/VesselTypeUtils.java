package MoseShipsBukkit.ShipsTypes;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel.BaseVessel;

public class VesselTypeUtils {

	// checks if the new location is touching a list of materials
	public boolean isMaterialTouchingMovingTo(List<MovingBlock> blocks, List<Material> material,
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
	public boolean isMaterialInMovingTo(List<MovingBlock> blocks, List<Material> material) {
		for (MovingBlock block : blocks) {
			if (material.contains(block.getMovingTo().getBlock().getType())) {
				return true;
			}
		}
		return false;
	}

	// checks if the vessel is moving into anything but the ignorelist
	public boolean isMovingInto(List<MovingBlock> blocks, List<Material> ignoreList) {
		if (ignoreList != null) {
			for (MovingBlock block : blocks) {
				if (!ignoreList.contains(block.getMovingTo().getBlock().getType())) {

					return true;
				}
			}
		}
		return false;
	}

	// checks if the vessel has enough fuel to be taken
	@SuppressWarnings("deprecation")
	public boolean checkFuel(Map<Material, Byte> fuel, BaseVessel vessel, int take) {
		if (fuel != null) {
			int count = 0;
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				if (block.getBlock().getState() instanceof Furnace) {
					Furnace bBlock = (Furnace) block.getBlock().getState();
					FurnaceInventory inv = bBlock.getInventory();
					ItemStack item = inv.getFuel();
					for (Entry<Material, Byte> entry : fuel.entrySet()) {
						if (item != null) {
							if (entry.getKey().equals(item.getType())) {
								if (entry.getValue() != -1) {
									if (entry.getValue() == item.getData().getData()) {
										if (item.getAmount() - take >= 0) {
											count = (count + take);
										} else {
											count = count + item.getAmount();
										}
									}
								} else {
									if (item.getAmount() - take >= 0) {
										count = (count + take);
									} else {
										count = count + item.getAmount();
									}
								}
							}
						}
					}
				}
				if (count >= take) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public int getWholeNumberFromSign(String line1, int lineReadNo, BaseVessel vessel, int take)
			throws NumberFormatException {
		int count = 0;
		for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
			if (block.getBlock().getState() instanceof Sign) {
				Sign sign = (Sign) block.getBlock().getState();
				if (sign.getLine(0).equals(line1)) {
					int value = Integer.parseInt(sign.getLine(lineReadNo));
					if (value - take >= 0) {
						count = (count + take);
					}
				}
			}
		}
		return count;
	}

	public double getDoubleNumberFromSign(String line1, int lineReadNo, BaseVessel vessel, int take)
			throws NumberFormatException {
		double count = 0;
		for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
			if (block.getBlock().getState() instanceof Sign) {
				Sign sign = (Sign) block.getBlock().getState();
				if (sign.getLine(0).equals(line1)) {
					double value = Double.parseDouble(sign.getLine(lineReadNo));
					if (value - take >= 0) {
						count = (count + take);
					}
				}
			}
		}
		return count;
	}

	@SuppressWarnings("deprecation")
	public int getTotalAmountOfFuel(Map<Material, Byte> fuel, BaseVessel vessel) {
		if (fuel != null) {
			int count = 0;
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				if (block.getBlock().getState() instanceof Furnace) {
					Furnace bBlock = (Furnace) block.getBlock().getState();
					FurnaceInventory inv = bBlock.getInventory();
					ItemStack item = inv.getFuel();
					for (Entry<Material, Byte> entry : fuel.entrySet()) {
						if (item != null) {
							if (entry.getKey().equals(item.getType())) {
								if (entry.getValue() != -1) {
									if (entry.getValue() == item.getData().getData()) {
										count = (count + item.getAmount());
									}
								} else {
									count = (count + item.getAmount());
								}
							}
						}
					}
				}
			}
			return count;
		}
		return 0;
	}

	public int getTotalAmountWholeOnSign(String line1, int readline, BaseVessel vessel) throws NumberFormatException {
		int count = 0;
		for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
			if (block.getBlock().getState() instanceof Sign) {
				Sign sign = (Sign) block.getBlock().getState();
				if (sign.getLine(0).equals(line1)) {
					int value = Integer.parseInt(sign.getLine(readline));
					count = (count + value);
				}
			}
		}
		return count;
	}

	public double getTotalAmountDoubleOnSign(String line1, int readline, BaseVessel vessel)
			throws NumberFormatException {
		double count = 0;
		for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
			if (block.getBlock().getState() instanceof Sign) {
				Sign sign = (Sign) block.getBlock().getState();
				if (sign.getLine(0).equals(line1)) {
					double value = Double.parseDouble(sign.getLine(readline));
					count = (count + value);
				}
			}
		}
		return count;
	}

	// does the same as above only takes the fuel away instead of checking it
	@SuppressWarnings("deprecation")
	public boolean takeFuel(Map<Material, Byte> fuel, BaseVessel vessel, int take) {
		if (fuel != null) {
			int count = 0;
			while (count != take) {
				for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
					if (block.getBlock().getState() instanceof Furnace) {
						Furnace bBlock = (Furnace) block.getBlock().getState();
						FurnaceInventory inv = bBlock.getInventory();
						ItemStack item = inv.getFuel();
						for (Entry<Material, Byte> entry : fuel.entrySet()) {
							if (item != null) {
								if (entry.getKey().equals(item.getType())) {
									if (entry.getValue() != -1) {
										if (entry.getValue() == item.getData().getData()) {
											if (item.getAmount() - take > 0) {
												count = (count + take);
												item.setAmount(item.getAmount() - take);
											} else {
												count = count + item.getAmount();
												Map<Integer, ItemStack> map = block.getItems();
												map.remove(1);
												map.put(1, null);
											}
										}
									} else {
										if (item.getAmount() - take > 0) {
											count = (count + take);
											item.setAmount(item.getAmount() - take);
										} else {
											count = count + item.getAmount();
											Map<Integer, ItemStack> map = block.getItems();
											map.remove(1);
											map.put(1, null);
										}
									}
								}
								if (count == take) {
									return true;
								} else if (count < take) {
									int differnce = take - count;
									item.setAmount(differnce);
									return true;
								}
							}
						}
					}
				}
			}
			if (count == take) {
				return true;
			}
			return false;
		}
		return true;
	}

	public boolean takeWholeNumberFromSign(String line1, int A, BaseVessel vessel, int take)
			throws NumberFormatException {
		int count = 0;
		while (count != take) {
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				if (block.getBlock().getState() instanceof Sign) {
					Sign sign = (Sign) block.getBlock().getState();
					if (sign.getLine(0).equals(line1)) {
						int value = Integer.parseInt(sign.getLine(A));
						if (value - take > 0) {
							count = (count + take);
							sign.setLine(A, value - take + "");
							sign.update();
							return true;
						}
					}
				}
			}
		}
		if (count >= take) {
			return true;
		}
		return false;
	}

	public boolean takeDoubleNumberFromSign(String line1, int A, BaseVessel vessel, int take)
			throws NumberFormatException {
		double count = 0;
		while (count != take) {
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				if (block.getBlock().getState() instanceof Sign) {
					Sign sign = (Sign) block.getBlock().getState();
					if (sign.getLine(0).equals(line1)) {
						double value = Double.parseDouble(sign.getLine(A));
						if (value - take > 0) {
							count = (count + take);
							sign.setLine(A, value - take + "");
							sign.update();
							return true;
						}
					}
				}
			}
		}
		if (count >= take) {
			return true;
		}
		return false;
	}

	public boolean addWholeNumberFromSign(String line1, int A, BaseVessel vessel, int add, int max)
			throws NumberFormatException {
		int count = 0;
		Bukkit.getConsoleSender()
				.sendMessage(Ships.runShipsMessage("Count: " + count + " Add: " + add + " Max: " + max, false));
		while (count != add) {
			Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("count != " + add, false));
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("special block found", false));
				if (block.getBlock().getState() instanceof Sign) {
					Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("block instanceof sign", false));
					Sign sign = (Sign) block.getBlock().getState();
					if (sign.getLine(0).equals(line1)) {
						Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("line 1 is correct", false));
						int value = Integer.parseInt(sign.getLine(A));
						if (value + add <= max) {
							Bukkit.getConsoleSender().sendMessage(Ships.runShipsMessage("value != max" + add, false));
							count = (count + add);
							sign.setLine(A, value + add + "");
							sign.update();
							return true;
						}
					}
				}
			}
		}
		if (count >= add) {
			return true;
		}
		return false;
	}

	public boolean addDoubleNumberFromSign(String line1, int A, BaseVessel vessel, int add, int max)
			throws NumberFormatException {
		double count = 0;
		while (count != add) {
			for (SpecialBlock block : vessel.getStructure().getSpecialBlocks()) {
				if (block.getBlock().getState() instanceof Sign) {
					Sign sign = (Sign) block.getBlock().getState();
					if (sign.getLine(0).equals(line1)) {
						double value = Double.parseDouble(sign.getLine(A));
						if (value + add <= max) {
							count = (count + add);
							sign.setLine(A, value + add + "");
							sign.update();
							return true;
						}
					}
				}
			}
		}
		if (count >= add) {
			return true;
		}
		return false;
	}

	// checks vessels current state and checks if the selected material is in
	// that state
	public boolean isMaterialInMovingFrom(List<MovingBlock> blocks, Material material) {
		for (MovingBlock block : blocks) {
			if (block.getBlock().getType().equals(material)) {
				return true;
			}
		}
		return false;
	}

	// checks vessels current state, then out of that state it grabs the
	// selected materials, then finds the percentage of those selected blocks
	// compared to the full list of blocks. returns true if the result is over
	// the minPercent
	public boolean isPercentInMovingFrom(List<MovingBlock> blocks, List<Material> checkForBlocks, float minPercent) {
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
	public float getOffByPercent(List<MovingBlock> blocks, List<Material> material, float minPercent) {
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
