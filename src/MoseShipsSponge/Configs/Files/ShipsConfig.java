package MoseShipsSponge.Configs.Files;

import MoseShipsSponge.Configs.BasicConfig;

public class ShipsConfig extends BasicConfig{
	
	public static final Object[] PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT = {"Structure", "StructureLimits", "TrackLimits"};
	
	public static final Object[] PATH_ALGORITHMS_BLOCKFINDER = {"Algorithms", "BlockFinder"};
	public static final Object[] PATH_ALGORITHMS_MOVEMENT = {"Algorithms", "Movement"};
	
	public ShipsConfig() {
		super("Configuration/Config");
	}

}
