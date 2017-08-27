package MoseShipsBukkit.Plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import MoseShipsBukkit.Commands.AutoPilotCMD;
import MoseShipsBukkit.Commands.BlockListCMD;
import MoseShipsBukkit.Commands.DebugCMD;
import MoseShipsBukkit.Commands.HelpCMD;
import MoseShipsBukkit.Commands.InfoCMD;
import MoseShipsBukkit.Commands.ShipsCMD;
import MoseShipsBukkit.Commands.SignCMD;
import MoseShipsBukkit.Configs.BlockList;
import MoseShipsBukkit.Listeners.ShipsListeners;
import MoseShipsBukkit.ShipBlock.Signs.ShipAltitudeSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipEOTSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipEngineSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipLicenceSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipSign;
import MoseShipsBukkit.ShipBlock.Signs.ShipWheelSign;
import MoseShipsBukkit.ShipBlock.Snapshot.BlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.BannerSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.BedSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.BrewingStandSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.ButtonSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.CarpetSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.ChestSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.CommandBlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.DispenserSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.DropperSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.EnderChestSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.FireSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.FurnaceSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.GateSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.HookSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.HopperSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.JukeBoxSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.LadderSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.LeverSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.LogSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.NoteBlockSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.ObserverSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.PistonSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.PotSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.PressurePlateSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.PumpkinSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.RailSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.RedstoneSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.RedstoneSquareSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.SignSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.SkullSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.SpawnerSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.StairsSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.TeleportSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.TorchSnapshot;
import MoseShipsBukkit.ShipBlock.Snapshot.MaterialSnapshot.TrapSnapshot;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Utils.VersionCheckingUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticAirship;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticOPShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticWatership;

public class ShipsMain extends JavaPlugin {

	public static String NAME;
	public static String VERSION;
	public static final String[] TESTED_MC = {
			"1.11.2",
			"1.11.1",
			"1.11.0",
			"1.10.2",
			"1.10.0",
			"1.9.4" };

	static ShipsMain PLUGIN;

	private void registerSigns() {
		ShipSign.SHIP_SIGNS.add(new ShipLicenceSign());
		ShipSign.SHIP_SIGNS.add(new ShipAltitudeSign());
		ShipSign.SHIP_SIGNS.add(new ShipEngineSign());
		ShipSign.SHIP_SIGNS.add(new ShipWheelSign());
		ShipSign.SHIP_SIGNS.add(new ShipAltitudeSign());
		ShipSign.SHIP_SIGNS.add(new ShipEOTSign());
	}

	private void registerCMDs() {
		new InfoCMD();
		new DebugCMD();
		new SignCMD();
		new HelpCMD();
		new BlockListCMD();
		new AutoPilotCMD();
	}

