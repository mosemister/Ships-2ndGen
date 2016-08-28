package MoseShipsSponge.Events.Vessel.Section;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Ships.VesselTypes.ShipType;

/**
 * The SectionDetectedEvent will fire when a part of a ship is detected,
 * this could be a block, entity or even the whole vessel intself
 */

public class SectionDetectedEvent extends AbstractEvent{

	Cause CAUSE;
	
	public SectionDetectedEvent(Cause cause){
		CAUSE = cause;
	}
	
	@Override
	public Cause getCause() {
		return CAUSE;
	}
	
	public static class BlockDetectedEvent extends SectionDetectedEvent{
		
		/**
		 * BlockDetectedEvent is fired by the default algorithms when they
		 * detect a block.
		 * 
		 *  please note that this will likely be removed
		 */
		
		Location<World> BLOCK;
		
		public BlockDetectedEvent(Location<World> loc, Cause cause){
			super(cause);
			BLOCK = loc;
		}
		
		public Location<World> getBlock(){
			return BLOCK;
		}
		
	}
	
	public static class VesselDetectedEvent extends BlockDetectedEvent{
		
		/**
		 * this is fired when a ShipType sign is found and read
		 */
		
		ShipType TYPE;
		
		public VesselDetectedEvent(ShipType type, Cause cause) {
			super(type.getLocation(), cause);
			TYPE = type;
		}
		
		public ShipType getVessel(){
			return TYPE;
		}
		
	}
	
	public static class EntityDetectedEvent extends SectionDetectedEvent{
		
		/**
		 * this is fired when a entity is located on the ship
		 * please note this is likely going to be removed
		 */

		ShipType TYPE;
		Entity ENTITY;
		
		public EntityDetectedEvent(Entity entity, ShipType type, Cause cause) {
			super(cause);
			ENTITY = entity;
			TYPE = type;
		}
		
		public ShipType getVessel(){
			return TYPE;
		}
		
		public Entity getEntity(){
			return ENTITY;
		}
		
	}

}
