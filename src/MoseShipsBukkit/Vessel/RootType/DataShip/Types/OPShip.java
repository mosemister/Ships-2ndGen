package MoseShipsBukkit.Vessel.RootType.DataShip.Types;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Movement.StoredMovement.AutoPilot;
import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.RootTypes.Implementations.AutoPilotableShip;
import MoseShipsBukkit.Vessel.RootType.DataShip.General.AbstractAirType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticOPShip;

public class OPShip extends AbstractAirType implements AutoPilotableShip {

	AutoPilot g_auto_pilot;
	
	public OPShip(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
	}
	
	public OPShip(ShipsData data){
		super(data);
	}

	@Override
	public StaticOPShip getStatic() {
		return StaticShipTypeUtil.getType(StaticOPShip.class).get();
	}

	@Override
	public Optional<AutoPilot> getAutoPilotData() {
		return Optional.ofNullable(g_auto_pilot);
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
