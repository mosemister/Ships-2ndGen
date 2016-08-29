package MoseShipsSponge.Ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShips.CustomDataHolder.DataHolder;

import MoseShipsSponge.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Configs.Files.ShipsConfig;

public class ShipsData extends DataHolder {

	public static final Object[] DATABASE_NAME = {
		"ShipsMeta",
		"Name"
	};
	public static final Object[] DATABASE_TYPE = {
		"ShipsMeta",
		"Type"
	};
	public static final Object[] DATABASE_PILOT = {
		"ShipsMeta",
		"Pilot"
	};
	public static final Object[] DATABASE_SUB_PILOTS = {
		"ShipsMeta",
		"Sub_Pilots"
	};
	public static final Object[] DATABASE_WORLD = {
		"ShipsMeta",
		"Location",
		"World"
	};
	public static final Object[] DATABASE_BLOCK = {
		"ShipsMeta",
		"Location",
		"Block"
	};
	public static final Object[] DATABASE_TELEPORT = {
		"ShipsMeta",
		"Location",
		"Teleport"
	};
	public static final Object[] DATABASE_STRUCTURE = {
		"ShipsStructure",
		"Basic"
	};

	protected String NAME;
	protected User USER;
	protected List<User> SUB_PILOTS = new ArrayList<>();
	protected List<Location<World>> STRUCTURE = new ArrayList<>();
	protected Location<World> MAIN_BLOCK;
	protected Location<World> TELEPORT;

	public ShipsData(String name, Location<World> sign, Location<World> teleport) {
		NAME = name;
		MAIN_BLOCK = sign;
		TELEPORT = teleport;
	}

	public ShipsData(ShipsData data) {
		data.cloneOnto(this);
	}

	public Optional<Sign> getLicence() {
		Optional<TileEntity> entity = MAIN_BLOCK.getTileEntity();
		if (entity.isPresent()) {
			if (entity.get() instanceof Sign) {
				return Optional.of((Sign) entity.get());
			}
		}
		return null;
	}

	public String getName() {
		return NAME;
	}

	public Optional<User> getOwner() {
		return Optional.ofNullable(USER);
	}

	public ShipsData setOwner(User user) {
		USER = user;
		return this;
	}

	public List<User> getSubPilots() {
		return SUB_PILOTS;
	}
	
	public World getWorld(){
		return MAIN_BLOCK.getExtent();
	}

	public List<Location<World>> getBasicStructure() {
		return STRUCTURE;
	}
	
	public boolean hasLocation(Location<World> loc){
		if(!loc.getExtent().equals(getWorld())){
			return false;
		}
		STRUCTURE.stream().anyMatch(b -> {
			return b.getBlockPosition().equals(loc.getBlockPosition());
		});
		return false;
	}

	public List<Location<World>> updateBasicStructure() {
		int trackLimit = ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.PATH_STRUCTURE_STRUCTURELIMITS_TRACKLIMIT);
		List<Location<World>> list = BasicBlockFinder.getConfigSelected().getConnectedBlocks(trackLimit, MAIN_BLOCK);
		STRUCTURE = list;
		return list;
	}

	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence) {
		STRUCTURE = locs;
		MAIN_BLOCK = licence;
		return locs;
	}

	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence, Location<World> teleport) {
		STRUCTURE = locs;
		MAIN_BLOCK = licence;
		TELEPORT = teleport;
		return locs;
	}

	public Location<World> getLocation() {
		return MAIN_BLOCK;
	}

	public Location<World> getTeleportToLocation() {
		return TELEPORT;
	}

	public ShipsData setTeleportToLocation(Location<World> loc) {
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
