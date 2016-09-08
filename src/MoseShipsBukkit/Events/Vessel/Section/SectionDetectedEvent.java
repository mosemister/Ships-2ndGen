package MoseShipsBukkit.Events.Vessel.Section;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

/**
 * The SectionDetectedEvent will fire when a part of a ship is detected,
 * this could be a block, entity or even the whole vessel intself
 */

public class SectionDetectedEvent extends Event{
	
	protected static HandlerList HANDLER;
	
	public SectionDetectedEvent(){
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLER;
	}
	
	public static HandlerList getHandlerList(){
		return HANDLER;
	}
	
	public static class BlockDetectedEvent extends SectionDetectedEvent{
		
		/**
		 * BlockDetectedEvent is fired by the default algorithms when they
		 * detect a block.
		 * 
		 *  please note that this will likely be removed
		 */
		
		Block BLOCK;
		
		public BlockDetectedEvent(Block loc){
			BLOCK = loc;
		}
		
		public Block getBlock(){
			return BLOCK;
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}
		
	}
	
	public static class VesselDetectedEvent extends BlockDetectedEvent{
		
		/**
		 * this is fired when a ShipType sign is found and read
		 */
		
		LoadableShip TYPE;
		
		public VesselDetectedEvent(LoadableShip type) {
			super(type.getLocation().getBlock());
			TYPE = type;
		}
		
		public LoadableShip getVessel(){
			return TYPE;
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}
		
	}
	
	public static class EntityDetectedEvent extends SectionDetectedEvent{
		
		/**
		 * this is fired when a entity is located on the ship
		 * please note this is likely going to be removed
		 */

		LoadableShip TYPE;
		Entity ENTITY;
		
		public EntityDetectedEvent(Entity entity, LoadableShip type) {
			ENTITY = entity;
			TYPE = type;
		}
		
		public LoadableShip getVessel(){
			return TYPE;
		}
		
		public Entity getEntity(){
			return ENTITY;
		}
		
		public static HandlerList getHandlerList(){
			return HANDLER;
		}
		
	}

}
