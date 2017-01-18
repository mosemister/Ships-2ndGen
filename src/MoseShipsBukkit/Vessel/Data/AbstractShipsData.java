package MoseShipsBukkit.Vessel.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsBukkit.ShipBlock.Structure.ShipStructure;
import MoseShipsBukkit.Utils.LocationUtil;

public class AbstractShipsData extends DataHolder implements ShipsData {

	public static final String DATABASE_NAME = "ShipsMeta.Name";
	public static final String DATABASE_TYPE = "ShipsMeta.Type";
	public static final String DATABASE_PILOT = "ShipsMeta.Pilot";
	public static final String DATABASE_SUB_PILOTS = "ShipsMeta.Sub_Pilots";
	public static final String DATABASE_WORLD = "ShipsMeta.Location.World";
	public static final String DATABASE_BLOCK = "ShipsMeta.Location.Block";
	public static final String DATABASE_TELEPORT = "ShipsMeta.Location.Teleport";
	public static final String DATABASE_STRUCTURE = "ShipsStructure.BasicStructure";

	protected String g_name;
	protected OfflinePlayer g_user;
	protected List<OfflinePlayer> g_sub_pilots = new ArrayList<OfflinePlayer>();
	protected ShipStructure g_structure = new ShipStructure(this);
	protected Block g_main;
	protected Location g_teleport;

	public AbstractShipsData(String name, Block sign, Location teleport) {
		g_name = name;
		g_main = sign;
		if (teleport == null) {
			g_teleport = sign.getLocation();
		} else {
			g_teleport = teleport;
		}
	}

	public AbstractShipsData(ShipsData data) {
		g_name = data.getName();
		Optional<OfflinePlayer> opOwner = data.getOwner();
		if(opOwner.isPresent()){
			g_user = opOwner.get();
		}
		g_sub_pilots = data.getSubPilots();
		g_structure = data.getStructure();
		g_main = data.getLocation().getBlock();
		g_teleport = data.getTeleportToLocation();
	}

	@Override
	public Optional<Sign> getLicence() {
		if (g_main.getState() instanceof Sign) {
			return Optional.of((Sign) g_main.getState());
		}
		return Optional.empty();
	}

	@Override
	public String getName() {
		return g_name;
	}

	@Override
	public Optional<OfflinePlayer> getOwner() {
		return Optional.ofNullable(g_user);
	}

	@Override
	public AbstractShipsData setOwner(OfflinePlayer user) {
		g_user = user;
		return this;
	}

	@Override
	public List<OfflinePlayer> getSubPilots() {
		return g_sub_pilots;
	}

	@Override
	public World getWorld() {
		return g_main.getWorld();
	}

	@Override
	public Map<String, String> getBasicData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Name: ", g_name);
		map.put("Licence: ", g_main.getX() + ", " + g_main.getY() + ", " + g_main.getZ() + ", " + g_main.getWorld().getName());
		map.put("Teleport: ", g_teleport.getX() + ", " + g_teleport.getY() + ", " + g_teleport.getZ() + ", " + g_teleport.getWorld().getName());
		map.put("Structure size:", g_structure.getRaw().size() + "");
		return map;
	}

	@Override
	public List<Block> getBasicStructure() {
		return g_structure.getRaw();
	}

	@Override
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Entity entity : g_main.getWorld().getEntities()) {
			Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
			if (g_structure.getRaw().contains(block)) {
				entities.add(entity);
			}
		}
		return entities;
	}

	@Override
	public boolean hasLocation(Location loc) {
		if (!loc.getWorld().equals(getWorld())) {
			return false;
		}
		for (Block block : g_structure.getRaw()) {
			if (LocationUtil.blocksEqual(block.getLocation(), loc)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Block> updateBasicStructure() {
		g_structure.updateStructure();
		return g_structure.getRaw();
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence) {
		List<Block> list = g_structure.getRaw();
		list.clear();
		list.addAll(locs);
		g_main = licence;
		return locs;
	}

	@Override
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport) {
		setBasicStructure(locs, licence);
		g_teleport = teleport;
		return locs;
	}

	@Override
	public Location getLocation() {
		return g_main.getLocation();
	}

	@Override
	public Location getTeleportToLocation() {
		return g_teleport;
	}

	@Override
	public AbstractShipsData setTeleportToLocation(Location loc) {
		g_teleport = loc;
		return this;
	}

	@Override
	public ShipStructure getStructure() {
		return g_structure;
	}

}
