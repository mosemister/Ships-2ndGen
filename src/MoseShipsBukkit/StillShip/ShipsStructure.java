package MoseShipsBukkit.StillShip;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;

import MoseShipsBukkit.Utils.ConfigLinks.Config;
import MoseShipsBukkit.Utils.MoseUtils.ExpandedList;

public class ShipsStructure {

	List<Block> PRI_BLOCKS = new ArrayList<Block>();
	List<Block> STA_BLOCKS = new ArrayList<Block>();
	List<SpecialBlock> SPE_BLOCKS = new ArrayList<SpecialBlock>();
	List<Block> AIR_BLOCKS = new ArrayList<Block>();

	@SuppressWarnings("deprecation")
	public ShipsStructure(List<Block> blocks) {
		for (Block block : blocks) {
			int id = block.getTypeId();
			SpecialBlock special = SpecialBlock.getSpecialBlock(block);
			if (special != null) {
				SPE_BLOCKS.add(special);
			} else if ((id == 50) || (id == 55) || (id == 51) || (id == 64) || (id == 65) || (id == 68) || (id == 69)
					|| (id == 70) || (id == 71) || (id == 72) || (id == 75) || (id == 76) || (id == 77) || (id == 93)
					|| (id == 94) || (id == 96) || (id == 131) || (id == 132) || (id == 143) || (id == 147)
					|| (id == 148) || (id == 149) || (id == 150) || (id == 167) || (id == 177) || (id == 193)
					|| (id == 194) || (id == 195) || (id == 196) || (id == 197)) {
				if (block.getState() instanceof Sign) {
					SpecialBlock block2 = new SpecialBlock((Sign) block.getState());
					SPE_BLOCKS.add(block2);
				} else {
					PRI_BLOCKS.add(block);
				}
			} else {
				STA_BLOCKS.add(block);
			}
		}
		injectAllInbetweenAir();
	}

	public List<Block> getPriorityBlocks() {
		return PRI_BLOCKS;
	}

	public List<Block> getStandardBlocks() {
		return STA_BLOCKS;
	}

	public List<SpecialBlock> getSpecialBlocks() {
		return SPE_BLOCKS;
	}

	public List<Block> getAirBlocks() {
		return AIR_BLOCKS;
	}

	public void injectAllInbetweenAir() {
		for (Block block : getAllBlocks()) {
			List<Block> blocks = getInbetweenAir(block);
			if (blocks != null) {
				for (Block block2 : blocks) {
					if (block2.getType().equals(Material.AIR)) {
						AIR_BLOCKS.add(block2);
					}
				}
			}
		}
	}

	public List<Block> getInbetweenAir(Block block) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Config.getConfig().getFile());
		List<Block> blocks = new ArrayList<Block>();
		int gap = config.getInt("Structure.StructureLimits.airCheckGap");
		for (int A = 1; A < gap; A++) {
			Block block2 = block.getRelative(0, -A, 0);
			if (contains(getAllBlocks(), block2)) {
				return blocks;
			} else if (block2.getType().equals(Material.AIR)) {
				blocks.add(block2);
			}
		}
		return null;
	}

	public ExpandedList<Block> getAllBlocks() {
		ExpandedList<Block> blocks = new ExpandedList<Block>();
		blocks.addAll(STA_BLOCKS);
		blocks.addAll(PRI_BLOCKS);
		for (SpecialBlock block : SPE_BLOCKS) {
			blocks.add(block.getBlock());
		}
		blocks.addAll(AIR_BLOCKS);
		return blocks;
	}

	private <T extends Object> boolean contains(List<T> list, T object) {
		for (Object obj : list) {
			if (obj.equals(object)) {
				return true;
			}
		}
		return false;
	}
}
