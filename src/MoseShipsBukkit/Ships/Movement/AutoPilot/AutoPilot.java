package MoseShipsBukkit.Ships.Movement.AutoPilot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;

import MoseShips.Maps.OrderedMap;
import MoseShips.Stores.TwoStore;
import MoseShipsBukkit.ShipsMain;
import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Causes.MovementResult.CauseKeys;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.VesselTypes.DataTypes.Live.LiveAutoPilotable;

public class AutoPilot {

	List<StoredMovement> MOVEMENTS = new ArrayList<StoredMovement>();
	LiveAutoPilotable SHIP;
	OfflinePlayer USER;
	int TARGET;
	boolean SHOULD_REPEATE;
	List<Integer> PROCESSES;
	int PROCESSED = 0;

	public AutoPilot(LiveAutoPilotable type, List<StoredMovement> movements, boolean repeate, int start,
			@Nullable OfflinePlayer user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		TARGET = start;
		USER = user;
	}

	public AutoPilot(LiveAutoPilotable type, List<StoredMovement> movements, boolean repeate, @Nullable OfflinePlayer user) {
		MOVEMENTS = movements;
		SHIP = type;
		SHOULD_REPEATE = repeate;
		USER = user;
	}

	public AutoPilot(LiveAutoPilotable type, Block moveTo, int speed, @Nullable OfflinePlayer user) {
		int height = 500;

		int toX = moveTo.getX();
		int toZ = moveTo.getZ();

		Location from = type.getLocation();
		int fromX = from.getBlockX();
		int fromZ = from.getBlockZ();

		Location stageOne = new Location(from.getWorld(), fromX, height, fromZ);
		Location stageTwo = new Location(from.getWorld(), fromX, height, toZ);
		Location stageThree = new Location(from.getWorld(), toX, height, toZ);

		List<Location> listOne = getBetween(from, stageOne, speed);
		List<Location> listTwo = getBetween(stageOne, stageTwo, speed);
		List<Location> listThree = getBetween(stageTwo, stageThree, speed);
		List<Location> listFour = getBetween(stageThree, moveTo.getLocation(), speed);
		List<Location> collection = new ArrayList<Location>();
		collection.addAll(listOne);
		collection.addAll(listTwo);
		collection.addAll(listThree);
		collection.addAll(listFour);
		for (Location loc : collection) {
			StoredMovement.Builder builder = new StoredMovement.Builder();
			builder.setTeleportTo(loc);
			StoredMovement movement = builder.build();
			MOVEMENTS.add(movement);
		}
		SHIP = type;
		SHOULD_REPEATE = false;
		USER = user;
	}

	public AutoPilot(LiveAutoPilotable type, double X, double Y, double Z, int speed, @Nullable OfflinePlayer user) {
		int height = 150;
		Location moveTo = type.getLocation().clone().add(X, Y, Z);

		int toX = moveTo.getBlockX();
		int toZ = moveTo.getBlockZ();

		Location from = type.getLocation();
		int fromX = from.getBlockX();
		int fromZ = from.getBlockZ();

		Location stageOne = new Location(from.getWorld(), fromX, height, fromZ);
		Location stageTwo = new Location(from.getWorld(), fromX, height, toZ);
		Location stageThree = new Location(from.getWorld(), toX, height, toZ);

		List<Location> listOne = getBetween(from, stageOne, speed);
		List<Location> listTwo = getBetween(stageOne, stageTwo, speed);
		List<Location> listThree = getBetween(stageTwo, stageThree, speed);
		List<Location> listFour = getBetween(stageThree, moveTo, speed);
		List<Location> collection = new ArrayList<Location>();
		collection.addAll(listOne);
		collection.add(stageOne);
		collection.addAll(listTwo);
		collection.add(stageTwo);
		collection.addAll(listThree);
		collection.add(stageThree);
		collection.addAll(listFour);
		System.out.println("size of movements: " + collection.size());
		for (Location loc : collection) {
			StoredMovement.Builder builder = new StoredMovement.Builder();
			builder.setTeleportTo(loc);
			StoredMovement movement = builder.build();
			MOVEMENTS.add(movement);
		}
		SHIP = type;
		SHOULD_REPEATE = false;
		USER = user;
	}

	public boolean isRunning() {
		return (PROCESSES != null);
	}

	public void stop() {
		if (PROCESSES != null) {
			for (int process : PROCESSES) {
				Bukkit.getScheduler().cancelTask(process);
			}
			PROCESSES = null;
			PROCESSED = 0;
		}

	}

	public boolean start() {
		return start(60);
	}

