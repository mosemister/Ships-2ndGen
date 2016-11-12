package MoseShipsBukkit.Events.Vessel.Create;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

public abstract class ShipsCreateFailedEvent extends ShipsEvent {

	protected String g_message;
	protected boolean g_display_message = true;
	protected Player g_player;
	
	public ShipsCreateFailedEvent(ShipsData ship, String message, Player player) {
		super(ship);
		g_message = message;
		g_player = player;
	}
	
	public ShipsCreateFailedEvent setMessage(String message){
		message = g_message;
		return this;
	}
	
	public String getMessage(){
		return g_message;
	}
	
	public ShipsCreateFailedEvent setMessageDisplay(boolean display){
		g_display_message = display;
		return this;
	}
	
	public boolean willMessageDisplay(){
		return g_display_message;
	}
	
	public Player getPlayer(){
		return g_player;
	}

	public static HandlerList getHandlerList() {
		return HANDLER;
	}

	public static class ConflictName extends ShipsCreateFailedEvent {

		protected LoadableShip g_conflict;

		public ConflictName(ShipsData ship, Player player, LoadableShip conflict) {
			super(ship, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_NAME), player);
		}

		public LoadableShip getConflict() {
			return g_conflict;
		}

		public String getName() {
			return g_conflict.getName();
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

	public static class ConflictType extends ShipsCreateFailedEvent {

		protected String g_type;

		public ConflictType(ShipsData ship, Player player, String type) {
			super(ship, ShipsConfig.CONFIG.get(String.class, ShipsConfig.PATH_MESSAGE_SIGN_CREATE_FAILED_TYPE), player);
			g_type = type;
		}

		public String getAttemptedType() {
			return g_type;
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

}
