package org.ships.event.commands.ShipsCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.blockhandler.BlockPriority;
import org.ships.block.configuration.MaterialConfiguration;
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.structure.ShipsStructure;
import org.ships.configuration.BlockList;
import org.ships.event.commands.CommandLauncher;
import org.ships.ship.LoadableShip;
import org.ships.ship.type.VesselType;
import org.ships.utils.Sorts;

public class Developer extends CommandLauncher {
	public Developer() {
		super("Developer", "", "All root commands", null, false, true);
	}

	@Override
	public void playerCommand(Player player, String[] args) {
	}

	public void help(ConsoleCommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "/ships developer loadedVessels");
		sender.sendMessage(ChatColor.GOLD + "/ships developer Vesseltypes");
		sender.sendMessage(ChatColor.GOLD + "/ships developer CVesseltypes");
		sender.sendMessage(ChatColor.GOLD + "/ships developer materialsList");
		sender.sendMessage(ChatColor.GOLD + "/ships developer ramMaterials");
		sender.sendMessage(ChatColor.GOLD + "/ships developer all");
		sender.sendMessage(ChatColor.GOLD + "/ships developer structure <vesselname>");
	}

	@Override
	public void consoleCommand(ConsoleCommandSender sender, String[] args) {
		if (args.length == 1) {
			this.help(sender);
		} else if (args[1].equalsIgnoreCase("loadedVessels")) {
			this.displayLoadedVessels(sender);
		} else if (args[1].equalsIgnoreCase("VesselTypes")) {
			this.displayVesselTypes(sender);
		} else if (args[1].equalsIgnoreCase("CustomVesselTypes")) {
			this.displayCustomVesselTypes(sender);
		} else if (args[1].equalsIgnoreCase("MaterialsList")) {
			this.displayMaterialsList(sender);
		} else if (args[1].equalsIgnoreCase("RamMaterials")) {
			this.displayRAMMaterialsList(sender);
		} else if (args[1].equalsIgnoreCase("InstructionOrder")) {
			this.displayInstructionOrder(sender);
		} else if (args[1].equalsIgnoreCase("Structure")) {
			this.displayVessel(sender, args);
		} else if (args[1].equalsIgnoreCase("All")) {
			sender.sendMessage("-----[LoadedVessels]-----");
			this.displayLoadedVessels(sender);
			sender.sendMessage("-----[BlockList]-----");
			this.displayBlockList(sender);
		}
	}

	private void displayInstructionOrder(ConsoleCommandSender sender) {
		sender.sendMessage("<Name>");
		List<MovementInstruction> list = Arrays.asList(MovementInstruction.values());
		Collections.sort(list);
		list.stream().forEach(l -> {
			sender.sendMessage(l.name());
		});
		System.out.println("<Priority>");
		List<BlockPriority> list2 = Arrays.asList(BlockPriority.values());
		Collections.sort(list2);
		list2.stream().forEach(l -> {
			sender.sendMessage(l.name());
		});
	}

	public void displayBlockList(ConsoleCommandSender sender) {
		sender.sendMessage("<Instruction> <Name>");
		for (MovementInstruction ins : MovementInstruction.values()) {
			ArrayList<MaterialConfiguration> list = new ArrayList<MaterialConfiguration>(BlockList.BLOCK_LIST.getCurrentWith(ins));
			Collections.sort(list, Sorts.SORT_MATERIAL_CONFIGURATION_BY_MATERIAL_NAME);
			list.stream().forEach(m -> {
				sender.sendMessage(m.getInstruction().name() + " " + m.getMaterial().name());
			});
			sender.sendMessage("\tTotal number of Materials in BlockList for " + ins.name() + ": " + list.size());
		}
	}

	public void displayMaterialsList(ConsoleCommandSender sender) {
		sender.sendMessage("<Name>");
		ArrayList<MaterialConfiguration> materials = new ArrayList<MaterialConfiguration>(BlockList.BLOCK_LIST.getCurrentWith(MovementInstruction.MATERIAL));
		Collections.sort(materials, Sorts.SORT_MATERIAL_CONFIGURATION_BY_MATERIAL_NAME);
		for (MaterialConfiguration configuration : materials) {
			sender.sendMessage(configuration.getMaterial().name());
		}
		sender.sendMessage("Total number of Materials in Materials List: " + materials.size());
	}

	public void displayRAMMaterialsList(ConsoleCommandSender sender) {
		sender.sendMessage("<Name>");
		Set<MaterialConfiguration> materials = BlockList.BLOCK_LIST.getCurrentWith(MovementInstruction.RAM);
		for (MaterialConfiguration item : materials) {
			sender.sendMessage(item.getMaterial().name());
		}
		sender.sendMessage("Total number of Materials in RAM Materials List: " + materials.size());
	}

	public void displayLoadedVessels(ConsoleCommandSender sender) {
		sender.sendMessage("<Name> | <Type> | <Owner> | <Location>");
		for (LoadableShip vessel : LoadableShip.getShips()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getVesselType().getName() + " | " + vessel.getOwner().getName() + " | " + (int) vessel.getTeleportLocation().getX() + "," + (int) vessel.getTeleportLocation().getY() + "," + (int) vessel.getTeleportLocation().getZ() + "," + vessel.getTeleportLocation().getWorld().getName());
		}
		sender.sendMessage("Total number of Vessels loaded: " + LoadableShip.getShips().size());
	}

	public void displayCustomVesselTypes(ConsoleCommandSender sender) {
		sender.sendMessage("<Type> | <Normal speed>");
		for (VesselType vessel : VesselType.customValues()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getDefaultSpeed());
		}
	}

	public void displayVesselTypes(ConsoleCommandSender sender) {
		sender.sendMessage("<Type> | <Normal speed>");
		for (VesselType vessel : VesselType.values()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getDefaultSpeed());
		}
	}

	public void displayVessel(ConsoleCommandSender sender, String[] args) {
		if (args.length < 3) {
			return;
		}
		LoadableShip vessel = LoadableShip.getShip(args[2]);
		if (vessel == null) {
			return;
		}
		ShipsStructure structure = vessel.getStructure();
		for (BlockPriority pri : BlockPriority.values()) {
			String name = pri.name().toLowerCase();
			name = "" + Character.toUpperCase(name.charAt(0)) + name.substring(1, name.length());
			sender.sendMessage("----" + name + " blocks----");
			for (BlockHandler<? extends BlockState> sBlock : structure.getBlocks(pri)) {
				Block block = sBlock.getBlock();
				sender.sendMessage(block.getType().name() + ", " + block.getX() + ", " + block.getY() + ", " + block.getZ() + ", " + block.getWorld().getName());
			}
		}
	}
}
