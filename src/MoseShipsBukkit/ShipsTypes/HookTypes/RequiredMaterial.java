package MoseShipsBukkit.ShipsTypes.HookTypes;

import java.util.List;

import org.bukkit.Material;

public interface RequiredMaterial {

	/**
	 * gets the requiredBlock
	 */

	public List<Material> getRequiredMaterial();

	/**
	 * gets the required percent needed
	 */
	public int getRequiredPercent();

}
