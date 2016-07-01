package MoseShipsBukkit.Listeners.ShipsCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Listeners.CommandLauncher;
import MoseShipsBukkit.ShipsTypes.VesselType;
import MoseShipsBukkit.StillShip.ShipsStructure;
import MoseShipsBukkit.StillShip.SpecialBlock;
import MoseShipsBukkit.StillShip.Vessel.Vessel;
import MoseShipsBukkit.Utils.MaterialItem;
import MoseShipsBukkit.Utils.ConfigLinks.MaterialsList;

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
		sender.sendMessage("<Name> | <Value>");
		MaterialsList list = MaterialsList.getMaterialsList();
		for (MaterialItem item : list.getMaterials()) {
			sender.sendMessage(item.getMaterial().name() + " | " + item.getData());
		}
		sender.sendMessage("Total number of Materials in Materials List: " + list.getMaterials().size());
	}

	public void displayRAMMaterialsList(ConsoleCommandSender sender) {
		sender.sendMessage("<Name> | <Value>");
		MaterialsList list = MaterialsList.getMaterialsList();
		for (MaterialItem item : list.getRamMaterials()) {
			sender.sendMessage(item.getMaterial() + " | " + item.getData());
		}
		sender.sendMessage("Total number of Materials in RAM Materials List: " + list.getMaterials().size());
	}

	public void displayLoadedVessels(ConsoleCommandSender sender) {
		sender.sendMessage("<Name> | <Type> | <Owner> | <Location>");
		for (Vessel vessel : Vessel.getVessels()) {
			sender.sendMessage(vessel.getName() + " | " + vessel.getVesselType().getName() + " | "
					+ vessel.getOwner().getName() + " | " + (int) vessel.getTeleportLocation().getX() + ","
					+ (int) vessel.getTeleportLocation().getY() + "," + (int) vessel.getTeleportLocation().getZ() + ","
					+ vessel.getTeleportLocation().getWorld().getName());
		}
		sender.sendMessage("Total number of Vessels loaded: " + Vessel.getVessels().size());
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
			Vessel vessel = Vessel.getVessel(args[2]);
			if (vessel != null) {
				ShipsStructure structure = vessel.getStructure();
				sender.sendMessage("----Special Blocks----");
				for (SpecialBlock sBlock : structure.getSpecialBlocks()) {
					Block block = sBlock.getBlock();
					sender.sendMessage(block.getType().name() + ", " + block.getX() + ", " + block.getY() + ", "
							+ block.getZ() + ", " + block.getWorld().getName());
				}
				sender.sendMessage("----Priority blocks----");
				for (Block block : structure.getPriorityBlocks()) {
					sender.sendMessage(block.getType().name() + ", " + block.getX() + ", " + block.getY() + ", "
							+ block.getZ() + ", " + block.getWorld().getName());
				}
				sender.sendMessage("----Normal blocks----");
				for (Block block : structure.getStandardBlocks()) {
					sender.sendMessage(block.getType().name() + ", " + block.getX() + ", " + block.getY() + ", "
							+ block.getZ() + ", " + block.getWorld().getName());
				}
			}
		}
	}
}
