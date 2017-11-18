package MoseShipsSponge.Vessel.RootTypes.DataShip.Types;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import MoseShipsSponge.Utils.StaticShipTypeUtil;
import MoseShipsSponge.Vessel.Common.RootTypes.ShipsData;
import MoseShipsSponge.Vessel.Common.Static.StaticShipType;
import MoseShipsSponge.Vessel.RootTypes.DataShip.General.AbstractWaterType;
import MoseShipsSponge.Vessel.RootTypes.DataShip.Types.Static.StaticAirship;

public class WaterShip extends AbstractWaterType {

	public WaterShip(String name, Location<World> sign, Vector3i teleport) {
		super(name, sign, teleport);
	}

	public WaterShip(ShipsData data) {
		super(data);
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticAirship.class).get();
	}

	@Override
	public void onRemove(Player player) {
	}
	
}
