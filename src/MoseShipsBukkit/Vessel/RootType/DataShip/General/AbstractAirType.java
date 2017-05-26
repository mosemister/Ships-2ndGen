package MoseShipsBukkit.Vessel.RootType.DataShip.General;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import MoseShipsBukkit.Events.ShipsCause;
import MoseShipsBukkit.Movement.Movement;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.StoredMovement.StoredMovement;
import MoseShipsBukkit.ShipBlock.BlockState;
import MoseShipsBukkit.Vessel.Common.GeneralTypes.AirType;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.RootType.DataShip.DataVessel;

public abstract class AbstractAirType extends DataVessel implements AirType {

	public AbstractAirType(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}

	public AbstractAirType(ShipsData data) {
		super(data);
	}

	@Override
	public Optional<FailedMovement> move(int X, int Y, int Z, ShipsCause cause) {
		return Movement.move(cause, this, X, Y, Z, new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> move(BlockFace dir, int speed, ShipsCause cause) {
		Block block = new Location(getWorld(), 0, 0, 0).getBlock().getRelative(dir, speed);
		return Movement.move(cause, this, block.getX(), block.getY(), block.getZ(), new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> rotateLeft(ShipsCause cause) {
		return Movement.rotateLeft(cause, this, new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> rotateRight(ShipsCause cause) {
		return Movement.rotateRight(cause, this, new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> teleport(StoredMovement move, ShipsCause cause) {
		return Movement.teleport(cause, this, move, new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> teleport(Location loc, ShipsCause cause) {
		return Movement.teleport(cause, this, loc, new BlockState(Material.AIR));
	}

	@Override
	public Optional<FailedMovement> teleport(Location loc, int X, int Y, int Z, ShipsCause cause) {
		return Movement.teleport(cause, this,
				new StoredMovement.Builder().setTeleportTo(loc).setX(X).setY(Y).setZ(Z).build(),
				new BlockState(Material.AIR));
	}

}
