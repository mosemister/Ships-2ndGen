package MoseShipsSponge.Event.Create.Fail;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Event.Create.ShipsCreateEvent;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;

public class ShipCreateFailedEvent extends ShipsCreateEvent{

	Text g_message;
	Text g_original_message;
	boolean g_display_message = true;
	Player g_player;
	
	public ShipCreateFailedEvent(ShipsData ship, Player player, Text message, Cause cause) {
		super(ship, cause);
		g_original_message = message;
		g_message = message;
		g_player = player;
	}
	
	public Text getMessage(){
		return g_message;
	}
	
	public Text getOrginalMessage(){
		return g_original_message;
	}
	
	public Player getPlayer(){
		return g_player;
	}
	
	public boolean shouldMessageDisplay(){
		return g_display_message;
	}
	
	public ShipCreateFailedEvent setMessage(Text message) {
		g_message = message;
		return this;
	}

	public ShipCreateFailedEvent setMessageWillDisplay(boolean check) {
		g_display_message = check;
		return this;
}

}