	public boolean start(long delay) {
		if (!isRunning()) {
			List<Integer> processes = new ArrayList<Integer>();
			for (int A = 0; A < MOVEMENTS.size(); A++) {
				final int B = A;
				Bukkit.getScheduler().scheduleSyncDelayedTask(ShipsMain.getPlugin(), new Runnable() {

					@Override
					public void run() {
						if (B == (MOVEMENTS.size() - 1)) {
							PROCESSES = null;
						}
						PROCESSED = PROCESSED + 1;
						System.out.println("Attempting to move");
						Optional<MovementResult> move = SHIP.teleport(MOVEMENTS.get(B));
						if (move.isPresent()) {
							MovementResult result = move.get();
							if (result.getFailedCause().isPresent()) {
								if (USER.isOnline()) {
									TwoStore<CauseKeys<Object>, Object> fail = result.getFailedCause().get();
									fail.getFirst().sendMessage(USER.getPlayer(), fail.getSecond());
								}
								stop();
							}
						}
					}

				}, (B * delay));
			}
			PROCESSES = processes;
			SHIP.setAutoPilotData(this);
			return true;
		}
		return false;
	}
	
	public int getMovesDone(){
		return PROCESSED;
	}

	public List<StoredMovement> getMovements() {
		return MOVEMENTS;
	}

	public LiveAutoPilotable getTargetShip() {
		return SHIP;
	}

	public Optional<OfflinePlayer> getTargetPlayer() {
		return Optional.ofNullable(USER);
	}

	public boolean isRepeating() {
		return SHOULD_REPEATE;
	}

	public Optional<MovementResult> next() {
		if (MOVEMENTS.size() != 0) {
			if (MOVEMENTS.size() < TARGET) {
				StoredMovement movement = MOVEMENTS.get(TARGET);
				Optional<MovementResult> opFail = SHIP.teleport(movement);
				TARGET++;
				return opFail;
			} else if (SHOULD_REPEATE) {
				StoredMovement movement = MOVEMENTS.get(0);
				Optional<MovementResult> opFail = SHIP.teleport(movement);
				TARGET++;
				return opFail;
			}
		}
		MovementResult fail = new MovementResult();
		fail.put(MovementResult.CauseKeys.AUTO_PILOT_OUT_OF_MOVES, this);
		return Optional.of(fail);
	}

	public OrderedMap<Integer, Block> getPath() {
		OrderedMap<Integer, Block> map = new OrderedMap<Integer, Block>();
		map.put(0, SHIP.getLocation().getBlock());
		Block previousLoc = SHIP.getLocation().getBlock();
		for (int A = 1; A < MOVEMENTS.size(); A++) {
			StoredMovement movement = MOVEMENTS.get(A - 1);
			Block ret = movement.getEndResult(previousLoc);
			map.put(A, ret);
			previousLoc = ret;
		}
		return map;
	}

	private List<Location> getBetween(Location loc1, Location loc2, int spacing) {
		System.out.println("\n X = " + loc1.getX() + " Y = " + loc1.getY() + " Z = " + loc1.getZ() + "\n X = " + loc2.getX() + " Y = " + loc2.getY() + " Z = " + loc2.getZ());
		List<Location> blocks = new ArrayList<Location>();
		World world = loc1.getWorld();
		if (loc1.getBlockX() == loc2.getBlockX()) {
			if (loc1.getBlockZ() < loc2.getBlockZ()) {
				int x = loc2.getBlockX();
				int y = loc2.getBlockY();
				int z = loc2.getBlockZ();
				int distance = (loc2.getBlockZ() - loc1.getBlockZ()) / spacing;
				for (int A = 0; A < distance; A++) {
					int Z = (z + A + spacing);
					if (Z > loc1.getBlockZ()) {
						blocks.add(new Location(world, x, y, loc1.getBlockZ()));
					} else {
						blocks.add(new Location(world, x, y, Z));
					}
				}
				return blocks;
			} else {
				int x = loc1.getBlockX();
				int y = loc1.getBlockY();
				int z = loc1.getBlockZ();
				int distance = (loc1.getBlockZ() - loc2.getBlockZ()) / spacing;
				for (int A = 0; A < distance; A++) {
					int Z = (z + A + spacing);
					if (Z > loc2.getBlockZ()) {
						blocks.add(new Location(world, x, y, loc2.getBlockZ()));
					} else {
						blocks.add(new Location(world, x, y, Z));
					}
				}
				return blocks;
			}
		} else {
			if (loc1.getBlockX() > loc2.getBlockX()) {
				int x = loc2.getBlockX();
				int y = loc2.getBlockY();
				int z = loc2.getBlockZ();
				int distance = (loc2.getBlockX() - loc1.getBlockX()) / spacing;
				for (int A = 0; A < distance; A++) {
					int X = (x + A + spacing);
					if (X > loc1.getBlockX()) {
						blocks.add(new Location(world, loc1.getBlockX(), y, z));
					} else {
						blocks.add(new Location(world, X, y, z));
					}
				}
				return blocks;
			} else {
				int x = loc1.getBlockX();
				int y = loc1.getBlockY();
				int z = loc1.getBlockZ();
				int distance = (loc1.getBlockX() - loc2.getBlockX()) / spacing;
				for (int A = 0; A < distance; A++) {
					int X = (x + A + spacing);
					if (X > loc2.getBlockX()) {
						blocks.add(new Location(world, loc2.getBlockX(), y, z));
					} else {
						blocks.add(new Location(world, X, y, z));
					}
				}
				return blocks;
			}
		}
	}

}
