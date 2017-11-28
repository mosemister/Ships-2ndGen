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
import MoseShipsBukkit.Vessel.Common.OpenLoader.OpenRAWLoader;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.Static.AbstractStaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.Common.Static.Types.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.PercentageRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Loader.Types.WaterShip.Ship6WaterShipLoader;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Watership;

public class StaticWatership extends AbstractStaticShipType implements StaticShipType, StaticRequiredPercent{

	public StaticWatership() {
		super("Ship", 500, 2000, 2, 3, 1, ShipsMain.getPlugin());
		StaticShipType.TYPES.add(this);
		if(!getFile().exists()) {
			saveDefaults(getFile());
		}
		loadDefaults(getFile());
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
	public void loadDefaults(File file) {
		StaticShipConfig config = new StaticShipConfig(file);
		setBoostSpeed(config.getDefaultBoostSpeed());
		setDefaultSpeed(config.getDefaultSpeed());
		setMaxSize(config.getDefaultMaxSize());
		setMinSize(config.getDefaultMinSize());
	}
	
	@Override
	public void saveDefaults(File file) {
		StaticShipConfig config = new StaticShipConfig(file);
		config.setOverride(3, StaticShipConfig.DATABASE_DEFAULT_BOOST);
		config.setOverride(4000, StaticShipConfig.DATABASE_DEFAULT_MAX_SIZE);
		config.setOverride(0, StaticShipConfig.DATABASE_DEFAULT_MIN_SIZE);
		config.setOverride(2, StaticShipConfig.DATABASE_DEFAULT_SPEED);
		config.setOverride(Arrays.asList(new BlockState(Material.WOOL, (byte) -1).toNoString()),
				StaticRequiredPercent.DEFAULT_REQUIRED_BLOCKS);
		config.setOverride(20, StaticRequiredPercent.DEFAULT_REQUIRED_PERCENT);
		config.save();	
	}

	@Override
	public OpenRAWLoader[] getLoaders() {
		OpenRAWLoader[] loaders = {new Ship6WaterShipLoader()};
		return loaders;
	}

	@Override
	public SOptional<LiveShip> createVessel(String name, Block licence) {
		Watership ship = new Watership(name, licence, licence.getLocation());
		PercentageRequirement percentD = ship.getPercentData();
		percentD.setPercentage(getDefaultRequiredPercent());
		percentD.setStates(getDefaultPercentBlocks());
		return new SOptional<LiveShip>((LiveShip)ship);
	}

	@Override
	public SOptional<LiveShip> loadVessel(ShipsData data, BasicConfig config) {
		Watership ship = new Watership(data);
		for(RequirementData reqD : ship.getRequirementData()){
			reqD.applyFromShip(config);
		}
		return new SOptional<LiveShip>((LiveShip)ship);
	}
	
	@Override
	public StaticWatership copy(String name, Plugin plugin) {
		StaticWatership ship = new StaticWatership();
		ship.setAltitudeSpeed(getAltitudeSpeed());
		ship.setBoostSpeed(getBoostSpeed());
		ship.setDefaultSpeed(getDefaultSpeed());
		ship.setMaxSize(getMaxSize());
		ship.setMinSize(getMinSize());
		ship.setName(name);
		ship.setPlugin(plugin);
		return ship;
	}

}
