package MoseShipsBukkit.Events.Vessel.Create.Fail;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.Create.ShipsCreateEvent;
import MoseShipsBukkit.Ships.ShipsData;

public abstract class ShipsCreateFailedEvent extends ShipsCreateEvent{
	
	String g_message;
	String g_original_message;
	boolean g_display_message = true;
	Player g_player;
	
	public ShipsCreateFailedEvent(ShipsData data, Player player, String message) {
		super(data);
		g_message = message;
		g_original_message = message;
		g_player = player;
		
	}
	
	public String getMessage(){
		return g_message;
	}
	
	public String getOriginalMessage(){
		return g_original_message;
	}
	
	public Player getPlayer(){
		return g_player;
	}
	
	public boolean shouldMessageDisplay(){
		return g_display_message;
	}
	
	public ShipsCreateFailedEvent setMessage(String message){
		g_message = message;
		return this;
	}
	
	public ShipsCreateFailedEvent setMessageWillDisplay(boolean check){
		g_display_message = check;
		return this;
	}
	
	public static HandlerList getHandlerList(){
		return g_handlers;
	}

}
