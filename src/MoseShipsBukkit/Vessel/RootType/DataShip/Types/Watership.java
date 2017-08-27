package MoseShipsBukkit.Vessel.RootType.DataShip.Types;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import MoseShipsBukkit.Utils.StaticShipTypeUtil;
import MoseShipsBukkit.Vessel.Common.RootTypes.ShipsData;
import MoseShipsBukkit.Vessel.Common.ShipCommands.ShipCommands;
import MoseShipsBukkit.Vessel.Common.Static.StaticShipType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.RequirementData;
import MoseShipsBukkit.Vessel.RootType.DataShip.Data.Required.PercentageRequirement;
import MoseShipsBukkit.Vessel.RootType.DataShip.General.AbstractWaterType;
import MoseShipsBukkit.Vessel.RootType.DataShip.Types.Static.StaticWatership;

public class Watership extends AbstractWaterType{

	public Watership(String name, Block sign, Location teleport) {
		super(name, sign, teleport);
		init();
	}
	
	public Watership(ShipsData data) {
		super(data);
		init();
	}
	
	public void init() {
		getCommands().add(ShipCommands.LOCK_ALTITUDE);
		addData(new PercentageRequirement());
	}
	
	public PercentageRequirement getPercentData() {
		for (RequirementData data : getRequirementData()){
			if(data instanceof PercentageRequirement){
				return (PercentageRequirement)data;
			}
		}
		return null;
	}

	@Override
	public StaticShipType getStatic() {
		return StaticShipTypeUtil.getType(StaticWatership.class).get();
	}

	@Override
	public void onRemove(Player player) {
	}

}
