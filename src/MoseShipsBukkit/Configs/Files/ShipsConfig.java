package MoseShipsBukkit.Configs.Files;

import MoseShipsBukkit.Configs.BasicConfig;

public class ShipsConfig extends BasicConfig {

	public static final String PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT = "Structure.StructureLimits.TrackLimits";
	public static final String PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE = "Structure.StructureLimits.TrackRepeate";
	public static final String PATH_ALGORITHMS_BLOCKFINDER = "Algorithms.BlockFinder";
	public static final String PATH_ALGORITHMS_MOVEMENT = "Algorithms.Movement";

	public static final ShipsConfig CONFIG = new ShipsConfig();

	public ShipsConfig() {
		super("Configuration/Config");
		applyMissing();
	}

	public ShipsConfig applyMissing() {
		set(300, PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		set(2, PATH_STRUCTURE_STRUCTURELIMITS_TRACKREPEATE);
		// this will be Ships6 when that algorithm is done
		set("Ships6", PATH_ALGORITHMS_MOVEMENT);
		set("Ships5", PATH_ALGORITHMS_BLOCKFINDER);
		save();
		return this;
	}

}
