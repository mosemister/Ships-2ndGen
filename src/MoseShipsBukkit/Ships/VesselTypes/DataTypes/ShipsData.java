package MoseShipsBukkit.Ships.VesselTypes.DataTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;

public interface ShipsData {
	
	public Optional<Sign> getLicence();
	public String getName();
	public Optional<OfflinePlayer> getOwner();
	public List<OfflinePlayer> getSubPilots();
	public World getWorld();
	public Map<String, String> getBasicData();
	public List<Block> getBasicStructure();
	public List<Entity> getEntities();
	public Location getLocation();
	public Location getTeleportToLocation();
	
	public boolean hasLocation(Location loc);
	
	public ShipsData setOwner(OfflinePlayer player);
	public List<Block> setBasicStructure(List<Block> locs, Block licence);
	public List<Block> setBasicStructure(List<Block> locs, Block licence, Location teleport);
	public ShipsData setTeleportToLocation(Location loc);
	
	public List<Block> updateBasicStructure();

}
