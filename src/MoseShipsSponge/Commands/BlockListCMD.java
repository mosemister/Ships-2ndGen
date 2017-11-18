package MoseShipsSponge.Commands;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.BlockList.ListType;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.PermissionUtil;

public class BlockListCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public BlockListCMD() {
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}

	@Override
	public String[] getAliases() {
		String[] args = { "blockList", "Materials" };
		return args;
	}

	@Override
	public Text getDescription() {
		return Text.builder("All commands based on the blockList").build();
	}

	@Override
	public String getPermission() {
		return PermissionUtil.BLOCK_LIST_CMD;
	}

	@Override
	public CommandResult execute(Player player, String... args) throws CommandException {
		return run(player, args);
	}

	@Override
	public CommandResult execute(ConsoleSource console, String... args) throws CommandException {
		return run(console, args);
	}

	private CommandResult run(CommandSource sender, String... args) {
		if (args.length == 1) {
			sender.sendMessage(ShipsMain.formatCMDHelp("-m = materials list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("-r = ram list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("-n = none list"));
			sender.sendMessage(ShipsMain.formatCMDHelp(
					"/ships blockList set <-m/-r/-n> <block number> [data value] : adds the block into the specified list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("/ships blockList list <-m/-r/-n>"));
		} else if (args[1].equalsIgnoreCase("set")) {
			if (args.length >= 4) {
				ListType type = BlockList.ListType.valueFrom(args[2]);
				if (type == null) {
					sender.sendMessage(
							Text.builder(args[2] + " is not a valid list type").color(TextColors.RED).build());
					return CommandResult.success();
				}
				Optional<BlockType> material = Sponge.getRegistry().getType(BlockType.class, args[3]);
				Optional<BlockState> state = Sponge.getRegistry().getType(BlockState.class, args[3]);
				if (!material.isPresent() || !state.isPresent()) {
					sender.sendMessage(Text.builder(args[3] + "is not a block").build());
					return CommandResult.success();
				}
				if (material.isPresent()) {
					material.get().getAllBlockStates().stream()
							.forEach(s -> BlockList.BLOCK_LIST.resetMaterial(material.get().getDefaultState(), type));
				} else if (state.isPresent()) {
					BlockList.BLOCK_LIST.resetMaterial(state.get(), type);
				} else {
					sender.sendMessage(ShipsMain.format("Error occured", true));
					return CommandResult.success();
				}
			}
		} else if (args[1].equalsIgnoreCase("list")) {
			ListType type = BlockList.ListType.valueFrom(args[2]);
			if (type == null) {
				sender.sendMessage(Text.builder(args[2] + " is not a valid list type").build());
				return CommandResult.success();
			}
			List<BlockState> states = BlockList.BLOCK_LIST.getBlocks(type);
			sender.sendMessage(Text.of(states.toString()));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
