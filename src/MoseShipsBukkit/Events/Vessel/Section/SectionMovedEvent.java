package MoseShipsBukkit.Events.Vessel.Section;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;

import MoseShipsBukkit.Events.Vessel.ShipsEvent;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;

/**
 * SectionMovedEvent is called when select part of a ship has fully moved from
 * its original place to its new position. These select parts include blocks
 * from the ship and entities that are on the ship. The event may not fire
 * depending on the Ships algorithm that is in place, both Ships 5 and Ships 6
 * algorithms will fire the targeted SectionMoveEvent, however custom algorithms
 * may not.
 */
public abstract class SectionMovedEvent extends ShipsEvent {

	public SectionMovedEvent(LoadableShip ship) {
		super(ship);
	}

	public static class BlockMovedEvent extends SectionMovedEvent {

		MovingBlock BLOCK;

		/**
		 * This event is fired under the SectionMovedEvent. This event will only
		 * fire if a block has fully moved, meaning you can listen for just the
		 * block moving instead of all the parts
		 */

		public BlockMovedEvent(LoadableShip ship, MovingBlock block) {
			super(ship);
			BLOCK = block;
		}

		public MovingBlock getBlock() {
			return BLOCK;
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}
	}

	public static class EntityMovedEvent extends SectionMovedEvent {

		Location LOCATION;
		Entity ENTITY;

		/**
		 * This event is fired under the SectionMovedEvent. This event will only
		 * fire if a entity has fully moved, meaning you can listen for just the
		 * entity moving instead of all the parts
		 */

		public EntityMovedEvent(LoadableShip ship, Entity entity, Location movingTo) {
			super(ship);
			LOCATION = movingTo;
			ENTITY = entity;
		}

		public Entity getEntity() {
			return ENTITY;
		}

		public Location getMovingTo() {
			return LOCATION;
		}

		public static HandlerList getHandlerList() {
			return HANDLER;
		}

	}

}
