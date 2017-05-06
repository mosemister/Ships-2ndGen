package MoseShipsSponge.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public interface ShipsCMD {

	public static final List<ShipsCMD> SHIPS_COMMANDS = new ArrayList<>();

	public String[] getAliases();

	public Text getDescription();

	public String getPermission();

	public interface ShipsPlayerCMD extends ShipsCMD {
		public CommandResult execute(Player player, String... args) throws CommandException;
	}

	public interface ShipsConsoleCMD extends ShipsCMD {
		public CommandResult execute(ConsoleSource console, String... args) throws CommandException;
	}

	public interface ShipsBlockCMD extends ShipsCMD {
		public CommandResult execute(CommandBlockSource block, String... args) throws CommandException;
	}

	public class Executer implements CommandCallable {

		@Override
		public Optional<Text> getHelp(CommandSource arg0) {
			return Optional.empty();
		}

		@Override
		public Optional<Text> getShortDescription(CommandSource arg0) {
			return Optional.of(Text.of("All commands relating to Ships"));
		}

		@Override
		public List<String> getSuggestions(CommandSource arg0, String arg1, Location<World> arg2)
				throws CommandException {
			return new ArrayList<>();
		}

		@Override
		public Text getUsage(CommandSource arg0) {
			return Text.of("/ships help");
		}

		@Override
		public CommandResult process(CommandSource source, String args) throws CommandException {
			String[] rArgs = args.split(" ");
			if (source instanceof Player) {
				Optional<ShipsCMD> opCMD = SHIPS_COMMANDS.stream().filter(cmd -> (cmd instanceof ShipsPlayerCMD))
						.filter(cmd -> {
							return Arrays.asList(cmd.getAliases()).stream().anyMatch(s -> s.equalsIgnoreCase(rArgs[0]));
						}).findFirst();
				if (opCMD.isPresent()) {
					Player player = (Player) source;
					ShipsPlayerCMD cmd = (ShipsPlayerCMD) opCMD.get();
					if (cmd.getPermission() != null) {
						if (player.hasPermission(cmd.getPermission())) {
							return cmd.execute(player, rArgs);
						}
					} else {
						return cmd.execute(player, rArgs);
					}
				}
			} else if (source instanceof ConsoleSource) {
				Optional<ShipsCMD> opCMD = SHIPS_COMMANDS.stream().filter(cmd -> (cmd instanceof ShipsConsoleCMD))
						.filter(cmd -> {
							return Arrays.asList(cmd.getAliases()).stream().anyMatch(s -> s.equalsIgnoreCase(rArgs[0]));
						}).findFirst();
				if (opCMD.isPresent()) {
					return ((ShipsConsoleCMD) opCMD.get()).execute((ConsoleSource) source, rArgs);
				}
			} else if (source instanceof CommandBlockSource) {
				Optional<ShipsCMD> opCMD = SHIPS_COMMANDS.stream().filter(cmd -> (cmd instanceof ShipsBlockCMD))
						.filter(cmd -> {
							return Arrays.asList(cmd.getAliases()).stream().anyMatch(s -> s.equalsIgnoreCase(rArgs[0]));
						}).findFirst();
				if (opCMD.isPresent()) {
					return ((ShipsBlockCMD) opCMD.get()).execute((CommandBlockSource) source, rArgs);
				}
			} else {
				new IOException("The source " + source + " is not regonised.");
			}
			return CommandResult.empty();
		}

		@Override
		public boolean testPermission(CommandSource source) {
			return true;
		}

	}

}
