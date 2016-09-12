package MoseShipsBukkit.CMD.Commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.Configs.Files.BlockList;
import MoseShipsBukkit.Configs.Files.BlockList.ListType;
import MoseShipsBukkit.Utils.Permissions;
import MoseShipsBukkit.Utils.State.BlockState;

public class BlockListCMD implements ShipsCMD.ShipsConsoleCMD, ShipsCMD.ShipsPlayerCMD {

	public BlockListCMD(){
		ShipsCMD.SHIPS_COMMANDS.add(this);
	}
	
	@Override
	public String[] getAliases() {
		String[] args = {"blockList", "Materials"};
		return args;
	}

	@Override
	public String getDescription() {
		return "All commands based on the BlockList";
	}

	@Override
	public String getPermission() {
		return Permissions.BLOCK_LIST_CMD;
	}

	@Override
	public boolean execute(Player player, String... args) {
		return run(player, args);
	}

	@Override
	public boolean execute(ConsoleCommandSender console, String... args) {
		return run(console, args);
	}
	
	@SuppressWarnings("deprecation")
	private boolean run(CommandSender sender, String... args){
		if(args.length == 1){
			sender.sendMessage(ShipsMain.formatCMDHelp("-m = materials list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("-r = ram list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("-n = none list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("/ships blockList set <-m/-r/-n> <block number> [data value] : adds the block into the specified list"));
			sender.sendMessage(ShipsMain.formatCMDHelp("/ships blockList list <-m/-r/-n>"));
		}else if(args[1].equalsIgnoreCase("set")){
			if(args.length >= 4){
				try{
				ListType type = BlockList.ListType.valueFrom(args[2]);
				if(type == null){
					sender.sendMessage(args[2] + " is not a valid list type");
					return true;
				}
				Material material = Material.getMaterial(Integer.parseInt(args[3]));
				if(material == null){
					sender.sendMessage(args[3] + "is not a material id");
					return true;
				}
				if(!material.isBlock()){
					sender.sendMessage(args[3] + "is not a block id (but is a item id)");
					return true;
				}
				if(args.length >= 5){
					try{
						BlockList.BLOCK_LIST.resetMaterial(material, Byte.parseByte(args[4]), type);
					}catch(NumberFormatException e){
						sender.sendMessage(args[4] + " is not a data value");
					}
				}else{
					BlockList.BLOCK_LIST.resetMaterial(material, (byte)-1, type);
				}
				}catch(NumberFormatException e){
					sender.sendMessage(args[3] + " is not a material id");
					return true;
				}
			}
		}else if(args[1].equalsIgnoreCase("list")){
			ListType type = BlockList.ListType.valueFrom(args[2]);
			if(type == null){
				sender.sendMessage(args[2] + " is not a valid list type");
				return true;
			}
			List<BlockState> states = BlockList.BLOCK_LIST.getBlocks(type);
			sender.sendMessage(states.toString());
			return true;
		}
		return false;
	}

}
