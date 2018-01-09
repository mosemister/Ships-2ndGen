package MoseShipsSponge.Plugin;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import MoseShipsSponge.VersionChecking;
import MoseShipsSponge.Commands.DebugCMD;
import MoseShipsSponge.Commands.InfoCMD;
import MoseShipsSponge.Commands.ShipsCMD;
import MoseShipsSponge.Commands.SignCMD;
import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Listeners.ShipsListeners;
import MoseShipsSponge.ShipBlock.Signs.ShipAltitudeSign;
import MoseShipsSponge.ShipBlock.Signs.ShipEngineSign;
import MoseShipsSponge.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsSponge.ShipBlock.Signs.ShipSign;
import MoseShipsSponge.ShipBlock.Signs.ShipWheelSign;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticAirship;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticOPShip;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticWaterShip;

@Plugin(id = ShipsMain.ID, name = ShipsMain.NAME, version = ShipsMain.VERSION)
public class ShipsMain {

	public static final String ID = "ships";
	public static final String NAME = "Ships";
	public static final String VERSION = "6.0.0.0|PreAlpha-Sponge(Alpha 1,3,0)";
	public static final int[] TESTED_API = {
			6
	};
	public static final String[] TESTED_MC = {
			"1.11.2" 
	};

	public static final File CONFIGURATION_ROOT = new File("config/Ships/");

	static ShipsMain PLUGIN;

	@Inject
	Game GAME;

	@Inject
	PluginContainer CONTAINER;

	private void registerSigns() {
		ShipSign.SHIP_SIGNS.add(new ShipLicenceSign());
		ShipSign.SHIP_SIGNS.add(new ShipAltitudeSign());
		ShipSign.SHIP_SIGNS.add(new ShipEngineSign());
		ShipSign.SHIP_SIGNS.add(new ShipWheelSign());
		//ShipSign.SHIP_SIGNS.add(new ShipEOTSign());
	}

	private void registerCMDs() {
		new InfoCMD();
		new DebugCMD();
		new SignCMD();
		//new HelpCMD();
		//new BlockListCMD();
		//new AutoPilotCMD();
	}

	private void registerShipTypes() {
		new StaticOPShip();
		new StaticAirship();
		new StaticWaterShip();
	}

	private void displayVersionChecking() {
		VersionChecking.VersionOutcome previous = null;
		for (String tested : TESTED_MC) {
			int[] test = VersionChecking.convert(tested);
			VersionChecking.VersionOutcome outcome = VersionChecking.isGreater(VersionChecking.MINECRAFT_VERSION, test);
			if (outcome.equals(VersionChecking.VersionOutcome.EQUAL)) {
				previous = outcome;
				break;
			} else if ((previous != null) && (!outcome.equals(previous))) {
				previous = null;
				break;
			} else {
				previous = outcome;
			}
		}
		ConsoleSource console = Sponge.getServer().getConsole();
		if (previous != null) {
			switch (previous) {
			case EQUAL:
				Text text = Text.builder("Your MC version has been tested with Ships").color(TextColors.GREEN).build();
				console.sendMessage(text);
				break;
			case GREATER:
				Text text2 = Text
						.builder(
								"Your MC version is greater then the tested versions. Ships should be uneffected however please be keep in mind that you should look for updates as your MC version is unsupported")
						.color(TextColors.YELLOW).build();
				console.sendMessage(text2);
				break;
			case LOWER:
				Text text3 = Text
						.builder(
								"Your MC version is lower then the tested versions. Ships is not supported at all, Please note that Ships may still work")
						.color(TextColors.RED).build();
				console.sendMessage(text3);
				break;
			}
		} else {
			Text text = Text
					.builder(
							"Your MC version is between tested versions. It is unlikely there will be a upgrade for ships to your MC version. Please keep in mind that Ships should still work fine.")
					.color(TextColors.RED).build();
			console.sendMessage(text);
		}
	}

	@Listener
	public void onEnable(GameStartingServerEvent event) {
		PLUGIN = this;
		GAME.getEventManager().registerListeners(this, new ShipsListeners());
		GAME.getCommandManager().register(this, new ShipsCMD.Executer(), "Ships", "Vessels");
		BlockList.BLOCK_LIST.reload();
		registerShipTypes();
		registerSigns();
		registerCMDs();
		displayVersionChecking();
	}

	@Listener
	public void onLoad(GameStartedServerEvent event) {
		// load the ship
		List<LiveShip> list = Loader.safeLoadAllShips();
		String out = null;
		for(LiveShip ship : list) {
			if(out == null) {
				out = ship.getName();
			}else {
				out = out + ", " + ship.getName(); 
			}
		}
		Sponge.getServer().getConsole().sendMessage(Text.builder("The following ships have been loaded").color(TextColors.GREEN).build());
		if(out != null){
			Sponge.getServer().getConsole().sendMessage(Text.builder(out).color(TextColors.AQUA).build());
		}
	}

	@Listener
	public void onDisable(GameStoppingServerEvent event) {
		for(int A = 0; A < Loader.getLoadedShips().size(); A++) {
			LiveShip ship = Loader.getLoadedShips().get(A);
			EventContext context = EventContext.builder().add(EventContextKeys.PLUGIN, ShipsMain.getPlugin().getContainer()).build();
			Cause cause = Cause.of(context, "Disabled Ships Plugin");
			ship.unload(cause);
		}
	}

	public Game getGame() {
		return GAME;
	}

	public PluginContainer getContainer() {
		return CONTAINER;
	}

	public static Optional<User> getUser(UUID uuid) {
		UserStorageService service = PLUGIN.GAME.getServiceManager().getRegistration(UserStorageService.class).get()
				.getProvider();
		return service.get(uuid);
	}

	public static LiteralText formatCMDHelp(String message) {
		return Text.builder(message).color(TextColors.AQUA).build();
	}

	public static LiteralText format(String message, boolean error) {
		if (error) {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD)
					.append(Text.builder(message).color(TextColors.RED).build()).build();
			return ret;
		} else {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD)
					.append(Text.builder(message).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static LiteralText format(Text text, boolean error) {
		if (error) {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD)
					.append(Text.builder(text.toPlain()).color(TextColors.RED).build()).build();
			return ret;
		} else {
			LiteralText ret = Text.builder("[Ships] ").color(TextColors.GOLD)
					.append(Text.builder(text.toPlain()).color(TextColors.AQUA).build()).build();
			return ret;
		}
	}

	public static ShipsMain getPlugin() {
		return PLUGIN;
	}

}
