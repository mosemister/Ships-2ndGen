package MoseShipsSponge;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import MoseShipsSponge.CMD.ShipsCMD;
import MoseShipsSponge.CMD.Commands.DebugCMD;
import MoseShipsSponge.CMD.Commands.InfoCMD;
import MoseShipsSponge.Listeners.ShipsListeners;
import MoseShipsSponge.Ships.VesselTypes.DefaultTypes.AirTypes.OpShip;

@Plugin(id = ShipsMain.ID, name = ShipsMain.NAME, version = ShipsMain.VERSION)
public class ShipsMain {

	public static final String ID = "ships";
	public static final String NAME = "Ships";
	public static final String VERSION = "6.0.0.0 | Developer version";
	public static final String TESTED_API = "5.0.0";
	public static final String[] TESTED_MC = {"1.10.2"};

	static ShipsMain PLUGIN;

	@Inject
	Game GAME;
	
	@Inject
	PluginContainer CONTAINER;

	private void registerCMDs(){
		new InfoCMD();
		new DebugCMD();
	}
	
	@Listener
	public void onEnable(GameStartingServerEvent event) {
		PLUGIN = this;
		new OpShip.StaticOPShip();
		GAME.getEventManager().registerListeners(this, new ShipsListeners());
		GAME.getCommandManager().register(this, new ShipsCMD.Executer(), "Ships", "Vessels");
		registerCMDs();
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
	
	public static Optional<User> getUser(UUID uuid){
		UserStorageService service = PLUGIN.GAME.getServiceManager().getRegistration(UserStorageService.class).get().getProvider();
		return service.get(uuid);
	}
	
	public static LiteralText formatCMDHelp(String message){
		return Text.builder(message).color(TextColors.AQUA).build();
	}

	public static LiteralText format(String message, boolean error) {
		if (error) {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.RED).build()).build();
			return ret;
		} else {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(message).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static LiteralText format(Text text, boolean error) {
		if (error) {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.RED).build()).build();
			return ret;
		} else {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD).append(Text.builder(text.toPlain()).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static ShipsMain getPlugin() {
		return PLUGIN;
	}

}