	private void registerSnapshotTypes() {
		// banner
		BlockSnapshot.VALUE_TYPES.put(Material.STANDING_BANNER, BannerSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WALL_BANNER, BannerSnapshot.class);
		// brewing stand
		BlockSnapshot.VALUE_TYPES.put(Material.BREWING_STAND, BrewingStandSnapshot.class);
		// chest
		BlockSnapshot.VALUE_TYPES.put(Material.CHEST, ChestSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.TRAPPED_CHEST, ChestSnapshot.class);
		// ender
		BlockSnapshot.VALUE_TYPES.put(Material.ENDER_CHEST, EnderChestSnapshot.class);
		// cmd
		BlockSnapshot.VALUE_TYPES.put(Material.COMMAND, CommandBlockSnapshot.class);
		// dispenser
		BlockSnapshot.VALUE_TYPES.put(Material.DISPENSER, DispenserSnapshot.class);
		// dropper
		BlockSnapshot.VALUE_TYPES.put(Material.DROPPER, DropperSnapshot.class);
		// furnace
		BlockSnapshot.VALUE_TYPES.put(Material.FURNACE, FurnaceSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BURNING_FURNACE, FurnaceSnapshot.class);
		// observer
		BlockSnapshot.VALUE_TYPES.put(Material.OBSERVER, ObserverSnapshot.class);
		// hopper
		BlockSnapshot.VALUE_TYPES.put(Material.HOPPER, HopperSnapshot.class);
		// jukebox
		BlockSnapshot.VALUE_TYPES.put(Material.JUKEBOX, JukeBoxSnapshot.class);
		// noteblock
		BlockSnapshot.VALUE_TYPES.put(Material.NOTE_BLOCK, NoteBlockSnapshot.class);
		// pot
		BlockSnapshot.VALUE_TYPES.put(Material.FLOWER_POT, PotSnapshot.class);
		// sign
		BlockSnapshot.VALUE_TYPES.put(Material.SIGN_POST, SignSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WALL_SIGN, SignSnapshot.class);
		// skull
		BlockSnapshot.VALUE_TYPES.put(Material.SKULL, SkullSnapshot.class);
		// mob
		BlockSnapshot.VALUE_TYPES.put(Material.MOB_SPAWNER, SpawnerSnapshot.class);
		// teleport
		BlockSnapshot.VALUE_TYPES.put(Material.ENDER_PORTAL, TeleportSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.PORTAL, TeleportSnapshot.class);
		// torch
		BlockSnapshot.VALUE_TYPES.put(Material.TORCH, TorchSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.REDSTONE_TORCH_OFF, TorchSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.REDSTONE_TORCH_ON, TorchSnapshot.class);
		// log
		BlockSnapshot.VALUE_TYPES.put(Material.LOG, LogSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.LOG_2, LogSnapshot.class);
		// rail
		BlockSnapshot.VALUE_TYPES.put(Material.RAILS, RailSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.ACTIVATOR_RAIL, RailSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DETECTOR_RAIL, RailSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.POWERED_RAIL, RailSnapshot.class);
		// piston
		BlockSnapshot.VALUE_TYPES.put(Material.PISTON_BASE, PistonSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.PISTON_STICKY_BASE, PistonSnapshot.class);
		// stairs
		BlockSnapshot.VALUE_TYPES.put(Material.ACACIA_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BIRCH_WOOD_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BRICK_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.COBBLESTONE_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DARK_OAK_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.JUNGLE_WOOD_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.NETHER_BRICK_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.PURPUR_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.QUARTZ_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.RED_SANDSTONE_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SANDSTONE_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SMOOTH_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SPRUCE_WOOD_STAIRS, StairsSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WOOD_STAIRS, StairsSnapshot.class);
		// craft bench

		// fire
		BlockSnapshot.VALUE_TYPES.put(Material.FIRE, FireSnapshot.class);
		// ladder
		BlockSnapshot.VALUE_TYPES.put(Material.LADDER, LadderSnapshot.class);
		// lever
		BlockSnapshot.VALUE_TYPES.put(Material.LEVER, LeverSnapshot.class);
		// pressure plate
		BlockSnapshot.VALUE_TYPES.put(Material.GOLD_PLATE, PressurePlateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.IRON_PLATE, PressurePlateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.STONE_PLATE, PressurePlateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WOOD_PLATE, PressurePlateSnapshot.class);
		// button
		BlockSnapshot.VALUE_TYPES.put(Material.STONE_BUTTON, ButtonSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.WOOD_BUTTON, ButtonSnapshot.class);
		// pumpkin
		BlockSnapshot.VALUE_TYPES.put(Material.PUMPKIN, PumpkinSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.JACK_O_LANTERN, PumpkinSnapshot.class);
		// redstone
		BlockSnapshot.VALUE_TYPES.put(Material.REDSTONE_COMPARATOR_OFF, RedstoneSquareSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.REDSTONE_COMPARATOR_ON, RedstoneSquareSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.REDSTONE_WIRE, RedstoneSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.TRIPWIRE, RedstoneSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DIODE_BLOCK_OFF, RedstoneSquareSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DIODE_BLOCK_ON, RedstoneSquareSnapshot.class);
		// trap
		BlockSnapshot.VALUE_TYPES.put(Material.TRAP_DOOR, TrapSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.IRON_TRAPDOOR, TrapSnapshot.class);
		// gate
		BlockSnapshot.VALUE_TYPES.put(Material.FENCE_GATE, GateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.ACACIA_FENCE_GATE, GateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.BIRCH_FENCE_GATE, GateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.DARK_OAK_FENCE_GATE, GateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.JUNGLE_FENCE_GATE, GateSnapshot.class);
		BlockSnapshot.VALUE_TYPES.put(Material.SPRUCE_FENCE_GATE, GateSnapshot.class);
		// hook
		BlockSnapshot.VALUE_TYPES.put(Material.TRIPWIRE_HOOK, HookSnapshot.class);
		// carpet
		BlockSnapshot.VALUE_TYPES.put(Material.CARPET, CarpetSnapshot.class);
		//bed
		BlockSnapshot.VALUE_TYPES.put(Material.BED_BLOCK, BedSnapshot.class);
		//end gateway
		BlockSnapshot.VALUE_TYPES.put(Material.END_GATEWAY, TeleportSnapshot.class);
	}

