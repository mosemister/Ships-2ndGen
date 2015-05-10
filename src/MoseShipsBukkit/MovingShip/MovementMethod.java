package MoseShipsBukkit.MovingShip;

import java.io.IOException;

import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Ships;
import MoseShipsBukkit.StillShip.Vessel;

public enum MovementMethod {
	
	ROTATE_LEFT("Rotate Left", 270),
	ROTATE_RIGHT("Rotate Right", 90),
	MOVE_FORWARD("Move Forward"),
	MOVE_BACKWARD("Move Backward"),
	MOVE_LEFT("Move Left"),
	MOVE_RIGHT("Move Right"),
	MOVE_UP("Move Up"),
	MOVE_DOWN("Move Down");
	
	int SPEED;
	String DIRECTION;
	
	private MovementMethod(String direction){
		DIRECTION = direction;
	}
	
	private MovementMethod(String direction, int rotate){
		DIRECTION = direction;
		SPEED = rotate;
	}
	
	public int getSpeed(){
		return SPEED;
	}
	
	public String getDirection(){
		return DIRECTION;
	}
	
	public void setSpeed(int speed){
		SPEED = speed;
	}
	
	public void setDirection(String direction){
		DIRECTION = direction;
	}
	
	public static MovementMethod getMovingDirection(Vessel vessel, BlockFace blockface){
		BlockFace vesselFace = vessel.getFacingDirection();
		if (vesselFace.equals(blockface)){
			return MovementMethod.MOVE_FORWARD;
		}else if (vesselFace.getOppositeFace().equals(blockface)){
			return MovementMethod.MOVE_BACKWARD;
		}else if (Ships.getSideFace(vesselFace, true).equals(blockface)){
			return MovementMethod.MOVE_LEFT;
		}else if (Ships.getSideFace(vesselFace, false).equals(blockface)){
			return MovementMethod.MOVE_RIGHT;
		}else{
			new IOException("getDirection does not support that BlockFace");
			return null;
		}
	}

}
