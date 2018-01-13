package MoseShipsSponge.Commands.SCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandFlags;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import MoseShipsSponge.Commands.Elements.ListTypeElement;
import MoseShipsSponge.Configs.BlockList;
import MoseShipsSponge.Configs.BlockList.ListType;
import MoseShipsSponge.Plugin.ShipsMain;
import MoseShipsSponge.Utils.PermissionUtil;

public class ShipsBlockListCommand {
	
	static final Text ITEM = Text.of("Item");
	static final Text BLOCK = Text.of("Block");
	static final Text LIST = Text.of("List");
	
	static class SeeBlocks implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			List<BlockState> states = new ArrayList<>();
			if(args.hasAny("m")) {
				states.addAll(BlockList.BLOCK_LIST.getBlocks(ListType.MATERIALS));
			}
			if(args.hasAny("r")) {
				states.addAll(BlockList.BLOCK_LIST.getBlocks(ListType.RAM));
			}
			if(args.hasAny("n")) {
				states.addAll(BlockList.BLOCK_LIST.getBlocks(ListType.NONE));
			}
			states.stream().forEach(s -> src.sendMessage(ShipsMain.formatCMDHelp(s.getId())));
			return CommandResult.success();
		}
	}
	
	static class CheckBlock implements CommandExecutor {

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			ItemType item;
			Optional<ItemType> opType = args.<ItemType>getOne(ITEM);
			if(opType.isPresent()) {
				item = opType.get();
			}else if(src instanceof Player) {
				Player player = (Player)src;
				Optional<ItemStack> opStack = player.getItemInHand(HandTypes.MAIN_HAND);
				if(opStack.isPresent()) {
					item = opStack.get().getType();
				}else {
					opStack = player.getItemInHand(HandTypes.OFF_HAND);
					if(opStack.isPresent()) {
						item = opStack.get().getType();
					}else{
						throw new CommandException(ShipsMain.format("No Item in hand or definded.", true));
					}
				}
			}else {
				throw new CommandException(ShipsMain.format("No Item definded", true));
			}
			if(!item.getBlock().isPresent()) {
				throw new CommandException(ShipsMain.format("Item does not have a block", true));
			}
			ListType list = BlockList.BLOCK_LIST.getList(item.getBlock().get());
			src.sendMessage(ShipsMain.format(item.getBlock().get().getName() + " is in the " + list.name() + " list", true));
			return CommandResult.success();
		}
		
	}
	
	static class SetBlock implements CommandExecutor {
		
		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
			BlockState state;
			Optional<BlockState> opState = args.<BlockState>getOne(BLOCK);
			ListType type = args.<ListType>getOne(LIST).get();
			if(!opState.isPresent()) {
				if(src instanceof Player) {
					Player player = (Player)src;
					Optional<ItemStack> item = player.getItemInHand(HandTypes.MAIN_HAND);
					if(!item.isPresent()) {
						throw new CommandException(ShipsMain.format("BlockState or Item in hand must be presented. /Ships blocklist set [BlockState] <List Type>", true));
					}
					Optional<BlockType> opType = item.get().getType().getBlock();
					if(!opType.isPresent()) {
						
					}
					BlockList.BLOCK_LIST.resetMaterial(opType.get(), type);
					src.sendMessage(ShipsMain.format("Changed block " + opType.get().getName() + " to " + type.name(), false));
				}
				throw new CommandException(ShipsMain.format("BlockState must be presented. /Ships blocklist set <BlockState> <List Type>", true));
			}else {
				state = opState.get();
			}
			BlockList.BLOCK_LIST.resetMaterial(state, type);
			src.sendMessage(ShipsMain.format("Changed block " + state.getId() + " to " + type.name(), false));
			return CommandResult.success();
		}
	}
	
	public static CommandSpec createCommands() {
		CommandSpec seeBlocks = CommandSpec.builder().description(Text.of("See what list a block is used in")).permission(PermissionUtil.SEE_BLOCK_CMD).arguments(GenericArguments.flags().flag("m", "r", "n").setUnknownShortFlagBehavior(CommandFlags.UnknownFlagBehavior.ERROR).buildWith(GenericArguments.none())).executor(new SeeBlocks()).build();
		CommandSpec showList = CommandSpec.builder().description(Text.of("Shows what list a block")).permission(PermissionUtil.SHOW_LIST_CMD).arguments(GenericArguments.optional(GenericArguments.catalogedElement(ITEM, ItemType.class))).executor(new CheckBlock()).build();
		CommandSpec setBlock = CommandSpec.builder().description(Text.of("Sets the list that a block lies in")).permission(PermissionUtil.SET_LIST_CMD).arguments(GenericArguments.catalogedElement(BLOCK, BlockState.class)).arguments(new ListTypeElement(LIST)).executor(new SetBlock()).build();
		return CommandSpec.builder().description(Text.of("All MaterialList commands")).permission(PermissionUtil.BLOCK_LIST_CMD).child(seeBlocks, "seeBlocks", "see").child(showList, "showList", "list", "l").child(setBlock, "setBlock", "set").build();
	}

}
