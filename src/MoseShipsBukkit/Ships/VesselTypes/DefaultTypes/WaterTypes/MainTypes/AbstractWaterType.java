package MoseShipsBukkit.Ships.VesselTypes.DefaultTypes.WaterTypes.MainTypes;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Causes.MovementResult;
import MoseShipsBukkit.Ships.ShipsData;
import MoseShipsBukkit.Ships.Movement.Movement;
import MoseShipsBukkit.Ships.Movement.StoredMovement;
import MoseShipsBukkit.Ships.VesselTypes.LoadableShip;
import MoseShipsBukkit.Utils.State.BlockState;

public abstract class AbstractWaterType extends LoadableShip implements WaterType {

	public AbstractWaterType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public AbstractWaterType(ShipsData data) {
		super(data);
	}
	
	@Override
	public Optional<MovementResult> move(int X, int Y, int Z) {
		return Movement.move(this, X, Y, Z, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> move(BlockFace dir, int speed) {
		Block block = new Location(getWorld(), 0, 0, 0).getBlock().getRelative(dir, speed);
		return Movement.move(this, block.getX(), block.getY(), block.getZ(), new BlockState(Material.AIR));
	}

	@Override
	public Optional<MovementResult> rotateLeft() {
		return Movement.rotateLeft(this, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> rotateRight() {
		return Movement.rotateRight(this, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(StoredMovement move) {
		return Movement.teleport(this, move, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(Location loc) {
		return Movement.teleport(this, loc, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
	}

	@Override
	public Optional<MovementResult> teleport(Location loc, int X, int Y, int Z) {
		return Movement.teleport(this, loc, X, Y, Z, new BlockState(Material.AIR), new BlockState(Material.WATER), new BlockState(Material.STATIONARY_WATER));
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
