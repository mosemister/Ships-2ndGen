package MoseShipsSponge.Ships.VesselTypes.DefaultTypes;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import MoseShipsSponge.Causes.FailedCause;
import MoseShipsSponge.Ships.Movement.MovingBlock;
import MoseShipsSponge.Ships.VesselTypes.ShipType;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.Live.LiveFuel;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.Live.LiveRequiredBlock;
import MoseShipsSponge.Ships.VesselTypes.DataTypes.Live.LiveRequiredPercent;

public class Airship extends ShipType implements LiveFuel, LiveRequiredPercent, LiveRequiredBlock{

	BlockType[] REQUIRED_BLOCK = {BlockTypes.FIRE};
	BlockType[] REQUIRED_BLOCKS = {BlockTypes.WOOL};
	
	
	public Airship(String name, User host, Location<World> sign, Location<World> teleport) {
		super(name, host, sign, teleport);
	}


	@Override
	public String[] getRequiredBlocks() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getRequiredPercent() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getRequirement() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String[] getPercentBlocks() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getFuel() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String[] getFuelMaterials() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean removeFuel(int Amount) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Optional<FailedCause> hasRequirements(List<MovingBlock> blocks, User user) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean shouldFall() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int getMaxBlocks() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getMinBlocks() {
		// TODO Auto-generated method stub
		return 0;
	}

}