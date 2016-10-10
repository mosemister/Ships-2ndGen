package MoseShipsBukkit.Events.Vessel.Command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Utils.State.BlockState;

public class ShipTrackEvent extends ShipsEvent {

	Map<Location, BlockState> g_structure = new HashMap<Location, BlockState>();

	public ShipTrackEvent(LoadableShip ship) {
		super(ship);
		for (Block block : ship.getBasicStructure()) {
			g_structure.put(block.getLocation(), new BlockState(Material.BEDROCK));
		}
	}

	public Map<Location, BlockState> getShowing() {
		return g_structure;
	}

}
