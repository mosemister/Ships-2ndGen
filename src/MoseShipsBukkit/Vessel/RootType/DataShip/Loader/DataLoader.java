package MoseShipsBukkit.Vessel.RootType.DataShip.Loader;

import java.io.File;
import java.util.Optional;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Vessel.Common.OpenLoader.Loader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.DataVessel;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;

public abstract class DataLoader extends OpenLoader {
	
	@Override
	public boolean willLoad(File file) {
		BasicConfig config = new BasicConfig(file);
		String type = config.get(String.class, Loader.OPEN_LOADER_NAME);
		if (type == null) {
			return false;
		}
		if (type.equals(getLoaderName())) {
			return true;
		}
		return false;
	}
		
	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		Optional<LiveShip> opShip = super.RAWLoad(file);
		if(opShip.isPresent()){
			DataVessel vessel = (DataVessel)opShip.get();
			for (RequirementData requirement : vessel.getRequirementData()){
				requirement.applyFromShip(new BasicConfig(vessel.getFile()));
			}
		}
		return opShip;
	}
	
	@Override
	public OpenRAWLoader RAWSave(LiveShip ship, File file) {
		super.RAWSave(ship, file);
		BasicConfig config = new BasicConfig(file);
		if(ship instanceof DataVessel){
			DataVessel vessel = (DataVessel)ship;
			for (RequirementData requirement : vessel.getRequirementData()){
				requirement.saveShip(config);
			}
		}
		return this;
	}
}
