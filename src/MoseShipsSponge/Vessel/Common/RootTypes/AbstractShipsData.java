package MoseShipsSponge.Vessel.Common.RootTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.CustomDataHolder.DataHolder;
import MoseShipsSponge.ShipBlock.Structure.ShipStructure;
import MoseShipsSponge.Utils.LocationUtils;

public class AbstractShipsData extends DataHolder implements ShipsData {

	public static final Object[] DATABASE_NAME = {
			"ShipsMeta",
			"Name" };
	public static final Object[] DATABASE_TYPE = {
			"ShipsMeta",
			"Type" };
	public static final Object[] DATABASE_PILOT = {
			"ShipsMeta",
			"Pilot" };
	public static final Object[] DATABASE_SUB_PILOTS = {
			"ShipsMeta",
			"Sub_Pilots" };
	public static final Object[] DATABASE_WORLD = {
			"ShipsMeta",
			"Location",
			"World" };
	public static final Object[] DATABASE_BLOCK = {
			"ShipsMeta",
			"Location",
			"Block" };
	public static final Object[] DATABASE_TELEPORT = {
			"ShipsMeta",
			"Location",
			"Teleport" };
	public static final Object[] DATABASE_STRUCTURE = {
			"ShipsStructure",
			"BasicStructure" };

	protected String g_name;
	protected User g_user;
	protected List<User> g_sub_pilots = new ArrayList<>();
	protected ShipStructure g_structure = new ShipStructure();
	protected Location<World> g_main;
	protected Vector3i g_teleport;

	public AbstractShipsData(String name, Location<World> sign, Vector3i teleport) {
		g_name = name;
		g_main = sign;
		if (teleport == null) {
			g_teleport = sign.getBlockPosition();
		} else {
			g_teleport = teleport;
		}
	}

	public AbstractShipsData(ShipsData data) {
		g_name = data.getName();
		Optional<User> opUser = data.getOwner();
		if (opUser.isPresent()) {
			g_user = opUser.get();
		}
		g_sub_pilots = data.getSubPilots();
		g_structure = data.getStructure();
		g_main = data.getLocation();
		g_teleport = data.getTeleportToLocation().getBlockPosition();
	}

	@Override
	public String getName() {
		return g_name;
	}

	@Override
	public Optional<User> getOwner() {
		return Optional.ofNullable(g_user);
	}

	@Override
	public List<User> getSubPilots() {
		return g_sub_pilots;
	}

	@Override
	public World getWorld() {
		return g_main.getExtent();
	}

	@Override
	public Map<String, String> getBasicData() {
		Map<String, String> map = new HashMap<>();
		map.put("Name", g_name);
		map.put("Licence",
				g_main.getX() + ", " + g_main.getY() + ", " + g_main.getZ() + ", " + g_main.getExtent().getName());
		map.put("Teleport", g_teleport.getX() + ", " + g_teleport.getY() + ", " + g_teleport.getZ() + ", "
				+ g_main.getExtent().getName());
		map.put("Structure size", g_structure.getRaw().size() + "");
		return map;
	}

	@Override
	public List<Entity> getEntities() {
		return g_main.getExtent().getEntities().stream().filter(
				e -> LocationUtils.blockWorldContains(getBasicStructure(), e.getLocation().getRelative(Direction.DOWN)))
				.collect(Collectors.toList());
	}

	@Override
	public ShipStructure getStructure() {
		return g_structure;
	}

	@Override
	public Location<World> getLocation() {
		return g_main;
	}

	@Override
	public Location<World> getTeleportToLocation() {
		return g_main.getExtent().getLocation(g_teleport);
	}

	@Override
	public boolean hasLocation(Location<World> loc) {
		return LocationUtils.blockWorldContains(getBasicStructure(), loc);
	}

	@Override
	public ShipsData setOwner(User data) {
		g_user = data;
		return this;
	}

	@Override
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence) {
		g_structure.setStructure(locs);
		g_main = licence;
		return locs;
	}

	@Override
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence,
			Location<World> teleport) {
		setBasicStructure(locs, licence);
		g_teleport = teleport.getBlockPosition();
		return locs;
	}

	@Override
	public ShipsData setTeleportToLocation(Location<World> loc) {
		g_teleport = loc.getBlockPosition();
		return this;
	}

	@Override
	public List<Location<World>> updateBasicStructure() {
		return g_structure.updateStructure(this).getRaw();
	}
		
	@Override
	public void updateBasicStructureOvertime(Runnable runnable) {
		g_structure.updateStructure(this, runnable);
	}

}
