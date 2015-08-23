package MoseShipsBukkit.ShipsTypes.HookTypes;

import MoseShipsBukkit.StillShip.Vessel;

public interface Cell {
	
	/**
	 * removes a set amount of cell power from a vessel.
	 * 
	 * <p>if this returns 'false' then the move 
	 * event will be cancelled. If returns 'true'
	 * then it will continue the move event</p>
	 */
	public boolean removeCellPower(Vessel vessel);
	
	/**
	 * gets the total amount of cell power
	 */
	public int getTotalCellPower(Vessel vessel);
	
	/**
	 * adds cell power
	 * 
	 * <p>power is determined by the vesselType</p>
	 */
	
	public void addCellPower();

}
