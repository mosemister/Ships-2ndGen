package MoseShipsBukkit.ShipsTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.MovingShip.MovementMethod;
import MoseShipsBukkit.MovingShip.MovingBlock;
import MoseShipsBukkit.StillShip.Vessel.MovableVessel;
import MoseShipsBukkit.StillShip.Vessel.ProtectedVessel;
import MoseShipsBukkit.Utils.ConfigLinks.Messages;

public abstract class VesselType{

	String TYPENAME;
	int DEFAULTSPEED;
	int DEFAULTBOOSTSPEED;
	int MIN_BLOCKS;
	int MAX_BLOCKS;
	boolean ISAUTOPILOTALLOWED;
	List<Material> MOVEIN;
	
	static List<VesselType> CUSTOMVESSELS = new ArrayList<VesselType>();
	
	public abstract boolean checkRequirements(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks, @Nullable Player player);
	public abstract boolean shouldFall(ProtectedVessel vessel);
	public abstract File getTypeFile();
	public abstract VesselType clone();
	public abstract void loadVesselFromFiveFile(ProtectedVessel vessel, File file);
	public abstract void createConfig();
	public abstract void loadDefault();
	public abstract void save(ProtectedVessel vessel);
	
	public VesselType(String name, List<Material> movein, int speed, int boost, boolean isAutoPilot){
		TYPENAME = name;
		DEFAULTSPEED = speed;
		ISAUTOPILOTALLOWED = isAutoPilot;
	}
	
	public void cloneVesselTypeData(VesselType type){
		type.TYPENAME = this.TYPENAME;
		type.DEFAULTSPEED = this.DEFAULTSPEED;
		type.DEFAULTBOOSTSPEED = this.DEFAULTBOOSTSPEED;
		type.MIN_BLOCKS = this.MIN_BLOCKS;
		type.MAX_BLOCKS = this.MAX_BLOCKS;
		type.ISAUTOPILOTALLOWED = this.ISAUTOPILOTALLOWED;
		type.MOVEIN = this.MOVEIN;
	}
	
	public String getName(){
		return TYPENAME;
	}
	
	public int getDefaultSpeed(){
		return DEFAULTSPEED;
	}
	
	public List<Material> getMoveInMaterials(){
		return MOVEIN;
	}
	
	public void setMoveInMaterials(List<Material> material){
		MOVEIN = material;
	}
	
	public void setDefaultSpeed(int A){
		DEFAULTSPEED = A;
	}
	
	public void setDefaultBoostSpeed(int A){
		DEFAULTBOOSTSPEED = A;
	}
	
	public int getDefaultBoostSpeed(){
		return DEFAULTBOOSTSPEED;
	}
	
	public boolean isAutoPilotAllowed(){
		return ISAUTOPILOTALLOWED;
	}
	
	public int getMinBlocks(){
		return MIN_BLOCKS;
	}
	
	public int getMaxBlocks(){
		return MAX_BLOCKS;
	}
	
	public void setMinBlocks(int A){
		MIN_BLOCKS = A;
	}
	
	public void setMaxBlocks(int A){
		MAX_BLOCKS = A;
	}
	
	public boolean attemptToMove(MovableVessel vessel, MovementMethod move, List<MovingBlock> blocks, @Nullable Player player){
		if(blocks.size() <= getMaxBlocks()){
			if(blocks.size() >= getMinBlocks()){
				return checkRequirements(vessel, move, blocks, player);
			}else{
				if (player != null){
					if (Messages.isEnabled()){
						player.sendMessage(Ships.runShipsMessage(Messages.getShipTooSmall(blocks.size(), getMinBlocks()), true));
					}
				}
				return false;
			}
		}else{
			if (player != null){
				if (Messages.isEnabled()){
					player.sendMessage(Ships.runShipsMessage(Messages.getShipTooBig(blocks.size(), getMaxBlocks()), true));
				}
			}
			return false;
		}
	}
	
	//adds custom vessels to Ships (newer method)
	public void inject(){
		CUSTOMVESSELS.add(this);
	}
	
	//gets a vesselType by its name, returns VesselType if found, returns null in non are found
	public static VesselType getTypeByName(String name){
		for(VesselType type : VesselType.values()){			
			if (type.getName().equalsIgnoreCase(name)){
				return type;
			}
		}
		return null;
	}
	
	//gets all the custom vesselTypes
	public static List<VesselType> customValues(){
		List<VesselType> types = CUSTOMVESSELS;
		return types;
	}
	
	//gets all vesselTypes no matter if they are custom or not
	public static List<VesselType> values(){
		List<VesselType> types = new ArrayList<VesselType>();
		types.addAll(CUSTOMVESSELS);
		for(VesselTypes type : VesselTypes.values()){
			types.add(type.get());
		}
		return types;
	}
}