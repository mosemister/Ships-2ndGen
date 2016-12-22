package MoseShipsBukkit.Events.Vessel.Command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Utils.State.BlockState;

public class ShipTrackEvent extends Event implements ShipsEvent {

	private ShipsData g_data;
	private Map<Location, BlockState> g_structure;
	
	private static final HandlerList g_handlers = new HandlerList();
	
	public ShipTrackEvent(ShipsData data){
		g_data = data;
		g_structure = new HashMap<>();
		data.getBasicStructure().stream().forEach(b -> {
			g_structure.put(b.getLocation(), new BlockState(Material.BEDROCK));
		});
	}
	
	public ShipTrackEvent(ShipsData data, Map<Location, BlockState> map){
		g_data = data;
		g_structure = map;
	}
	
	public Map<Location, BlockState> getShowing(){
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
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}
}
