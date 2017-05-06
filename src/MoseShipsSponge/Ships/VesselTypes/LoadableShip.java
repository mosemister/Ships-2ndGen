package MoseShipsSponge.Ships.VesselTypes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import MoseShips.Bypasses.FinalBypass;
import MoseShipsSponge.Algorthum.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Causes.MovementResult;
import MoseShipsSponge.Movement.MovingBlock;
import MoseShipsSponge.Movement.Type.RotateType;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipLoader;
import MoseShipsSponge.Ships.VesselTypes.Loading.ShipsLocalDatabase;
import MoseShipsSponge.Ships.VesselTypes.Running.ShipsTaskRunner;
import MoseShipsSponge.Signs.ShipsSigns.SignType;

public abstract class LoadableShip extends ShipsData implements LiveData {

	public abstract Optional<MovementResult> hasRequirements(List<MovingBlock> blocks, Cause cause);

	public abstract Map<Text, Object> getInfo();
	
	public abstract void onSave(ShipsLocalDatabase database);
	
	public abstract void onRemove(@Nullable Player player);
	
	public abstract StaticShipType getStatic();
	
	protected boolean g_moving = false;
	protected int g_max_blocks = 4000;
	protected int g_min_blocks = 200;
	protected boolean g_remove = false;
	ShipsTaskRunner g_task_runner = new ShipsTaskRunner(this);

	static List<LoadableShip> gs_ships = new ArrayList<>();

	public LoadableShip(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}

	public LoadableShip(ShipsData data) {
		super(data);
	}
	
	@Override
	public ShipsTaskRunner getTaskRunner(){
		return g_task_runner;
	}
	
	public Optional<MovementResult> rotate(RotateType type, Cause cause){
		switch(type){
			case LEFT: return rotateLeft(cause);
			case RIGHT: return rotateRight(cause);
		}
		return null;
	}
	
	@Override
	public boolean willRemoveNextCycle(){
		return g_remove;
	}
	
	@Override
	public LoadableShip setRemoveNextCycle(boolean remove){
		g_remove = remove;
		return this;
	}
	
	@Override
	public boolean isLoaded(){
		return gs_ships.stream().anyMatch(ship -> ship.getName().equals(getName()));
	}
	
	@Override
	public LoadableShip load(){
		if(isLoaded()){
			return this;
		}
		gs_ships.add(this);
		return this;
	}
	
	@Override
	public LoadableShip unload(){
		gs_ships.remove(this);
		g_task_runner.pauseScheduler();
		return this;
	}
	
	@Override
	public int getMaxBlocks(){
		return g_max_blocks;
	}
	
	@Override
	public int getMinBlocks(){
		return g_min_blocks;
	}
	
	@Override
	public LoadableShip setMaxBlocks(int A){
		g_max_blocks = A;
		return this;
	}
	
	@Override
	public LoadableShip setMinBlocks(int A){
		g_min_blocks = A;
		return this;
	}
	
	@Override
	public boolean isMoving(){
		return g_moving;
	}
	
	@Override
	public void remove(){
		remove(null);
	}
	
	@Override
	public void remove(@Nullable Player player){
		gs_ships.remove(this);
		onRemove(player);
		getLocalDatabase().getFile().delete();
	}
	
	@Override
	public ShipsLocalDatabase getLocalDatabase(){
		return new ShipsLocalDatabase(this);
	}
	
	@Override
	public ShipsData cloneOnto(ShipsData data){
		super.cloneOnto(data);
		if (data instanceof LoadableShip) {
			LoadableShip ship = (LoadableShip) data;
			ship.g_moving = this.g_moving;
			ship.g_max_blocks = this.g_max_blocks;
			ship.g_min_blocks = this.g_min_blocks;
			ship.g_remove = this.g_remove;
			return ship;
		}
		return data;
	}
	
	public void setMoving(boolean check){
		g_moving = check;
	}

	@Override
	public List<Location<World>> setBasicStructure(List<Location<World>> locs, Location<World> licence) {
		List<Location<World>> structure = super.setBasicStructure(locs, licence);
		getLocalDatabase().saveBasicShip(this);
		return structure;
	}

