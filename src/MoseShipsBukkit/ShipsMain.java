package MoseShipsBukkit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsBukkit.CMD.ShipsCMD;
import MoseShipsBukkit.CMD.Commands.BlockListCMD;
import MoseShipsBukkit.CMD.Commands.DebugCMD;
import MoseShipsBukkit.CMD.Commands.HelpCMD;
import MoseShipsBukkit.CMD.Commands.InfoCMD;
import MoseShipsBukkit.CMD.Commands.SignCMD;
import MoseShipsBukkit.Configs.Files.BlockList;
import MoseShipsBukkit.Listeners.ShipsListeners;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.BlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.BannerSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.BrewingStandSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.ChestSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.CommandBlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.DispenserSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.DropperSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.FurnaceSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.HopperSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.JukeBoxSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.NoteBlockSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.PotSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.SignSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.SkullSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.SpawnerSnapshot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.Block.Snapshots.TeleportSnapshot;
import MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.AirTypes.OpShip;

public class ShipsMain extends JavaPlugin {

	public static String NAME;
	public static String VERSION;
	public static final String[] TESTED_MC = { "1.9.4" };

	static ShipsMain PLUGIN;

	private void registerCMDs() {
		new InfoCMD();
		new DebugCMD();
		new SignCMD();
		new HelpCMD();
		new BlockListCMD();
	}

	private void registerSnapshotTypes() {
		BlockSnapshot.VALUE_TYPES.put(Material.STANDING_BANNER, BannerSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WALL_BANNER, BannerSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BREWING_STAND, BrewingStandSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.CHEST, ChestSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.TRAPPED_CHEST, ChestSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.COMMAND, CommandBlockSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DISPENSER, DispenserSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DROPPER, DropperSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.FURNACE, FurnaceSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BURNING_FURNACE, FurnaceSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.HOPPER, HopperSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.JUKEBOX, JukeBoxSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.NOTE_BLOCK, NoteBlockSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.FLOWER_POT, PotSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SIGN_POST, SignSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WALL_SIGN, SignSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SKULL, SkullSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.MOB_SPAWNER, SpawnerSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.ENDER_PORTAL, TeleportSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.PORTAL, TeleportSnapshot.class);
	}

	private void registerShipTypes() {
		new OpShip.StaticOPShip();
	}

	private void displayVersionChecking() {
		VersionChecking.VersionOutcome previous = null;
		for (String tested : TESTED_MC) {
			VersionChecking.VersionOutcome outcome = VersionChecking.isGreater(VersionChecking.MINECRAFT_VERSION,
					VersionChecking.convert(tested));
			if (outcome.equals(VersionChecking.VersionOutcome.EQUAL)) {
				previous = outcome;
				break;
			} else if ((previous != null) && (!outcome.equals(previous))) {
				previous = null;
				break;
			} else {
				previous = outcome;
			}
		}
		if (previous != null) {
			ConsoleCommandSender console = getServer().getConsoleSender();
			switch (previous) {
				case EQUAL:
					console.sendMessage(ChatColor.GREEN + "Your MC version has been tested with Ships");
					break;
				case GREATER:
					console.sendMessage(ChatColor.YELLOW
							+ "Your MC version is greater then the tested versions. Ships should be uneffected however please be keep in mind that you should look for updates as your MC version is unsupported");
					break;
				case LOWER:
					console.sendMessage(ChatColor.RED
							+ "Your MC version is lower then the tested versions. Ships is not supported at all, Please note that Ships may still work");
					break;
			}
		} else {
			ConsoleCommandSender console = getServer().getConsoleSender();
			console.sendMessage(ChatColor.RED
					+ "Your MC version is between tested versions. It is unlikely there will be a upgrade for ships to your MC version. Please keep in mind that Ships should still work fine.");
		}
	}

	public void onEnable() {
		PLUGIN = this;
		NAME = getName();
		VERSION = getDescription().getVersion();
		getServer().getPluginManager().registerEvents(new ShipsListeners(), this);
		getCommand("Ships").setExecutor(new ShipsCMD.Executer());
		BlockList.BLOCK_LIST.reload();
		registerSnapshotTypes();
		registerCMDs();
		registerShipTypes();
		displayVersionChecking();

	}

	public static String formatCMDHelp(String message) {
		return ChatColor.AQUA + message;
	}

	public static String format(String message, boolean error) {
		if (error) {
			String ret = ChatColor.GOLD + "[Ships] " + ChatColor.RED + message;
			return ret;
		} else {
			String ret = ChatColor.GOLD + "[Ships] " + ChatColor.AQUA + message;
			return ret;
		}
	}

	public static ShipsMain getPlugin() {
		return PLUGIN;
	}

}
