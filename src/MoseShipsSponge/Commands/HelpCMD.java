package MoseShipsSponge.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class HelpCMD implements ShipsCMD.ShipsPlayerCMD, ShipsCMD.ShipsConsoleCMD{
	
	public HelpCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = {"help"};
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.builder("Display all ships commands").build();
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public CommandResult execute(ConsoleSource console, String... args) throws CommandException {
		ShipsCMD.SHIPS_COMMANDS.stream().filter(c -> c instanceof ShipsCMD.ShipsConsoleCMD).forEach(c -> console.sendMessage(Text.builder("/ships " + c.getAliases()[0] + ": " + c.getDescription()).color(TextColors.AQUA).build()));
		return CommandResult.success();
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		ShipsCMD.SHIPS_COMMANDS.stream().filter(c -> {
			if(c instanceof ShipsCMD.ShipsPlayerCMD) {
				ShipsCMD.ShipsPlayerCMD pCMD = (ShipsCMD.ShipsPlayerCMD)c;
				if((pCMD.getPermission() != null) && (player.hasPermission(pCMD.getPermission()))) {
					return true;
				}
			}
			return false;
		}).forEach(c -> player.sendMessage(Text.builder("/ships " + c.getAliases()[0] + ": " + c.getDescription()).color(TextColors.AQUA).build()));
		return CommandResult.success();
	}
	
	

}
