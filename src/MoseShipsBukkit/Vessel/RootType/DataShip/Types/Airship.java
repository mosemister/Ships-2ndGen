package MoseShipsBukkit.Vessel.RootType.DataShip.Types;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Movement.Result.MovementResult;
import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Utils.SOptional;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.Implementations.AutoPilotableShip;
import MoseShipsBukkit.Vessel.Common.RootTypes.Implementations.FallableShip;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.BlockRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.FuelRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.PercentageRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.General.AbstractAirType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticAirship;

public class Airship extends AbstractAirType implements AutoPilotableShip, FallableShip {

	AutoPilot g_auto_pilot;
	
	public Airship(ShipsData data) {
		super(data);
		addData(new PercentageRequirement());
		addData(new BlockRequirement());
		addData(new FuelRequirement());
	}
	
	public Airship(String name, Block lic, Location teleport){
		super(name, lic, teleport);
		addData(new PercentageRequirement());
		addData(new BlockRequirement());
		addData(new FuelRequirement());
	}
	
	public PercentageRequirement getPercentData(){
		for (RequirementData data : getRequirementData()){
			if(data instanceof PercentageRequirement){
				return (PercentageRequirement)data;
			}
		}
		return null;
	}
	
	public FuelRequirement getFuelData(){
		for (RequirementData data : getRequirementData()){
			if(data instanceof FuelRequirement){
				return (FuelRequirement)data;
			}
		}
		return null;
	}
	
	public BlockRequirement getBlockData(){
		for (RequirementData data : getRequirementData()){
			if(data instanceof BlockRequirement){
				return (BlockRequirement)data;
			}
		}
		return null;
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticAirship.class).get();
	}

	@Override
	public void onRemove(Player player) {
	}

	@Override
	public boolean shouldFall() {
		SOptional<FailedMovement> requirement = hasRequirements(createUnofficalMovingBlocks(BlockFace.DOWN, 2));
		if(requirement.isPresent()){
			FailedMovement failed = requirement.get();
			if (failed.getResult().equals(MovementResult.OUT_OF_FUEL)){
				return true;
			}
		}
		return false;
	}

	@Override
	public SOptional<AutoPilot> getAutoPilotData() {
		return new SOptional<AutoPilot>(g_auto_pilot);
	}

	@Override
	public AutoPilotableShip setAutoPilotData(AutoPilot auto) {
		g_auto_pilot = auto;
		return this;
	}
}
