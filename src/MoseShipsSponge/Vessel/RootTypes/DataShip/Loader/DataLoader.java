package MoseShipsSponge.Vessel.RootTypes.DataShip.Loader;

import java.io.File;
import java.util.Optional;

import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Vessel.Common.OpenLoader.Loader;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenLoader;
import MoseShipsSponge.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;
import MoseShipsSponge.Vessel.RootTypes.DataShip.DataVessel;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.RequirementData;

public abstract class DataLoader extends OpenLoader {

	@Override
	public boolean willLoad(File file) {
		BasicConfig config = new BasicConfig(file);
		String type = config.get(String.class, Loader.OPEN_LOADER_NAME);
		if(type == null){
			return false;
		}
		if(type.equals(getLoaderName())){
			return true;
		}
		return false;
	}
	
	@Override
	public Optional<LiveShip> RAWLoad(File file) {
		Optional<LiveShip> opShip = super.RAWLoad(file);
		if(opShip.isPresent()){
			DataVessel vessel = (DataVessel)opShip.get();
			for(RequirementData requirement : vessel.getRequirementData()){
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
			for(RequirementData requirement : vessel.getRequirementData()){
				requirement.saveShip(config);
			}
		}
		config.save();
		return this;
	}

}
