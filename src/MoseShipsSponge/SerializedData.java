package MoseShipsSponge;

import java.util.Map;

import MoseShips.CustomDataHolder.DataHandler;

public interface SerializedData extends DataHandler {

	public Map<String, Object> getSerializedData();

}
