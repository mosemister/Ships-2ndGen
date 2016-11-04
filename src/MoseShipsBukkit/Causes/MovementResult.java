package MoseShipsBukkit.Causes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Ships.Movement.AutoPilot.AutoPilot;
import MoseShipsBukkit.Ships.Movement.MovingBlock.MovingBlock;
import MoseShipsBukkit.Utils.State.BlockState;

public class MovementResult {

	List<TwoStore<CauseKeys<Object>, Object>> CAUSES = new ArrayList<TwoStore<CauseKeys<Object>, Object>>();

	public Set<TwoStore<CauseKeys<Object>, Object>> entrySet() {
		return new HashSet<TwoStore<CauseKeys<Object>, Object>>(CAUSES);
	}

	@SuppressWarnings("unchecked")
	public <E extends Object> MovementResult put(CauseKeys<E> key, E value) {
		for (TwoStore<CauseKeys<Object>, Object> store : CAUSES) {
			if (store.getFirst().equals(key)) {
				CAUSES.remove(store);
			}
		}
		TwoStore<CauseKeys<Object>, Object> store = new TwoStore<CauseKeys<Object>, Object>((CauseKeys<Object>) key,
				value);
		CAUSES.add(store);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <E> Optional<E> get(CauseKeys<E> key) {
		for (TwoStore<CauseKeys<Object>, Object> store : CAUSES) {
			if (store.getFirst().equals(key)) {
				return Optional.of((E) store.getSecond());
			}
		}
		return Optional.empty();
	}

	public <E> Optional<TwoStore<CauseKeys<Object>, Object>> getFailedCause() {
		if (CAUSES.isEmpty()) {
			return Optional.empty();
		}

		TwoStore<CauseKeys<Object>, Object> store = CAUSES.get(CAUSES.size() - 1);
		return Optional.ofNullable(store);
	}

	public static abstract class CauseKeys<T extends Object> {

		public static CauseKeys<Boolean> FUEL_REMOVE_ERROR = new CauseKeys<Boolean>() {

			@Override
			public void sendMessage(Player player, Object value) {
				player.sendMessage("Ships failed to collect fuel to remove");
			}

		};

		public static CauseKeys<Boolean> NOT_IN_WATER = new CauseKeys<Boolean>() {

			@Override
			public void sendMessage(final Player player, Object value) {
				player.sendMessage(ShipsMain.format("Ship is not in water", true));
			}

		};

		public static CauseKeys<List<MovingBlock>> MOVING_BLOCKS = new CauseKeys<List<MovingBlock>>() {

			@SuppressWarnings({
				"unchecked",
				"deprecation"
			})
			@Override
			public void sendMessage(final Player player, Object value) {
				if (value instanceof List) {
					player.sendMessage(ShipsMain.format("Detection ahead. They are bedrock for 3 seconds", true));
					final List<MovingBlock> list = (List<MovingBlock>) value;
					for (MovingBlock block : list) {
						player.sendBlockChange(block.getMovingTo(), Material.BEDROCK, (byte) 0);
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

						@Override
						public void run() {
							for (MovingBlock block : list) {
								Block block2 = block.getMovingTo().getBlock();
								player.sendBlockChange(block.getMovingTo(), block2.getType(), block2.getData());
							}

						}

					}, (3 * 20));
				}

			}

		};
		public static CauseKeys<AutoPilot> AUTO_PILOT_OUT_OF_MOVES = new CauseKeys<AutoPilot>() {

			@Override
			public void sendMessage(Player player, Object value) {
				player.sendMessage(ShipsMain.format("AutoPilot ran out of moves", true));
			}

		};
		public static CauseKeys<Boolean> OUT_OF_FUEL = new CauseKeys<Boolean>() {

			@Override
			public void sendMessage(Player player, Object value) {
				player.sendMessage(ShipsMain.format("Out of fuel", true));
			}

		};
		public static CauseKeys<BlockState> MISSING_REQUIRED_BLOCK = new CauseKeys<BlockState>() {

			@Override
			public void sendMessage(Player player, Object value) {
				if (value instanceof BlockState) {
					BlockState state = (BlockState) value;
					if (state.getMaterial().equals(Material.FIRE)) {
						player.sendMessage("You are missing a burner from your ship");
					} else {
						player.sendMessage(ShipsMain.format(
								"You are missing " + state.getMaterial() + ":" + state.getData() + " from your ship",
								true));
					}
				}

			}

		};
		public static CauseKeys<Integer> NOT_ENOUGH_BLOCKS = new CauseKeys<Integer>() {

			@Override
			public void sendMessage(Player player, Object value) {
				if (value instanceof Integer) {
					int blocks = (Integer) value;
					player.sendMessage(ShipsMain.format("You need " + blocks + " more blocks", true));
				}

			}

		};
		public static CauseKeys<Integer> TOO_MANY_BLOCKS = new CauseKeys<Integer>() {

			@Override
			public void sendMessage(Player player, Object value) {
				if (value instanceof Integer) {
					int blocks = (Integer) value;
					player.sendMessage(ShipsMain.format("You need " + blocks + " less blocks", true));
				}
			}

		};
		public static CauseKeys<TwoStore<BlockState, Float>> NOT_ENOUGH_PERCENT = new CauseKeys<TwoStore<BlockState, Float>>() {

			@SuppressWarnings("unchecked")
			@Override
			public void sendMessage(Player player, Object value) {
				if (value instanceof TwoStore) {
					TwoStore<BlockState, Float> value2 = (TwoStore<BlockState, Float>) value;
					player.sendMessage(ShipsMain.format("You need " + value2.getSecond() + " more blocks of "
							+ value2.getFirst().getMaterial() + ":" + value2.getFirst().getData(), true));
				}
			}

		};

		public abstract void sendMessage(Player player, Object value);
	}

}
