package MoseShipsSponge.Vessel.RootTypes.DataShip.Types;

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Movement.StoredMovement.AutoPilot;
import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.Common.RootTypes.Implementations.AutoPilotableShip;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.Required.BlockRequirement;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Data.Required.FuelRequirement;
import MoseShipsSponge.Vessel.RootTypes.DataShip.General.AbstractAirType;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticAirship;

public class Airship extends AbstractAirType implements AutoPilotableShip {

	AutoPilot g_auto_pilot;
	
	public Airship(String name, Location<World> sign, Vector3i tel){
		super(name, sign, tel);
		init();
	}
	
	public Airship(ShipsData data) {
		super(data);
		init();
	}
	
	private void init(){
		addData(new BlockRequirement());
		addData(new FuelRequirement());
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticAirship.class).get();
	}

	@Override
	public Optional<AutoPilot> getAutoPilotData() {
		return Optional.of(g_auto_pilot);
	}

	@Override
	public AutoPilotableShip setAutoPilotData(AutoPilot auto) {
		g_auto_pilot = auto;
		return this;
	}

	@Override
	public void onRemove(Player player) {
	}

}
