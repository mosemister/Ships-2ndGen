package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes.MainTypes;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Causes.ShipsCause;
import MoseShipsBukkit.Causes.Failed.MovementResult;
import MoseShipsBukkit.Ships.AbstractShipsData;
import MoseShipsBukkit.Ships.Movement.Movement;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Utils.State.BlockState;

public abstract class AbstractWaterType extends LoadableShip implements WaterType {

	public AbstractWaterType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public AbstractWaterType(AbstractShipsData data) {
		super(data);
	}
	
	@Override
	public Optional<MovementResult> move(int X, int Y, int Z, ShipsCause cause) {
		return Movement.move(cause, this, X, Y, Z, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> move(BlockFace dir, int speed, ShipsCause cause) {
		Block block = new Location(getWorld(), 0, 0, 0).getBlock().getRelative(dir, speed);
		return Movement.move(cause, this, block.getX(), block.getY(), block.getZ(), new BlockState(Material.AIR));
	}

	@Override
	public Optional<MovementResult> rotateLeft(ShipsCause cause) {
		return Movement.rotateLeft(cause, this, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> rotateRight(ShipsCause cause) {
		return Movement.rotateRight(cause, this, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(StoredMovement move, ShipsCause cause) {
		return Movement.teleport(cause, this, move, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(Location loc, ShipsCause cause) {
		return Movement.teleport(cause, this, loc, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z, ShipsCause cause) {
		return Movement.teleport(cause, this, loc, X, Y, Z, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public int getWaterLevel() {
		BlockFace[] faces = {
			BlockFace.EAST,
			BlockFace.NORTH,
			BlockFace.SOUTH,
			BlockFace.WEST
		};
		int height = -1;
		for (Block block : getBasicStructure()) {
			if (block.getY() > height) {
				for (BlockFace face : faces) {
					Block block2 = block.getRelative(face);
					if (block2.getType().equals(Material.STATIONARY_WATER) || (block2.getType().equals(Material.WATER))) {
						height = block2.getY();
						break;
					}
				}
			}
		}
		return height;
	}

}
