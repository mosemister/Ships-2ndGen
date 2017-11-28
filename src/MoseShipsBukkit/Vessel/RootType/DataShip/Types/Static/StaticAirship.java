package MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.StaticShipConfig;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.AbstractStaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.Types.StaticFuel;
import MoseShipsBukkit.Vessel.Common.Static.Types.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.BlockRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.FuelRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.PercentageRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.Airship.Ships6AirshipLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Airship;

public class StaticAirship extends AbstractStaticShipType implements StaticShipType, StaticFuel, StaticRequiredPercent {
		
	public StaticAirship() {
		super("Airship", 200, 4000, 2, 3, 2, ShipsMain.getPlugin());
		StaticShipTypeUtil.inject(this);
		if(!getFile().exists()) {
			firstSave(getFile());
		}
		loadDefaults(getFile());
	}
	
	private void firstSave(File file) {
		StaticShipConfig config = new StaticShipConfig(file);
		config.setOverride(getAltitudeSpeed(), StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
		config.setOverride(getBoostSpeed(), StaticShipConfig.DATABASE_DEFAULT_BOOST);
		config.setOverride(getMaxSize(), StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
		config.setOverride(getMinSize(), StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
		config.setOverride(getDefaultSpeed(), StaticShipConfig.DATABASE_DEFAULT_SPEED);
		config.setOverride(Arrays.asList(new BlockState(Material.WOOL).toNoString()),
				StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
		config.setOverride(60, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		config.setOverride(1, StaticFuel.DEFAULT_FUEL_CONSUMPTION);
		config.setOverride(Arrays.asList(new BlockState(Material.COAL).toNoString()), StaticFuel.DEFAULT_FUEL);
		config.save();
	}
	
	@Override
	public int getDefaultRequiredPercent() {
		StaticShipConfig config = new StaticShipConfig(getName());
		return config.get(Integer.class, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
	}

	@Override
	public BlockState[] getDefaultPercentBlocks() {
		StaticShipConfig config = new StaticShipConfig(getName());
		List<String> sStates = config.getList(String.class, StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
		BlockState[] states = BlockState.getStates(sStates);
		return states;
	}

	@Override
	public BlockState[] getDefaultFuelMaterial() {
		StaticShipConfig config = new StaticShipConfig(getName());
		List<String> sStates = config.getList(String.class, DEFAULT_FUEL);
		BlockState[] states = BlockState.getStates(sStates);
		return states;
	}

	@Override
	public int getDefaultConsumptionAmount() {
		StaticShipConfig config = new StaticShipConfig(getName());
		return config.get(Integer.class, DEFAULT_FUEL_CONSUMPTION);
	}
	
	@Override
	public void setDefaultRequiredPercent(int percent) {
		StaticShipConfig config = new StaticShipConfig(getName());
		config.setOverride(percent, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		config.save();
	}

	@Override
	public void setDefaultPercentBlocks(BlockState... state) {
		List<String> list = new ArrayList<String>();
		for(BlockState state2 : state) {
			list.add(state2.toNoString());
		}
		StaticShipConfig config = new StaticShipConfig(getName());
		config.setOverride(list, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		config.save();
	}

	@Override
	public void setDefaultFuelMaterial(BlockState... state) {
		List<String> list = new ArrayList<String>();
		for(BlockState state2 : state) {
			list.add(state2.toNoString());
		}
		StaticShipConfig config = new StaticShipConfig(getName());
		config.setOverride(list, StaticFuel.DEFAULT_FUEL);
		config.save();
	}

	@Override
	public void setDefaultConsumptionAmount(int amount) {
		StaticShipConfig config = new StaticShipConfig(getName());
		config.setOverride(amount, StaticFuel.DEFAULT_FUEL_CONSUMPTION);
		config.save();
	}
	
	@Override
	public void loadDefaults(File file) {
		StaticShipConfig config = new StaticShipConfig(file);
		setAltitudeSpeed(config.getDefaultAltitudeSpeed());
		setBoostSpeed(config.getDefaultBoostSpeed());
		setDefaultSpeed(config.getDefaultSpeed());
		setMaxSize(config.getDefaultMaxSize());
		setMinSize(config.getDefaultMinSize());
		setDefaultConsumptionAmount(config.get(Integer.class, DEFAULT_FUEL_CONSUMPTION));
		setDefaultFuelMaterial(BlockState.getStates(config.getList(String.class, DEFAULT_FUEL)));
		setDefaultPercentBlocks(BlockState.getStates(config.getList(String.class, DEFAULT_REQUIRED_BLOCKS)));
		setDefaultRequiredPercent(config.get(Integer.class, DEFAULT_REQUIRED_PERCENT));
	}
	
	@Override
	public void saveDefaults(File file) {
		StaticShipConfig config = new StaticShipConfig(file);
		List<String> percent = new ArrayList<String>();
		List<String> fuel = new ArrayList<String>();
		for(BlockState state : getDefaultPercentBlocks()) {
			percent.add(state.toNoString());
		}
		for(BlockState state : getDefaultFuelMaterial()) {
			fuel.add(state.toNoString());
		}
		config.setOverride(getAltitudeSpeed(), StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
		config.setOverride(getBoostSpeed(), StaticShipConfig.DATABASE_DEFAULT_BOOST);
		config.setOverride(getMaxSize(), StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
		config.setOverride(getMinSize(), StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
		config.setOverride(getDefaultSpeed(), StaticShipConfig.DATABASE_DEFAULT_SPEED);
		config.setOverride(percent,
				StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
		config.setOverride(getDefaultRequiredPercent(), StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		config.setOverride(getDefaultConsumptionAmount(), StaticFuel.DEFAULT_FUEL_CONSUMPTION);
		config.setOverride(fuel, StaticFuel.DEFAULT_FUEL);
		config.save();
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {
				new Ships6AirshipLoader()
		};
		return loaders;
	}

	@Override
	public SOptional<LiveShip> createVessel(String name, Block licence) {
		Airship ship = new Airship(name, licence, licence.getLocation());
		FuelRequirement fuelD = ship.getFuelData();
		BlockRequirement blockD = ship.getBlockData();
		PercentageRequirement percentD = ship.getPercentData();
		fuelD.setStates(getDefaultFuelMaterial());
		fuelD.setTakeAmount(getDefaultConsumptionAmount());
		blockD.setState(new BlockState(Material.FIRE));
		percentD.setPercentage(getDefaultRequiredPercent());
		percentD.setStates(getDefaultPercentBlocks());
		return new SOptional<LiveShip>((LiveShip) ship);
	}

	@Override
	public SOptional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		Airship ship = new Airship(data);
		for(RequirementData reqD : ship.getRequirementData()){
			reqD.applyFromShip(config);
		}
		return new SOptional<LiveShip>((LiveShip) ship);
	}

	@Override
	public StaticAirship copy(String name, Plugin plugin) {
		StaticAirship airship = new StaticAirship();
		airship.setAltitudeSpeed(getAltitudeSpeed());
		airship.setBoostSpeed(getBoostSpeed());
		airship.setDefaultConsumptionAmount(getDefaultConsumptionAmount());
		airship.setDefaultFuelMaterial(getDefaultFuelMaterial());
		airship.setDefaultPercentBlocks(getDefaultPercentBlocks());
		airship.setDefaultRequiredPercent(getDefaultRequiredPercent());
		airship.setDefaultSpeed(getDefaultSpeed());
		airship.setMaxSize(getMaxSize());
		airship.setMinSize(getMinSize());
		airship.setName(name);
		airship.setPlugin(getPlugin());
		return airship;
	}

}
