package MoseShipsSponge.Events.Vessel;

import MoseShipsSponge.Ships.ShipsData;

public interface ShipsEvent<T extends ShipsData> {
	
	public T getShip();

}
