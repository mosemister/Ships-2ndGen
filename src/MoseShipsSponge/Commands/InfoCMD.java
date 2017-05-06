package MoseShipsSponge.Commands;

import java.util.Arrays;
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipLoadingError;

public class InfoCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD{

	public InfoCMD(){
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}
	
	@Override
	public String[] getAliases() {
		String[] args = {"Info"};
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
		if(args.length > 1){
			displayInfo(player, args[1]);
		}else{
			basicInfo(player);
		}
		return CommandResult.success();
	}

	@Override
	public CommandResult execute(ConsoleSource console, String... args) throws CommandException {
		System.out.println("Args size: " + args.length);
		if(args.length > 1){
			System.out.println("display info");
			displayInfo(console, args[1]);
		}else{
			basicInfo(console);
		}
		return CommandResult.success();
	}
	
	private void displayInfo(CommandSource source, String type){
		if(type.equalsIgnoreCase("Basic")){
			basicInfo(source);
		}else{
			Optional<LoadableShip> opShip = ShipLoader.loadShip(type);
			if(opShip.isPresent()){
				shipInfo(source, opShip.get());
			}else{
				ShipLoadingError error = ShipLoader.getError(type);
				source.sendMessage(ShipsMain.format("Ships failed to gain the information for that Ship. Error name of " + error.name(), true));
			}
		}
	}
	
	private void basicInfo(CommandSource source){
		String MCVersion = ShipsMain.getPlugin().getGame().getPlatform().getMinecraftVersion().getName();
		String tMCVersion = null;
		for(String mc : ShipsMain.TESTED_MC){
			if(tMCVersion == null){
				tMCVersion = mc;
			}else{
				tMCVersion = tMCVersion + ", " + mc;
			}
		}
		
		source.sendMessage(ShipsMain.formatCMDHelp("|----[Ships info]----|"));
		source.sendMessage(ShipsMain.formatCMDHelp("Ships Version: " + ShipsMain.VERSION));
		if(Arrays.asList(ShipsMain.TESTED_MC).contains(MCVersion)){
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("), Text.builder(MCVersion).color(TextColors.GREEN).build(), ShipsMain.formatCMDHelp(") " + tMCVersion)));
		}else{
			source.sendMessage(Text.join(ShipsMain.formatCMDHelp("recommended MC version: ("), Text.builder(MCVersion).color(TextColors.RED).build(), ShipsMain.formatCMDHelp(") " + tMCVersion)));
		}
	}
	
	private void shipInfo(CommandSource source, LoadableShip ship){
		ship.getInfo().entrySet().stream().forEach(e -> Text.join(e.getKey(), ShipsMain.formatCMDHelp(e.getValue().toString())));
	}

}
