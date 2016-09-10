package MoseShipsBukkit.Ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import MoseShips.CustomDataHolder.DataHolder;

import MoseShipsBukkit.BlockFinder.BlockFinderUtils;
import MoseShipsBukkit.Configs.Files.ShipsConfig;
import MoseShipsBukkit.Utils.LocationUtils;

public class ShipsData extends DataHolder {

	public static final String DATABASE_NAME = "ShipsMeta.Name";
	public static final String DATABASE_TYPE = "ShipsMeta.Type";
	public static final String DATABASE_PILOT = "ShipsMeta.Pilot";
	public static final String DATABASE_SUB_PILOTS = "ShipsMeta.Sub_Pilots";
	public static final String DATABASE_WORLD = "ShipsMeta.Location.World";
	public static final String DATABASE_BLOCK = "ShipsMeta.Location.Block";
	public static final String DATABASE_TELEPORT = "ShipsMeta.Location.Teleport";
	public static final String DATABASE_STRUCTURE = "ShipsStructure.Basic";

	protected String NAME;
	protected OfflinePlayer USER;
	protected List<OfflinePlayer> SUB_PILOTS = new ArrayList<OfflinePlayer>();
	protected List<Block> STRUCTURE = new ArrayList<Block>();
	protected Block MAIN_BLOCK;
	protected Location TELEPORT;

	public ShipsData(String name, Block sign, Location teleport) {
		NAME = name;
		MAIN_BLOCK = sign;
		TELEPORT = teleport;
	}

	public ShipsData(ShipsData data) {
		data.cloneOnto(this);
	}

	public Optional<Sign> getLicence() {
		if (MAIN_BLOCK.getState() instanceof Sign) {
			return Optional.of((Sign) MAIN_BLOCK.getState());
		}
		return Optional.empty();
	}

	public String getName() {
		return NAME;
	}

	public Optional<OfflinePlayer> getOwner() {
		return Optional.ofNullable(USER);
	}

	public ShipsData setOwner(OfflinePlayer user) {
		USER = user;
		return this;
	}

	public List<OfflinePlayer> getSubPilots() {
		return SUB_PILOTS;
	}

	public World getWorld() {
		return MAIN_BLOCK.getWorld();
	}

	public List<Block> getBasicStructure() {
		return STRUCTURE;
	}

	public boolean hasLocation(Location loc) {
		if (!loc.getWorld().equals(getWorld())) {
			return false;
		}
		for (Block block : STRUCTURE) {
			if (LocationUtils.blocksEqual(block.getLocation(), loc)) {
				return true;
			}
		}
		return false;
	}

	public List<Block> updateBasicStructure() {
		Integer trackLimit = ShipsConfig.CONFIG.get(Integer.class,
				ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		if (trackLimit == null) {
			trackLimit = 5000;
		}
		List<Block> list = BlockFinderUtils.getConfigSelected().getConnectedBlocks(trackLimit, MAIN_BLOCK);
		STRUCTURE = list;
		return list;
	}

	public List<Block> setBasicStructure(List<Block> locs, Block licence) {
		STRUCTURE = locs;
		MAIN_BLOCK = licence;
		return locs;
	}

	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport) {
		STRUCTURE = locs;
		MAIN_BLOCK = licence;
		TELEPORT = teleport;
		return locs;
	}

	public Location getLocation() {
		return MAIN_BLOCK.getLocation();
	}

	public Location getTeleportToLocation() {
		return TELEPORT;
	}

	public ShipsData setTeleportToLocation(Location loc) {
		TELEPORT = loc;
		return this;
	}

	public ShipsData cloneOnto(ShipsData data) {
		data.MAIN_BLOCK = this.MAIN_BLOCK;
		data.NAME = this.NAME;
		data.STRUCTURE = this.STRUCTURE;
		data.SUB_PILOTS = this.SUB_PILOTS;
		data.TELEPORT = this.TELEPORT;
		data.USER = this.USER;
		data.DATA = this.DATA;
		return data;
	}

}
