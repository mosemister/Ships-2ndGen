package MoseShipsSponge.Ships.Utils;

import java.util.Optional;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Ships.Movement.Movement.Rotate;

public class StoredMovement {
	
	int X, Y, Z;
	Location<World> TELEPORT;
	Rotate ROTATE;
	Cause CAUSE;
	
	public StoredMovement(Location<World> teleport, Rotate rotate, int x, int y, int z, Cause cause){
		X = x;
		Y = y;
		Z = z;
		TELEPORT = teleport;
		ROTATE = rotate;
		CAUSE = cause;
	}
	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public int getZ(){
		return Z;
	}
	
	public Optional<Location<World>> getTeleportTo(){
		return Optional.ofNullable(TELEPORT);
	}
	
	public Optional<Rotate> getRotation(){
		return Optional.ofNullable(ROTATE);
	}
	
	public Location<World> getEndResult(Location<World> start){
		if (TELEPORT != null) {
			Location<World> ret = TELEPORT.add(X, Y, Z);
			return ret;
		} else {
			Location<World> ret = start.add(X, Y, Z);
			return ret;
		}
	}
	
	public Cause getCause(){
		return CAUSE;
	}
	
	public static class Builder{
		
		int X, Y, Z;
		Location<World> TELEPORT;
		Rotate ROTATE;
		Cause CAUSE;
		
		public Builder setX(int x){
			X = x;
			return this;
		}
		
		public Builder setY(int y){
			Y = y;
			return this;
		}
		
		public Builder setZ(int z){
			Z = z;
			return this;
		}
		
		public Builder setTeleportTo(Location<World> loc){
			TELEPORT = loc;
			return this;
		}
		
		public Builder setRotation(Rotate rotate){
			ROTATE = rotate;
			return this;
		}
		
		public Cause getCause(){
			return CAUSE;
		}
		
		public Builder setCause(Cause cause){
			CAUSE = cause;
			return this;
		}
		
		public StoredMovement build(){
			return new StoredMovement(TELEPORT, ROTATE, X, Y, Z, CAUSE);
		}
	}

}
