package MoseShipsBukkit.Vessel.RootType.DataShip.Data;

import java.util.Map;
import java.util.Optional;

import MoseShips.CustomDataHolder.DataHandler;
import MoseShipsBukkit.Configs.BasicConfig;
import MoseShipsBukkit.Movement.Result.FailedMovement;
import MoseShipsBukkit.Utils.Lists.MovingBlockList;
import MoseShipsBukkit.Vessel.Common.RootTypes.LiveShip;

public interface RequirementData extends DataHandler {

	public Optional<FailedMovement> hasRequirements(LiveShip ship, MovingBlockList blocks);
	public void applyFromShip(BasicConfig config);
	public void saveShip(BasicConfig config);
	public Map<String, Object> getInfo(LiveShip ship);

}
