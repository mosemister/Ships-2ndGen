package MoseShipsBukkit.Ships.Movement.MovingBlock.Block;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class BlockSnapshot {

	Location g_loc;
	Material g_material;
	MaterialData g_data;

	public static final Map<Material, Class<? extends BlockSnapshot>> VALUE_TYPES = new HashMap<Material, Class<? extends BlockSnapshot>>();

	protected BlockSnapshot(BlockState state) {
		g_loc = state.getLocation();
		g_material = state.getType();
		g_data = state.getData();
	}

	public Location getLocation() {
		return g_loc;
	}

	public Material getMaterial() {
		return g_material;
	}

	public MaterialData getData() {
		return g_data;
	}

	public void placeBlock() {
		placeBlock(g_loc.getBlock());
	}

	@SuppressWarnings("deprecation")
	public void placeBlock(Block loc) {
		placeBlock(loc, g_data.getData());
	}

	public void placeBlock(Block loc, byte data) {
		placeBlock(loc, g_material, data);
	}

	@SuppressWarnings("deprecation")
	public void placeBlock(Block loc, Material material, byte data) {
		if (this instanceof SpecialSnapshot) {
			SpecialSnapshot spec = (SpecialSnapshot) this;
			spec.onRemove(loc);
		}
		loc.setType(material, false);
		if (material.equals(g_material)) {
			g_data.setData(data);
			loc.getState().setData(g_data);
			if (this instanceof SpecialSnapshot) {
				SpecialSnapshot spec = (SpecialSnapshot) this;
				spec.onPlace(loc);
			}
		} else {
			loc.setData(data, false);
		}
	}

	public static BlockSnapshot createSnapshot(Block block) {
		return createSnapshot(block.getState());
	}

	public static BlockSnapshot createSnapshot(BlockState state) {
		for (Entry<Material, Class<? extends BlockSnapshot>> entry : VALUE_TYPES.entrySet()) {
			if (entry.getKey().equals(state.getType())) {
				try {
					BlockSnapshot snapshot = entry.getValue().getConstructor(state.getClass()).newInstance(state);
					return snapshot;
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return new BlockSnapshot(state);
	}

}
