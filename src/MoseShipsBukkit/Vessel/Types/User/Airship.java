package MoseShipsBukkit.Vessel.Types.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Configs.ShipsLocalDatabase;
import MoseShipsBukkit.Configs.StaticShipConfig;
import MoseShipsBukkit.Movement.MovingBlock;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Plugin.ShipsMain;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Tasks.Types.FallingTask;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Data.AbstractShipsData;
import MoseShipsBukkit.Vessel.Data.LiveShip;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveAutoPilotable;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveFallable;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveFuel;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveRequiredBlock;
import MoseShipsBukkit.Vessel.DataProcessors.Live.LiveRequiredPercent;
import MoseShipsBukkit.Vessel.DataProcessors.Static.StaticFuel;
import MoseShipsBukkit.Vessel.DataProcessors.Static.StaticRequiredPercent;
import MoseShipsBukkit.Vessel.Static.StaticShipType;
import MoseShipsBukkit.Vessel.Types.AbstractAirType;

public class Airship extends AbstractAirType
		implements LiveAutoPilotable, LiveFallable, LiveFuel, LiveRequiredBlock, LiveRequiredPercent {

	int g_req_percent;
	int g_cons_amount;
	BlockState[] g_req_p_blocks;
	BlockState[] g_req_fuel;
	AutoPilot g_auto_pilot;

	public Airship(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
		getTaskRunner().register(ShipsMain.getPlugin(), new FallingTask());
	}

	public Airship(AbstractShipsData data) {
		super(data);
	}

	public Airship setRequiredPercent(int A) {
		g_req_percent = A;
		return this;
	}

	public Airship setPercentBlocks(BlockState... blocks) {
		g_req_p_blocks = blocks;
		return this;
	}

	public Airship setFuels(BlockState... states) {
		g_req_fuel = states;
		return this;
	}

	public Airship setFuelConsumption(int A) {
		g_cons_amount = A;
		return this;
	}

	@Override
	public Optional<AutoPilot> getAutoPilotData() {
		return Optional.ofNullable(g_auto_pilot);
	}

	@Override
	public Airship setAutoPilotData(AutoPilot pilot) {
		g_auto_pilot = pilot;
		return this;
	}

	@Override
	public int getRequiredPercent() {
		return g_req_percent;
	}

	@Override
	public int getAmountOfPercentBlocks() {
		List<Block> structure = getBasicStructure();
		if (!structure.isEmpty()) {
			List<Block> blocks = new ArrayList<Block>();
			for (Block block : structure) {
				if (BlockState.contains(block, getPercentBlocks())) {
					blocks.add(block);
				}
			}
			int percent = (int) (blocks.size() * 100.0f) / structure.size();
			return percent;
		}
		return 0;
	}

	@Override
	public BlockState[] getPercentBlocks() {
		return g_req_p_blocks;
	}

	@Override
	public BlockState[] getRequiredBlocks() {
		BlockState[] req = {
				new BlockState(Material.FIRE) };
		return req;
	}

	@Override
	public boolean hasRequiredBlock() {
		for (Block block : getBasicStructure()) {
			if (block.getType().equals(Material.FIRE)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getFuel() {
		int fuel = 0;
		for (Block block : getBasicStructure()) {
			if (block.getState() instanceof Furnace) {
				Furnace furn = (Furnace) block.getState();
				FurnaceInventory inv = furn.getInventory();
				for (ItemStack itemS : inv.getContents()) {
					for (BlockState state : g_req_fuel) {
						if (state.looseMatch(itemS)) {
							fuel = fuel + itemS.getAmount();
						}
					}
				}
			}
		}
		return fuel;
	}

	@Override
	public BlockState[] getFuelMaterials() {
		return g_req_fuel;
	}

	@Override
	public int getConsumptionAmount() {
		return g_cons_amount;
	}

	@Override
	public boolean removeFuel(final int take) {
		int amountRequired = take;
		Map<Furnace, Integer> map = new HashMap<Furnace, Integer>();
		for (Block block : getBasicStructure()) {
			if (amountRequired == 0) {
				break;
			}
			if (block.getState() instanceof Furnace) {
				Furnace furn = (Furnace) block.getState();
				FurnaceInventory inv = furn.getInventory();
				for (ItemStack item : inv.getContents()) {
					if (amountRequired == 0) {
						break;
					}
					for (BlockState state : g_req_fuel) {
						if (amountRequired == 0) {
							break;
						}
						if (state.looseMatch(item)) {
							if (amountRequired < item.getAmount()) {
								map.put(furn, amountRequired);
								amountRequired = 0;
							} else {
								map.put(furn, item.getAmount());
								amountRequired = amountRequired - item.getAmount();
							}
						}
					}
				}
			}
		}
		if (amountRequired == 0) {
			for (Entry<Furnace, Integer> entry : map.entrySet()) {
				FurnaceInventory inv = entry.getKey().getInventory();
				int amountTaken = 0;
				for (ItemStack item : inv.getContents()) {
					if (amountTaken == take) {
						break;
					}
					for (BlockState state : g_req_fuel) {
						if (amountTaken == take) {
							break;
						}
						if (state.looseMatch(item)) {
							if (item.getAmount() >= entry.getValue()) {
								item.setAmount(item.getAmount() - entry.getValue());
								amountTaken = take;
								break;
							} else {
								inv.remove(item);
								amountTaken = amountTaken + item.getAmount();
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public Optional<FailedMovement> hasRequirements(List<MovingBlock> blocks) {
		int hasPercent = getAmountOfPercentBlocks();
		int percent = getRequiredPercent();
		if (!hasRequiredBlock()) {
			return Optional
					.of(new FailedMovement(this, MovementResult.MISSING_REQUIRED_BLOCK, new BlockState(Material.FIRE)));
		}
		if (hasPercent < percent) {
			return Optional.of(new FailedMovement(this, MovementResult.NOT_ENOUGH_PERCENT,
					new TwoStore<BlockState, Float>(getPercentBlocks()[0], (float) (percent - hasPercent))));
		}
		if (getFuel() >= getConsumptionAmount()) {
			if (removeFuel(getConsumptionAmount())) {
				return Optional.empty();
			} else {
				return Optional.of(new FailedMovement(this, MovementResult.FUEL_REMOVE_ERROR, true));
			}
		} else {
			return Optional.of(new FailedMovement(this, MovementResult.OUT_OF_FUEL, true));
		}
	}

	@Override
	public boolean shouldFall() {
		if (getFuel() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> getInfo() {
		return new HashMap<String, Object>();
	}

	@Override
	public void onSave(ShipsLocalDatabase database) {
		List<String> blocks = new ArrayList<String>();
		for (BlockState state : g_req_p_blocks) {
			blocks.add(state.toNoString());
		}
		List<String> fuels = new ArrayList<String>();
		for (BlockState state : g_req_fuel) {
			fuels.add(state.toNoString());
		}
		database.set(g_req_percent, LiveRequiredPercent.REQUIRED_PERCENT);
		database.set(blocks, LiveRequiredPercent.REQUIRED_BLOCKS);
		database.set(fuels, LiveFuel.FUEL_MATERIALS);
		database.set(g_cons_amount, LiveFuel.FUEL_CONSUMPTION);
	}

	@Override
	public void onRemove(Player player) {
	}

	@Override
	public StaticAirship getStatic() {
		return StaticShipTypeUtil.getType(StaticAirship.class).get();
	}

	public static class StaticAirship implements StaticShipType, StaticFuel, StaticRequiredPercent {

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
		public boolean autoPilot() {
			return false;
		}

		@Override
		public ShipsMain getPlugin() {
			return ShipsMain.getPlugin();
		}

		@Override
		public Optional<LiveShip> createVessel(String name, Block licence) {
			Airship ship = new Airship(name, licence, licence.getLocation());
			ship.setRequiredPercent(getDefaultRequiredPercent());
			ship.setPercentBlocks(getDefaultPercentBlocks());
			ship.setFuelConsumption(getDefaultConsumptionAmount());
			ship.setFuels(getDefaultFuelMaterial());
			return Optional.of((LiveShip) ship);
		}

		@Override
		public Optional<LiveShip> loadVessel(AbstractShipsData data, BasicConfig config) {
			Airship ship = new Airship(data);
			ship.setRequiredPercent(getDefaultRequiredPercent());
			ship.setPercentBlocks(getDefaultPercentBlocks());
			ship.setFuelConsumption(getDefaultConsumptionAmount());
			ship.setFuels(getDefaultFuelMaterial());

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
}
