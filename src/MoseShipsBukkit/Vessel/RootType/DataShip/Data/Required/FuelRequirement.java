package MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Material;

import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public class FuelRequirement implements RequirementData {

	public static final int REQUIRES_ALL = 1;
	public static final int REQUIRES_ANY = 0;

	Map<BlockState, Integer> g_state = new HashMap<BlockState, Integer>();
	int g_mode = REQUIRES_ANY;

	public Set<BlockState> getStates() {
		return g_state.keySet();
	}

	public FuelRequirement setStates(Map<BlockState, Integer> map) {
		g_state = map;
		return this;
	}

	public int getMode() {
		return g_mode;
	}

	public FuelRequirement setMode(int mode) {
		g_mode = mode;
		return this;
	}

	@Override
	public Optional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks) {
		MovingBlockList fur = blocks.filterBlocks(new BlockState(Material.FURNACE));
		fur.addAll(blocks.filterBlocks(new BlockState(Material.BURNING_FURNACE)));

		return null;
	}

	@Override
	public Map<String, Object> getInfo(LiveShip ship) {
		// TODO Auto-generated method stub
		return null;
	}

}
