package MoseShipsSponge;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import MoseShipsSponge.Listeners.ShipsListeners;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes.OpShip;

@Plugin(id = ShipsMain.ID, name = ShipsMain.NAME, version = ShipsMain.VERSION)
public class ShipsMain {

	public static final String ID = "ships";
	public static final String NAME = "Ships";
	public static final String VERSION = "6.0.0.0 | Developer version";

	static ShipsMain PLUGIN;

	@Inject
	Game GAME;
	
	@Inject
	PluginContainer CONTAINER;

	@Listener
	public void onEnable(GameStartingServerEvent event) {
		PLUGIN = this;
		new OpShip.StaticOPShip();
		GAME.getEventManager().registerListeners(this, new ShipsListeners());
	}

	@Listener
	public void onDisable(GameStoppingServerEvent event) {

	}

	public Game getGame() {
		return GAME;
	}
	
	public PluginContainer getContainer(){
		return CONTAINER;
	}

	public static Text format(String message, boolean error) {
		if (error) {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.RED).build()).build();
			return ret;
		} else {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static Text format(Text text, boolean error) {
		if (error) {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.RED).build()).build();
			return ret;
		} else {
			Text ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static ShipsMain getPlugin() {
		return PLUGIN;
	}

}
