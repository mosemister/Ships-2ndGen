package MoseShipsSponge.Vessel.RootTypes.DataShip.Data;

import java.util.Map;
import java.util.Optional;

import MoseShips.CustomDataHolder.DataHandler;
import MoseShipsSponge.Configs.BasicConfig;
import MoseShipsSponge.Movement.Result.FailedMovement;
import MoseShipsSponge.Utils.Lists.MovingBlockList;
import MoseShipsSponge.Vessel.Common.RootTypes.LiveShip;

public interface RequirementData extends DataHandler {

	public Optional<FailedMovement> hasRequirement(LiveShip ship, MovingBlockList blocks);

	public void applyFromShip(BasicConfig config);

	public void saveShip(BasicConfig config);

	public Map<String, Object> getInfo(LiveShip ship);

}
