package MoseShipsSponge.Vessel.Common.RootTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.ShipBlock.Structure.ShipStructure;

public interface ShipsData {

	public String getName();

	public Optional<User> getOwner();

	public List<User> getSubPilots();

	public World getWorld();

	public Map<String, String> getBasicData();

	public ShipStructure getStructure();

	public List<Entity> getEntities();

	public Location<World> getLocation();

	public Location<World> getTeleportToLocation();

	public default List<Location<World>> getBasicStructure() {
		return getStructure().getRaw();
	}

	public default Optional<Sign> getLicence() {
		Optional<TileEntity> opTile = getLocation().getTileEntity();
		if (opTile.isPresent()) {
			TileEntity tile = opTile.get();
			if (tile instanceof Sign) {
				Sign sign = (Sign) tile;
				return Optional.of(sign);
			}
		}
		return Optional.empty();
	}

	public boolean hasLocation(Location<World> loc);

	public ShipsData setOwner(User data);

	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence);

	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence,
			Location<World> teleport);

	public ShipsData setTeleportToLocation(Location<World> loc);

	public List<Location<World>> updateBasicStructure();

}
