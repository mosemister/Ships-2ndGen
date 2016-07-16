package MoseShipsSponge.Ships.VesselTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3i;

import MoseShips.Bypasses.FinalBypass;
import MoseShipsSponge.ShipsMain;
import MoseShipsSponge.BlockFinder.BasicBlockFinder;
import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.ShipsData;
import MoseShipsSponge.Ships.Movement.Movement;
import MoseShipsSponge.Ships.Movement.Movement.Rotate;
import MoseShipsSponge.Ships.Movement.MovingBlock;
import MoseShipsSponge.Ships.Movement.StoredMovement;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.LiveData;
import MoseShipsSponge.Signs.ShipsSigns.SignType;

public abstract class ShipType extends ShipsData {

	public abstract Optional<FailedCause> hasRequirements(List<MovingBlock> blocks, @Nullable User user);

	public abstract boolean shouldFall();

	public abstract int getMaxBlocks();

	public abstract int getMinBlocks();

	public abstract Map<Text, Object> getInfo();
	
	public abstract StaticShipType getStatic();

	static List<ShipType> SHIPS = new ArrayList<>();

	public ShipType(String name, Location<World> sign, Location<World> teleport) {
		super(name, sign, teleport);
	}
	
	public ShipType(ShipsData data){
		super(data);
	}
	
	public Optional<FailedCause> move(Vector3i moveBy, Cause cause){
		return Movement.move(this, moveBy, cause);
	}
	
	public Optional<FailedCause> move(Direction dir, int speed, Cause cause){
		Vector3i vector3i = ShipsMain.convert(dir, speed);
		return move(vector3i, cause);
	}
	
	public Optional<FailedCause> move(int X, int Y, int Z, Cause cause){
		return Movement.move(this, X, Y, Z, cause);
	}
	
	public Optional<FailedCause> rotateLeft(Cause cause){
		return Movement.rotateLeft(this, cause);
	}
	
	public Optional<FailedCause> rotateRight(Cause cause){
		return Movement.rotateRight(this, cause);
	}
	
	public Optional<FailedCause> rotate(Rotate type, Cause cause){
		return Movement.rotate(this, cause, type);
	}
	
	public Optional<FailedCause> teleport(StoredMovement move){
		return Movement.teleport(this, move);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<FailedCause> teleport(Location<? extends Extent> loc, Cause cause){
		Location<World> loc2 = null;
		if(loc.getExtent() instanceof Chunk){
			Chunk chunk = (Chunk)loc.getExtent();
			loc2 = chunk.getWorld().getLocation(loc.getBlockPosition());
		}else{
			loc2 = (Location<World>)loc;
		}
		return Movement.teleport(this, loc2, cause);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<FailedCause> teleport(Location<? extends Extent> loc, int X, int Y, int Z, Cause cause){
		Location<World> loc2 = null;
		if(loc.getExtent() instanceof Chunk){
			Chunk chunk = (Chunk)loc.getExtent();
			loc2 = chunk.getWorld().getLocation(loc.getBlockPosition());
		}else{
			loc2 = (Location<World>)loc;
		}
		return Movement.teleport(this, loc2, X, Y, Z, cause);
	}
	
	public static void inject(ShipType type){
		SHIPS.add(type);
	}

	public static Optional<ShipType> getShip(String name) {
		return SHIPS.stream().filter(s -> s.getName().equals(name)).findFirst();
	}

	public static Optional<ShipType> getShip(Text text) {
		return getShip(text.toPlain());
	}

	public static Optional<ShipType> getShip(SignType type, Sign sign, boolean refresh) {
		if (type.equals(SignType.LICENCE)) {
			Text text = sign.lines().get(2);
			return getShip(text.toPlain());
		}else{
			if(refresh){
				//List<Location<World>> structure = BasicBlockFinder.SHIPS5.getConnectedBlocks(ShipsConfig.CONFIG.get(Integer.class, ShipsConfig.STRUCTURE_STRUCTURELIMITS_TRACKLIMIT), sign.getLocation());
				System.out.println("finding ship");
				List<Location<World>> structure = BasicBlockFinder.SHIPS5.getConnectedBlocks(5000, sign.getLocation());
				System.out.println("structure size: " + structure.size());
				FinalBypass<Optional<ShipType>> shipType = new FinalBypass<>(null);
				structure.stream().forEach(l -> {
					Optional<TileEntity> opTE = l.getTileEntity();
					if(opTE.isPresent()){
						TileEntity TE = opTE.get();
						if(TE instanceof Sign){
							Sign sign2 = (Sign)TE;
							Text text = sign2.lines().get(2);
							shipType.set(getShip(text.toPlain()));
						}
					}
				});
				return shipType.get();
			}else{
				FinalBypass<ShipType> shipType = new FinalBypass<>(null);
				SHIPS.stream().forEach(s -> {
					s.getBasicStructure().stream().forEach(l -> {
						if(l.equals(sign.getLocation())){
							shipType.set(s);
						}
					});
				});
				return Optional.ofNullable(shipType.get());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Optional<ShipType> getShip(Location<? extends Extent> loc) {
		if (loc.getExtent() instanceof Chunk) {
			Chunk chunk = (Chunk) loc.getExtent();
			loc = chunk.getWorld().getLocation(loc.getBlockPosition());
		}
		final Location<World> loc2 = (Location<World>) loc;
		return SHIPS.stream().filter(s -> s.getBasicStructure().stream().anyMatch(b -> (b.getBlockPosition().equals(loc2.getBlockPosition())) && (b.getExtent().equals(loc2.getExtent())))).findAny();
	}

	public static List<ShipType> getShips() {
		return SHIPS;
	}

	@SuppressWarnings("unchecked")
	public static <T extends ShipType> List<T> getShips(Class<T> type) {
		return (List<T>) SHIPS.stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}

	public static List<ShipType> getShipsByRequirements(Class<? extends LiveData> type) {
		return SHIPS.stream().filter(s -> type.isInstance(s)).collect(Collectors.toList());
	}

}
