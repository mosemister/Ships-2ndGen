package org.ships.event.commands.ShipsCommands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.ships.block.blockhandler.BlockHandler;
import org.ships.block.configuration.MovementInstruction;
import org.ships.block.structure.ShipsStructure;
import org.ships.configuration.MaterialsList;
import org.ships.event.commands.CommandLauncher;

import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.Vessel.LoadableShip;

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
			help(sender);
		} else {
			if (args[1].equalsIgnoreCase("loadedVessels")) {
				displayLoadedVessels(sender);
			} else if (args[1].equalsIgnoreCase("VesselTypes")) {
				displayVesselTypes(sender);
			} else if (args[1].equalsIgnoreCase("CustomVesselTypes")) {
				displayCustomVesselTypes(sender);
			} else if (args[1].equalsIgnoreCase("MaterialsList")) {
				displayMaterialsList(sender);
			} else if (args[1].equalsIgnoreCase("RamMaterials")) {
				displayRAMMaterialsList(sender);
			} else if (args[1].equalsIgnoreCase("Structure")) {
				displayVessel(sender, args);
			} else if (args[1].equalsIgnoreCase("All")) {
				sender.sendMessage("-----[LoadedVessels]-----");
				displayLoadedVessels(sender);
				sender.sendMessage("-----[Materials]-----");
				displayMaterialsList(sender);
				sender.sendMessage("-----[RAM]-----");
			}
		}
	}

	public void displayMaterialsList(ConsoleCommandSender sender) {
		sender.sendMessage("<Name>");
		MaterialsList list = MaterialsList.getMaterialsList();
		Set<Material> materials = list.getMaterials(MovementInstruction.MATERIAL);
		for (Material item : materials) {
			sender.sendMessage(item.name());
		}
		sender.sendMessage("Total number of Materials in Materials List: " + materials.size());
	}

	public void displayRAMMaterialsList(ConsoleCommandSender sender) {
		sender.sendMessage("<Name>");
		MaterialsList list = MaterialsList.getMaterialsList();
		Set<Material> materials = list.getMaterials(MovementInstruction.RAM);
		for (Material item : materials) {
			sender.sendMessage(item.name());
		}
		sender.sendMessage("Total number of Materials in RAM Materials List: " + materials.size());
	}

	public void displayLoadedVessels(ConsoleCommandSender sender) {
		sender.sendMessage("<Name> | <Type> | <Owner> | <Location>");
		for (LoadableShip vessel : LoadableShip.getShips()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getVesselType().getName() + " | "
					+ vessel.getOwner().getName() + " | " + (int) vessel.getTeleportLocation().getX() + ","
					+ (int) vessel.getTeleportLocation().getY() + "," + (int) vessel.getTeleportLocation().getZ() + ","
					+ vessel.getTeleportLocation().getWorld().getName());
		}
		sender.sendMessage("Total number of Vessels loaded: " + LoadableShip.getShips().size());
		return;
	}

	public void displayCustomVesselTypes(ConsoleCommandSender sender) {
		sender.sendMessage("<Type> | <Normal speed>");
		for (VesselType vessel : VesselType.customValues()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getDefaultSpeed());
		}
		return;
	}

	public void displayVesselTypes(ConsoleCommandSender sender) {
		sender.sendMessage("<Type> | <Normal speed>");
		for (VesselType vessel : VesselType.values()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getDefaultSpeed());
		}
		return;
	}

	public void displayVessel(ConsoleCommandSender sender, String[] args) {
		if (args.length >= 3) {
			LoadableShip vessel = LoadableShip.getShip(args[2]);
			if (vessel != null) {
				ShipsStructure structure = vessel.getStructure();
				sender.sendMessage("----Special Blocks----");
				for (BlockHandler sBlock : structure.getSpecialBlocks()) {
					Block block = sBlock.getBlock();
					sender.sendMessage(block.getType().name() + ", " + block.getX() + ", " + block.getY() + ", "
							+ block.getZ() + ", " + block.getWorld().getName());
				}
				sender.sendMessage("----Priority blocks----");
				for (BlockHandler block : structure.getPriorityBlocks()) {
					sender.sendMessage(block.getBlock().getType().name() + ", " + block.getBlock().getX() + ", " + block.getBlock().getY() + ", "
							+ block.getBlock().getZ() + ", " + block.getBlock().getWorld().getName());
				}
				sender.sendMessage("----Normal blocks----");
				for (BlockHandler block : structure.getStandardBlocks()) {
					sender.sendMessage(block.getBlock().getType().name() + ", " + block.getBlock().getX() + ", " + block.getBlock().getY() + ", "
							+ block.getBlock().getZ() + ", " + block.getBlock().getWorld().getName());
				}
			}
		}
	}
}