	@Override
	public LoadableShip setTeleportToLocation(Location<World> loc) {
		super.setTeleportToLocation(loc);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	@Override
	public LoadableShip setOwner(User user) {
		super.setOwner(user);
		getLocalDatabase().saveBasicShip(this);
		return this;
	}

	public static Optional<LoadableShip> getShip(String name) {
		return gs_ships.stream().filter(s -> s.getName().equals(name)).findFirst();
	}

	public static Optional<LoadableShip> getShip(Text text) {
		return getShip(text.toPlain());
	}

	public static Optional<LoadableShip> getShip(SignType type, Sign sign, boolean refresh) {
		if (type.equals(SignType.LICENCE)) {
			Text text = sign.lines().get(2);
			return getShip(text.toPlain());
		} else {
			if (refresh) {
				// List<Location<World>> structure =
				// BasicBlockFinder.SHIPS5.getConnectedBlocks(ShipsConfig.CONFIG.get(Integer.class,
				// ShipsConfig.STRUCTURE_STRUCTURELIMITS_TRACKLIMIT),
				// sign.getLocation());
				System.out.println("finding ship");
				List<Location<World>> structure = BasicBlockFinder.SHIPS5.getConnectedBlocks(5000, sign.getLocation());
				System.out.println("structure size: " + structure.size());
				FinalBypass<Optional<LoadableShip>> shipType = new FinalBypass<>(null);
				structure.stream().forEach(l -> {
					Optional<TileEntity> opTE = l.getTileEntity();
					if (opTE.isPresent()) {
						TileEntity TE = opTE.get();
						if (TE instanceof Sign) {
							Sign sign2 = (Sign) TE;
							Text text = sign2.lines().get(2);
							Optional<LoadableShip> type2 = getShip(text.toPlain());
							if (type2.isPresent()) {
								type2.get().setBasicStructure(structure, l);
							}
							shipType.set(type2);
						}
					}
				});
				return shipType.get();
			} else {
				FinalBypass<LoadableShip> shipType = new FinalBypass<>(null);
				gs_ships.stream().forEach(s -> {
					s.getBasicStructure().stream().forEach(l -> {
						if (l.equals(sign.getLocation())) {
							shipType.set(s);
						}
					});
				});
				return Optional.ofNullable(shipType.get());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Optional<LoadableShip> getShip(Location<? extends Extent> loc, boolean updateStructure) {
		if (loc.getExtent() instanceof Chunk) {
			Chunk chunk = (Chunk) loc.getExtent();
			loc = chunk.getWorld().getLocation(loc.getBlockPosition());
		}
		final Location<World> loc2 = (Location<World>) loc;
		return gs_ships.stream().filter(s -> {
			// CHECK THOUGH ALL SHIPS
			if (updateStructure) {
				// UPDATE THE STRUCTURE IF SPECIFIED
				s.updateBasicStructure();
			}
			return s.hasLocation(loc2);
		}).findFirst();
	}

	public static List<LoadableShip> getReasentlyUsedShips() {
		return gs_ships;
	}

	public static List<LoadableShip> getShips() {
		List<LoadableShip> ships = new ArrayList<>();
		ships.addAll(gs_ships);
		StaticShipType.TYPES.stream().forEach(t -> {
			File[] files = new File("config/Ships/VesselData/" + t.getName()).listFiles();
			if (files != null) {
				for (File file : files) {
					String name = file.getName().replace(".conf", "");
					if (!ships.stream().anyMatch(s -> s.getName().equals(name))) {
						Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
						if (opShip.isPresent()) {
							ships.add(opShip.get());
						}
					}
				}
			}
		});
		return ships;
	}

	public static <T extends StaticShipType> List<LoadableShip> getShips(StaticShipType type) {
		List<LoadableShip> ships = new ArrayList<>();
		gs_ships.stream().forEach(s -> {
			if (type.equals(s.getStatic())) {
				ships.add(s);
			}
		});
		File[] files = new File("config/Ships/VesselData/" + type.getName()).listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName().replace(".conf", "");
				if (!ships.stream().anyMatch(s -> s.getName().equals(name))) {
					Optional<LoadableShip> opShip = ShipLoader.loadShip(file);
					if (opShip.isPresent()) {
						ships.add(opShip.get());
					}
				}
			}
		}
		return ships;
	}

	public static List<LoadableShip> getShipsByRequirements(Class<? extends LiveData> type) {
		return getShips().stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}

}
