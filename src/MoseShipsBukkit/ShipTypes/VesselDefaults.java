package MoseShipsBukkit.ShipTypes;

import java.io.File;

import MoseShipsBukkit.StillShip.Vessel;

public interface VesselDefaults {

	void save(Vessel vessel);
	void createConfig();
	void loadDefault();
	File getFile();
	Vessel loadFromClassicVesselFile(Vessel vessel, File file);
	Vessel loadFromNewVesselFile(Vessel vessel, File file);
	
	int getMaxBlocks();
	int getMinBlocks();
	void setMaxBlocks(int amount);
	void setMinBlocks(int amount);
	
}
