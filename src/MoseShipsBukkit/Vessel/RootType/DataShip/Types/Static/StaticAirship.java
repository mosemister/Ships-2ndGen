package MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.block.Block;

import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.StaticShipConfig;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.Types.StaticFuel;
import MoseShipsBukkit.Vessel.Common.Static.Types.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.BlockRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.FuelRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.PercentageRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.Airship.Ships6AirshipLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Airship;

public class StaticAirship implements StaticShipType, StaticFuel, StaticRequiredPercent {

	public StaticAirship() {
		StaticShipTypeUtil.inject(this);
		File file = new File("plugins/Ships/Configuration/ShipTypes/Airship.yml");
		if (!file.exists()) {
			StaticShipConfig config = new StaticShipConfig(file);
			config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_ALTITUDE);
			config.setOverride(3, StaticShipConfig.DATABASE_DEFAULT_BOOST);
			config.setOverride(4000, StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
			config.setOverride(0, StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
			config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_SPEED);
			config.setOverride(Arrays.asList(new BlockState(Material.WOOL, (byte) -1).toNoString()),
					StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
			config.setOverride(60, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
			config.setOverride(1, StaticFuel.DEFAULT_FUEL_CONSUMPTION);
			config.setOverride(Arrays.asList(new BlockState(Material.COAL).toNoString()), StaticFuel.DEFAULT_FUEL);
			config.save();
		}
	}
	
	@Override
	public String getName() {
		return "Airship";
	}

	@Override
	public int getDefaultSpeed() {
		return 2;
	}

	@Override
	public int getBoostSpeed() {
		return 3;
	}

	@Override
	public int getAltitudeSpeed() {
		return 2;
	}

	@Override
	public ShipsMain getPlugin() {
		return ShipsMain.getPlugin();
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {
				new Ships6AirshipLoader()
		};
		return loaders;
	}

	@Override
	public Optional<LiveShip> createVessel(String name, Block licence) {
		Airship ship = new Airship(name, licence, licence.getLocation());
		FuelRequirement fuelD = ship.getFuelData();
		BlockRequirement blockD = ship.getBlockData();
		PercentageRequirement percentD = ship.getPercentData();
		fuelD.setStates(getDefaultFuelMaterial());
		fuelD.setTakeAmount(getDefaultConsumptionAmount());
		blockD.setState(new BlockState(Material.FIRE));
		percentD.setPercentage(getDefaultRequiredPercent());
		percentD.setStates(getDefaultPercentBlocks());
		return Optional.of((LiveShip) ship);
	}

	@Override
	public Optional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		Airship ship = new Airship(data);
		for(RequirementData reqD : ship.getRequirementData()){
			reqD.applyFromShip(config);
		}
		return Optional.of((LiveShip) ship);
	}

	@Override
	public int getDefaultRequiredPercent() {
		StaticShipConfig config = new StaticShipConfig("Airship");
		return config.get(Integer.class, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
	}

	@Override
	public BlockState[] getDefaultPercentBlocks() {
		StaticShipConfig config = new StaticShipConfig("Airship");
		List<String> sStates = config.getList(String.class, StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
		BlockState[] states = BlockState.getStates(sStates);
		return states;
	}

	@Override
	public BlockState[] getDefaultFuelMaterial() {
		BlockState[] ret = {
				new BlockState(Material.COAL) };
		return ret;
	}

	@Override
	public int getDefaultConsumptionAmount() {
		return 1;
	}

}
