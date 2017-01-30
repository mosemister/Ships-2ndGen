package MoseShipsBukkit.ShipBlock;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class BlockState {

	Material MATERIAL;
	byte DATA;

	public BlockState(Material material, byte data) {
		MATERIAL = material;
		DATA = data;
	}

	@SuppressWarnings("deprecation")
	public BlockState(Block block) {
		MATERIAL = block.getType();
		DATA = block.getData();
	}

	public BlockState(Material material) {
		MATERIAL = material;
		DATA = (byte) -1;
	}

	public Material getMaterial() {
		return MATERIAL;
	}

	public byte getData() {
		return DATA;
	}

	@SuppressWarnings("deprecation")
	public String toNoString() {
		return MATERIAL.getId() + ":" + DATA;
	}

	public boolean looseMatch(BlockState state) {
		if ((state.getMaterial().equals(MATERIAL))
				&& ((DATA == state.getData()) || (DATA == -1) || (state.getData() == -1))) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean looseMatch(Block block) {
		if ((block.getType().equals(MATERIAL))
				&& ((DATA == block.getData()) || (DATA == -1) || (block.getData() == -1))) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean looseMatch(ItemStack item) {
		if (item != null) {
			if ((item.getType().equals(MATERIAL))
					&& ((DATA == item.getData().getData()) || (DATA == -1) || (item.getData().getData() == -1))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BlockState) {
			BlockState state = (BlockState) obj;
			if ((state.getMaterial().equals(getMaterial())) && (state.getData() == getData())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return MATERIAL.name() + ":" + DATA;
	}

	@SuppressWarnings("deprecation")
	public static boolean contains(Block block, BlockState... list) {
		for (BlockState state : list) {
			if (state.getMaterial().equals(block.getType())) {
				if (state.getData() == -1) {
					return true;
				} else if (state.getData() == block.getData()) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static BlockState[] getStates(List<String> states) {
		BlockState[] states2 = new BlockState[states.size()];
		for (int A = 0; A < states.size(); A++) {
			String value = states.get(A);
			String[] args = value.split(":");
			byte data = Byte.parseByte(args[1]);
			Material material = Material.getMaterial(Integer.parseInt(args[0]));

			states2[A] = new BlockState(material, data);
		}
		return states2;
	}

}
