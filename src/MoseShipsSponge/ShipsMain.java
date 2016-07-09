package MoseShipsSponge;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;

import MoseShipsSponge.Listeners.ShipsListeners;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.OpShip;

@Plugin(id=ShipsMain.ID, name=ShipsMain.NAME, version=ShipsMain.VERSION)
public class ShipsMain {
	
	public static final String ID = "ships";
	public static final String NAME = "Ships";
	public static final String VERSION = "6.0.0.0 | Developer version";
	
	static ShipsMain PLUGIN;
	
	@Inject
	Game GAME;
	
	@Listener
	public void onEnable(GameStartingServerEvent event){
		PLUGIN = this;
		new OpShip.StaticOPShip();
		GAME.getEventManager().registerListeners(this, new ShipsListeners());
	}
	
	@Listener
	public void onDisable(GameStoppingServerEvent event){
		
	}
	
	public Game getGame(){
		return GAME;
	}
	
	public static Vector3i convert(Direction direction, int speed){
		switch(direction){
		case DOWN:
			return new Vector3i(0, -speed, 0);
		case EAST:
			break;
		case EAST_NORTHEAST:
			break;
		case EAST_SOUTHEAST:
			break;
		case NONE:
			break;
		case NORTH:
			break;
		case NORTHEAST:
			break;
		case NORTHWEST:
			break;
		case NORTH_NORTHEAST:
			break;
		case NORTH_NORTHWEST:
			break;
		case SOUTH:
			break;
		case SOUTHEAST:
			break;
		case SOUTHWEST:
			break;
		case SOUTH_SOUTHEAST:
			break;
		case SOUTH_SOUTHWEST:
			break;
		case UP:
			return new Vector3i(0, speed, 0);
		case WEST:
			break;
		case WEST_NORTHWEST:
			break;
		case WEST_SOUTHWEST:
			break;
		}
		return new Vector3i(0, 0, 0);
	}
	
	public static Text format(String message, boolean error){
		if (error) {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.RED).build()).build();
			return ret;
		} else {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}
	
	public static Text format(Text text, boolean error){
		if (error) {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.RED).build()).build();
			return ret;
		} else {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}
	
	public static ShipsMain getPlugin(){
		return PLUGIN;
	}
	

}
