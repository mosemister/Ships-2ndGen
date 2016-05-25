package MoseShipsSponge.Ships;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ShipsData {
	
	public static final Object[] DATABASE_NAME = {"ShipsMeta", "Name"};
	public static final Object[] DATABASE_PILOT = {"ShipsMeta", "Pilot"};
	public static final Object[] DATABASE_SUB_PILOTS = {"ShipsMeta", "Sub_Pilots"};
	public static final Object[] DATABASE_WORLD = {"ShipsMeta", "Location", "World"};
	public static final Object[] DATABASE_BLOCK = {"ShipsMeta", "Location", "Block"};
	public static final Object[] DATABASE_TELEPORT = {"ShipsMeta", "Location", "Teleport"};
	
	String NAME;
	User USER;
	List<User> SUB_PILOTS;
	List<Location<World>> STRUCTURE;
	Location<World> MAIN_BLOCK;
	Location<World> TELEPORT;
	
	public ShipsData(String name, User host, Location<World> sign, Location<World> teleport){
		NAME = name;
		USER = host;
		MAIN_BLOCK = sign;
		TELEPORT = teleport;
	}
	
	public Optional<Sign> getLicence(){
		Optional<TileEntity> entity = MAIN_BLOCK.getTileEntity();
		if(entity.isPresent()){
			if(entity.get() instanceof Sign){
				return Optional.of((Sign)entity.get());
			}
		}
		return null;
	}
	
	public String getName(){
		return NAME;
	}
	
	public User getOwner(){
		return USER;
	}
	
	public List<User> getSubPilots(){
		return SUB_PILOTS;
	}
	
	public List<Location<World>> getBasicStructure(){
		return STRUCTURE;
	}
	
	public List<Location<World>> updateBasicStructure(){
		return null;
	}
	
	public Location<World> getLocation(){
		return MAIN_BLOCK;
	}
	
	public Location<World> getTeleportToLocation(){
		return TELEPORT;
	}
	
	public ShipsLocalDatabase getLocalDatabase() throws IOException{
		return new ShipsLocalDatabase(this);
	}
	
	public ShipsData cloneOnto(ShipsData data){
		data.MAIN_BLOCK = this.MAIN_BLOCK;
		data.NAME = this.NAME;
		data.STRUCTURE = this.STRUCTURE;
		data.SUB_PILOTS = this.SUB_PILOTS;
		data.TELEPORT = this.TELEPORT;
		data.USER = this.USER;
		return data;
	}
	
}
