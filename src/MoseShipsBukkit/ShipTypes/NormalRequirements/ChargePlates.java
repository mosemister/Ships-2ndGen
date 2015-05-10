package MoseShipsBukkit.ShipTypes.NormalRequirements;

import MoseShipsBukkit.StillShip.Vessel;

public interface ChargePlates {

	int getChargeTakeAmount();
	int getChargeMaxAmount();
	void takeCharge(Vessel vessel);
	void setChargeTakeAmount(int A);
	void setChargeMaxAmount(int A);
}
