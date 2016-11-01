package MoseShipsBukkit.Configs.Files;

import MoseShipsBukkit.Configs.BasicConfig;

public class ShipsConfig extends BasicConfig {

	public static final String PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT = "Advanced.Structure.StructureLimits.TrackLimits";
	public static final String PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE = "Advanced.Structure.StructureLimits.TrackRepeate";
	public static final String PATH_ALGORITHMS_BLOCKFINDER = "Advanced.Algorithms.BlockFinder";
	public static final String PATH_ALGORITHMS_MOVEMENT = "Advanced.Algorithms.Movement";
	public static final String PATH_ALGORITHMS_CLEARFREQUENCY = "Advanced.Algorithms.ClearFrequency";

	public static final String PATH_ONSNEAK_REMOVE_SHIP = "Configuration.OnSneak.RemoveShipsBlocks";
	public static final String PATH_COLLIDE_SHOW_TIME = "Configuration.Collide.DisplayTime";

	public static final ShipsConfig CONFIG = new ShipsConfig("Configuration/Config");

	public ShipsConfig(String path) {
		super(path);
		applyMissing();
	}

	public ShipsConfig applyMissing() {
		set(300, PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		set(2, PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
		// this will be Ships6 when that algorithm is done
		set("Ships 6", PATH_ALGORITHMS_MOVEMENT);
		set("Ships 5", PATH_ALGORITHMS_BLOCKFINDER);
		set(10, PATH_ALGORITHMS_CLEARFREQUENCY);
		// ONSNEAK stuff
		set(false, PATH_ONSNEAK_REMOVE_SHIP);
		// COLLIDE stuff
		set(3, PATH_COLLIDE_SHOW_TIME);
		save();
		return this;
	}

}
