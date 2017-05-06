package MoseShipsBukkit.Events.Command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.ShipEvent;
import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;

/**
 * Implemented
 * 
 * @author Mose
 *
 */
public class ShipTrackEvent extends Event implements ShipEvent {

	private ShipsData g_data;
	private Map<Location, BlockState> g_structure;
	private ShipsCause g_cause;

	private static final HandlerList g_handlers = new HandlerList();

	public ShipTrackEvent(ShipsCause cause, ShipsData data) {
		g_data = data;
		g_cause = cause;
		g_structure = new HashMap<Location, BlockState>();
		for (Block block : data.getBasicStructure()) {
			g_structure.put(block.getLocation(), new BlockState(Material.BEDROCK));
		}
	}

	public ShipTrackEvent(ShipsData data, Map<Location, BlockState> map) {
		g_data = data;
		g_structure = map;
	}

	public Map<Location, BlockState> getShowing() {
		return g_structure;
	}

	@Override
	public ShipsData getShip() {
		return g_data;
	}

	@Override
	public HandlerList getHandlers() {
		return g_handlers;
	}

	@Override
	public ShipsCause getCause() {
		return g_cause;
	}

	public static HandlerList getHandlerList() {
		return g_handlers;
	}
}
