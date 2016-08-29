package MoseShipsSponge.Events.Vessel.Section;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Events.Vessel.ShipsEvent;
import MoseShipsSponge.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.LoadableShip;

/**
 * SectionMovedEvent is called when select part of a ship has fully moved from its original
 * place to its new position. These select parts include blocks from the ship and entities that
 * are on the ship. 
 * The event may not fire depending on the Ships algorithm that is in place, both Ships 5 and
 * Ships 6 algorithms will fire the targeted SectionMoveEvent, however custom algorithms may not.
 */
public abstract class SectionMovedEvent<S extends LoadableShip> extends ShipsEvent<S>{
	
	public SectionMovedEvent(S ship, Cause cause) {
		super(ship, cause);
	}
	
	public static class BlockMovedEvent <S extends LoadableShip> extends SectionMovedEvent<S>{

		MovingBlock BLOCK;
		
		/**
		 * This event is fired under the SectionMovedEvent. This event will only fire if a
		 * block has fully moved, meaning you can listen for just the block moving instead
		 * of all the parts 
		 */
		
		public BlockMovedEvent(S ship, MovingBlock block, Cause cause) {
			super(ship, cause);
			BLOCK = block;
		}
		
		public MovingBlock getBlock(){
			return BLOCK;
		}
	}
	
	public static class EntityMovedEvent <S extends LoadableShip> extends SectionMovedEvent<S>{

		Location<World> LOCATION;
		Entity ENTITY;
		
		/**
		 * This event is fired under the SectionMovedEvent. This event will only fire if a
		 * entity has fully moved, meaning you can listen for just the entity moving instead
		 * of all the parts 
		 */
		
		public EntityMovedEvent(S ship, Entity entity, Location<World> movingTo, Cause cause) {
			super(ship, cause);
			LOCATION = movingTo;
			ENTITY = entity;
		}
		
		public Entity getEntity(){
			return ENTITY;
		}
		
		public Location<World> getMovingTo(){
			return LOCATION;
		}
		
	}

}
