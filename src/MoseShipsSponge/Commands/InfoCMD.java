package MoseShipsSponge.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.spongepowered.api.Platform.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.BlockFinderUtil;
import MoseShipsSponge.Utils.MovementAlgorithmUtil;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public class InfoCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public InfoCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {
				"Info" };
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.of("This displays the basic information about ships");
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		if (args.length > 1) {
			displayInfo(player, args[1]);
		} else {
			basicInfo(player);
		}
		return CommandResult.success();
	}

	@Override
	public CommandResult execute(ConsoleSource console, String... args) throws CommandException {
		System.out.println("Args size: " + args.length);
		if (args.length > 1) {
			System.out.println("display info");
			displayInfo(console, args[1]);
		} else {
			basicInfo(console);
		}
		return CommandResult.success();
	}

	private void displayInfo(CommandSource source, String type) {
		if (type.equalsIgnoreCase("Basic")) {
			basicInfo(source);
		} else {
			Optional<LiveShip> opShip = Loader.safeLoadShip(type);
			if (opShip.isPresent()) {
				shipInfo(source, opShip.get());
			} /*
				 * else{ ShipLoadingError error = ShipLoader.getError(type);
				 * source.sendMessage(ShipsMain.
				 * format("Ships failed to gain the information for that Ship. Error name of "
				 * + error.name(), true)); }
				 */
		}
	}

	private void basicInfo(CommandSource source) {
		String MCVersion = ShipsMain.getPlugin().getGame().getPlatform().getMinecraftVersion().getName();
		int aPIVersion = Integer.parseInt("" + Sponge.getPlatform().getContainer(Component.API).getVersion().get().charAt(0)); 
		String tMCVersion = null;
		List<Integer> testedAPI = new ArrayList<>();
		String tAPIVersion = null;
		String[] shipsVersion = ShipsMain.VERSION.split(Pattern.quote("|"));
		for (String mc : ShipsMain.TESTED_MC) {
			if (tMCVersion == null) {
				tMCVersion = mc;
			} else {
				tMCVersion = tMCVersion + ", " + mc;
			}
		}
		for(int api : ShipsMain.TESTED_API){
			if(tAPIVersion == null){
				tAPIVersion = api + "";
				testedAPI.add(api);
			}else{
				tAPIVersion = tAPIVersion + ", " + api;
				testedAPI.add(api);
			}
		}

		source.sendMessage(ShipsMain.formatCMDHelp("|----[Ships info]----|"));
		source.sendMessage(ShipsMain.formatCMDHelp("Ships Version: " + shipsVersion[0] + "-Sponge"));
		source.sendMessage(ShipsMain.formatCMDHelp("Ships Version Name: " + shipsVersion[1]));
		if(testedAPI.contains(aPIVersion)){
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended API version: ("),
					Text.builder(aPIVersion + "").color(TextColors.GREEN).build(),
					ShipsMain.formatCMDHelp(") " + tAPIVersion)));
		} else {
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended API version: ("),
					Text.builder(aPIVersion + "").color(TextColors.RED).build(), ShipsMain.formatCMDHelp(") " + tAPIVersion)));
		}
		if (Arrays.asList(ShipsMain.TESTED_MC).contains(MCVersion)) {
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("),
					Text.builder(MCVersion).color(TextColors.GREEN).build(),
					ShipsMain.formatCMDHelp(") " + tMCVersion)));
		} else {
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("),
					Text.builder(MCVersion).color(TextColors.RED).build(), ShipsMain.formatCMDHelp(") " + tMCVersion)));
		}
		source.sendMessage(ShipsMain.formatCMDHelp("BlockFinder Algorithm: " + BlockFinderUtil.getConfigSelected().getName()));
		source.sendMessage(ShipsMain.formatCMDHelp("Movement Algorithm: " + MovementAlgorithmUtil.getConfig().getName()));
	}

	private void shipInfo(CommandSource source, LiveShip ship) {
		ship.getInfo().entrySet().stream()
				.forEach(e -> Text.join(Text.of(e.getKey()), ShipsMain.formatCMDHelp(e.getValue().toString())));
	}

}