	private void registerShipTypes() {
		new StaticOPShip();
		new StaticAirship();
		new StaticWatership();
		/*new WaterShip.StaticWaterShip();
		new HybridShip.StaticHybridShip();
		new Marsship.StaticMarsship();*/
	}

	private void loadShips() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				String goodFiles = null;
				for (StaticShipType type : StaticShipTypeUtil.getTypes()) {
					String badFiles = null;
					File folder = new File("plugins/Ships/VesselData/" + type.getName());
					File[] files = folder.listFiles();
					if (files != null) {
						for (File file : files) {
							SOptional<LiveShip> opShip = Loader.loadFromFile(file);
							if (!opShip.isPresent()) {
								if (badFiles == null) {
									badFiles = file.getName().replace(".yml", "");
								} else {
									badFiles = badFiles + ", " + file.getName().replace(".yml", "");
								}
							} else {
								if (goodFiles == null) {
									goodFiles = opShip.get().getName();
								} else {
									goodFiles = goodFiles + ", " + opShip.get().getName();
								}
							}
						}
						if (badFiles != null) {
							Bukkit.getServer().getConsoleSender()
									.sendMessage(ChatColor.RED + "The following " + type.getName()
											+ " have issues loading. Use '/Ships info <ship name>' if you want more detail on the failed load \n "
											+ badFiles);
						}
					}
				}
				if (goodFiles != null) {
					Bukkit.getServer().getConsoleSender().sendMessage(
							ChatColor.AQUA + "The following ships are loading without issue \n" + goodFiles);
				} else {
					Bukkit.getServer().getConsoleSender().sendMessage(
							ChatColor.GREEN + "Issue loading Ships data. Is this your first time using Ships?");
				}
			}

		}, 1);
	}

	private void displayVersionChecking() {
		VersionCheckingUtil.VersionOutcome previous = null;
		for (String tested : TESTED_MC) {
			VersionCheckingUtil.VersionOutcome outcome = VersionCheckingUtil
					.isGreater(VersionCheckingUtil.MINECRAFT_VERSION, VersionCheckingUtil.convert(tested));
			if (outcome.equals(VersionCheckingUtil.VersionOutcome.EQUAL)) {
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
		PluginCommand command = getCommand("Ships");
		ShipsCMD.Executer cmd = new ShipsCMD.Executer();
		command.setExecutor(cmd);
		command.setTabCompleter(cmd);
		BlockList.BLOCK_LIST.reload();
		registerSigns();
		registerShipTypes();
		registerSnapshotTypes();
		registerCMDs();
		displayVersionChecking();
		loadShips();
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
